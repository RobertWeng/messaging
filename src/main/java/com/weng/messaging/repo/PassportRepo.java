package com.weng.messaging.repo;

import com.weng.messaging.model.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassportRepo extends JpaRepository<Passport, Long> {
    Optional<Passport> findByClientTypeAndClientId(Passport.ClientType clientType, String clientId);
}
