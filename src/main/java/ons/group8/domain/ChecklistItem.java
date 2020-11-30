package ons.group8.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="checklist_item")
public class ChecklistItem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    long id;
    @Column(name="topicId")
    private String topicId;
    @Column(name="description")
    private String description;


    public ChecklistItem(String topicId, String description) {

        this.topicId = topicId;
        this.description=description;
    }
}
