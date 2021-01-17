package ons.group8.domain;


import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_template_id")
    private ChecklistTemplate checklistTemplate;

    @Column(name = "topic_name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "topic", cascade = {CascadeType.ALL})
    private List<ChecklistTemplateItem> items;

    public Topic(String name, String description, List<ChecklistTemplateItem> items) {
        this(null, null, name, description, items);
    }

    public Topic(ChecklistTemplate template, String name, String description, List<ChecklistTemplateItem> items) {
        this(null, template, name, description, items);
    }

    public Topic(Topic topicToClone) {
        this(null,
                topicToClone.getName(),
                topicToClone.getDescription(),
                topicToClone.items
                        .stream()
                        .map(i -> new ChecklistTemplateItem(i.getDescription()))
                        .collect(Collectors.toList()));
        items.stream().forEach(i -> i.setTopic(this));
    }
}
