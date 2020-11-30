package ons.group8.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="checklist_template_item ")
public class ChecklistTemplateItem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    long id;

}
