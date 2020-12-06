package ons.group8.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="personal_checklist")
public class PersonalChecklist {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User userId;
    @ManyToOne
    @JoinColumn(name="checklist_template_id")
    private ChecklistTemplate checklistTemplate;
    @Column(name="date_assigned")
    private String dateAssigned;
    @Column(name="date_complete")
    private String dateComplete;

    public PersonalChecklist(User userId, ChecklistTemplate checklistTemplate, String dateAssigned, String dateComplete) {
        this.userId = userId;
        this.checklistTemplate = checklistTemplate;
        this.dateAssigned = dateAssigned;
        this.dateComplete = dateComplete;
    }
}
