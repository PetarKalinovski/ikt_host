package com.iktproject.config;

import com.iktproject.model.Material;
import com.iktproject.model.Subject;
import com.iktproject.model.Type;
import com.iktproject.model.User;
import com.iktproject.repository.SubjectRepository;
import com.iktproject.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    @PostConstruct
    public void initData() {
        Subject math = new Subject("Mathematics", "Algebra and Geometry", new ArrayList<>());
        Subject physics = new Subject("Physics", "Mechanics and Thermodynamics", new ArrayList<>());

        Material algebra = new Material("Algebra Basics", "This is content about algebra.", math);
        Material geometry = new Material("Geometry Basics", "This is content about geometry.", math);
        Material mechanics = new Material("Mechanics Intro", "This is content about mechanics.", physics);



        math.addMaterial(algebra);
        math.addMaterial(geometry);
        physics.addMaterial(mechanics);

        subjectRepository.save(math);
        subjectRepository.save(physics);
    }
}
