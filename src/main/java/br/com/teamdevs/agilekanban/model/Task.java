package br.com.teamdevs.agilekanban.model;

import java.util.List;

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
    private String dueDate;
    private boolean completed;
    private List<String> comments;
    private List<String> attachments;
    private List<String> subtasks;
    private List<String> dependencies;
    private List<String> tags;
}
