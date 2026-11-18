package com.jardvcode.erp.checklists.entity.bot;

import jakarta.persistence.*;

@Entity
@Table(name = "bot_users")
public class BotUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "platform")
    private String platform;

    public static BotUserEntity create(Long userId) {
        BotUserEntity entity = new BotUserEntity();

        entity.userId = userId;
        entity.platform = "TELEGRAM";

        return entity;
    }

}
