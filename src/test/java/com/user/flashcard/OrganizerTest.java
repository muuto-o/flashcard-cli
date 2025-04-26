package com.user.flashcard;

import com.user.flashcard.models.Flashcard;
import org.junit.jupiter.api.Test;

import java.util.List;
import com.user.flashcard.models.organizer.CardOrganizer;
import com.user.flashcard.models.organizer.RandomSorter;
import com.user.flashcard.models.organizer.RecentMistakesFirstSorter;
import com.user.flashcard.models.organizer.WorstFirstSorter;

import static org.junit.jupiter.api.Assertions.*;

class OrganizerTest {

    @Test
    void testRandomSorter() {
        CardOrganizer sorter = new RandomSorter();
        List<Flashcard> cards = List.of(new Flashcard("Q1", "A1"), new Flashcard("Q2", "A2"));
        List<Flashcard> sorted = sorter.organize(cards);
        assertEquals(2, sorted.size());
    }

    @Test
    void testWorstFirstSorter() {
        CardOrganizer sorter = new WorstFirstSorter();
        Flashcard c1 = new Flashcard("Q1", "A1");
        c1.setMistakes(3);
        Flashcard c2 = new Flashcard("Q2", "A2");
        c2.setMistakes(1);
        List<Flashcard> sorted = sorter.organize(List.of(c1, c2));
        assertEquals("Q1", sorted.get(0).getQuestion());
    }

    @Test
    void testRecentMistakesFirstSorter() {
        CardOrganizer sorter = new RecentMistakesFirstSorter();
        Flashcard c1 = new Flashcard("Q1", "A1");
        c1.setLastMistakeTime(null); // Never missed
        Flashcard c2 = new Flashcard("Q2", "A2");
        c2.recordMistake(); // Missed now
        List<Flashcard> sorted = sorter.organize(List.of(c1, c2));
        assertEquals("Q2", sorted.get(0).getQuestion());
    }
}
