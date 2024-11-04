package tony.boilerplate.security.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secret;
    private final Long tokenValidMilisecond = 1000L * 60 * 60;

    // 빈 생성 후 초기화 메서드
    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String uid, List<String> roles) {
      log.info("[createToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(uid);
        claims.put("roles", roles);
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        log.info("[createToken] 토큰 생성 완료");
        return token;
    }

    public String getUserName(String token) {
        log.info("[getUserName] 토큰으로 유저이름 조회 시작");
        String info = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        log.info("[getUserName] 토큰으로 유저이름 조회 완료");
        return info;
    }

    public Authentication getAuthentication(String token) {
        log.info("[getAuthentication] 토큰으로 인증정보 조회 시작");
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserName(token));
        log.info("[getAuthentication] 토큰으로 인증정보 조회 완료");
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        log.info("[resolveToken] Http헤더에서 token 값 추출 : {}", request.getHeader("Authorization"));
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String token) {
        log.info("[validateToken] 토큰 유효성 검사 시작");
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("[validateToken] 토큰 유효성 검사 실패");
            return false;
        }
    }
}
