package com.jobtrack.jobtrack.controller;



import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jobtrack.jobtrack.ai.GeminiService;
import com.jobtrack.jobtrack.dto.AnalyzeJobRequest;
import com.jobtrack.jobtrack.dto.JobAnalysis;
import com.jobtrack.jobtrack.dto.ResumeMatchRequest;
import com.jobtrack.jobtrack.dto.ResumeMatchResult;
import com.jobtrack.jobtrack.service.PdfService;

import jakarta.validation.Valid;



@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final GeminiService geminiService;
    private final PdfService pdfService;    

   public AiController(
        GeminiService geminiService,
        PdfService pdfService) {

    this.geminiService = geminiService;
    this.pdfService = pdfService;
}

    @GetMapping("/test")
    public String testGemini() {
        return geminiService.testGemini();
    }

    @PostMapping("/analyze")
public JobAnalysis analyzeJobDescription(
        @Valid @RequestBody AnalyzeJobRequest request) {

    return geminiService.analyzeJobDescription(
            request.getJobDescription()
    );
}
    @PostMapping("/match")
    public ResumeMatchResult matchResumeToJob(
        @Valid @RequestBody ResumeMatchRequest request) {

    return geminiService.matchResumeToJob(
            request.getResume(),
            request.getJobDescription()
    );
}

    @PostMapping(
        value = "/match-pdf",
        consumes = "multipart/form-data"
)
public ResumeMatchResult matchResumePdf(
        @RequestParam("resume") MultipartFile resume,
        @RequestParam("jobDescription") String jobDescription) {

    String resumeText = pdfService.extractText(resume);

    return geminiService.matchResumeToJob(
            resumeText,
            jobDescription
    );
}
}