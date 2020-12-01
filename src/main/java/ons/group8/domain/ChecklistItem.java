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
    @ManyToOne
    @JoinColumn(name="personal_checklist_id")
    private PersonalChecklist personalChecklist;
    @ManyToOne
    @JoinColumn(name="checklist_template_item_id")
    private ChecklistTemplateItem checklistTemplateItem;

    public ChecklistItem(PersonalChecklist personalChecklist, ChecklistTemplateItem checklistTemplateItem) {
        this.personalChecklist = personalChecklist;
        this.checklistTemplateItem = checklistTemplateItem;
    }
}
