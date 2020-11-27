package ons.group8.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

//import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Users {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    long id;

    @Column(name="email")
    private String email;
    @Column(name="firstName")
    private String fname;
    @Column(name="lastName")
    private String lname;
    @Column(name="password")
    private String password;


    public Users(String theEmail, String thePassword, String theFname, String theLname){
//        this.id = null;
        this.email = theEmail;
        this.password = thePassword;
        this.fname = theFname;
        this.lname = theLname;
    }
}
