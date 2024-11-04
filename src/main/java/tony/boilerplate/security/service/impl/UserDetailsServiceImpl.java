package tony.boilerplate.security.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tony.boilerplate.security.exception.CustomException;
import tony.boilerplate.security.exception.ErrorCode;
import tony.boilerplate.security.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("[loadUserByUsername] username: {}", username);
        return userRepository.getUserByUid(username).orElseThrow(()-> {
            log.error("[loadUserByUsername] User not found with username: {}", username);
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        });
    }
}
