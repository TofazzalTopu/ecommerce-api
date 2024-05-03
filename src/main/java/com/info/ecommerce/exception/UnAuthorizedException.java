package com.info.ecommerce.exception;

import org.hibernate.service.spi.ServiceException;

public class UnAuthorizedException extends ServiceException {
    public UnAuthorizedException(String message) {
        super(message);
    }
}
