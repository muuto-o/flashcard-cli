package com.user.flashcard.models.achievement;

import com.user.flashcard.models.Flashcard;
import java.util.List;

public class CorrectAchievement implements Achievement {
    @Override
    public String getName() {
        return "CORRECT";
    }

    @Override
    public boolean isAchieved(List<Flashcard> cards) {
        return cards.stream().allMatch(c -> c.getMistakes() == 0);
    }
}