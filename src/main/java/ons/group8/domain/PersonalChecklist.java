package ons.group8.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="personal_checklist")
public class PersonalChecklist {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User userId;
    @ManyToOne
    @JoinColumn(name="checklist_template_id")
    private ChecklistTemplate checklistTemplate;
    @Column(name="date_assigned")
    private LocalDate dateAssigned;
    @Column(name="date_complete")
    private LocalDate dateComplete;

    public PersonalChecklist(User userId, ChecklistTemplate checklistTemplate, LocalDate dateAssigned, LocalDate dateComplete) {
        this.userId = userId;
        this.checklistTemplate = checklistTemplate;
        this.dateAssigned = dateAssigned;
        this.dateComplete = dateComplete;
    }

    public PersonalChecklist(User userId, ChecklistTemplate checklistTemplate, LocalDate dateAssigned){
        this(null, userId, checklistTemplate, dateAssigned, null);
    }
}
