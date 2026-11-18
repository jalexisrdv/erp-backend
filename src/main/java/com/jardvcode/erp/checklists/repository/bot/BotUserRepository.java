package com.jardvcode.erp.checklists.repository.bot;

import com.jardvcode.erp.checklists.entity.bot.BotUserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BotUserRepository extends JpaRepository<BotUserEntity, Long> {
    Optional<BotUserEntity> findByUserIdAndPlatform(Long userId, String platform);

    @Transactional
    void deleteByUserIdAndPlatform(Long userId, String platform);
}
