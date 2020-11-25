package ons.group8.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
public class ChecklistItem {
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
