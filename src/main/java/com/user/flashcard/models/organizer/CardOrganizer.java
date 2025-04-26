package com.user.flashcard.models.organizer;

import com.user.flashcard.models.Flashcard;
import java.util.List;

public interface CardOrganizer {
    List<Flashcard> organize(List<Flashcard> cards);
}
