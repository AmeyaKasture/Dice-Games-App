package androidsamples.java.dicegames;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;

import android.widget.TextView;

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
    public void rollDie_Six_addsFiveCoins() {
        // Store the initial coin count
        final int[] initialCoinCount = {0};

        // Check initial coin count
        onView(withId(R.id.txt_balance)).check((view, noViewFoundException) -> {
            String coinText = ((TextView) view).getText().toString(); // Get text from the TextView
            initialCoinCount[0] = Integer.parseInt(coinText.replaceAll("\\D+", "")); // Extract numerical part
        });

        // Keep rolling the die until a six is rolled
        while (true) {
            // Perform a click to roll the die
            onView(withId(R.id.btn_die)).perform(click());

            // Check the die value
            final int[] dieValue = {0};
            onView(withId(R.id.btn_die)).check((view, noViewFoundException) -> {
                String dieRollText = ((TextView) view).getText().toString();
                dieValue[0] = Integer.parseInt(dieRollText);
            });

            // If a 6 is rolled, check if the coin count has increased by 5
            if (dieValue[0] == 6) {
                onView(withId(R.id.txt_balance)).check((view, noViewFoundException) -> {
                    String updatedCoinText = ((TextView) view).getText().toString();
                    int updatedCoinCount = Integer.parseInt(updatedCoinText.replaceAll("\\D+", ""));
                    assertEquals(initialCoinCount[0] + 5, updatedCoinCount);
                });
                break; // Exit the loop once the condition is met
            }
        }
    }



    @Test
    public void balanceRemainsSameAfterNavigatingToSecondPage() {
        // Set the balance to a known value (e.g., 50)
        final int[] initialBalance = {0};

        // Check initial balance
        onView(withId(R.id.txt_balance)).check((view, noViewFoundException) -> {
            String balanceText = ((TextView) view).getText().toString(); // Get text from the TextView
            initialBalance[0] = Integer.parseInt(balanceText.replaceAll("\\D+", "")); // Extract numerical part
        });

        // Navigate to the second page (assuming there's a button or method for navigation)
        onView(withId(R.id.btn_games)).perform(click());
        String expectedBalanceText = String.valueOf(initialBalance[0]);
        onView(withId(R.id.coinsBalanceTextView)).check(matches(withText(expectedBalanceText)));
    }

    @Test
    public void rollDie_One_addsNoCoins() {
        // Store the initial coin count
        final int[] initialCoinCount = {10};

        // Check initial coin count
        onView(withId(R.id.txt_balance)).check((view, noViewFoundException) -> {
            String coinText = ((TextView) view).getText().toString(); // Get text from the TextView
            initialCoinCount[0] = Integer.parseInt(coinText.replaceAll("\\D+", "")); // Extract numerical part
        });

        // Keep rolling the die until a 1 is rolled
        while (true) {
            // Perform a click to roll the die
            onView(withId(R.id.btn_die)).perform(click());

            // Check the die value
            final int[] dieValue = {0};
            onView(withId(R.id.btn_die)).check((view, noViewFoundException) -> {
                String dieRollText = ((TextView) view).getText().toString();
                dieValue[0] = Integer.parseInt(dieRollText);
            });

            // If a 6 is rolled, update the initialCoinCount because balance has changed
            if (dieValue[0] == 6) {
                onView(withId(R.id.txt_balance)).check((view, noViewFoundException) -> {
                    String updatedCoinText = ((TextView) view).getText().toString();
                    initialCoinCount[0] = Integer.parseInt(updatedCoinText.replaceAll("\\D+", "")); // Update initial balance
                });
            }
            // If a 1 is rolled, ensure the balance remains unchanged
            else if (dieValue[0] == 1) {
                onView(withId(R.id.txt_balance)).check((view, noViewFoundException) -> {
                    String updatedCoinText = ((TextView) view).getText().toString();
                    int updatedCoinCount = Integer.parseInt(updatedCoinText.replaceAll("\\D+", ""));
                    assertEquals(initialCoinCount[0], updatedCoinCount); // Balance should remain the same
                });
                break; // Exit the loop once the condition is met
            }
        }
    }










}



