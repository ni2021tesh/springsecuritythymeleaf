package info.niteshjha.service;

import info.niteshjha.model.SecurityQuestionDefinition;
import info.niteshjha.repository.SecurityQuestionDefinitionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityQuestionDefinitionService {

    private SecurityQuestionDefinitionRepository securityQuestionDefinitionRepository;

    public SecurityQuestionDefinitionService(SecurityQuestionDefinitionRepository securityQuestionDefinitionRepository) {
        this.securityQuestionDefinitionRepository = securityQuestionDefinitionRepository;
    }


    public SecurityQuestionDefinition getSecurityDefinition(Long definitionId) {
        return this.securityQuestionDefinitionRepository.findBySecQueId(definitionId);
    }

    public List<SecurityQuestionDefinition> getAllSecQuestion() {
        return this.securityQuestionDefinitionRepository.findAll();
    }

    public void saveAllSecurityQuestionDefinition(List<SecurityQuestionDefinition> securityQuestionDefinitions) {
        this.securityQuestionDefinitionRepository.saveAll(securityQuestionDefinitions);
    }
}
