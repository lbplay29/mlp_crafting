package net.ona.mlpfrien.util;

public class BasicUtilities {
    public static int loopingInt(int max, int current) {
        if (current>=max) {
            current=0;
        } else {
            current++;
        }
        return current;
    }
}
