package com.jobtrack.jobtrack.ai;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Schema;
import com.google.genai.types.Type;
import com.jobtrack.jobtrack.dto.JobAnalysis;
import com.jobtrack.jobtrack.dto.ResumeMatchResult;


@Service
public class GeminiService {

    private final Client client;
    private final ObjectMapper objectMapper;

    public GeminiService() {

        this.objectMapper = new ObjectMapper();

        this.client = Client.builder()
                .apiKey(System.getenv("GEMINI_API_KEY"))
                .build();
    }

    public String testGemini() {

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-3.5-flash-lite",
                        "Explain Spring Boot in one sentence.",
                        null
                );

        return response.text();
    }

    public JobAnalysis analyzeJobDescription(String jobDescription) {

        String prompt = """
                Analyze the following job description.

                Extract the information and return it according to the
                provided JSON schema.

                Important:
                - Do not invent requirements that are not present.
                - If a field is not mentioned, return an empty string or empty array.
                - Keep the summary concise.

                Job Description:
                %s
                """.formatted(jobDescription);

        Schema schema =
                Schema.builder()
                        .type(Type.Known.OBJECT)
                        .properties(
                                java.util.Map.of(

                                        "summary",
                                        Schema.builder()
                                                .type(Type.Known.STRING)
                                                .build(),

                                        "requiredSkills",
                                        Schema.builder()
                                                .type(Type.Known.ARRAY)
                                                .items(
                                                        Schema.builder()
                                                                .type(Type.Known.STRING)
                                                                .build()
                                                )
                                                .build(),

                                        "preferredSkills",
                                        Schema.builder()
                                                .type(Type.Known.ARRAY)
                                                .items(
                                                        Schema.builder()
                                                                .type(Type.Known.STRING)
                                                                .build()
                                                )
                                                .build(),

                                        "experience",
                                        Schema.builder()
                                                .type(Type.Known.STRING)
                                                .build(),

                                        "technologies",
                                        Schema.builder()
                                                .type(Type.Known.ARRAY)
                                                .items(
                                                        Schema.builder()
                                                                .type(Type.Known.STRING)
                                                                .build()
                                                )
                                                .build(),

                                        "keywords",
                                        Schema.builder()
                                                .type(Type.Known.ARRAY)
                                                .items(
                                                        Schema.builder()
                                                                .type(Type.Known.STRING)
                                                                .build()
                                                )
                                                .build()
                                )
                        )
                        .required(
                                java.util.List.of(
                                        "summary",
                                        "requiredSkills",
                                        "preferredSkills",
                                        "experience",
                                        "technologies",
                                        "keywords"
                                )
                        )
                        .build();

        GenerateContentConfig config =
                GenerateContentConfig.builder()
                        .responseMimeType("application/json")
                        .candidateCount(1)
                        .responseSchema(schema)
                        .build();

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-3.5-flash-lite",
                        prompt,
                        config
                );

        try {
            return objectMapper.readValue(
                    response.text(),
                    JobAnalysis.class
            );
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                    "Failed to parse AI response",
                    e
            );
        }
    }
        
        public ResumeMatchResult matchResumeToJob(
        String resume,
        String jobDescription) {

    String prompt = """
            Compare the resume with the job description.

            Analyze only the information provided.
            Do not invent skills, experience, or qualifications.

            Determine:
            1. A match score from 0 to 100 based on how well the resume
               matches the requirements in the job description.
            2. Skills that appear in both the resume and job description.
            3. Important skills required by the job description that are
               missing from the resume.
            4. How the candidate's stated experience compares with the
               experience requirements in the job description.
            5. Practical recommendations for improving the candidate's
               match.

            Resume:
            %s

            Job Description:
            %s
            """.formatted(resume, jobDescription);

    Schema schema =
            Schema.builder()
                    .type(Type.Known.OBJECT)
                    .properties(
                            java.util.Map.of(

                                    "matchScore",
                                    Schema.builder()
                                            .type(Type.Known.INTEGER)
                                            .build(),

                                    "matchedSkills",
                                    Schema.builder()
                                            .type(Type.Known.ARRAY)
                                            .items(
                                                    Schema.builder()
                                                            .type(Type.Known.STRING)
                                                            .build()
                                            )
                                            .build(),

                                    "missingSkills",
                                    Schema.builder()
                                            .type(Type.Known.ARRAY)
                                            .items(
                                                    Schema.builder()
                                                            .type(Type.Known.STRING)
                                                            .build()
                                            )
                                            .build(),

                                    "experienceMatch",
                                    Schema.builder()
                                            .type(Type.Known.STRING)
                                            .build(),

                                    "recommendations",
                                    Schema.builder()
                                            .type(Type.Known.ARRAY)
                                            .items(
                                                    Schema.builder()
                                                            .type(Type.Known.STRING)
                                                            .build()
                                            )
                                            .build()
                            )
                    )
                    .required(
                            java.util.List.of(
                                    "matchScore",
                                    "matchedSkills",
                                    "missingSkills",
                                    "experienceMatch",
                                    "recommendations"
                            )
                    )
                    .build();

    GenerateContentConfig config =
            GenerateContentConfig.builder()
                    .responseMimeType("application/json")
                    .candidateCount(1)
                    .responseSchema(schema)
                    .build();

    GenerateContentResponse response =
            client.models.generateContent(
                    "gemini-3.5-flash-lite",
                    prompt,
                    config
            );

    try {
        return objectMapper.readValue(
                response.text(),
                ResumeMatchResult.class
        );
    } catch (JsonProcessingException e) {
        throw new IllegalStateException(
                "Failed to parse AI resume match response",
                e
        );
        }
    }
    }

