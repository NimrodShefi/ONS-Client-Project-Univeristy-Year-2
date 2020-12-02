package ons.group8.domain.checklist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String topicTitle;
    private String topicDescription;
    private List<String> items;

    public Topic(String topicTitle, String topicDescription){
        this(null,topicTitle, topicDescription, null);
    }

    public Topic(String topicTitle, String topicDescription, List<String> items){
        this(null, topicTitle, topicDescription, items);
    }
}
