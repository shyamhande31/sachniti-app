package com.noticegenerator.entity;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;

    private final String email;

    public JwtResponse(String jwttoken,String email) {
        this.jwttoken = jwttoken;
        this.email=email;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public String getEmail(){
        return this.email;
    }
}
