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
    @JoinColumn(name="userId")
    private Users users;
    @ManyToOne
    @JoinColumn(name="checklistTemplateId")
    private ChecklistTemplate checklistTemplate;
    @Column(name="dateAssigned")
    private Date dateAssigned;
    @Column(name="dateChecked")
    private Date dateChecked;

    public PersonalChecklist(Users users, ChecklistTemplate checklistTemplate, Date dateAssigned, Date dateChecked) {
        this.users = users;
        this.checklistTemplate = checklistTemplate;
        this.dateAssigned = dateAssigned;
        this.dateChecked = dateChecked;
    }
}
