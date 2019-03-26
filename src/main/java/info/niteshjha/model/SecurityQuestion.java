package info.niteshjha.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SecurityQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;


    @OneToOne(targetEntity = SecurityQuestionDefinition.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "SEC_QUE_ID", nullable = false)
    private SecurityQuestionDefinition securityQuestionDefinition;

    private String answer;


    public SecurityQuestion(User user, SecurityQuestionDefinition securityQuestionDefinition, String answer) {
        this.user = user;
        this.securityQuestionDefinition = securityQuestionDefinition;
        this.answer = answer;
    }
}
