package com.jobtrack.jobtrack.dto;

import java.util.List;

public class ResumeMatchResult {

    private int matchScore;

    private List<String> matchedSkills;

    private List<String> missingSkills;

    private String experienceMatch;

    private List<String> recommendations;

    public ResumeMatchResult() {}

    public int getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(List<String> matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }

    public String getExperienceMatch() {
        return experienceMatch;
    }

    public void setExperienceMatch(String experienceMatch) {
        this.experienceMatch = experienceMatch;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }
}