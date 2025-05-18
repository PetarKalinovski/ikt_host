package com.iktproject.service.impl;

import com.iktproject.model.Subject;
import com.iktproject.repository.SubjectRepository;
import com.iktproject.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject findById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
    }

    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject update(Long id, Subject subjectDetails) {
        Subject subject = findById(id);
        subject.setSubjectName(subjectDetails.getSubjectName());
        subject.setSubjectDescription(subjectDetails.getSubjectDescription());

        return subjectRepository.save(subject);
    }

    @Override
    public void delete(Long id) {
        Subject subject = findById(id);
        subjectRepository.delete(subject);
    }

    @Override
    public Subject findBySubjectName(String subjectName) {
        return subjectRepository.findBySubjectName(subjectName);
    }
}
