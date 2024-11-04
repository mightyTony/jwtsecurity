package tony.boilerplate.security.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tony.boilerplate.security.data.dto.request.SignInRequest;
import tony.boilerplate.security.data.dto.request.SignUpRequest;
import tony.boilerplate.security.data.dto.response.SignInResponse;
import tony.boilerplate.security.data.dto.response.SignUpResponse;
import tony.boilerplate.security.service.SignService;

@RestController
@RequestMapping("/sign-api")
@Slf4j
@RequiredArgsConstructor
public class SignController {

    private final SignService signService;

    @PostMapping("/sign-in")
    public SignInResponse signIn(@RequestBody SignInRequest request) {
        log.info("[로그인 시도 중] id : {} ", request.getUid());
        SignInResponse signInResponse = signService.signIn(request);

        if(signInResponse.isSuccess()) {
            log.info("[로그인 성공] id : {} ", request.getUid());
        } else {
            log.info("[로그인 실패] id : {} ", request.getUid());
        }
        return signInResponse;
    }

    @PostMapping("/sign-up")
    public SignUpResponse signUp(@RequestBody SignUpRequest request) {
        log.info("[회원가입 시도중] , id : {}, name : {}, role : {}", request.getUid(), request.getName(), request.getRole());
        SignUpResponse signUpResponse = signService.signUp(request);
        log.info("[회원가입 성공] , id : {}, name : {}, role : {}", request.getUid(), request.getName(), request.getRole());
        return signUpResponse;
    }
}
