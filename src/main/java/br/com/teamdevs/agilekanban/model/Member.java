package br.com.teamdevs.agilekanban.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Member {
    private String userId;
    private String role;
}
