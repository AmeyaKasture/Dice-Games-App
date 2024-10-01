package androidsamples.java.dicegames;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A {@link Fragment} that implements the Wallet screen.
 */
public class WalletFragment extends Fragment {

    private static final String TAG = "WalletFragment";
    private GamesViewModel vm;
    private Button btnDie;
    private TextView txtBalance;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        vm = new ViewModelProvider(requireActivity()).get(GamesViewModel.class);
        Log.d(TAG, "VM: " + vm);

        // Initialize UI components
        btnDie = view.findViewById(R.id.btn_die);
        txtBalance = view.findViewById(R.id.txt_balance);

        // Set content descriptions for accessibility
        btnDie.setContentDescription("Roll the die to change your wallet balance");
        txtBalance.setContentDescription("Current wallet balance: " + vm.balance);

        // Set initial balance
        updateBalance();

        // Handle die roll button click
        btnDie.setOnClickListener(v -> {
            Log.d(TAG, "Rolled");
            vm.rollWalletDie();
            if (vm.getWinFlag() == 2) {
                Toast.makeText(getActivity(), "Congratulations! You won!", Toast.LENGTH_SHORT).show();
                vm.set_flag(0);
            }
            btnDie.setText(String.valueOf(vm.walletDie.value())); // Update button text with die value// Show toast with die value
            updateBalance(); // Update balance on the screen
        });

        // Navigate to GamesFragment
        view.findViewById(R.id.btn_games).setOnClickListener(v -> {
            Log.d(TAG, "Going to GamesFragment");
            Toast.makeText(getActivity(), "Navigating to Games", Toast.LENGTH_SHORT).show(); // Show navigation toast
            NavDirections action = WalletFragmentDirections.actionWalletFragmentToGamesFragment();
            Navigation.findNavController(view).navigate(action);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        // Save balance when fragment is paused
        DiceGamesPrefs.setBalance(requireContext(), vm.balance);
        Toast.makeText(getActivity(), "Balance saved", Toast.LENGTH_SHORT).show(); // Show toast indicating balance was saved
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        // Retrieve the saved balance when fragment resumes
        vm.balance = DiceGamesPrefs.balance(requireContext());
        Toast.makeText(getActivity(), "Balance restored: " + vm.balance, Toast.LENGTH_SHORT).show(); // Show toast with restored balance
        // Update the balance TextView
        updateBalance();
    }

    // Method to update balance TextView
    private void updateBalance() {
        txtBalance.setText("Coins: " + vm.balance);
        txtBalance.setContentDescription("Current wallet balance: " + vm.balance); // Update content description
    }
}
