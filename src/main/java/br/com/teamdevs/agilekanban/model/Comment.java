package br.com.teamdevs.agilekanban.model;

import java.util.List;
import java.time.LocalDate;

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
    private LocalDate timestamp;
    private List<Attachment> attachments;
}
