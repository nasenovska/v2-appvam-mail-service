package com.appvam.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Table(name = "assignments")
@NoArgsConstructor
@Data
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Email
    private String receiver;

    private LocalDate deadline;

    private long taskId;

    private String token;
}
