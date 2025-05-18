package com.iktproject.controller;

import com.iktproject.model.*;
import com.iktproject.service.MaterialService;
import com.iktproject.service.SubjectService;
import com.iktproject.service.impl.TestService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class SubjectController {

    private final SubjectService subjectService;
    private final MaterialService materialService;
    private final TestService testService;

    public SubjectController(
            SubjectService subjectService, MaterialService materialService, TestService testService) {
        this.subjectService = subjectService;
        this.materialService = materialService;
        this.testService = testService;
    }

    @GetMapping("/subjects")
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        return "subjects";
    }

    @GetMapping("/subjects/{id}/materials")
    public String showMaterials(@PathVariable Long id, Model model) {
        Subject subject = subjectService.findById(id);
        model.addAttribute("subject", subject);
        model.addAttribute("materials", subject.getMaterials());
        return "materials";
    }

    @GetMapping("/subjects/{id}/materials/import")
    public String showImportPage(@PathVariable Long id, Model model) {
        Subject subject = subjectService.findById(id);
        if (subject == null) {
            throw new RuntimeException("Subject not found");
        }
        model.addAttribute("subject", subject);
        return "import-material";  // your Thymeleaf template name
    }

    @PostMapping("/subjects/{id}/materials/import")
    public String importMaterials(@PathVariable Long id,
                                  @RequestParam("file") MultipartFile file)
            throws IOException {
        Subject subject = subjectService.findById(id);

        if (subject == null) {
            throw new RuntimeException("Subject not found");
        }

        materialService.importFromFile(file, subject);

        return "redirect:/subjects/" + id + "/materials";
    }

    @GetMapping("/subjects/{id}/materials/generate")
    public String showGenerateMaterialForm(@PathVariable Long id, Model model) {
        Subject subject = subjectService.findById(id);
        model.addAttribute("subject", subject);
        return "generate-material";
    }

    @PostMapping("/subjects/{id}/generate-material")
    public String generateMaterial(@PathVariable Long id, @RequestParam("prompt") String prompt, Model model)
            throws IOException {
        Subject subject = subjectService.findById(id);

        try {
            testService.generateMaterial(id, prompt);
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while generating material: " + e.getMessage());
            model.addAttribute("subject", subject);
            return "generate-material";
        }

        return "redirect:/subjects/" + id + "/materials";
    }

    @GetMapping("/subjects/{id}/generate-test")
    public String generateTest(@PathVariable Long id, Model model) {
        List<Question> questions = testService.generateTestForMaterial(id);
        model.addAttribute("questions", questions);
        model.addAttribute("subjectId", id);
        return "test";
    }

    @PostMapping("/submit-test")
    public String submitTest(@RequestParam Map<String, String> answers,
                             @RequestParam Long subjectId,
                             Principal principal) {

        // Testing purpose.
        System.out.println(answers);

        int correctCount = 0;
        int total = 0;

        for (int i = 0; ; i++) {
            String answerKey = "answer" + i;
            String correctKey = "correct" + i;


            if (!answers.containsKey(answerKey) || !answers.containsKey(correctKey)) {
                break;
            }

            String selected = answers.get(answerKey);
            String correct = answers.get(correctKey);

            if (selected.equals(correct)) {
                correctCount++;
            }

            total++;
        }


        double grade = testService.calculateGrade(correctCount, total);

        User user = testService.getLoggedInUser(principal.getName());
        if (user.getType() == Type.STUDENT) {
            user.setTakenTests(user.getTakenTests() + 1);
            user.setGradeAverage((grade + user.getGradeAverage()) / user.getTakenTests());
            testService.saveUser(user);
        }

        return "redirect:/subjects";
    }

    @GetMapping("subjects/{id}/export-pdf")
    public ResponseEntity<ByteArrayResource> exportToPdf(@PathVariable Long id)
    {
        Material material=materialService.findById(id);

        byte[] pdfBytes= materialService.exportToPDF(material.getTitle(),material.getContent());


        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=material_"+material.getTitle()+".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfBytes.length)
                .body(new ByteArrayResource(pdfBytes));
    }
}