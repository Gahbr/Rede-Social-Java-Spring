package com.sysmap.parrot.services.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JWTService implements IJWTService {
    private final long EXPIRATION_TIME = 7200000;
    private final String KEY ="33743677397A24432646294A404E635266556A586E5A7234753778214125442A" ;

    public String generateToken(String userId) {
        return Jwts
                .builder()
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(genSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key genSignInKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY));
    }
}
