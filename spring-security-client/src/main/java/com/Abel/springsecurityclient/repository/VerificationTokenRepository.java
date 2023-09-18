package com.Abel.springsecurityclient.repository;

import com.Abel.springsecurityclient.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
}
