package androidsamples.java.dicegames;

import java.util.Random;

/**
 * Implements of a six-faced {@link Die} using {@link Random}.
 */
public class Die6 implements Die {
    private static final int FACES = 6;
    Random rng;
    int value;

    public Die6() {
        rng = new Random();
    }
    public Die6(int val){
        this.value=val;
    }

    @Override
    public void roll() {
        value = 1 + rng.nextInt(FACES);
    }

    @Override
    public int value() {
        return value;
    }
}
