package br.com.teamdevs.agilekanban.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "allData")
public class User {
    @Id
    @JsonIgnore
    private String id;

    @Field(name = "username")
    private String username;

    @Field(name = "password")
    private String password;

    @Field(name = "email")
    private String email;

    @Field(name = "role")
    private String role;

    @Field(name = "tasks")
    private List<Project> projects;

    @Field(name = "createdTasks")
    private List<Task> createdTasks;

    @Field(name = "assignedTasks")
    private List<Task> assignedTasks;
}
