package info.niteshjha.service;

import info.niteshjha.model.SecurityQuestion;
import info.niteshjha.repository.SecurityQuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class SecurityQuestionService {

    private SecurityQuestionRepository securityQuestionRepository;

    public SecurityQuestionService(SecurityQuestionRepository securityQuestionRepository) {
        this.securityQuestionRepository = securityQuestionRepository;
    }


    public SecurityQuestion saveSecurityQuestion(SecurityQuestion securityQuestion) {
        return this.securityQuestionRepository.save(securityQuestion);
    }


    public SecurityQuestion getSecurityQuestionByUserIdAndAnswer(Long securityQuestionId, Long userId, String answer) {
        return this.securityQuestionRepository.findBySecurityQuestionDefinition_SecQueIdAndUser_IdAndAnswer(securityQuestionId, userId, answer);
    }
}
