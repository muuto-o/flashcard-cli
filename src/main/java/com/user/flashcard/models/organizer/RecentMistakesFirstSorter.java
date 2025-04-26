package com.user.flashcard.models.organizer;

import com.user.flashcard.models.Flashcard;
import java.util.*;

public class RecentMistakesFirstSorter implements CardOrganizer {
    @Override
    public List<Flashcard> organize(List<Flashcard> cards) {
        List<Flashcard> sorted = new ArrayList<>(cards);
        sorted.sort((a, b) -> {
            if (a.getMistakes() > 0 && b.getMistakes() == 0)
                return -1;
            if (b.getMistakes() > 0 && a.getMistakes() == 0)
                return 1;
            return 0;
        });
        return sorted;
    }
}
