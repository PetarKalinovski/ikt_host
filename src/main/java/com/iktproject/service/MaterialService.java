package com.iktproject.service;

import com.iktproject.model.Material;
import com.iktproject.model.Subject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MaterialService {
    List<Material> findAll();
    Material findById(Long id);
    Material save(Material material);
    Material update(Long id, Material material);
    void delete(Long id);
    List<Material> importFromFile(MultipartFile file, Subject subject) throws IOException;

    byte[] exportToPDF(String title,String content);
}
