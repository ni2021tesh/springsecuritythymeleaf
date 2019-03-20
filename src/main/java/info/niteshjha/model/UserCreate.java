// Copyright (c) 2018 Travelex Ltd

package info.niteshjha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class UserCreate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateCreated;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateModified;
}
