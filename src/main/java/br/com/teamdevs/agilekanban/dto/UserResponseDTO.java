package br.com.teamdevs.agilekanban.dto;

import java.util.List;

import br.com.teamdevs.agilekanban.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private List<User> data;
}
