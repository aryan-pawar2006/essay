package com.aryan.essay_improver.controller;

import com.aryan.essay_improver.dto.EssayRequest;
import com.aryan.essay_improver.service.AIService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/essay")
@CrossOrigin(origins = "*")
public class EssayController {

    private final AIService aiService;

    // Constructor Injection
    public EssayController(AIService aiService) {
        this.aiService = aiService;
    }

    // POST API
    @PostMapping("/improve")
    public String improveEssay(@RequestBody EssayRequest request) {
        return aiService.improveEssay(request.getText());
    }
}