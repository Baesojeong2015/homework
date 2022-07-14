package com.sparta.week033.dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class SignupRequestDto {
    private String username;
    private String password;
    private String passwordCheck;
}