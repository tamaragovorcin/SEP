package com.BankCardService.Model;


public class CheckoutForm {

    private Integer amount;
    private String featureRequest;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getFeatureRequest() {
        return featureRequest;
    }

    public void setFeatureRequest(String featureRequest) {
        this.featureRequest = featureRequest;
    }

    public CheckoutForm() {
    }
}
