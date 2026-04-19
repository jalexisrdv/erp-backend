package com.erp.authentication.service;

import com.erp.authentication.entity.RefreshTokenEntity;
import com.erp.authentication.exception.SessionExpiredException;
import com.erp.authentication.exception.SessionDoesNotExistException;
import com.erp.authentication.repository.RefreshTokenRepository;
import com.erp.shared.domain.DomainError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public final class RefreshTokenService {

    private final static Logger LOG = LoggerFactory.getLogger(RefreshTokenService.class);

    private final RefreshTokenRepository repository;

    public RefreshTokenService(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    public RefreshTokenEntity create(Long userId) {
        try {
            return repository.save(RefreshTokenEntity.create(userId));
        } catch(Exception e) {
            LOG.info(e.getMessage(), e);
            throw e;
        }
    }

    public RefreshTokenEntity update(String refreshToken) {
        try {
            RefreshTokenEntity token = repository.findByToken(refreshToken).orElseThrow(() -> new SessionDoesNotExistException());

            repository.deleteById(token.getId());

            if(token.isTokenExpirated()) {
                throw new SessionExpiredException();
            }

            return repository.save(RefreshTokenEntity.create(token.getUserId()));
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.info(e.getMessage(), e);
            throw e;
        }
    }

}
