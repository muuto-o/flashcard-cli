package com.user.flashcard;

import com.user.flashcard.cli.Cli;
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
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String file = "cards/cards.txt"; // Adjust your path
        String order = "random";
        int repetitions = 1;
        boolean invert = false;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--help" -> {
                    Cli.printHelp();
                    return;
                }
                default -> {
                    if (!args[i].startsWith("--")) {
                        file = args[i];
                    } else if (args[i].equals("--order") && i + 1 < args.length) {
                        order = args[++i];
                    } else if (args[i].equals("--repetitions") && i + 1 < args.length) {
                        repetitions = Integer.parseInt(args[++i]);
                    } else if (args[i].equals("--invertCards")) {
                        invert = true;
                    } else {
                        System.out.println("❌ Unknown option: " + args[i]);
                        Cli.printHelp();
                        return;
                    }
                }
            }
        }

        FlashcardRepository repo = new FlashcardFileRepository();
        CardOrganizer organizer = getOrganizer(order);
        FlashcardService flashcardService = new FlashcardService(repo, organizer);
        AchievementService achievementService = new AchievementService(List.of(
                new CorrectAchievement(),
                new RepeatAchievement(),
                new ConfidentAchievement()));

        while (true) {
            System.out.println("\n=== Flashcard System ===");
            System.out.println("Defaults: file=" + file + ", order=" + order + ", repetitions=" + repetitions
                    + ", invert=" + invert);
            System.out.println("1. Start Quiz Session");
            System.out.println("2. Change Defaults");
            System.out.println("3. Add Flashcard");
            System.out.println("4. Delete Flashcard");
            System.out.println("5. Edit Flashcard");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    List<Flashcard> cards = flashcardService.runSession(file, repetitions, invert);
                    achievementService.evaluateAchievements(cards);
                }
                case "2" -> {
                    System.out.print("Enter file path (current: " + file + "): ");
                    String newFile = scanner.nextLine().trim();
                    if (!newFile.isEmpty())
                        file = newFile;

                    System.out.print(
                            "Enter order [random, worst-first, recent-mistakes-first] (current: " + order + "): ");
                    String newOrder = scanner.nextLine().trim();
                    if (!newOrder.isEmpty()) {
                        order = newOrder;
                        organizer = getOrganizer(order);
                        flashcardService = new FlashcardService(repo, organizer); // Update organizer
                    }

                    System.out.print("Enter repetitions (current: " + repetitions + "): ");
                    String newReps = scanner.nextLine().trim();
                    if (!newReps.isEmpty())
                        repetitions = Integer.parseInt(newReps);

                    System.out.print("Invert cards? [true/false] (current: " + invert + "): ");
                    String newInvert = scanner.nextLine().trim();
                    if (!newInvert.isEmpty())
                        invert = Boolean.parseBoolean(newInvert);
                }
                case "3" -> System.out.println("Add Flashcard (to be implemented)");
                case "4" -> System.out.println("Delete Flashcard (to be implemented)");
                case "5" -> System.out.println("Edit Flashcard (to be implemented)");
                case "6" -> {
                    System.out.println("Exiting. Goodbye!");
                    return;
                }
                default -> System.out.println("❌ Invalid choice. Try again.");
            }
        }
    }

    // while (true) {
    // System.out.println("\n=== Flashcard System ===");
    // System.out.println("1. Start Quiz Session");
    // System.out.println("2. Add Flashcard");
    // System.out.println("3. Delete Flashcard");
    // System.out.println("4. Edit Flashcard");
    // System.out.println("5. Exit");
    // System.out.print("Choose an option: ");
    // String choice = scanner.nextLine();

    // switch (choice) {
    // // case "1" -> runQuizSession(scanner, repo, file);
    // case "2" -> Cli.addFlashcardCLI(scanner, repo, file);
    // case "3" -> Cli.deleteFlashcardCLI(scanner, repo, file);
    // case "4" -> Cli.editFlashcardCLI(scanner, repo, file);
    // case "5" -> {
    // System.out.println("Exiting. Goodbye!");
    // return;
    // }
    // default -> System.out.println("Invalid choice. Try again.");
    // }
    // }
    // }

    // private static void runQuizSession(Scanner scanner, FlashcardRepository repo,
    // String file) {
    // System.out.print("Choose order (random, worst-first, recent-mistakes-first):
    // ");
    // String order = scanner.nextLine();
    // System.out.print("How many repetitions per card? ");
    // int reps = Integer.parseInt(scanner.nextLine());

    // FlashcardService flashcardService = new FlashcardService(repo, organizer);
    // AchievementService achievementService = new AchievementService(List.of(
    // new CorrectAchievement(),
    // new RepeatAchievement(),
    // new ConfidentAchievement()));

    // List<Flashcard> cards = flashcardService.runSession(file, reps, false);
    // achievementService.evaluateAchievements(cards);
    // }

    private static CardOrganizer getOrganizer(String order) {
        return switch (order) {
            case "worst-first" -> new WorstFirstSorter();
            case "recent-mistakes-first" -> new RecentMistakesFirstSorter();
            default -> new RandomSorter();
        };

    }

}
