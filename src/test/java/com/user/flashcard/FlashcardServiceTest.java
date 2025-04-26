package com.user.flashcard;

import com.user.flashcard.models.Flashcard;
import com.user.flashcard.models.organizer.RandomSorter;
import com.user.flashcard.repos.FlashcardFileRepository;
import com.user.flashcard.services.FlashcardService;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlashcardServiceTest {

    @Test
    void testRunSession() {
        FlashcardFileRepository repo = new FlashcardFileRepository();
        FlashcardService service = new FlashcardService(repo, new RandomSorter());
        String fileName = "test_session_cards.txt";

        Flashcard card = new Flashcard("Q", "A");
        repo.saveFlashcards(List.of(card), fileName);

        // Mock user input (correct answer)
        System.setIn(new ByteArrayInputStream("A\n".getBytes()));

        List<Flashcard> sessionCards = service.runSession(fileName, 1, false);
        Flashcard updatedCard = sessionCards.get(0);
        assertEquals(1, updatedCard.getCorrectAnswers());

        // Cleanup
        new java.io.File(fileName).delete();
    }
}
