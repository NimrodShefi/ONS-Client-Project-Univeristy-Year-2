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
@Table(name="checklist_template")
public class ChecklistTemplate {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;

    @Column(name="list_name")
    private String name;

    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "checklistTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> topics;

    public ChecklistTemplate(User author, String list_name, String description, List<Topic> topics){
        this(null, author, list_name, description, topics);
    }
}
