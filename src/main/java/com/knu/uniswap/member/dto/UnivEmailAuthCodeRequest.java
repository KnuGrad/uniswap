package com.knu.uniswap.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UnivEmailAuthCodeRequest {

    @Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+[.][a-zA-Z]{2,3}$", message="이메일 주소 양식을 확인해 주세요")
    @NotBlank(message = "이메일을 입력해 주세요.")
    private String email;

    @Size(min = 1, max = 20)
    @NotBlank(message = "대학교명을 입력해 주세요.")
    private String univName;

}
