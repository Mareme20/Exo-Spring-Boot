package com.marieme.wallet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Active l'audit JPA (createdAt / updatedAt automatiques) sur les entites
 * qui etendent com.marieme.wallet.entity.BaseEntity.
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
