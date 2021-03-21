package org.firstinspires.ftc.teamcode.util;

/**
 * (helpful documentation about how this represents the height of a ring stack)
 * (because why would you use an int, that would be cringe)
 * (because you might accidentally put a 2 or a 3)
 */
public enum RingStackHeight {
    ZERO(0),
    ONE(1),
    FOUR(4);

    public final int height;

    RingStackHeight(int height) {
        this.height = height;
    }
}