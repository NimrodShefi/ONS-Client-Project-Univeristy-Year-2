package ons.group8.domain.checklist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TopicDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long checklistTemplateId;
    private String name;
    private String description;

    public TopicDB(Long checklistTemplateId, String name, String description){
        this(null, checklistTemplateId, name, description);
    }
}
