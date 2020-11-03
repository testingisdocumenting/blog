package org.testingisdocumenting.examples.gamestore.server;

import java.util.Random;

public class ArtificialDelay {
    private static final Random random = new Random();
    private ArtificialDelay() {
    }

    public static void artificialDelay(int millisFrom, int millisTo) {
        int delay = random.nextInt(millisTo - millisFrom) + millisFrom;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
