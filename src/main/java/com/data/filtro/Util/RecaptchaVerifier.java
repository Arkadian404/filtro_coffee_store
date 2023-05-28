package com.data.filtro.Util;

import com.data.filtro.model.custom.RecaptchaResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RecaptchaVerifier {
    private final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
//    private final String SECRET_KEY = "6LfiwUMmAAAAABFYAblbj4CWFGRohSDhybL0VQ2U";

    private final String SECRET_KEY = "6LeTbEkmAAAAAELPDS_Gdvb-wlu2t2FLzMt9Wznw";

    public boolean verify(String response) {
        RestTemplate restTemplate = new RestTemplate();
        String url = RECAPTCHA_VERIFY_URL;
        String params = String.format("?secret=%s&response=%s", SECRET_KEY, response);
        String complete = url + params;
        RecaptchaResponse recaptchaResponse = restTemplate.postForObject(complete, null, RecaptchaResponse.class);
        System.out.println(recaptchaResponse.getHostname());
        System.out.println(recaptchaResponse.getChallenge_ts());
        System.out.println(recaptchaResponse.isSuccess() ? "OK" : "NO");
        return recaptchaResponse.isSuccess();
    }
}
