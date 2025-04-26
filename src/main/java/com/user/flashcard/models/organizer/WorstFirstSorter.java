package com.user.flashcard.models.organizer;

import com.user.flashcard.models.Flashcard;
import java.util.*;

public class WorstFirstSorter implements CardOrganizer {
    @Override
    public List<Flashcard> organize(List<Flashcard> cards) {
        List<Flashcard> sorted = new ArrayList<>(cards);
        sorted.sort(Comparator.comparingInt(Flashcard::getMistakes).reversed());
        return sorted;
    }
}
