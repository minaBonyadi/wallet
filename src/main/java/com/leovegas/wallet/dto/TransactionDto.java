package com.leovegas.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

     Long id;

     TransactionType type;

     BigDecimal amount;

     PlayerDto playerDto;
}
