package tony.boilerplate.security.service;

import tony.boilerplate.security.data.dto.request.SignUpRequest;
import tony.boilerplate.security.data.dto.request.SignInRequest;
import tony.boilerplate.security.data.dto.response.SignInResponse;
import tony.boilerplate.security.data.dto.response.SignUpResponse;

public interface SignService {

    SignUpResponse signUp(SignUpRequest request);

    SignInResponse signIn(SignInRequest request);
}
