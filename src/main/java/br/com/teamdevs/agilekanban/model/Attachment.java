package br.com.teamdevs.agilekanban.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attachment {
    private String taskId;
    private String userId;
    private String filename;
    private String timestamp;
}
