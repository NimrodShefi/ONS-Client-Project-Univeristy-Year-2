package ons.group8.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChecklistTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    private String list_name;
    private String description;

    public ChecklistTemplate(Long user_id, String list_name, String description){
        this(null, user_id, list_name, description);
    }
}
