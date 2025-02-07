package br.com.joaopedro.objective.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        super("Saldo insuficiente para realizar a transação");
    }
}
