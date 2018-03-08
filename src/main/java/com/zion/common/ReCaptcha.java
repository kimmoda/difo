package com.zion.common;

public class ReCaptcha {
    private String siteSecret;
    private String secretParam;
    private String responseParam;
    private String recaptchaResponse;
    private String siteVerifyUrl;

    public String getSiteSecret() {
        return siteSecret;
    }

    public void setSiteSecret(String siteSecret) {
        this.siteSecret = siteSecret;
    }

    public String getSecretParam() {
        return secretParam;
    }

    public void setSecretParam(String secretParam) {
        this.secretParam = secretParam;
    }

    public String getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(String responseParam) {
        this.responseParam = responseParam;
    }

    public String getRecaptchaResponse() {
        return recaptchaResponse;
    }

    public void setRecaptchaResponse(String recaptchaResponse) {
        this.recaptchaResponse = recaptchaResponse;
    }

    public String getSiteVerifyUrl() {
        return siteVerifyUrl;
    }

    public void setSiteVerifyUrl(String siteVerifyUrl) {
        this.siteVerifyUrl = siteVerifyUrl;
    }
}
