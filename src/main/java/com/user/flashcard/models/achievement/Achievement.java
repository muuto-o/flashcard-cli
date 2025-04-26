package com.user.flashcard.models.achievement;

import com.user.flashcard.models.Flashcard;
import java.util.List;

public interface Achievement {
    String getName();

    boolean isAchieved(List<Flashcard> cards);
}
