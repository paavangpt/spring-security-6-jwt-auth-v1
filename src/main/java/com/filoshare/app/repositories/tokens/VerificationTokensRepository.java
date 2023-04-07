package com.filoshare.app.repositories.tokens;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filoshare.app.models.tokens.VerificationTokens;
import com.filoshare.app.models.tokens.VeritificationType;

@Repository
public interface VerificationTokensRepository extends JpaRepository<VerificationTokens, String>{

    Optional<VerificationTokens> findByTokenAndOtpAndType(String token, String otp, VeritificationType type);
    
}
