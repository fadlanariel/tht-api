package com.fadlan.tht.dto.response;

import java.util.List;

import com.fadlan.tht.dto.TransactionDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryResponse {

    private int offset;
    private int limit;
    private List<TransactionDto> records;
}
