package br.com.teamdevs.agilekanban.model;

import br.com.teamdevs.agilekanban.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    private String userId;
    private Enum<Role> role;
}
