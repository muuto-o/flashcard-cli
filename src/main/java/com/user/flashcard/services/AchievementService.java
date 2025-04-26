package com.user.flashcard.services;

import com.user.flashcard.models.Flashcard;
import com.user.flashcard.models.achievement.Achievement;

import java.util.ArrayList;
import java.util.List;

public class AchievementService {
    private final List<Achievement> achievements;

    public AchievementService(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public List<String> evaluateAchievements(List<Flashcard> cards) {
        System.out.println("\n🏆 Achievements Unlocked:");
        boolean unlocked = false;
        List<String> unlockedList = new ArrayList<>();
        for (Achievement a : achievements) {
            if (a.isAchieved(cards)) {
                System.out.println("- " + a.getName());
                unlockedList.add(a.getName());
                unlocked = true;
            }
        }
        if (!unlocked)
            System.out.println("- None");

        return unlockedList;
    }
}
