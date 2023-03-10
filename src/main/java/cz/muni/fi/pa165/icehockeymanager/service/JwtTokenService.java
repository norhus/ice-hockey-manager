package cz.muni.fi.pa165.icehockeymanager.service;

import cz.muni.fi.pa165.icehockeymanager.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenService {

    public static final int TOKEN_VALIDITY_S = 5 * 24 * 60 * 60;
    @Value("${jwt_secret}")
    private String jwtSecret;

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public String generateJwtToken(UserDto userDto) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder().setClaims(claims).setSubject(userDto.email())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusSeconds(TOKEN_VALIDITY_S)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512).compact();
    }

    public boolean isJwtTokenValid(String token, UserDetails userDetails) {
        String username = extractClaim(token, Claims::getSubject);
        boolean isTokenExpired = extractClaim(token, Claims::getExpiration).before(new Date());

        return (username.equals(userDetails.getUsername()) && !isTokenExpired);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
