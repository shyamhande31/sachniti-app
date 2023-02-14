package com.noticegenerator.entity;

import lombok.Getter;
import lombok.Setter;

public class UserInfo {
    @Getter @Setter
    private Integer userId;
    @Getter @Setter
    private String userName;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private Integer contactNumber;
    @Getter @Setter
    private String role;
    @Getter @Setter
    private String password;
}
