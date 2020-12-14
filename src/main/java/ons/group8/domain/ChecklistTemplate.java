package ons.group8.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="checklist_template")
public class ChecklistTemplate {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;

    @NotBlank(message="List name field is mandatory")
    @Column(name="list_name", length=120)
    private String name;

    @Column(name="description", length=500)
    private String description;

    @OneToMany(mappedBy = "checklistTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> topics;

    public ChecklistTemplate(User author, String list_name, String description, List<Topic> topics){
        this(null, author, list_name, description, topics);
    }
}
