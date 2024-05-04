package ru.netology.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Builder
@Table(name = "users", schema = "netology_diploma")
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

}