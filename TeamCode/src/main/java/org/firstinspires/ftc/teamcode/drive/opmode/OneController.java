package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.drive.ArcturusDrive;

/**
 * A teleop for use with a single controller.
 * Of course, it still works with two controllers plugged in,
 * but it will only use one of them.
 */
@TeleOp(group = "drive")
public class OneController extends OpMode {
    private ArcturusDrive drive;
    private CRServo intake;

    @Override
    public void init() {
        drive = new ArcturusDrive(hardwareMap);
        intake = hardwareMap.get(CRServo.class, "intake");

        telemetry.addData("statuis", "wait ing ...");
    }

    @Override
    public void start() {
        telemetry.addData("Status", "STARTED!!");
    }

    @Override
    public void loop() {
        // calculate motor numbers (very important)
        double leftFront = -Range.clip(gamepad1.left_stick_y - gamepad1.left_stick_x, -1, 1);
        double leftRear = -Range.clip(gamepad1.left_stick_y + gamepad1.right_stick_x, -1, 1);
        double rightRear = -Range.clip(gamepad1.right_stick_y - gamepad1.left_stick_x, -1, 1);
        double rightFront = -Range.clip(gamepad1.right_stick_y + gamepad1.right_stick_x, -1, 1);

        // Set the power of each motor to the values calculated above (very obscure)
        drive.setMotorPowers(leftFront, leftRear, rightRear, rightFront);

        /*
        left stick = translate
        right stick = rotate
        drive.setWeightedDrivePower(
            new Pose2d(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x
            )
        );
        */

        if (gamepad1.a) {
            intake.setPower(1);
        } else if (gamepad1.b) {
            intake.setPower(0);
        } else if (gamepad1.y) {
            intake.setPower(-1);
        }
    }

    @Override
    public void stop() {
        telemetry.addData("staatus", "stopped..");
    }
}
