package bkendu2s7.u2s7.exceptions;

import bkendu2s7.u2s7.payloads.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ManagerException {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleBadRequest(BadRequestException ex){
        if(ex.getErrorsList() != null) {
            String message = ex.getErrorsList().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". " ));
            return new ErrorResponseDTO(message, LocalDateTime.now());

        } else {
            return new ErrorResponseDTO(ex.getMessage(), LocalDateTime.now());
        }
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleNotFound(NotFoundException ex){
        return new ErrorResponseDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleGenericErrors(Exception ex){
        ex.printStackTrace();
        return new ErrorResponseDTO("Problema lato server! Giuro che lo risolveremo presto!", LocalDateTime.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDTO handleUnauthorized(UnauthorizedException ex){
        return new ErrorResponseDTO(ex.getMessage(), LocalDateTime.now());
    }
}
