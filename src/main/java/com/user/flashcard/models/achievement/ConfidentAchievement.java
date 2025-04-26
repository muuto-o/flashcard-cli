package com.user.flashcard.models.achievement;

import java.util.List;
import com.user.flashcard.models.Flashcard;

public class ConfidentAchievement implements Achievement {
    @Override
    public String getName() {
        return "CONFIDENT";
    }

    @Override
    public boolean isAchieved(List<Flashcard> cards) {
        return cards.stream().anyMatch(c -> c.getCorrectAnswers() >= 3);
    }
}