package ons.group8.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="checklist_template_item ")
public class ChecklistTemplateItem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name="topic_id")
    private Topic topic;

    @Column(name="description")
    private String description;

    public ChecklistTemplateItem(String description){
        this(null, null, description);
    }
}
