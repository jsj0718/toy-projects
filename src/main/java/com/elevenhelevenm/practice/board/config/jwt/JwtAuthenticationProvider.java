package com.elevenhelevenm.practice.board.config.jwt;

import com.elevenhelevenm.practice.board.domain.member.Member;
import com.elevenhelevenm.practice.board.service.member.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationProvider {

    private String secretKey = "secret";

    private long tokenValidTime = 1000L * 60 * 60; //1시간

    private final MemberService memberService;

    //JWT 토큰 생성
    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk); //JWT Payload에 저장되는 정보 단위
        claims.put("roles", roles);

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) //정보 저장
                .setIssuedAt(now) //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) //set expire time
                .signWith(SignatureAlgorithm.HS256, secretKey) //사용할 암호화 알고리즘과 signature에 들어갈 secret 값 셋팅
                .compact();

    }

    //jwt 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        Member member = memberService.loadMemberByEmail(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(member, "", member.getAuthorities());
    }

    //토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //Request의 Header에서 token 값을 가져옴 ("X-AUTH-TOKEN" : "TOKEN값")
    public String resolveToken(HttpServletRequest request) {
        String token = null;
        Cookie cookie = WebUtils.getCookie(request, "X-AUTH-TOKEN");
        if (cookie != null) token = cookie.getValue();

        return token;
    }

    //토큰 유효성과 만료일자 확인
    public boolean validateToken(String jwtToken) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
        return !claims.getBody().getExpiration().before(new Date());
    }
}
