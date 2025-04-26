package com.user.flashcard;

import com.user.flashcard.models.Flashcard;
import com.user.flashcard.repos.FlashcardFileRepository;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlashcardFileRepositoryTest {

    @Test
    void testSaveAndLoadFlashcards() {
        FlashcardFileRepository repo = new FlashcardFileRepository();
        String fileName = "test_cards.txt";

        Flashcard card = new Flashcard("Test Q", "Test A");
        card.setMistakes(2);
        card.setCorrectAnswers(1);
        card.setLastMistakeTime(LocalDateTime.now());

        repo.saveFlashcards(List.of(card), fileName);
        List<Flashcard> loadedCards = repo.loadFlashcards(fileName);

        assertEquals(1, loadedCards.size());
        Flashcard loadedCard = loadedCards.get(0);
        assertEquals("Test Q", loadedCard.getQuestion());
        assertEquals("Test A", loadedCard.getAnswer());
        assertEquals(2, loadedCard.getMistakes());
        assertEquals(1, loadedCard.getCorrectAnswers());
        assertNotNull(loadedCard.getLastMistakeTime());

        // Clean up
        new File(fileName).delete();
    }
}
