package info.niteshjha.repository;

import info.niteshjha.model.SecurityQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestion, Long> {

    SecurityQuestion findBySecurityQuestionDefinition_SecQueIdAndUser_IdAndAnswer(Long securityQuestionDefinition_id, Long user_id, String answer);
}
