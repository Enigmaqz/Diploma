package ru.netology.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "files", schema = "netology_diploma")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @Column(nullable = false, unique = true)
    private String filename;

    @Column(nullable = false)
    private Long size;

    @Lob
    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "login")
    private User user;

}