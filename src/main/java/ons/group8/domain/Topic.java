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
@Table(name="topic")
public class Topic {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name="checklist_template_id")
    private ChecklistTemplate checklistTemplate;
    @Column(name="topic_name")
    private String name;
    @Column(name="description")
    private String description;
    @OneToMany(mappedBy = "topic")
    private List<ChecklistTemplateItem> items;

    public Topic(ChecklistTemplate checklistTemplate, String name, String description){
        this(null, checklistTemplate, name, description, null);
    }

    public Topic(String name, String description){
        this(null, null, name, description, null);
    }

    public Topic(String name, String description, List<ChecklistTemplateItem> items){
        this(null, null,name, description, items);
    }
}
