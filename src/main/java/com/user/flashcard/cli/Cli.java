package com.user.flashcard.cli;

import java.util.List;
import java.util.Scanner;

import com.user.flashcard.models.Flashcard;
import com.user.flashcard.repos.FlashcardRepository;

public class Cli {

    public static void printHelp() {
        System.out.println("Usage: flashcard <cards-file> [options]");
        System.out.println("Options:");
        System.out.println("  --help              Show this help message");
        System.out.println(
                "  --order <order>     Sorting order: random, worst-first, recent-mistakes-first (default: random)");
        System.out.println("  --repetitions <n>   Number of repetitions per card (default: 1)");
        System.out.println("  --invertCards       Invert question and answer");
    }

    public static void addFlashcardCLI(Scanner scanner, FlashcardRepository repo, String file) {
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

    public static void deleteFlashcardCLI(Scanner scanner, FlashcardRepository repo, String file) {
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

    public static void editFlashcardCLI(Scanner scanner, FlashcardRepository repo, String file) {
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
