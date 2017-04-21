package fr.mario8705.timemachine;

public final class Time {
    /**
     * Get the system time in seconds.
     * @return The time in seconds.
     */
    public static float getTime() {
        return System.nanoTime() / 1000000000.0f;
    }
}
