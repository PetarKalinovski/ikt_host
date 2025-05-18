package com.iktproject.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @ManyToOne
    @JoinColumn(name="subject_id")
    private Subject subject;

    public Material() {}

    public Material(String title, String content, Subject subject) {
        this.title = title;
        this.content = content;
        this.subject = subject;
    }

}

