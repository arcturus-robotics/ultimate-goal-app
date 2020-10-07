package org.firstinspires.ftc.teamcode.robot.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Utilities;
import org.firstinspires.ftc.teamcode.robot.RobotOpMode;

/**
 * A teleop for use with a single controller.
 */
@TeleOp(name = "Single Controller Teleop", group = "Robot Teleop")
//@Disabled
public class SingleControllerTeleop extends RobotOpMode {
    @Override
    public void loop() {
        // Calculate motor power based on input from the gamepad.
        // The formulas were copied from some ancient texts found during Relic Recovery.
        float frontLeft = (float) -Utilities.driveSafely(gamepad1.left_stick_y - gamepad1.left_stick_x);
        float frontRight = (float) -Utilities.driveSafely(gamepad1.right_stick_y + gamepad1.right_stick_x);
        float backLeft = (float) -Utilities.driveSafely(gamepad1.left_stick_y + gamepad1.right_stick_x);
        float backRight = (float) -Utilities.driveSafely(gamepad1.right_stick_y - gamepad1.left_stick_x);

        // Make motors go brrr.
        robot.frontLeftDrive.setPower(frontLeft);
        robot.frontRightDrive.setPower(frontRight);
        robot.backLeftDrive.setPower(backLeft);
        robot.backRightDrive.setPower(backRight);
    }
}
