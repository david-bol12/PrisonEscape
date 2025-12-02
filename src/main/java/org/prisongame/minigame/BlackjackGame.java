package org.prisongame.minigame;
import org.prisongame.character.Player;
import org.prisongame.commands.Command;
import org.prisongame.ui.Input;
import org.prisongame.ui.Output;
import org.prisongame.ui.SoundController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Represents a playing card.
 */
class Card {
    private final String rank;
    private final String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    /**
     * Returns the base value of the card (Ace=11 initially, Face cards=10).
     */
    public int getValue() {
        return switch (rank) {
            case "Ace" -> 11;
            case "King", "Queen", "Jack" -> 10;
            default -> Integer.parseInt(rank);
        };
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

/**
 * Represents the deck of cards, capable of shuffling and dealing.
 */
class Deck {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        // Populate the deck with 52 cards
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    /**
     * Shuffles the deck randomly.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Deals the top card from the deck.
     * @return The Card dealt.
     */
    public Card dealCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }
}

/**
 * Contains the main game logic for Blackjack, refactored to use Output.println()
 * for all display text, strictly separating logic from presentation.
 */
public class BlackjackGame extends Minigame {

    @Override
    public Minigame processCommand(Command command) throws InterruptedException {

        if (state == GameState.BET) {
            int bet;
            if (command.getCommandWord().equalsIgnoreCase("quit")) {
                SoundController.stopPlayer();
                return null;
            }
            try {
                bet = Integer.parseInt(command.getCommandWord());
            } catch (Exception e) {
                output.println("Invalid Bet");
                output.println("Please enter a bet or enter 'quit' ");
                return this;
            }
            if (player.getMoney() < bet) {
                output.println("Insufficient Funds");
                output.println("Please enter a bet or enter 'quit' ");
                return this;
            }
            this.currentBet = bet;
            result = startGame(currentBet);
        } else if (state == GameState.PLAYER_TURN) {
            result = processPlayerMove(command.getCommandWord());
        }
         // The method run on each input
        // 4. Check if the game resulted in a final state
        if (result != null) {
            // Game is over: handle the payout and transition out of the minigame
            if (state == GameState.QUERY) {
                if (command.getCommandWord().equalsIgnoreCase("y")) {
                    return new BlackjackGame(output, input, player);
                }
                output.println("OK Goodbye!");
                SoundController.stopPlayer();
                return null;
            }

            output.println((result.amountWon < 0 ? "You Lose: " : "You Win: ") + "$" + Math.abs(result.amountWon));
            player.setMoney(player.getMoney() + result.amountWon);
            output.println("Play again?");

            state = GameState.QUERY;
        }
        return this;
        // }
    }

    /**
     * Helper class to hold the final game result and monetary outcome.
     */
    public static class GameResult {
        public final String status;   // WIN, LOSS, PUSH, PLAYER_BLACKJACK, DEALER_BLACKJACK, BUST
        public final int amountWon; // Positive for profit, negative for loss, 0 for push.

        public GameResult(String status, int amountWon) {
            this.status = status;
            this.amountWon = amountWon;
        }
    }

    private enum GameState {
        PLAYER_TURN, DEALER_TURN, GAME_OVER, QUERY, BET
    }

    private final Deck deck;
    private final List<Card> playerHand;
    private final List<Card> dealerHand;
    private GameState state;
    private int currentBet;
    private GameResult result;
    private final int DELAY_TIME = 1500;

    public BlackjackGame(Output output, Input input, Player player) {
        super(output, input, player);
        deck = new Deck();
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        this.state = GameState.BET;
        this.currentBet = 0;
        this.result = null;
        output.println("Please enter a bet or enter 'quit' ");
        if (!SoundController.getIsPlaying()) {
            SoundController.playMusic(SoundController.Sound.BLACKJACK_MUSIC);
            SoundController.playSFX(SoundController.Sound.BLACKJACK_START);
        }
    }

    /**
     * Calculates the value of a hand, correctly handling Aces (11 or 1).
     *
     * @param hand The list of cards to score.
     * @return The calculated score.
     */
    public int calculateHandValue(List<Card> hand) {
        int value = 0;
        int aceCount = 0;

        for (Card card : hand) {
            value += card.getValue();
            if (card.getRank().equals("Ace")) {
                aceCount++;
            }
        }

        // Adjust for Aces if the total value exceeds 21
        while (value > 21 && aceCount > 0) {
            value -= 10; // Change Ace value from 11 to 1
            aceCount--;
        }

        return value;
    }

    /**
     * Prints the player's hand and score using output.println().
     */
    public void displayPlayerHand() throws InterruptedException {
        output.println("\n--- Your Hand ---");
        for (Card card : playerHand) {
            output.println("  - " + card);
        }
        output.println("Total Value: " + calculateHandValue(playerHand));
        input.disablePeriod(DELAY_TIME);
    }

    /**
     * Prints the dealer's hand using output.println().
     *
     * @param initialDeal If true, hides the second card (hole card).
     */
    public void displayDealerHand(boolean initialDeal) throws InterruptedException {
        output.println("\n--- Dealer's Hand ---");

        // Always show the first card (up card)
        output.println("  - " + dealerHand.get(0));

        if (initialDeal) {
            output.println("  - [Hidden Card]");
        } else {
            // Full display
            for (int i = 1; i < dealerHand.size(); i++) {
                output.println("  - " + dealerHand.get(i));
            }
            output.println("Total Value: " + calculateHandValue(dealerHand));
        }
        input.disablePeriod(DELAY_TIME);
    }

    /**
     * Initializes a new game round, deals initial cards, sets the bet,
     * and prints the starting status.
     *
     * @param bet The amount the player is wagering for the round.
     * @return null if the game continues (PLAYER_TURN), or the final GameResult
     * if an immediate Blackjack ends the game.
     */
    public GameResult startGame(int bet) throws InterruptedException {
        if (bet <= 0) {
            output.println("Error: Bet must be a positive amount.");
            return new GameResult("ERROR", 0);
        }
        this.currentBet = bet;

        deck.shuffle();
        playerHand.clear();
        dealerHand.clear();

        // 1. Initial Deal (Player, Dealer, Player, Dealer)
        playerHand.add(deck.dealCard());
        dealerHand.add(deck.dealCard());
        playerHand.add(deck.dealCard());
        dealerHand.add(deck.dealCard());

        output.println("Bet placed: " + currentBet);
        input.disablePeriod(DELAY_TIME);

        state = GameState.PLAYER_TURN;

        // Check for immediate Blackjack
        if (calculateHandValue(playerHand) == 21 || calculateHandValue(dealerHand) == 21) {
            this.state = GameState.DEALER_TURN;
            displayDealerHand(false); // Reveal dealer hand immediately
            displayPlayerHand();

            // Finalize immediately
            return determineWinnerResult();
        }

        // Return initial state
        displayDealerHand(true);
        displayPlayerHand();
        output.println("\nIt is your turn. Enter 'H' to Hit or 'S' to Stand.");

        return null; // Game continues
    }

    /**
     * Handles the player's move (Hit or Stand).
     * This method is called repeatedly by the external game (e.g., in a Flow's onNext)
     * until the round ends.
     *
     * @param choice "H" for Hit, "S" for Stand.
     * @return The final GameResult if the game is over, or null if the game continues.
     */
    public GameResult processPlayerMove(String choice) throws InterruptedException { // Method name updated to processPlayerMove
        if (state != GameState.PLAYER_TURN) {
            output.println("Error: Game is not currently in the player's turn phase.");
            return null;
        }
        if (choice.equalsIgnoreCase("H")) {
            // Player Hits
            Card newCard = deck.dealCard();
            playerHand.add(newCard);

            output.println("You drew: " + newCard);
            displayPlayerHand();

            if (calculateHandValue(playerHand) > 21) {
                // Player Busts - Game over
                this.state = GameState.GAME_OVER;
                input.disablePeriod(1000);
                output.println("\nBust! Your score is " + calculateHandValue(playerHand) + ".");
                return determineWinnerResult();
            }
            // Player continues
            output.println("\nDo you want to (H)it or (S)tand?");
            return null;

        } else if (choice.equalsIgnoreCase("S")) {
            // Player Stands - Move to dealer turn
            this.state = GameState.DEALER_TURN;
            output.println("You stand with a score of " + calculateHandValue(playerHand) + ".");
            return processDealerTurn();
        } else {
            output.println("Invalid action. Please enter 'H' (Hit) or 'S' (Stand).");
            return null;
        }
    }

    /**
     * Processes the Dealer's turn automatically once the player stands or busts.
     *
     * @return The final GameResult.
     */
    public GameResult processDealerTurn() throws InterruptedException {
        if (state != GameState.DEALER_TURN) {
            output.println("\nError: Cannot process dealer turn in the current state.");
            return new GameResult("ERROR", 0);
        }

        output.println("\n--- Dealer's Turn ---");
        displayDealerHand(false); // Reveal hole card

        // Dealer hits until score is 17 or more
        while (calculateHandValue(dealerHand) < 17) {
            output.println("Dealer hits.");
            Card newCard = deck.dealCard();
            dealerHand.add(newCard);
            output.println("Dealer drew: " + newCard);

            // Re-display full dealer hand after drawing
            displayDealerHand(false);

            if (calculateHandValue(dealerHand) > 21) {
                output.println("Dealer busts with a score of " + calculateHandValue(dealerHand) + "!");
                break;
            }
        }

        if (calculateHandValue(dealerHand) <= 21) {
            output.println("Dealer stands with a score of " + calculateHandValue(dealerHand) + ".");
        }

        // Game ends after dealer turn
        this.state = GameState.GAME_OVER;
        input.disablePeriod(DELAY_TIME);
        return determineWinnerResult();
    }

    /**
     * Compares hands and returns the GameResult object, calculating the payout.
     *
     * @return A GameResult object containing the status and amount won/lost.
     */
    public GameResult determineWinnerStatus() {
        int playerScore = calculateHandValue(playerHand);
        int dealerScore = calculateHandValue(dealerHand);

        // 1. Check for Player Bust
        if (playerScore > 21) {
            return new GameResult("BUST", -currentBet);
        }

        // 2. Check for Blackjack situations (only on initial deal)
        if (playerHand.size() == 2 && playerScore == 21) {
            if (dealerHand.size() == 2 && dealerScore == 21) {
                // Both Blackjack (Push)
                return new GameResult("PUSH", 0);
            } else {
                int payout =  (currentBet * 2);
                return new GameResult("PLAYER_BLACKJACK", payout);
            }
        }
        if (dealerHand.size() == 2 && dealerScore == 21) {
            // Dealer Blackjack (Player Loses)
            return new GameResult("DEALER_BLACKJACK", -currentBet);
        }

        // 3. Final Comparison (After all moves)
        if (dealerScore > 21) {
            return new GameResult("WIN", currentBet *2); // Dealer busted
        } else if (playerScore > dealerScore) {
            return new GameResult("WIN", currentBet *2);
        } else if (dealerScore > playerScore) {
            return new GameResult("LOSS", -currentBet);
        } else {
            return new GameResult("PUSH", 0); // Tie
        }
    }

    /**
     * Compares hands, prints the final result message, and returns the result object.
     *
     * @return The final GameResult.
     */
    private GameResult determineWinnerResult() throws InterruptedException {
        input.disablePeriod(DELAY_TIME);
        GameResult result = determineWinnerStatus();

        output.println("\n======== FINAL RESULTS ========");
        output.println("Your Score: " + calculateHandValue(playerHand));
        output.println("Dealer Score: " + calculateHandValue(dealerHand));

        String resultMessage;

        switch (result.status) {
            case "PLAYER_BLACKJACK":
                SoundController.playSFX(SoundController.Sound.BLACKJACK_WIN);
                resultMessage = "BLACKJACK! You Win !";
                break;
            case "DEALER_BLACKJACK":
                SoundController.playSFX(SoundController.Sound.BLACKJACK_LOSE);
                resultMessage = "Dealer has Blackjack! You Lose";
                break;
            case "BUST":
                SoundController.playSFX(SoundController.Sound.BLACKJACK_LOSE);
                resultMessage = "You busted! You Lose";
                break;
            case "WIN":
                SoundController.playSFX(SoundController.Sound.BLACKJACK_WIN);
                resultMessage = "You Win!";
                break;
            case "LOSS":
                SoundController.playSFX(SoundController.Sound.BLACKJACK_LOSE);
                resultMessage = "You Lose";
                break;
            case "PUSH":
                SoundController.playSFX(SoundController.Sound.BLACKJACK_DRAW);
                resultMessage = "It's a push (tie). Your bet is returned.";
                break;
            default:
                resultMessage = "Game finished unexpectedly.";
        }

        output.println(resultMessage);
        output.println("===============================");
        input.disablePeriod(DELAY_TIME);

        return result;
    }

    /**
     * Checks if the current game round is over.
     *
     * @return True if the state is GAME_OVER, false otherwise.
     */
    public boolean isGameOver() {
        return state == GameState.GAME_OVER;
    }
}

