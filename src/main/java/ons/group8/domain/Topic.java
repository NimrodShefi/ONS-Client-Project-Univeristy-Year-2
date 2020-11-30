package ons.group8.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="topic")
public class Topic {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    long id;
    @ManyToOne
    @JoinColumn(name="checklistTemplateId")
    private ChecklistTemplate checklistTemplate;
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
}
