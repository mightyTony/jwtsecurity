package tony.boilerplate.security.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tony.boilerplate.security.config.security.JwtTokenProvider;
import tony.boilerplate.security.data.dto.request.SignUpRequest;
import tony.boilerplate.security.data.dto.request.SignInRequest;
import tony.boilerplate.security.data.dto.response.SignInResponse;
import tony.boilerplate.security.data.dto.response.SignUpResponse;
import tony.boilerplate.security.data.entity.User;
import tony.boilerplate.security.exception.CustomException;
import tony.boilerplate.security.exception.ErrorCode;
import tony.boilerplate.security.repository.UserRepository;
import tony.boilerplate.security.service.SignService;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignServiceImpl implements SignService {
    public final UserRepository userRepository;
    public final JwtTokenProvider jwtTokenProvider;
    public final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        log.info("[회원 가입 정보 전달]");
        User user;
        if(request.getRole().equalsIgnoreCase("admin")){
            user = User.builder()
                .uid(request.getUid())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList("ROLE_ADMIN"))
                .build();
        } else {
            user = User.builder()
                .uid(request.getUid())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        }

        User savedUser = userRepository.save(user);
        log.info("[회원 가입 완료]");
        return SignUpResponse.builder()
            .success(true)
            .code(200)
            .msg("회원 가입 완료")
            .build();
    }

    @Override
    public SignInResponse signIn(SignInRequest request) {
        log.info("[로그인 정보 전달]");
        User user = userRepository.getUserByUid(request.getUid())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        log.info(" user : {}", user.getUid());
        log.info(" password : {} , user.password : {}", passwordEncoder.encode(request.getPassword()), user.getPassword());

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            log.info("[로그인 실패] 비밀번호 다름 id : {} ", request.getUid());
            throw new CustomException(ErrorCode.USERDATA_INCORRECT);
        }

        SignInResponse response = SignInResponse.builder()
                .success(true)
                .code(200)
                .msg("로그인 성공")
                .token(jwtTokenProvider.createToken(user.getUid(), user.getRoles()))
                .build();

        return response;
    }
}
