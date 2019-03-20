// Copyright (c) 2018 Travelex Ltd

package info.niteshjha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    private Long id;
    private String email;
    private String password;
    private String confirmPassword;
}
