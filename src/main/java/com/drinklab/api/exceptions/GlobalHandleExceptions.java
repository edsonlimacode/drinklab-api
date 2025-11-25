package com.drinklab.api.exceptions;


import com.drinklab.api.exceptions.customExceptions.BadRequestException;
import com.drinklab.api.exceptions.customExceptions.NotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalHandleExceptions extends ResponseEntityBase {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {

        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

        ProblemDetails problemDetails = getProblemDetails(internalServerError, ex.getMessage())
                .build();

        return super.handleExceptionInternal(ex, problemDetails, new HttpHeaders(), internalServerError, request);

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ProblemDetails problemDetails = getProblemDetails(badRequest, ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(problemDetails);

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        String detail = String.format("O parâmetro %s, recebeu o valor '%s' que é inválido, informe um tipo compatível requerido '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        ProblemDetails problemDetails = getProblemDetails(badRequest, detail).build();

        return ResponseEntity.badRequest().body(problemDetails);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        HttpStatus notFound = HttpStatus.METHOD_NOT_ALLOWED;

        String errorMessage = String.format("Nenhuma uri corresponde ao metodo %s, para este recurso", ex.getMethod());

        ProblemDetails problemDetails = getProblemDetails(notFound, errorMessage)
                .build();

        return ResponseEntity.status(notFound).body(problemDetails);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {

        HttpStatus notFound = HttpStatus.BAD_REQUEST;

        ProblemDetails problemDetails = getProblemDetails(notFound, ex.getMessage())
                .build();

        return ResponseEntity.status(notFound).body(problemDetails);

    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        HttpStatus notFound = HttpStatus.NOT_FOUND;

        String errorMessage = String.format("Recurso não encontrado: %s, para o método: %s", ex.getResourcePath(), ex.getHttpMethod());

        ProblemDetails problemDetails = getProblemDetails(notFound, errorMessage)
                .build();

        return ResponseEntity.status(notFound).body(problemDetails);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        Throwable rootCause = ex.getRootCause();

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, badRequest);
        }

        ProblemDetails problemDetails = getProblemDetails(badRequest, "Corpo da requisição inválido, verique os dados enviados e tente novamente")
                .build();

        return ResponseEntity.status(badRequest).body(problemDetails);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpStatus badRequest) {

        String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

        String detail = String.format("A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. Corrija para um valor compatível com o tipo numérico %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        ProblemDetails problemDetails = getProblemDetails(badRequest, detail).build();

        return ResponseEntity.status(badRequest).body(problemDetails);
    }

    public static ProblemDetails.ProblemDetailsBuilder getProblemDetails(HttpStatus status, String detail) {
        return ProblemDetails
                .builder()
                .status(status.value())
                .title(HttpStatus.valueOf(status.value()).getReasonPhrase())
                .detail(detail)
                .date(LocalDateTime.now().toString());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        List<ProblemDetails.Field> fieldList = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> {

            String message = this.messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

            return ProblemDetails.Field.builder()
                    .field(fieldError.getField())
                    .message(message)
                    .build();
        }).toList();

        ProblemDetails problemDetails = getProblemDetails(badRequest, "Um ou mais campos estão inválidos, verique os campos e tente novamente")
                .fields(fieldList)
                .build();

        return ResponseEntity.status(badRequest).body(problemDetails);
    }

}
