package ons.group8.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class User {
    private Integer id;
    private String email;
    private String fname;
    private String lname;
    private String password;
    private List<String> roles;
}
