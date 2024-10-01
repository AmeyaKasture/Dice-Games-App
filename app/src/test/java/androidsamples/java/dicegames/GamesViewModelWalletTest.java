package androidsamples.java.dicegames;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GamesViewModelWalletTest {
    private static final int INCR_VALUE = 5;
    private static final int WIN_VALUE = 6;

    @Spy
    private Die6 walletDie;
    @InjectMocks
    private GamesViewModel m = new GamesViewModel();
    private AutoCloseable closeable;

    @Before
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);

    }

    @After
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void rolling6IncrementsBalanceBy5() {
        int oldBalance = m.balance;
        when(walletDie.value()).thenReturn(WIN_VALUE);

        m.rollWalletDie();
        assertThat(m.balance, is(oldBalance + INCR_VALUE));
    }

    @Test
    public void rolling1DoesNotChangeBalance() {
        int oldBalance = m.balance;
        when(walletDie.value()).thenReturn(1);

        m.rollWalletDie();
        assertThat(m.balance, is(oldBalance));
    }
    @Test
    public void rolling3DoesNotChangeBalance() {
        int oldBalance = m.balance;
        when(walletDie.value()).thenReturn(3);

        m.rollWalletDie();
        assertThat(m.balance, is(oldBalance));
    }

    @Test
    public void rollingTwoSixesIncreasesBalanceBy10() {
        int oldBalance = m.balance; // Store the current balance
        when(walletDie.value()).thenReturn(6); // Mock the die to return 6 for the first roll
        m.rollWalletDie(); // First roll

        // Now simulate rolling the die again, returning 6 for the second roll
        when(walletDie.value()).thenReturn(6); // Mock the die to return 6 again
        m.rollWalletDie(); // Second roll

        // Check if the balance is updated correctly
        int expectedBalance = oldBalance + 10; // Assuming 10 is added when rolling two sixes
        assertThat(m.balance, is(expectedBalance)); // Verify the expected balance
    }

    @Test
    public void rollingOneAndSixIncreasesBalanceBy5() {
        int oldBalance = m.balance; // Store the current balance
        when(walletDie.value()).thenReturn(1); // Mock the die to return 1 for the first roll
        m.rollWalletDie(); // First roll

        // Now simulate rolling the die again, returning 6 for the second roll
        when(walletDie.value()).thenReturn(6); // Mock the die to return 6 for the second roll
        m.rollWalletDie(); // Second roll

        // Check if the balance is updated correctly
        int expectedBalance = oldBalance + 5; // Assuming 5 is added when rolling 1 followed by 6
        assertThat(m.balance, is(expectedBalance)); // Verify the expected balance
    }







}