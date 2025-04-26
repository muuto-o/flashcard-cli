package com.user.flashcard.models.organizer;

import com.user.flashcard.models.Flashcard;
import java.util.*;

public class WorstFirstSorter implements CardOrganizer {
    @Override
    public List<Flashcard> organize(List<Flashcard> cards) {
        List<Flashcard> sorted = new ArrayList<>(cards);
        System.out.println("🛠️ Sorting by mistakes (highest first):");
        for (Flashcard card : cards) {
            System.out.println(card.getQuestion() + " - Mistakes: " + card.getMistakes());
        }
        sorted.sort(Comparator.comparingInt(Flashcard::getMistakes).reversed());
        return sorted;
    }
}
