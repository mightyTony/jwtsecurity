package tony.boilerplate.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorDto {
    private final String message;
    private final HttpStatus status;

    public ErrorDto(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
