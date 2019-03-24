package info.niteshjha.service;

import info.niteshjha.model.PasswordResetToken;
import info.niteshjha.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenService {

    private PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public PasswordResetToken createPasswordResetToken(PasswordResetToken passwordResetToken) {
        return this.passwordResetTokenRepository.save(passwordResetToken);
    }

    public PasswordResetToken getPasswordResetToken(String token) {
        return this.passwordResetTokenRepository.findByToken(token);
    }

    public PasswordResetToken updatePasswordResetToken(PasswordResetToken resetToken) {
        return this.passwordResetTokenRepository.save(resetToken);
    }

}
