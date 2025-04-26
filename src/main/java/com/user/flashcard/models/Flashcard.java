package com.user.flashcard.models;

import java.time.LocalDateTime;

public class Flashcard {
    private String question;
    private String answer;
    private int mistakes;
    private int correctAnswers;
    private int totalAttempts;
    private LocalDateTime lastMistakeTime;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public void recordCorrect() {
        correctAnswers++;
        totalAttempts++;
    }

    public void recordMistake() {
        mistakes++;
        totalAttempts++;
        lastMistakeTime = LocalDateTime.now();
        System.out.println("⏰ Mistake recorded for: " + question + " at " + lastMistakeTime);
    }

    public void setLastMistakeTime(LocalDateTime lastMistakeTime) {
        this.lastMistakeTime = lastMistakeTime;
    }

    public LocalDateTime getLastMistakeTime() {
        return lastMistakeTime;
    }
}
