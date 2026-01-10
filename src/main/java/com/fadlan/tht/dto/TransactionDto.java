package com.fadlan.tht.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {
    private String invoiceNumber;
    private String transactionType;
    private String description;
    private Long totalAmount;
    private LocalDateTime createdOn;

    public TransactionDto() {
    }

    public TransactionDto(String invoiceNumber, String transactionType, String description, Long totalAmount,
            LocalDateTime createdOn) {
        this.invoiceNumber = invoiceNumber;
        this.transactionType = transactionType;
        this.description = description;
        this.totalAmount = totalAmount;
        this.createdOn = createdOn;
    }
}
