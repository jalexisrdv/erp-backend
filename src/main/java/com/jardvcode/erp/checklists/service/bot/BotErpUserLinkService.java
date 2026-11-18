package com.jardvcode.erp.checklists.service.bot;

import com.jardvcode.erp.authentication.service.AuthenticatedUserProvider;
import com.jardvcode.erp.checklists.entity.bot.BotUserEntity;
import com.jardvcode.erp.checklists.repository.bot.BotUserRepository;
import com.jardvcode.erp.shared.domain.DomainError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BotErpUserLinkService {

    private final static Logger LOG = LoggerFactory.getLogger(BotErpUserLinkService.class);

    private final BotUserRepository repository;
    private final AuthenticatedUserProvider userProvider;

    public BotErpUserLinkService(BotUserRepository repository, AuthenticatedUserProvider userProvider) {
        this.repository = repository;
        this.userProvider = userProvider;
    }

    public boolean unlink(String platform) {
        try {
            repository.deleteByUserIdAndPlatform(userProvider.getUserId(), platform);

            Optional<BotUserEntity> botUserEntity = repository.findByUserIdAndPlatform(userProvider.getUserId(), platform);

            return botUserEntity.isPresent();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    public boolean isLinked(String platform) {
        try {
            Optional<BotUserEntity> linkedUser = repository.findByUserIdAndPlatform(userProvider.getUserId(), platform);

            return linkedUser.isPresent();
        } catch (DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}