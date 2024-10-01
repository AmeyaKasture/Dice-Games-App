package androidsamples.java.dicegames;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;

import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void enableAccessibilityChecks() {
        AccessibilityChecks.enable().setRunChecksFromRootView(true);
    }

    @Test
    public void dummyAccessibilityTest() {
        onView(withId(R.id.btn_die)).perform(click());
    }
    @Test
    public void testRollingDieIncrementsBalanceWhenRollingSix() {
        // Use TestDie6 to ensure the die always rolls a six
        GamesViewModel viewModel = new GamesViewModel(new TestDie6());

        // Set the initial balance (assuming it's 0)
        viewModel.setBalance(0);

        // Simulate rolling the die
        viewModel.rollWalletDie(); // or however you trigger the roll

        // Check if the balance is updated correctly after rolling a six
        assertEquals(5, viewModel.balance); // Update based on your expected logic
    }
    @Test
    public void testRollingDieDoesNotIncrementBalanceWhenRollingOne() {
        // Set the initial balance (assuming it's 0)
        onView(withId(R.id.txt_balance)).check(matches(withText("Coins: 0")));

        // Use TestDie6 to ensure the die always rolls a one
        // You can create a new TestDie6 class or modify the existing one for this test.
        GamesViewModel viewModel = new GamesViewModel(new TestDie6One());

        // Set the initial balance to 0
        viewModel.setBalance(0);

        // Simulate rolling the die
        viewModel.rollWalletDie(); // or however you trigger the roll

        // Check if the balance is still 0 after rolling a one
        onView(withId(R.id.txt_balance)).check(matches(withText("Coins: 0"))); // Balance should remain 0
    }



}



