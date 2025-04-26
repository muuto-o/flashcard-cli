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
        cards = organizer.organize(cards);

        for (Flashcard card : cards) {
            System.out.println(card.getQuestion() + " : " + card.getLastMistakeTime());
        }

        Scanner scanner = new Scanner(System.in);
        for (Flashcard card : cards) {
            for (int i = 0; i < repetitions; i++) {
                boolean correct = askQuestion(scanner, card, invert);
                if (correct) {
                    card.recordCorrect();
                } else {
                    card.recordMistake();
                }
            }
        }

        repository.saveFlashcards(cards, fileName);
        System.out.println("\ud83d\udcbe Flashcards saved with updated stats!");

        return cards;
    }

    private boolean askQuestion(Scanner scanner, Flashcard card, boolean invert) {
        System.out.println(invert ? card.getAnswer() : card.getQuestion());
        String input = scanner.nextLine().trim();
        String expected = invert ? card.getQuestion() : card.getAnswer();
        if (input.equalsIgnoreCase(expected)) {
            System.out.println("\u2705 Correct!");
            return true;
        } else {
            System.out.println("\u274c Wrong! Correct answer: " + expected);
            return false;
        }
    }
}
