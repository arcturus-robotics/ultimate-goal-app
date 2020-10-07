package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * (the utils)
 */
public class Utilities {
    /**
     * Sleep for the specified duration on the current thread.
     * @param duration The duration to sleep for (for the last time, IN MILLISECONDS!).
     */
    public static void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Calculate the remaining time of autonomous.
     *
     * @param period The elapsed time.
     * @return The remaining time of autonomous.
     * If the result of the calculation is less than 0,
     * 0 will be returned.
     * @see Constants#AUTONOMOUS_DURATION
     */
    public static long autonomousTimeRemaining(ElapsedTime period) {
        long remaining = Constants.AUTONOMOUS_DURATION - (long) period.milliseconds();
        if (remaining < 0) {
            remaining = 0;
        }
        return remaining;
    }

    /**
     * Clip drive motor power numbers into a safe range.
     * @param power That funky motor power number (specifically a double-precision floating point one).
     * @return Safe motor power number.
     * @see Constants#DRIVE_SAFELY
     */
    public static double driveSafely(double power) {
        return Range.clip(power, -Constants.DRIVE_SAFELY, Constants.DRIVE_SAFELY);
    }
}
