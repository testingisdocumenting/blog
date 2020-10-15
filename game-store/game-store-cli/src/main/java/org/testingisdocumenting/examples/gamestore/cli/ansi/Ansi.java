package org.testingisdocumenting.examples.gamestore.cli.ansi;

public class Ansi {
    public static void print(Object... styleOrValues) {
        System.out.println(new AutoResetAnsiString(styleOrValues));
    }
}
