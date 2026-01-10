package com.fadlan.tht.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse {
    private String invoiceNumber;
    private String serviceCode;
    private String serviceName;
    private String transactionType;
    private Long totalAmount;
    private LocalDateTime createdOn;
}