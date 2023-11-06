package com.board.config.auth;

import com.board.token.dto.TokenDto;
import com.board.token.service.TokenService;
import com.board.config.exception.BaseException;
import com.board.config.auth.redis.RedisDao;
import com.board.config.exception.NotPermitException;
import com.board.entity.Member;
import com.board.entity.Subject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

import static com.board.constant.BaseStatus.INTERNAL_SERVER_ERROR;
import static com.board.constant.BaseStatus.INVALID_TOKEN;

/**
 * JWT를 생성하고 검증하는 컴포넌트
 */
@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    // 토큰 유효시간 30분
    @Value("${security.jwt.token.expire-length}")
    private long tokenValidTime;

    @Value("${spring.jwt.live.atk}")
    private long atkLiveTime;

    @Value("${spring.jwt.live.rtk}")
    private long rtkLiveTime;

    private final RedisDao redisDao;

    private final TokenService tokenService;

    private final ObjectMapper objectMapper;

    public JwtTokenProvider(RedisDao redisDao
            , ObjectMapper objectMapper
            , TokenService tokenService) {
        this.redisDao = redisDao;
        this.objectMapper = objectMapper;
        this.tokenService = tokenService;
    }

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * 토큰 생성
     * @param subject
     * @return
     */
    public String createToken(Subject subject, long tokenLiveTime) throws BaseException {
        String subjectStr = null;
        try {
            subjectStr = objectMapper.writeValueAsString(subject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BaseException(INTERNAL_SERVER_ERROR);
        }

        Claims claims = Jwts.claims().setSubject(subjectStr);   // JWT payload에 저장되는 정보 단위

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)  // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenLiveTime)) // 토큰 만료시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 설정
                .compact()
                ;
    }

    /**
     * 멤버 정보를 기반으로 토큰 발급 및 redis 저장
     * @param member
     * @return
     * @throws JsonProcessingException
     */
// redis 기반
//    public TokenResponse createTokenByLogin(Member member) throws BaseException {
//        // AccessToken, RefreshToken 발급
//        Subject atkSubject = Subject.atk(member.getMemberId(), member.getEmail());
//        Subject rtkSubject = Subject.rtk(member.getMemberId(), member.getEmail());
//
//        String atk = createToken(atkSubject, atkLiveTime);
//        String rtk = createToken(rtkSubject, rtkLiveTime);
//
//        // Redis에 RefreshToken 저장
//        redisDao.setValues(member.getEmail(), rtk, Duration.ofMillis(rtkLiveTime));
//        return new TokenResponse(atk, rtk);
//    }
    public TokenResponse createTokenByLogin(Member member) throws BaseException {
        // AccessToken, RefreshToken 발급
        Subject atkSubject = Subject.atk(member.getMemberId(), member.getEmail());
        Subject rtkSubject = Subject.rtk(member.getMemberId(), member.getEmail());

        String atk = createToken(atkSubject, atkLiveTime);
        String rtk = createToken(rtkSubject, rtkLiveTime);

        Date now = new Date();
        TokenDto tokenDto = new TokenDto(member.getMemberId(), atk, atkLiveTime, rtk, rtkLiveTime);
        // DB에 RefreshToken 저장
        tokenService.insertToken(tokenDto);
        return new TokenResponse(atk, rtk);
    }

    /**
     * AccessToken 재발급
     * @param member
     * @return
     * @throws JsonProcessingException
     */
// redis 기반
//    public TokenResponse reissueAtk(Member member) throws BaseException {
//        String rtkInRedis = redisDao.getValues(member.getEmail());
//        if(Objects.isNull(rtkInRedis)) throw new NotPermitException(INVALID_TOKEN);
//
//        // 멤버 정보를 기반으로 AccessToken 재발급
//        Subject atkSubject = Subject.atk(member.getMemberId(), member.getEmail());
//        String atk = createToken(atkSubject, atkLiveTime);
//        return new TokenResponse(atk, null);
//    }
    public TokenResponse reissueAtk(Member member) throws BaseException {
        String rtk = tokenService.selectRtkByMemberEmail(member.getEmail());
        if(Objects.isNull(rtk)) throw new NotPermitException(INVALID_TOKEN);

        // 멤버 정보를 기반으로 AccessToken 재발급
        Subject atkSubject = Subject.atk(member.getMemberId(), member.getEmail());
        String atk = createToken(atkSubject, atkLiveTime);

        // DB update
        TokenDto tokenDto = new TokenDto(member.getMemberId(), atk, atkLiveTime, rtk, rtkLiveTime);
        tokenService.updateAccessToken(tokenDto);

        return new TokenResponse(atk, rtk);
    }

    /**
     * 토큰에서 값 추출
     * @param token
     * @return
     */
    public Subject getSubject(String token) throws BaseException {
        String subjectStr = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        Subject subject = null;
        try {
            subject = objectMapper.readValue(subjectStr, Subject.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BaseException(INTERNAL_SERVER_ERROR);
        }
        return subject;
    }

    /**
     * 유효한 토큰인지 확인
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            // 유효 기간 지났으면 만료된 토큰이다.
            if(claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 토큰 삭제하기(로그아웃)
     * @param atk
     */
    public void deleteToken(String atk) {
        // Redis에서 해당 이메일의 토큰 삭제하기
        //Subject subject = this.getSubject(atk);
        //redisDao.deleteValues(subject.getEmail());

        // DB에서 해당 이메일의 토큰 삭제하기
        tokenService.deleteToken(atk);
    }

}
