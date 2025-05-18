package com.iktproject.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subjectName;
    private String subjectDescription;

    @OneToMany(mappedBy =  "subject", cascade = CascadeType.ALL)
    private List<Material> materials;

    public void addMaterial(Material material) {
        materials.add(material);
        material.setSubject(this);
    }

    public void removeMaterial(Material material) {
        materials.remove(material);
        material.setSubject(null);
    }

    public Subject() {}

    public Subject(String subjectName, String subjectDescription, List<Material> materials) {
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
        this.materials = materials;
    }
}
