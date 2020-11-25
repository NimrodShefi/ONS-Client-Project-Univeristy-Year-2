package ons.group8.domain;

import java.util.List;
import java.util.Optional;

public class ChecklistItem {
    private Integer checkId;
    private String checkHeader;
    private String checkContent;
    private Boolean checked;


    public ChecklistItem(Integer checkId, String checkHeader, String checkContent,
                         Boolean checked) {
        this.checkId = null;
        this.checkHeader = checkHeader;
        this.checkContent = checkContent;
        this.checked = checked;
    }
}
