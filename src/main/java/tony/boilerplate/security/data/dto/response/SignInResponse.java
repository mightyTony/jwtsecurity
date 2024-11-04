package tony.boilerplate.security.data.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class SignInResponse {

    private String token;
    private boolean success;
    private int code;
    private String msg;
//    private String refreshToken;
//    private String uid;
//    private String name;
//    private String role;

    @Builder
    public SignInResponse(String token, boolean success, int code, String msg) {
        this.token = token;
        this.success = success;
        this.code = code;
        this.msg = msg;
    }
}
