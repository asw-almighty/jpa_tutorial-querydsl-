package com.example.jpabook_practice1.exception;

import lombok.AllArgsConstructor;

public class NotEnoughStockException extends RuntimeException
{
	public NotEnoughStockException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public NotEnoughStockException(Throwable cause)
	{
		super(cause);
	}

	public NotEnoughStockException(String message)
	{
		super(message);
	}

	public NotEnoughStockException()
	{
	}
}
