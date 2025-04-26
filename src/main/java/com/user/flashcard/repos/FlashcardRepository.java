package com.user.flashcard.repos;

import com.user.flashcard.models.Flashcard;
import java.util.List;

public interface FlashcardRepository {
    List<Flashcard> loadFlashcards(String fileName);

    void saveFlashcards(List<Flashcard> cards, String fileName);

    void addFlashcard(Flashcard card, String fileName);

    void deleteFlashcard(Flashcard card, String fileName);
}
