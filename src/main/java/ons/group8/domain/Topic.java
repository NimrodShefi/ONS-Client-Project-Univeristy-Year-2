package ons.group8.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    private String topicTitle;
    private String topicDescription;
    private List<String> items;

    public Topic(String topicTitle, String topicDescription){
        this(topicTitle, topicDescription, null);
    }
}
