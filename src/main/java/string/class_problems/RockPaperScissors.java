package string.class_problems;

import java.util.Random;
import java.util.Scanner;

// Custom Unchecked Exception (extends RuntimeException)
class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(String message) {
        super(message);
    }
}

// Custom Checked Exception (extends Exception)
class GameInterruptedException extends Exception {
    public GameInterruptedException(String message) {
        super(message);
    }
}

public class RockPaperScissors {
    private static final String ROCK = "Rock";
    private static final String PAPER = "Paper";
    private static final String SCISSORS = "Scissors";
    private static final String[] VALID_MOVES = {ROCK, PAPER, SCISSORS};

    public static String playRound(String playerMove, String computerMove) {
        if (playerMove == null || computerMove == null) {
            throw new InvalidMoveException("Moves cannot be null.");
        }
        
        String pMove = playerMove.trim();
        String cMove = computerMove.trim();
        
        if (!isValidMove(pMove) || !isValidMove(cMove)) {
            throw new InvalidMoveException("Invalid move encountered in round evaluation: \"" + pMove + "\" or \"" + cMove + "\"");
        }

        if (pMove.equalsIgnoreCase(cMove)) {
            return "Draw";
        }

        switch (pMove.toLowerCase()) {
            case "rock":
                return cMove.equalsIgnoreCase(SCISSORS) ? "Player Wins" : "Computer Wins";
            case "paper":
                return cMove.equalsIgnoreCase(ROCK) ? "Player Wins" : "Computer Wins";
            case "scissors":
                return cMove.equalsIgnoreCase(PAPER) ? "Player Wins" : "Computer Wins";
            default:
                throw new InvalidMoveException("Unrecognized move: " + pMove);
        }
    }

    private static boolean isValidMove(String move) {
        for (String validMove : VALID_MOVES) {
            if (validMove.equalsIgnoreCase(move)) {
                return true;
            }
        }
        return false;
    }

    public static String generateComputerMove() {
        Random random = new Random();
        int index = random.nextInt(VALID_MOVES.length);
        return VALID_MOVES[index];
    }

    public static void runGameSession(Scanner scanner, int totalRounds) {
        System.out.println("\n=== Welcome to the Rock-Paper-Scissors College Arcade! ===");
        System.out.println("Rules: Type \"Rock\", \"Paper\", or \"Scissors\" for each round.");
        System.out.println("Type \"quit\" to exit the arcade at any point.\n");

        String[] playerMoves = new String[totalRounds];
        String[] computerMoves = new String[totalRounds];
        String[] results = new String[totalRounds];

        int wins = 0;
        int losses = 0;
        int draws = 0;
        int completedRounds = 0;

        for (int round = 1; round <= totalRounds; round++) {
            System.out.print("Round " + round + " - Enter your move: ");
            String playerInput = scanner.nextLine();

            if (playerInput.trim().equalsIgnoreCase("quit")) {
                try {
                    throw new GameInterruptedException("Player opted to quit the arcade session prematurely.");
                } catch (GameInterruptedException e) {
                    System.out.println("[Checked Exception Caught] " + e.getMessage());
                    break;
                }
            }

            try {
                if (!isValidMove(playerInput.trim())) {
                    throw new InvalidMoveException("The input \"" + playerInput + "\" is not a valid move (Rock, Paper, Scissors).");
                }

                String compMove = generateComputerMove();
                String outcome = playRound(playerInput.trim(), compMove);

                playerMoves[round - 1] = playerInput.trim();
                computerMoves[round - 1] = compMove;
                results[round - 1] = outcome;

                if (outcome.equals("Player Wins")) {
                    wins++;
                    System.out.println("Round " + round + " \u2014 Player: " + playerInput.trim() + ", Computer: " + compMove + " -> Player Wins!");
                } else if (outcome.equals("Computer Wins")) {
                    losses++;
                    System.out.println("Round " + round + " \u2014 Player: " + playerInput.trim() + ", Computer: " + compMove + " -> Computer Wins!");
                } else {
                    draws++;
                    System.out.println("Round " + round + " \u2014 Player: " + playerInput.trim() + ", Computer: " + compMove + " -> Draw!");
                }

                completedRounds++;
            } catch (InvalidMoveException e) {
                System.out.println("[Unchecked Exception Caught] Error: " + e.getMessage());
                System.out.println("Please try again for Round " + round + ".");
                round--;
            }
        }

        if (completedRounds > 0) {
            printSummaryTable(completedRounds, playerMoves, computerMoves, results);
            double winPercentage = ((double) wins / completedRounds) * 100.0;
            System.out.printf("\nFinal Summary (after %d rounds):\n", completedRounds);
            System.out.printf("Wins: %d | Losses: %d | Draws: %d | Win %% = %.1f%%\n", wins, losses, draws, winPercentage);
        } else {
            System.out.println("No rounds were completed.");
        }
    }

    private static void printSummaryTable(int completedRounds, String[] playerMoves, String[] computerMoves, String[] results) {
        System.out.println("\n--------------------------------------------------------------");
        System.out.printf("%-10s | %-15s | %-15s | %-15s\n", "Round", "Player Move", "Computer Move", "Result");
        System.out.println("--------------------------------------------------------------");
        for (int i = 0; i < completedRounds; i++) {
            System.out.printf("%-10d | %-15s | %-15s | %-15s\n", (i + 1), playerMoves[i], computerMoves[i], results[i]);
        }
        System.out.println("--------------------------------------------------------------");
    }
}
