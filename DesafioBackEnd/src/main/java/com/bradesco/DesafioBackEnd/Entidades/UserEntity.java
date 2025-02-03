package com.bradesco.DesafioBackEnd.Entidades;

import com.bradesco.DesafioBackEnd.Enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.usertype.UserType;

import java.util.Date;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {

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

