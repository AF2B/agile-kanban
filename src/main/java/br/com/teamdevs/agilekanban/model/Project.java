package br.com.teamdevs.agilekanban.model;

import java.time.LocalDate;
import java.util.List;

import br.com.teamdevs.agilekanban.enums.ProjectStatus;
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
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;
}
