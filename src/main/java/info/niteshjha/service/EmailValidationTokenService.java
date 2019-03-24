package info.niteshjha.service;

import info.niteshjha.model.EmailValidationToken;
import info.niteshjha.model.User;
import info.niteshjha.repository.EmailValidationTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class EmailValidationTokenService {

    private EmailValidationTokenRepository emailValidationTokenRepository;

    public EmailValidationTokenService(EmailValidationTokenRepository emailValidationTokenRepository) {
        this.emailValidationTokenRepository = emailValidationTokenRepository;
    }

    public EmailValidationToken createValidationTokenForUser(String token, User user) {
        return this.emailValidationTokenRepository.save(new EmailValidationToken(token, user));
    }

    public EmailValidationToken getEmailValidationToken(String token) {
        return this.emailValidationTokenRepository.findByToken(token);
    }
    public EmailValidationToken updateToken(EmailValidationToken token){
        return this.emailValidationTokenRepository.save(token);
    }
}
