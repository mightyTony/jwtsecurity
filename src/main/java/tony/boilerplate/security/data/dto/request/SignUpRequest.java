package tony.boilerplate.security.data.dto.request;

import lombok.Data;

@Data
public class SignUpRequest {

    private String uid;
    private String password;
    private String name;
    private String role;
}
