package com.user.flashcard.repos;

import com.user.flashcard.models.Flashcard;
import java.io.*;
import java.util.*;

public class FlashcardFileRepository implements FlashcardRepository {
    @Override
    public List<Flashcard> loadFlashcards(String fileName) {
        List<Flashcard> cards = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                String[] parts = line.split("::");
                if (parts.length != 2)
                    throw new RuntimeException("Invalid format: " + line);
                cards.add(new Flashcard(parts[0].trim(), parts[1].trim()));
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
                writer.write(card.getQuestion() + "::" + card.getAnswer());
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