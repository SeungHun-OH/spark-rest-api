package com.spark.dating.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spark.dating.common.RestApiException;
import com.spark.dating.common.exception.JwtErrorCode;
import com.spark.dating.dto.login.AuthMember;
import com.spark.dating.member.MemberService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	@Autowired
	private MemberService memberService;
	
    private final byte[] secret = "com.spark.dating.web.backend.project.mycompany.secret.key.restapi".getBytes(StandardCharsets.UTF_8);
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 300;

    private final SecretKey secretKey = Keys.hmacShaKeyFor(secret);
    
    public String generateToken(AuthMember authMember) {
    	long now = System.currentTimeMillis();
        Claims claims = Jwts.claims()
        		.add("memberNo", authMember.getMNo())
//        		.add("memberUuid", authMember.getMUuid())
        		.build();
    	return Jwts.builder()
        		.claims(claims)
        		.issuedAt(new Date(now))
        		.expiration(new Date(now+EXPIRATION_TIME))
        		.signWith(secretKey)
        		.compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        }catch (SecurityException | MalformedJwtException e) {
        	log.info("Invalid JWT", e);
            throw new RestApiException(JwtErrorCode.INVALID_JWT);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT", e);
            throw new RestApiException(JwtErrorCode.EXPIRED_JWT);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT", e);
            throw new RestApiException(JwtErrorCode.UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty", e);
            throw new RestApiException(JwtErrorCode.EMPTY_JWT);
        } catch (JwtException e) {
            log.info("JWT Token Error", e);
            throw new RestApiException(JwtErrorCode.INVALID_JWT);
        } 
    }
    
    public Claims parseClaims(String token) {
    	try {
    		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    	}catch (ExpiredJwtException e) {
    		throw new RestApiException(JwtErrorCode.EXPIRED_JWT);
		}
    }
    
//    public Boolean isTokenExpired(String token) {
//        return Jwts.parser()
//                .verifyWith(secretKey)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload()
//                .getExpiration()
//                .before(new Date());
//    }
    
    public String getToken(String token){
        if(token == null) {
        	throw new RestApiException(JwtErrorCode.EMPTY_JWT);
        }
        if(!token.startsWith("Bearer ")){
        	throw new RestApiException(JwtErrorCode.INVALID_JWT);
        }
        return token.substring(7);
    }
    
    public boolean existsByNo(Long memberId) {
    	return memberService.existsByNo(memberId);
    }
    
    public Long getMemberNo(String token) {
		return parseClaims(token).get("memberNo", Long.class);
	}   
}
