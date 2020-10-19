package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.drive.ArcturusMecanumDrive;

/**
 * A teleop for use with a single controller.
 * Of course, it still works with two controllers plugged in,
 * but it will only use one of them.
 */
@TeleOp(group = "drive")
public class OneController extends OpMode {
    private ArcturusMecanumDrive drive;

    @Override
    public void init() {
        drive = new ArcturusMecanumDrive(hardwareMap);

        telemetry.addData("statuis", "wait ing ...");
    }

    @Override
    public void start() {
        telemetry.addData("Status", "STARTED!!");
    }

    @Override
    public void loop() {
        // calculate motor numbers (very important)
        double leftFront = -(gamepad1.left_stick_y - gamepad1.left_stick_x);
        double leftRear = -(gamepad1.left_stick_y + gamepad1.right_stick_x);
        double rightRear = -(gamepad1.right_stick_y - gamepad1.left_stick_x);
        double rightFront = -(gamepad1.right_stick_y + gamepad1.right_stick_x);

        // Set the power of each motor to the values calculated above (very obscure)
        drive.setMotorPowers(leftFront, leftRear, rightRear, rightFront);

        /*
        it would be nice to know what this means
        my guess: left stick is forward/backward/left/right movement and right stick is turning
                  because blah blah blah x, y, rotation
        drive.setWeightedDrivePower(
            new Pose2d(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x
            )
        );
        */
    }

    @Override
    public void stop() {
        telemetry.addData("staatus", "stopped..");
    }
}
