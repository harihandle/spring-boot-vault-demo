package com.harihandle.java.spring.springbootvaultdemo.model;

import lombok.Data;

import java.util.Map;

@Data
public class MyCredentials {

    private String username;
    private String password;
    private Map others;
}
