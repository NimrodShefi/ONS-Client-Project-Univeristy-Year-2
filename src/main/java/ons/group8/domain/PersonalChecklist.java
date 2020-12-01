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
    private User user;
    @ManyToOne
    @JoinColumn(name="checklist_template_id")
    private ChecklistTemplate checklistTemplate;
    @Column(name="date_assigned")
    private Date dateAssigned;
    @Column(name="date_complete")
    private Date dateComplete;

    public PersonalChecklist(User user, ChecklistTemplate checklistTemplate, Date dateAssigned, Date dateComplete) {
        this.user = user;
        this.checklistTemplate = checklistTemplate;
        this.dateAssigned = dateAssigned;
        this.dateComplete = dateComplete;
    }
}
