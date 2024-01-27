package br.com.teamdevs.agilekanban.model;

import java.util.List;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    private String title;
    private String description;
    private String project;
    private String assignedTo;
    private String createdBy;
    private String priority;
    private LocalDate dueDate;
    private boolean completed;
    private List<Comment> comments;
    private List<Attachment> attachments;
    private List<String> subtasks;
    private List<String> dependencies;
    private List<String> tags;
}
