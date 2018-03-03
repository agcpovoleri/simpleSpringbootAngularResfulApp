package com.example.forumapp.config.exception;

import com.example.forumapp.dto.GenericErrorResponse;
import com.example.forumapp.exception.BusinessValidationException;
import com.example.forumapp.exception.UserNotLoggedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
public class GlobalExceptionMapper {

    private final MessageSource messages;

    @Autowired
    public GlobalExceptionMapper(MessageSource messages) {
        this.messages = messages;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();

        Stream<ObjectError> errors = bindingResult.getAllErrors().stream();

        return errors
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .map(this::messages)
                .collect(toList());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public GenericErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        return buildFromError(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public GenericErrorResponse handleSecurityException(SecurityException e){
        return buildFromError(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BusinessValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GenericErrorResponse handleBusinessValidationException(BusinessValidationException e){
        return buildFromError(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotLoggedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public GenericErrorResponse handleUnauthorizedValidationException(UserNotLoggedException e){
        return buildFromError(e, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public GenericErrorResponse handleRuntimeValidationException(RuntimeException e){
        return buildFromError(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public GenericErrorResponse handleEntityNotFoundException(Exception e) {
        return buildFromError(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String messages(String key) {
        return this.messages.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    private GenericErrorResponse buildFromError(Throwable e, HttpStatus errorStatus){

        final String errorMessage = this.messages.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale());
        return new GenericErrorResponse(errorMessage, HttpStatus.CONFLICT);
    };
}
