package com.data.filtro.model.custom;

import lombok.Data;

@Data
public class RecaptchaResponse {
    private boolean success;
    private String challenge_ts;
    private String hostname;
}
