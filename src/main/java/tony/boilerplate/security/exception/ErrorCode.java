package tony.boilerplate.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 200

    // 400
    USERDATA_INCORRECT(HttpStatus.BAD_REQUEST, "유저 데이터가 일치 하지 않습니다"),
    // 600
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String msg;
}
