package com.user.flashcard.services;

import com.user.flashcard.models.*;
import com.user.flashcard.models.organizer.CardOrganizer;
import com.user.flashcard.repos.FlashcardRepository;

import java.util.List;
import java.util.Scanner;

public class FlashcardService {
    static Scanner scanner = new Scanner(System.in);
    private final FlashcardRepository repository;
    private final CardOrganizer organizer;

    public FlashcardService(FlashcardRepository repository, CardOrganizer organizer) {
        this.repository = repository;
        this.organizer = organizer;
    }

    public List<Flashcard> runSession(String fileName, int repetitions, boolean invert) {
        List<Flashcard> cards = repository.loadFlashcards(fileName);
        List<Flashcard> organized = organizer.organize(cards);

        for (Flashcard card : organized) {
            int correctCount = 0;
            while (correctCount < repetitions) {
                System.out.println(invert ? card.getAnswer() : card.getQuestion());
                String input = scanner.nextLine();
                String expected = invert ? card.getQuestion() : card.getAnswer();
                if (input.equalsIgnoreCase(expected)) {
                    System.out.println("✅ Correct!");
                    card.recordCorrect();
                    correctCount++;
                } else {
                    System.out.println("❌ Wrong! Correct answer: " + expected);
                    card.recordMistake();
                }
            }
        }
        return cards; // Return for achievements evaluation
    }
}
