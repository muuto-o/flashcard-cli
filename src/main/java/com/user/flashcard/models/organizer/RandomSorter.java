package com.user.flashcard.models.organizer;

import com.user.flashcard.models.Flashcard;
import java.util.*;

public class RandomSorter implements CardOrganizer {
    @Override
    public List<Flashcard> organize(List<Flashcard> cards) {
        List<Flashcard> shuffled = new ArrayList<>(cards);
        Collections.shuffle(shuffled);
        return shuffled;
    }
}
