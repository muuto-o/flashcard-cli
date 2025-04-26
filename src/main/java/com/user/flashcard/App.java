package com.user.flashcard;

import com.user.flashcard.models.Flashcard;
import com.user.flashcard.models.achievement.*;
import com.user.flashcard.models.organizer.*;
import com.user.flashcard.repos.*;
import com.user.flashcard.services.*;

import java.util.List;
import java.util.Scanner;

public class App {
    // public static void main(String[] args) {
    // if (args.length == 0 || args[0].equals("--help")) {
    // System.out.println(
    // "Usage: java -jar flashcard-system.jar <cards-file> [--order
    // random|worst-first|recent-mistakes-first] [--repetitions n]
    // [--invertCards]");
    // return;
    // }

    // String file = args[0];
    // String order = "random";
    // int reps = 1;
    // boolean invert = false;

    // for (int i = 1; i < args.length; i++) {
    // switch (args[i]) {
    // case "--order" -> order = args[++i];
    // case "--repetitions" -> reps = Integer.parseInt(args[++i]);
    // case "--invertCards" -> invert = true;
    // }
    // }

    // FlashcardRepository repo = new FlashcardFileRepository();
    // CardOrganizer organizer = switch (order) {
    // case "worst-first" -> new WorstFirstSorter();
    // case "recent-mistakes-first" -> new RecentMistakesFirstSorter();
    // default -> new RandomSorter();
    // };

    // FlashcardService flashcardService = new FlashcardService(repo, organizer);
    // AchievementService achievementService = new AchievementService(List.of(
    // new CorrectAchievement(),
    // new RepeatAchievement(),
    // new ConfidentAchievement()));

    // List<Flashcard> cards = flashcardService.runSession(file, reps, invert);
    // achievementService.evaluateAchievements(cards);
    // }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlashcardRepository repo = new FlashcardFileRepository();
        String file = "cards/cards.txt"; // Adjust your path

        while (true) {
            System.out.println("\n=== Flashcard System ===");
            System.out.println("1. Start Quiz Session");
            System.out.println("2. Add Flashcard");
            System.out.println("3. Delete Flashcard");
            System.out.println("4. Edit Flashcard");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> runQuizSession(scanner, repo, file);
                case "2" -> addFlashcardCLI(scanner, repo, file);
                case "3" -> deleteFlashcardCLI(scanner, repo, file);
                case "4" -> editFlashcardCLI(scanner, repo, file);
                case "5" -> {
                    System.out.println("Exiting. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void runQuizSession(Scanner scanner, FlashcardRepository repo, String file) {
        System.out.print("Choose order (random, worst-first, recent-mistakes-first): ");
        String order = scanner.nextLine();
        System.out.print("How many repetitions per card? ");
        int reps = Integer.parseInt(scanner.nextLine());

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

        List<Flashcard> cards = flashcardService.runSession(file, reps, false);
        achievementService.evaluateAchievements(cards);
    }

    private static void addFlashcardCLI(Scanner scanner, FlashcardRepository repo, String file) {
        String question;
        do {
            System.out.print("Enter question: ");
            question = scanner.nextLine().trim();
            if (question.isEmpty())
                System.out.println("❌ Question cannot be empty.");
        } while (question.isEmpty());

        String answer;
        do {
            System.out.print("Enter answer: ");
            answer = scanner.nextLine().trim();
            if (answer.isEmpty())
                System.out.println("❌ Answer cannot be empty.");
        } while (answer.isEmpty());

        Flashcard newCard = new Flashcard(question, answer);
        repo.addFlashcard(newCard, file);
        System.out.println("✅ Flashcard added!");
    }

    private static void deleteFlashcardCLI(Scanner scanner, FlashcardRepository repo, String file) {
        List<Flashcard> cards = repo.loadFlashcards(file);
        if (cards.isEmpty()) {
            System.out.println("❌ No flashcards available to delete.");
            return;
        }

        System.out.println("\n📝 Flashcards:");
        for (int i = 0; i < cards.size(); i++) {
            System.out.println((i + 1) + ". " + cards.get(i).getQuestion() + " :: " + cards.get(i).getAnswer());
        }

        int index = -1;
        do {
            System.out.print("Enter the number of the flashcard to delete (0 to cancel): ");
            String input = scanner.nextLine().trim();
            try {
                index = Integer.parseInt(input);
                if (index < 0 || index > cards.size()) {
                    System.out.println("❌ Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        } while (index < 0 || index > cards.size());

        if (index == 0) {
            System.out.println("❌ Deletion cancelled.");
            return;
        }

        Flashcard cardToDelete = cards.get(index - 1);
        repo.deleteFlashcard(cardToDelete, file);
        System.out.println("✅ Flashcard deleted!");
    }

    private static void editFlashcardCLI(Scanner scanner, FlashcardRepository repo, String file) {
        List<Flashcard> cards = repo.loadFlashcards(file);
        if (cards.isEmpty()) {
            System.out.println("❌ No flashcards available to edit.");
            return;
        }

        System.out.println("\n📝 Flashcards:");
        for (int i = 0; i < cards.size(); i++) {
            System.out.println((i + 1) + ". " + cards.get(i).getQuestion() + " :: " + cards.get(i).getAnswer());
        }

        int index = -1;
        do {
            System.out.print("Enter the number of the flashcard to edit (0 to cancel): ");
            String input = scanner.nextLine().trim();
            try {
                index = Integer.parseInt(input);
                if (index < 0 || index > cards.size()) {
                    System.out.println("❌ Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        } while (index < 0 || index > cards.size());

        if (index == 0) {
            System.out.println("❌ Edit cancelled.");
            return;
        }

        Flashcard cardToEdit = cards.get(index - 1);

        System.out.print("Enter new question (leave blank to keep current): ");
        String newQuestion = scanner.nextLine().trim();
        if (!newQuestion.isEmpty()) {
            cardToEdit.setQuestion(newQuestion);
        }

        System.out.print("Enter new answer (leave blank to keep current): ");
        String newAnswer = scanner.nextLine().trim();
        if (!newAnswer.isEmpty()) {
            cardToEdit.setAnswer(newAnswer);
        }

        repo.saveFlashcards(cards, file);
        System.out.println("✅ Flashcard updated!");
    }

}
