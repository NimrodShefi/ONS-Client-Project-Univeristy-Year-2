package ons.group8.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="topic")
public class Topic {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="checklist_template_id")
    private ChecklistTemplate checklistTemplate;

    @Column(name="topic_name")
    private String name;

    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "topic", cascade = {CascadeType.ALL})
    private List<ChecklistTemplateItem> items;

    public Topic(String name, String description, List<ChecklistTemplateItem> items){
        this(null, null, name, description, items);
    }
}
