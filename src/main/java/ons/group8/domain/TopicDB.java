package ons.group8.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "topic")
public class TopicDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long checklistTemplateId;
    private String topic_name;
    private String description;

    public TopicDB(Long checklistTemplateId, String name, String description){
        this(null, checklistTemplateId, name, description);
    }
}
