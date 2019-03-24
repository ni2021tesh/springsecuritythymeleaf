package info.niteshjha.repository;

import info.niteshjha.model.EmailValidationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailValidationTokenRepository extends JpaRepository<EmailValidationToken, Long> {
    EmailValidationToken findByToken(String token);
}
