package androidsamples.java.dicegames;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

public class GamesViewModel extends ViewModel {

    private int wager;
    int balance;
    private GameType gameType = GameType.NONE; // Default to NONE
    private GameResult gameResult = GameResult.UNDECIDED; // Default to UNDECIDED
    private Die6[] dice; // Array of Die6 for the game
    public Die6 walletDie; // Single Die6 for the wallet
    private int winFlag;
    private static final String PREFS_NAME = "dice_games_prefs";
    private static final String KEY_BALANCE = "wallet_balance";


    // Constructor
    public GamesViewModel() {
        this.balance = 0; // Initialize balance to 0
        this.dice = new Die6[4]; // Create an array for four dice
        for (int i = 0; i < 4; i++) {
            dice[i] = new Die6(); // Initialize each Die6
        }
        this.walletDie = new Die6(); // Initialize wallet die
        this.winFlag = 0;
    }

    public void set_model(GamesViewModel m,int arr[]){
        this.balance=m.balance;
        this.dice=m.dice;
        this.walletDie=m.walletDie;
        this.winFlag=m.winFlag;
        for (int i = 0; i < 4; i++) {
            dice[i] = new Die6(arr[i]); // Initialize each Die6
        }
    }

    // Constructor allowing dependency injection
    public GamesViewModel(Die6 walletDie) {
        this.walletDie = walletDie; // Injected wallet die
        this.balance = 0; // Initialize balance to 0
        this.dice = new Die6[4]; // Create an array for four dice
        for (int i = 0; i < 4; i++) {
            dice[i] = new Die6(); // Initialize each Die6
        }
        this.winFlag = 0;
    }

    // Load balance from SharedPreferences
    public void loadBalance(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("GamePrefs", Context.MODE_PRIVATE);
        balance = sharedPreferences.getInt("walletBalance", 0); // Default to 0 if not found
    }

    public void saveBalance(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("GamePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("walletBalance", balance);
        editor.apply();
    }

    // Setter for wager
    public void setWager(int wager) {
        this.wager = wager;
    }

    // Setter for balance
    public void setBalance(int balance) {
        this.balance = balance;
    }

    // Setter for game type
    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public void roller() {
        for (Die6 die : dice) {
            die.roll(); // Roll each die
        }
    }

    // Roll all dice for gameplay
    public int[] diceValues() {
        int[] values = new int[4];
        for (int i = 0; i < dice.length; i++) {
            values[i] = dice[i].value(); // Get the value of each rolled die
        }
        return values; // Return the array of values
    }

    // Roll the wallet die and update balance
    public void rollWalletDie() {
        walletDie.roll(); // Roll the wallet die
        int rolledValue = walletDie.value(); // Get the rolled value
        if (rolledValue == 6) {
            balance += 5; // Increment balance by 5 if rolled 6
            this.winFlag = 2; // Set winFlag for win
        } else {
            this.winFlag = 0; // Reset winFlag for other rolls
        }
    }

    private boolean isWagerValid(int multiplier) {
        int maxWager = balance / multiplier; // Calculate the max wager based on balance and multiplier
        return wager > 0 && wager <= maxWager; // Check if wager is within the allowed limit
    }

    private void updateBalanceAfterWin(int multiplier) {
        balance += wager * multiplier; // Add the winnings to the balance
    }

    private void updateBalanceAfterLoss(int multiplier) {
        balance -= wager * multiplier; // Subtract the wager * multiplier from the balance
    }

    public int getWinFlag() {
        return winFlag;
    }

    void set_flag(int val) {
        this.winFlag = val;
    }

    // Check if the wager is valid based on the balance and game type
    public boolean isValidWager() {
        if (wager <= 0 || gameType == GameType.NONE) {
            return false;
        }

        // Validate wager based on game type
        switch (gameType) {
            case TWO_ALIKE:
                return wager <= balance / 2;
            case THREE_ALIKE:
                return wager <= balance / 3;
            case FOUR_ALIKE:
                return wager <= balance / 4;
            default:
                return false;
        }
    }

    // Play the game and return the result
    public GameResult play() {
        // Check if wager is set
        if (wager <= 0) {
            throw new IllegalStateException("Wager not set, can't play!");
        }

        // Check if game type is set
        if (gameType == GameType.NONE) {
            throw new IllegalStateException("Game Type not set, can't play!");
        }
        roller();
        int[] rolledDice = diceValues(); // Roll the dice and get the values
        int multiplier;
        switch (gameType) {
            case FOUR_ALIKE:
                multiplier = 4; // 4x multiplier for "Four Alike"
                if (isWagerValid(multiplier)) {
                    if (areAllEqual(rolledDice)) {
                        gameResult = GameResult.WIN;
                        updateBalanceAfterWin(multiplier);
                        winFlag = 2; // Set winFlag to 2 for win
                    } else {
                        gameResult = GameResult.LOSS;
                        updateBalanceAfterLoss(multiplier);
                        winFlag = 1; // Set winFlag to 1 for loss
                    }
                } else {
                    gameResult = GameResult.UNDECIDED; // Invalid wager, undecided game
                    winFlag = 0; // Set winFlag to 0 for undecided
                }
                break;

            case THREE_ALIKE:
                multiplier = 3; // 3x multiplier for "Three Alike"
                if (isWagerValid(multiplier)) {
                    if (areThreeEqual(rolledDice)) {
                        gameResult = GameResult.WIN;
                        updateBalanceAfterWin(multiplier);
                        winFlag = 2;
                    } else {
                        gameResult = GameResult.LOSS;
                        updateBalanceAfterLoss(multiplier);
                        winFlag = 1;
                    }
                } else {
                    gameResult = GameResult.UNDECIDED;
                    winFlag = 0;
                }
                break;

            case TWO_ALIKE:
                multiplier = 2; // 2x multiplier for "Two Alike"
                if (isWagerValid(multiplier)) {
                    if (areTwoEqual(rolledDice)) {
                        gameResult = GameResult.WIN;
                        updateBalanceAfterWin(multiplier);
                        winFlag = 2;
                    } else {
                        gameResult = GameResult.LOSS;
                        updateBalanceAfterLoss(multiplier);
                        winFlag = 1;
                    }
                } else {
                    gameResult = GameResult.UNDECIDED;
                    winFlag = 0;
                }
                break;

            default:
                gameResult = GameResult.UNDECIDED; // If no valid game type, default to UNDECIDED
                winFlag = 0;
                break;
        }
        return gameResult; // Return the result of the game
    }

    // Helper methods to check for alike dice
    private boolean areAllEqual(int[] dice) {
        return dice[0] == dice[1] && dice[1] == dice[2] && dice[2] == dice[3];
    }

    private boolean areThreeEqual(int[] dice) {
        return (dice[0] == dice[1] && dice[1] == dice[2]) ||
                (dice[0] == dice[1] && dice[1] == dice[3]) ||
                (dice[0] == dice[2] && dice[2] == dice[3]) ||
                (dice[1] == dice[2] && dice[2] == dice[3]);
    }

    private boolean areTwoEqual(int[] dice) {
        return (dice[0] == dice[1]) ||
                (dice[0] == dice[2]) ||
                (dice[0] == dice[3]) ||
                (dice[1] == dice[2]) ||
                (dice[1] == dice[3]) ||
                (dice[2] == dice[3]);
    }

    // Getter for the game result
    public GameResult getGameResult() {
        return gameResult;
    }
}
