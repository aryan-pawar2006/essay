package com.aryan.essay_improver.dto;

import java.util.List;

public class EssayResponse {

    private String corrected;
    private String improved;
    private List<String> suggestions;
    private int score;

    public String getCorrected() { return corrected; }
    public void setCorrected(String corrected) { this.corrected = corrected; }

    public String getImproved() { return improved; }
    public void setImproved(String improved) { this.improved = improved; }

    public List<String> getSuggestions() { return suggestions; }
    public void setSuggestions(List<String> suggestions) { this.suggestions = suggestions; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}