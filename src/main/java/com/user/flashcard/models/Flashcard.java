package com.user.flashcard.models;

public class Flashcard {
    private final String question;
    private final String answer;
    private int mistakes;
    private int correctAnswers;
    private int totalAttempts;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getMistakes() {
        return mistakes;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
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
    }
}
