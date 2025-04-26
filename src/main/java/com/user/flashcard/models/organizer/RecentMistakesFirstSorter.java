package com.user.flashcard.models.organizer;

import com.user.flashcard.models.Flashcard;
import java.util.*;

public class RecentMistakesFirstSorter implements CardOrganizer {
    @Override
    public List<Flashcard> organize(List<Flashcard> cards) {
        return cards.stream()
                .sorted(Comparator.comparing(
                        Flashcard::getLastMistakeTime,
                        Comparator.nullsLast(Comparator.reverseOrder()) //
                ))
                .toList();
    }
}
