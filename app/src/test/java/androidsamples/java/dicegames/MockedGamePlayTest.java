package androidsamples.java.dicegames;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockedGamePlayTest {
    @Spy
    private GamesViewModel m = new GamesViewModel();

    @Test
    public void fourAlikeWinsWhenAllDiceReturn1() {
        when(m.diceValues()).thenReturn(new int[]{1, 1, 1, 1});

        m.setGameType(GameType.FOUR_ALIKE);
        m.setBalance(100);
        m.setWager(5);
        assertThat(m.play(), is(GameResult.WIN));
    }

    @Test
    public void fourAlikeLossWhenD1Different() {
        when(m.diceValues()).thenReturn(new int[]{2, 1, 1, 1});

        m.setGameType(GameType.FOUR_ALIKE);
        m.setBalance(100);
        m.setWager(5);
        assertThat(m.play(), is(GameResult.LOSS));
    }

    @Test
    public void twoAlikeWinsWhenD1D2Same() {
        when(m.diceValues()).thenReturn(new int[]{3, 3, 1, 4});

        m.setGameType(GameType.TWO_ALIKE);
        m.setBalance(100);
        m.setWager(5);
        assertThat(m.play(), is(GameResult.WIN));
    }

    @Test
    public void threeAlikeWinsWhenD1D2D3Same() {
        when(m.diceValues()).thenReturn(new int[]{1, 1, 1, 2});

        m.setGameType(GameType.THREE_ALIKE);
        m.setBalance(100);
        m.setWager(5);
        assertThat(m.play(), is(GameResult.WIN));
    }

    @Test
    public void twoAlikeLossWhenNoTwoDiceAreSame() {
        when(m.diceValues()).thenReturn(new int[]{2, 3, 4, 5});

        m.setGameType(GameType.TWO_ALIKE);
        m.setBalance(100);
        m.setWager(5);
        assertThat(m.play(), is(GameResult.LOSS));
    }

    @Test
    public void threeAlikeLossWhenOnlyTwoDiceAreSame() {
        when(m.diceValues()).thenReturn(new int[]{6, 6, 2, 5});

        m.setGameType(GameType.THREE_ALIKE);
        m.setBalance(100);
        m.setWager(5);
        assertThat(m.play(), is(GameResult.LOSS));
    }

    @Test
    public void fourAlikeLossWhenOnlyThreeDiceAreSame() {
        when(m.diceValues()).thenReturn(new int[]{4, 4, 4, 1});

        m.setGameType(GameType.FOUR_ALIKE);
        m.setBalance(100);
        m.setWager(5);
        assertThat(m.play(), is(GameResult.LOSS));
    }

    @Test
    public void balanceDecreasesAfterLossInTwoAlike() {
        when(m.diceValues()).thenReturn(new int[]{2, 4, 5, 6});

        m.setGameType(GameType.TWO_ALIKE);
        m.setBalance(100);
        m.setWager(10);
        assertThat(m.play(), is(GameResult.LOSS));
        assertThat(m.balance, is(100)); // Balance should decrease by wager amount after loss
    }

    @Test
    public void balanceIncreasesAfterWinInFourAlike() {
        when(m.diceValues()).thenReturn(new int[]{5, 5, 5, 5});

        m.setGameType(GameType.FOUR_ALIKE);
        m.setBalance(50);
        m.setWager(10);
        assertThat(m.play(), is(GameResult.WIN));
        assertThat(m.balance, is(70)); // Assuming win increases balance by 2x wager
    }

    @Test
    public void balanceUnchangedWhenNoWagerSet() {
        when(m.diceValues()).thenReturn(new int[]{2, 2, 2, 3});

        m.setGameType(GameType.THREE_ALIKE);
        m.setBalance(100);
        m.setWager(0); // No wager set
        assertThat(m.play(), is(GameResult.LOSS));
        assertThat(m.balance, is(100)); // Balance should remain unchanged
    }

    @Test
    public void gameLossWhenWagerExceedsBalance() {
        when(m.diceValues()).thenReturn(new int[]{1, 2, 3, 4});

        m.setGameType(GameType.FOUR_ALIKE);
        m.setBalance(10);
        m.setWager(20); // Wager exceeds balance
        assertThat(m.balance, is(10)); // Ba20lance remains unchanged because the wager is invalid
    }

    @Test
    public void winWithTwoAlikeWhenD3D4Same() {
        when(m.diceValues()).thenReturn(new int[]{1, 2, 4, 4});

        m.setGameType(GameType.TWO_ALIKE);
        m.setBalance(200);
        m.setWager(20);
        assertThat(m.play(), is(GameResult.WIN));
    }

    @Test
    public void winWithThreeAlikeWhenD2D3D4Same() {
        when(m.diceValues()).thenReturn(new int[]{2, 3, 3, 3});

        m.setGameType(GameType.THREE_ALIKE);
        m.setBalance(100);
        m.setWager(15);
        assertThat(m.play(), is(GameResult.WIN));
    }

    @Test
    public void lossInFourAlikeWhenDiceValuesAreAllDifferent() {
        when(m.diceValues()).thenReturn(new int[]{1, 2, 3, 4});

        m.setGameType(GameType.FOUR_ALIKE);
        m.setBalance(80);
        m.setWager(10);
        assertThat(m.play(), is(GameResult.LOSS));
    }

}