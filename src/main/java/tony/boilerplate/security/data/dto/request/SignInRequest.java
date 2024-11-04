package tony.boilerplate.security.data.dto.request;

import lombok.Data;

@Data
public class SignInRequest {
    private String uid;
    private String password;
}
