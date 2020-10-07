package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Utilities;

/**
 * A fancy shmancy superclass for code reuse.
 * This is specifically for autonomouses (autonomice?).
 */
public class RobotLinearOpMode extends LinearOpMode {
    protected Robot robot;
    protected ElapsedTime period = new ElapsedTime();

    /**
     * Run the opmode.
     */
    @Override
    public void runOpMode() {
        // Initialize the robot using the hardware map.
        robot = new Robot(hardwareMap);

        // Wait until the driver presses PLAY.
        waitForStart();

        // Reset the elapsed time so we can accurately measure it.
        period.reset();

        sleep(Constants.AUTONOMOUS_DELAY_DURATION);
    }
}
