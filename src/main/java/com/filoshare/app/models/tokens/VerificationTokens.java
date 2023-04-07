package com.filoshare.app.models.tokens;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "verification_token")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationTokens {

    @Id
    @NonNull
    private String token;

    @NonNull
    private String otp;

    @NonNull
    @Enumerated(EnumType.STRING)
    private VeritificationType type;

}
