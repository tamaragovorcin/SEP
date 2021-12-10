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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class PayPalService {

    @Autowired
    BankCardFeignClient bankCardFeignClient;

    @Autowired
    PayPalRepository payPalRepository;

    private APIContext apiContext;

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
            String successUrl,
            String clientId,
            String clientSecret) throws PayPalRESTException {
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
        apiContext = new APIContext(oAuthTokenCredential(clientId,clientSecret).getAccessToken(),requestId );
        apiContext.setConfigurationMap(paypalSdkConfig());

        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
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

    @Scope("prototype")
    @Bean
    public OAuthTokenCredential oAuthTokenCredential(String clientId, String clientSecret) {
        return new OAuthTokenCredential(
                clientId,
                clientSecret,
                paypalSdkConfig());
    }

    public static void browse(String url) {
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
