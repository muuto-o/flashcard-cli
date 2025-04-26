package com.user.flashcard;

import com.user.flashcard.models.Flashcard;
import com.user.flashcard.models.achievement.*;
import com.user.flashcard.models.organizer.*;
import com.user.flashcard.repos.*;
import com.user.flashcard.services.*;

import java.util.List;

public class App {
    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("--help")) {
            System.out.println(
                    "Usage: java -jar flashcard-system.jar <cards-file> [--order random|worst-first|recent-mistakes-first] [--repetitions n] [--invertCards]");
            return;
        }

        String file = args[0];
        String order = "random";
        int reps = 1;
        boolean invert = false;

        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--order" -> order = args[++i];
                case "--repetitions" -> reps = Integer.parseInt(args[++i]);
                case "--invertCards" -> invert = true;
            }
        }

        FlashcardRepository repo = new FlashcardFileRepository();
        CardOrganizer organizer = switch (order) {
            case "worst-first" -> new WorstFirstSorter();
            case "recent-mistakes-first" -> new RecentMistakesFirstSorter();
            default -> new RandomSorter();
        };

        FlashcardService flashcardService = new FlashcardService(repo, organizer);
        AchievementService achievementService = new AchievementService(List.of(
                new CorrectAchievement(),
                new RepeatAchievement(),
                new ConfidentAchievement()));

        List<Flashcard> cards = flashcardService.runSession(file, reps, invert);
        achievementService.evaluateAchievements(cards);
    }
}
