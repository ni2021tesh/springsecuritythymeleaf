package info.niteshjha.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SecurityQuestionDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long secQueId;
    private String question;

}
