package com.jana.products.service;


import com.jana.products.exception.TokenRefreshException;
import com.jana.products.model.RefreshToken;
import com.jana.products.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${app.jwtRefreshExpirationMs}")
    private static Long jwtRefreshExpirationMs;

    private final UserRepo userRepository;
    private final CacheManagerService<RefreshToken> cacheManagerService;

    public Optional<RefreshToken> findByToken(String token) {
        return cacheManagerService.getByKey(token);
    }

    public RefreshToken createRefreshToken(String email) {

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findByEmail(email));
        refreshToken.setExpiryDate(Calendar.getInstance().getTimeInMillis() + 90000000);
        refreshToken.setToken(UUID.randomUUID().toString());
        cacheManagerService.cachedObject(refreshToken.getToken(), refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Calendar.getInstance().getTimeInMillis()) < 0) {
            cacheManagerService.deleteFromCache(token.getToken());
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }



}