package com.PayPalService;

import com.PayPalService.FeignClients.BankCardFeignClient;
import com.PayPalService.Model.PayPalPayment;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayPalService {

    @Autowired
    BankCardFeignClient bankCardFeignClient;

    @Autowired
    PayPalRepository payPalRepository;

    private APIContext apiContext;
/*
    public PayPalService() throws PayPalRESTException {
       // apiContext= apiContext();
    }*/

    public String getBankCard() {
        return bankCardFeignClient.getByName(" path variable");
    }

    public Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);


        String requestId = Long.toString(System.nanoTime());
        apiContext = new APIContext(oAuthTokenCredential().getAccessToken(),requestId );
        apiContext.setConfigurationMap(paypalSdkConfig());

        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        System.out.println(apiContext.getRequestId());
        return payment.execute(apiContext, paymentExecute);
    }



    public void savePayment(Payment payment) {
        PayPalPayment payPalPayment = new PayPalPayment();

        payPalPayment.setPaymentId(payment.getId());
        payPalPayment.setPayerId(payment.getPayer().getPayerInfo().getPayerId());
        payPalPayment.setTotal(payment.getTransactions().get(0).getAmount().getTotal());
        payPalPayment.setCurrency(payment.getTransactions().get(0).getAmount().getCurrency());
        payPalPayment.setCreateTime(payment.getCreateTime());

        payPalRepository.save(payPalPayment);
    }

    @Bean
    public Map<String, String> paypalSdkConfig() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", "sandbox");
        return configMap;
    }

    @Bean
    public OAuthTokenCredential oAuthTokenCredential() {
        return new OAuthTokenCredential("AV53WcoFcJBV8FmGKp_qz7ZFUk8nAoLODD5D5A-OsHfVsQVSB4WIxPm2JH63jUDkn-HGvd2HdqmTz6Sf",
                "EJgHCJKK3314ZbozPXB7p6okMQy8ohBMtA4cYrr2qsWnJbs9twn7c_Z1JmJhf1KXO1tZrV4mgxsVD2G8",
                paypalSdkConfig());
    }
/*
    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        String requestId = Long.toString(System.nanoTime());
        APIContext context = new APIContext(oAuthTokenCredential().getAccessToken(),requestId );
        context.setConfigurationMap(paypalSdkConfig());
        return context;
    }*/
}
