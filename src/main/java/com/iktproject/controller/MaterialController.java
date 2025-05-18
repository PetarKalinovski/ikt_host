package com.iktproject.controller;

import com.iktproject.model.Material;
import com.iktproject.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/materials/{id}/view")
    public String viewMaterial(@PathVariable("id") Long id, Model model) {
        Material material = materialService.findById(id);
        if (material == null) {
            return "redirect:/subjects";  // Or a 404 page
        }
        model.addAttribute("material", material);
        return "view-material";
    }
}
