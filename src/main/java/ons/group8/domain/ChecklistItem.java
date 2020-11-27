package ons.group8.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChecklistItem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer checkId;
    private String checkHeader;
    private String checkContent;
    private Boolean checked;


    public ChecklistItem(String checkHeader, String checkContent, Boolean checked) {

        this.checkHeader = checkHeader;
        this.checkContent = checkContent;
        this.checked = checked;
    }
}
