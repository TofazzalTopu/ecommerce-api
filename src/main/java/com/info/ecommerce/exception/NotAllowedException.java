package com.info.ecommerce.exception;

import org.hibernate.service.spi.ServiceException;

public class NotAllowedException extends ServiceException {
    public NotAllowedException(String message) {
        super(message);
    }
}
