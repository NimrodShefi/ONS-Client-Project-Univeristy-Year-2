package ons.group8.controllers.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistProgressDTO {
    private Long checkedItemsCount;

    private Long checklistItemsCount;
}
