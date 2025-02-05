package com.bradesco.DesafioBackEnd.Entities;

import com.bradesco.DesafioBackEnd.Enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {

    /* Poderia utilizar @Column nos atributos, porém o banco utilizado H2 já mapeia esses atributos da Entidade no banco */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private String fullName;
    private String email;
    private String phone;
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    private UserTypeEnum userType;
}

