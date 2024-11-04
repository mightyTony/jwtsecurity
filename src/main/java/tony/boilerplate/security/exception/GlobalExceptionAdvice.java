package tony.boilerplate.security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorDto> handleCustomException(CustomException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage(),e.getErrorCode().getStatus());

        return new ResponseEntity<>(errorDto, e.getErrorCode().getStatus());
    }
}
