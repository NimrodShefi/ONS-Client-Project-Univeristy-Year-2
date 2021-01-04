package ons.group8.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @ColumnTransformer(
            read = "AES_DECRYPT(email, 'key')",
            write = "AES_ENCRYPT(?, 'key')"
            //https://www.programmersought.com/article/4488957993/
    )
    private String email;

    @NotNull
    @NotEmpty
    @ColumnTransformer(
            read = "AES_DECRYPT(first_name, 'key')",
            write = "AES_ENCRYPT(?, 'key')"
    )
    private String firstName;

    @NotNull
    @NotEmpty
    @ColumnTransformer(
            read = "AES_DECRYPT(last_name, 'key')",
            write = "AES_ENCRYPT(?, 'key')"
    )
    private String lastName;

    @NotNull
    @NotEmpty
    private String password;

    private boolean accountNonLocked;

    private int failedAttempt;

    private LocalDateTime lockTime;

    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")) // this means that by adding this, when a role is added to the hash then it will be added to the user_role table with the user
    private Set<Role> roles = new HashSet<>();


    public User(String email, String password, String firstName, String lastName) {
        this(null, email, firstName, lastName, password, true, 0, null , true, new HashSet<>());
    }

    public void addRole(Role role) {
        roles.add(role);
    }
}

