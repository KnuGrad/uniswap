package com.knu.uniswap.member.service;

import com.knu.uniswap.common.exception.ValidationException;
import com.knu.uniswap.member.dto.UnivEmailAuthCodeRequest;
import com.knu.uniswap.member.dto.UnivEmailCertificationRequest;
import com.univcert.api.UnivCert;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UnivEmailService {

    @Value("${univCert.apiKey}")
    private String apiKey;

    public Map<String, Object> sendCode(UnivEmailAuthCodeRequest request) {
        try {
            return UnivCert.certify(apiKey, request.getEmail(), request.getUnivName(), true);
        } catch (IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    public Map<String, Object> certifyCode(UnivEmailCertificationRequest request) {
        try {
            return UnivCert.certifyCode(apiKey, request.getEmail(), request.getUnivName(), request.getCode());
        } catch (IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }

}
