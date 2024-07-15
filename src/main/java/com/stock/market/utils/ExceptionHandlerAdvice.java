package com.stock.market.utils;

import com.stock.market.utils.exceptions.ApplicationException;
import com.stock.market.utils.exceptions.BusinessException;
import com.stock.market.utils.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ApplicationException.class)
    public Result handledApplicationException(ApplicationException ex) {
        return new Result(false, StatusCode.APPLICATION_EXCEPTION, ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public Result handledNotFoundException(NotFoundException ex) {
        return new Result(false, StatusCode.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result businessException(BusinessException ex) {
        return new Result(false, StatusCode.BUSINESS_EXCEPTION, ex.getMessage());
    }
}
