package com.user.flashcard;

import com.user.flashcard.models.Flashcard;
import com.user.flashcard.models.achievement.*;
import com.user.flashcard.services.AchievementService;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AchievementServiceTest {

    @Test
    void testAchievementsUnlocked() {
        Flashcard card = new Flashcard("Q", "A");
        card.recordCorrect();

        AchievementService service = new AchievementService(List.of(
                new CorrectAchievement(),
                new RepeatAchievement(),
                new ConfidentAchievement()));

        List<String> achievements = service.evaluateAchievements(List.of(card));
        assertTrue(achievements.contains("CORRECT"));
    }
}
