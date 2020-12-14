package ons.group8.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="checklist_item")
public class ChecklistItem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="personal_checklist_id")
    private PersonalChecklist personalChecklist;
    @ManyToOne
    @JoinColumn(name="checklist_template_item_id")
    private ChecklistTemplateItem checklistTemplateItem;
    @Column(name="checked")
    private boolean isChecked;

    public ChecklistItem(PersonalChecklist personalChecklist, ChecklistTemplateItem checklistTemplateItem) {
        this.personalChecklist = personalChecklist;
        this.checklistTemplateItem = checklistTemplateItem;
    }

    public ChecklistItem(ChecklistTemplateItem checklistTemplateItem, boolean isChecked){
        this(null, null, checklistTemplateItem, isChecked);
    }
}
