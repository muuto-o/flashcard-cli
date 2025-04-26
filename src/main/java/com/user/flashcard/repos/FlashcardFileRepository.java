package com.user.flashcard.repos;

import com.user.flashcard.models.Flashcard;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class FlashcardFileRepository implements FlashcardRepository {
    @Override
    public List<Flashcard> loadFlashcards(String fileName) {
        List<Flashcard> cards = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("::");
                Flashcard card = new Flashcard(parts[0].trim(), parts[1].trim());
                if (parts.length >= 4) {
                    card.setMistakes(Integer.parseInt(parts[2].trim()));
                    card.setCorrectAnswers(Integer.parseInt(parts[3].trim()));
                    if (parts.length == 5 && !parts[4].trim().isEmpty()) {
                        card.setLastMistakeTime(LocalDateTime.parse(parts[4].trim())); // 🔥 Load lastMistakeTime
                    }
                }
                cards.add(card);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load flashcards: " + e.getMessage());
        }
        return cards;
    }

    @Override
    public void saveFlashcards(List<Flashcard> cards, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Flashcard card : cards) {
                String lastMistakeTimeStr = card.getLastMistakeTime() != null ? card.getLastMistakeTime().toString()
                        : "";
                writer.write(card.getQuestion() + "::" + card.getAnswer() + "::"
                        + card.getMistakes() + "::" + card.getCorrectAnswers() + "::" + lastMistakeTimeStr);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save flashcards: " + e.getMessage());
        }
    }

    @Override
    public void addFlashcard(Flashcard card, String fileName) {
        // TODO Auto-generated method stub
        List<Flashcard> cards = loadFlashcards(fileName);
        cards.add(card);
        saveFlashcards(cards, fileName);
    }

    @Override
    public void deleteFlashcard(Flashcard card, String fileName) {
        // TODO Auto-generated method stub
        List<Flashcard> cards = loadFlashcards(fileName);
        cards.removeIf(c -> c.getQuestion().equalsIgnoreCase(card.getQuestion())
                && c.getAnswer().equalsIgnoreCase(card.getAnswer()));
        saveFlashcards(cards, fileName);
    }
}