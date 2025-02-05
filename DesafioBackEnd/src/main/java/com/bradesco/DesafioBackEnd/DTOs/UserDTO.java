package com.bradesco.DesafioBackEnd.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {


    @NotBlank(message = "Insira o Nome Completo, por favor.")
    @NotNull
    private String fullName;

    @NotBlank(message = "Insira o Email, por favor.")
    @NotNull
    @Email(message = "Formato de Email inválido!")
    private String email;

    @NotBlank(message = "Insira o número de telefone, por favor.")
    @NotNull
    @Pattern(
            regexp = "\\+\\d{1,3}\\s\\d{2}\\s\\d{4,5}-\\d{4}",
            message = "Número de telefone deve ser no formato: +CC XX XXXXX-XXXX"
    )
    private String phone;

    @NotNull(message = "Insira a data de aniversário, por favor.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date birthDate;

    @NotBlank(message = "Tipo de usuário deve ser: ADMIN, EDITOR, VIEWER")
    @NotNull
    private String userType;
}
