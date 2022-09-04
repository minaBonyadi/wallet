package com.leovegas.wallet.exception;

public class TransactionRunningException extends RuntimeException{
	public TransactionRunningException(String message) {
		super(message);
	}
}
