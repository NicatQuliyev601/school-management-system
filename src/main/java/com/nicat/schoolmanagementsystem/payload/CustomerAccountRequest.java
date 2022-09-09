package com.nicat.schoolmanagementsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAccountRequest {
    private Long id;
    private String accountNumber;
    private String currency;
    private Double balance;
}
