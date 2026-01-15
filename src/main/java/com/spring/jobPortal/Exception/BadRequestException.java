package com.spring.jobPortal.Exception;

public class BadRequestException extends RuntimeException
{
    public BadRequestException(String s)
    {
        super(s);
    }
}
