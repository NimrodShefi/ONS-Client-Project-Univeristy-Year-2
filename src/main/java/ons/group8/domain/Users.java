package ons.group8.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String email;
    private String firstName;
    private String lastName;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE"
            , joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "id"))
    private List<Roles> roles;

    public Users(Long id, String email, List<Roles> roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
    }
}
