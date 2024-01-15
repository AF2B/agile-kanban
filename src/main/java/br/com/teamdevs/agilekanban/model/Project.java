package br.com.teamdevs.agilekanban.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    private String name;
    private String description;
    private List<Member> members;
    private List<String> tasks;
    private String startDate;
    private String endDate;
    private String status;
}