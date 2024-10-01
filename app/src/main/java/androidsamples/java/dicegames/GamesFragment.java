package androidsamples.java.dicegames;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

/**
 * A {@link Fragment} that implements the Game Play screen.
 */
public class GamesFragment extends Fragment {

    private GamesViewModel gamesViewModel;
    private TextView coinsBalanceTextView;
    private EditText inputEditText;
    private RadioGroup alikeRadioGroup;
    private Button goButton;
    private Button infoButton;
    public static  String TAG="tagger";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_games, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        gamesViewModel = new ViewModelProvider(this).get(GamesViewModel.class);

        // Bind UI components
        coinsBalanceTextView = view.findViewById(R.id.coinsBalanceTextView);
        inputEditText = view.findViewById(R.id.inputEditText);
        alikeRadioGroup = view.findViewById(R.id.alikeRadioGroup);
        goButton = view.findViewById(R.id.goButton);
        infoButton = view.findViewById(R.id.infoButton);

        // Set content descriptions for accessibility
        coinsBalanceTextView.setContentDescription("Current balance: " + gamesViewModel.balance);
        inputEditText.setContentDescription("Input wager amount here");
        goButton.setContentDescription("Click to play the game");
        infoButton.setContentDescription("Click for game information");

        // Update the balance TextView initially
        coinsBalanceTextView.setText(String.valueOf(gamesViewModel.balance));

        // Set up click listener for the INFO button
        infoButton.setOnClickListener(v -> {
            NavDirections action = GamesFragmentDirections.actionGamesFragmentToInfoFragment();
            Navigation.findNavController(view).navigate(action);
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        // Save balance when fragment is paused
        DiceGamesPrefs.setBalance(requireContext(), gamesViewModel.balance);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        // Retrieve the saved balance when fragment resumes
        gamesViewModel.balance = DiceGamesPrefs.balance(requireContext());
        Log.d(TAG, "current balance is " + gamesViewModel.balance);

        // Update the balance TextView
        updateBalanceTextView();

        // Set up the GO button's onClick listener in onResume
        goButton.setOnClickListener(v -> handleGoButtonClick(getView()));
    }

    private void handleGoButtonClick(View view) {
        // Get the wager from the EditText
        String wagerText = inputEditText.getText().toString();
        if (!wagerText.isEmpty()) {
            int wager = Integer.parseInt(wagerText);
            gamesViewModel.setWager(wager);

            // Set the game type based on selected RadioButton
            int selectedId = alikeRadioGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedRadioButton = view.findViewById(selectedId);
                switch (selectedRadioButton.getText().toString()) {
                    case "2 Alike":
                        gamesViewModel.setGameType(GameType.TWO_ALIKE);
                        break;
                    case "3 Alike":
                        gamesViewModel.setGameType(GameType.THREE_ALIKE);
                        break;
                    case "4 Alike":
                        gamesViewModel.setGameType(GameType.FOUR_ALIKE);
                        break;
                }

                // Validate wager and play the game
                if (gamesViewModel.isValidWager()) {
                    GameResult result = gamesViewModel.play();
                    // Handle the game result (update UI accordingly)
                    updateDiceUI(gamesViewModel.diceValues());
                    // Update the balance after playing
                    updateBalanceTextView();

                    // Display a toast message based on winFlag
                    int winFlag = gamesViewModel.getWinFlag();
                    if (winFlag == 2) {
                        Toast.makeText(getActivity(), "Congratulations! You won!", Toast.LENGTH_SHORT).show();
                        gamesViewModel.set_flag(0);
                    } else if (winFlag == 1) {
                        Toast.makeText(getActivity(), "Sorry! You lost!", Toast.LENGTH_SHORT).show();
                        gamesViewModel.set_flag(0);
                    } else {
                        Toast.makeText(getActivity(), "Undecided or invalid wager. Try again.", Toast.LENGTH_SHORT).show();
                        gamesViewModel.set_flag(0);
                    }

                } else {
                    // Handle invalid wager (show a message or a toast)
                    Toast.makeText(getActivity(), "Invalid wager!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void updateBalanceTextView() {
        coinsBalanceTextView.setText(String.valueOf(gamesViewModel.balance));
        coinsBalanceTextView.setContentDescription("Current balance: " + gamesViewModel.balance);
    }

    private void updateDiceUI(int[] diceValues) {
        // Update the TextViews for dice display (assuming you have them set up in the layout)
        ((TextView) getView().findViewById(R.id.num1)).setText(String.valueOf(diceValues[0]));
        ((TextView) getView().findViewById(R.id.num2)).setText(String.valueOf(diceValues[1]));
        ((TextView) getView().findViewById(R.id.num3)).setText(String.valueOf(diceValues[2]));
        ((TextView) getView().findViewById(R.id.num4)).setText(String.valueOf(diceValues[3]));

        // Set content descriptions for dice values
        ((TextView) getView().findViewById(R.id.num1)).setContentDescription("Die 1 rolled: " + diceValues[0]);
        ((TextView) getView().findViewById(R.id.num2)).setContentDescription("Die 2 rolled: " + diceValues[1]);
        ((TextView) getView().findViewById(R.id.num3)).setContentDescription("Die 3 rolled: " + diceValues[2]);
        ((TextView) getView().findViewById(R.id.num4)).setContentDescription("Die 4 rolled: " + diceValues[3]);
    }
}
