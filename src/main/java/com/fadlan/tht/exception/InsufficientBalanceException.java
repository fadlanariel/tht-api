package com.fadlan.tht.exception;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException() {
        super("Saldo tidak mencukupi untuk melakukan transaksi");
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }

}
