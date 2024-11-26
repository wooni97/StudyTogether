package dev.flab.studytogether.api.handler;

import dev.flab.studytogether.common.exception.BadRequestException;
import dev.flab.studytogether.common.exception.NotFoundException;
import dev.flab.studytogether.api.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ApiResponse<Void> badRequestExceptionHandle(BadRequestException e) {
        return ApiResponse.badRequest(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<Void> notFoundExceptionHandle(NotFoundException e) {
        return ApiResponse.notFound(e.getMessage());
    }
}
