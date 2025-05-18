package com.iktproject.service;

import com.iktproject.model.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> findAll();
    Subject findById(Long id);
    Subject save(Subject subject);
    Subject update(Long id, Subject subject);
    void delete(Long id);
    Subject findBySubjectName(String subjectName);
}
