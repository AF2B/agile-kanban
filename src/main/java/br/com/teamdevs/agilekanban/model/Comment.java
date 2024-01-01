package br.com.teamdevs.agilekanban.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    private String taskId;
    private String userId;
    private String text;
    private String timestamp;
    private List<String> attachments;
}
