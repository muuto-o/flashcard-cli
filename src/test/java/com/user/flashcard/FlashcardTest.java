package com.user.flashcard;

import org.junit.jupiter.api.Test;

import com.user.flashcard.models.Flashcard;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FlashcardTest {

    @Test
    void testGettersAndSetters() {
        Flashcard card = new Flashcard("Q", "A");
        card.setQuestion("New Q");
        card.setAnswer("New A");
        card.setMistakes(3);
        card.setCorrectAnswers(2);
        card.setLastMistakeTime(LocalDateTime.now());

        assertEquals("New Q", card.getQuestion());
        assertEquals("New A", card.getAnswer());
        assertEquals(3, card.getMistakes());
        assertEquals(2, card.getCorrectAnswers());
        assertNotNull(card.getLastMistakeTime());
    }

    @Test
    void testRecordCorrect() {
        Flashcard card = new Flashcard("Q", "A");
        card.recordCorrect();
        assertEquals(1, card.getCorrectAnswers());
        assertEquals(1, card.getTotalAttempts());
    }

    @Test
    void testRecordMistake() {
        Flashcard card = new Flashcard("Q", "A");
        card.recordMistake();
        assertEquals(1, card.getMistakes());
        assertEquals(1, card.getTotalAttempts());
        assertNotNull(card.getLastMistakeTime());
    }
}
