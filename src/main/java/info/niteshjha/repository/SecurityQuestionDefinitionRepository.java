package info.niteshjha.repository;

import info.niteshjha.model.SecurityQuestionDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityQuestionDefinitionRepository extends JpaRepository<SecurityQuestionDefinition, Long> {

    SecurityQuestionDefinition findBySecQueId(Long id);

}
