package com.noticegenerator.entity;

import lombok.Getter;
import lombok.Setter;

public class GenerateNoticesDetails {
    @Getter @Setter
    private String referenceNumber;
    @Getter @Setter
    private String customerName;
    @Getter @Setter
    private String loanNumber;
}
