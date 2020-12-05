package ons.group8.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="checklist_template")
public class ChecklistTemplate {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @Column(name="list_name")
    private String name;
    @Column(name="description")
    private String description;

    public ChecklistTemplate(User user, String list_name, String description){
        this(null, user, list_name, description);
    }
}
