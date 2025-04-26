package com.user.flashcard.models.achievement;

import com.user.flashcard.models.Flashcard;
import java.util.List;

public class RepeatAchievement implements Achievement {
    @Override
    public String getName() {
        return "REPEAT";
    }

    @Override
    public boolean isAchieved(List<Flashcard> cards) {
        return cards.stream().anyMatch(c -> c.getTotalAttempts() >= 5);
    }
}