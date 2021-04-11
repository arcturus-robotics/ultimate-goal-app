package org.firstinspires.ftc.teamcode.drive.opmode;

import android.text.style.TabStopSpan;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;

import org.firstinspires.ftc.teamcode.drive.ArcturusDrive;
import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalizer;

/**
 * A teleop for use with a single controller.
 * Of course, it still works with two controllers plugged in,
 * but it will only use one of them.
 */
@TeleOp(group = "drive")
public class OneController extends OpMode {
    private static final Pose2d ORIGIN = new Pose2d(-63.0, -56.0, 0.0);
    private final Pose2d targetA = new Pose2d(-5.0, -35.0, 0.0);
    private final Pose2d targetB = new Pose2d(0.0, -12.0, Math.PI);
    Mode currentMode = Mode.MANUAL;
    private ArcturusDrive drive;
    private DcMotorEx leftShooter, rightShooter, frontIntake, backIntake;
    private CRServo ringPusher;

    @Override
    public void init() {
        drive = new ArcturusDrive(hardwareMap);

        frontIntake = hardwareMap.get(DcMotorEx.class, "frontIntake");
        backIntake = hardwareMap.get(DcMotorEx.class, "backIntake");

        ringPusher = hardwareMap.get(CRServo.class, "ringPusher");

        leftShooter = hardwareMap.get(DcMotorEx.class, "leftShooter");
        rightShooter = hardwareMap.get(DcMotorEx.class, "rightShooter");

        telemetry.addData("statis", "waiting ...");
    }

    @Override
    public void start() {
        telemetry.addData("Status", "STARTED!!");
    }

    @Override
    public void loop() {
        drive.update();
        Pose2d poseEstimate = drive.getPoseEstimate();

        switch (currentMode) {
            case MANUAL:
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
                    frontIntake.setPower(1);
                    backIntake.setPower(-1);
                } else if (gamepad1.b) {
                    frontIntake.setPower(0);
                    backIntake.setPower(0);
                } else if (gamepad1.y) {
                    frontIntake.setPower(-1);
                    frontIntake.setPower(1);
                }

                if (gamepad1.x) {
                    leftShooter.setPower(-1);
                    rightShooter.setPower(1);
                } else {
                    leftShooter.setPower(0);
                    rightShooter.setPower(0);
                }

                if (gamepad1.left_bumper) {
                    Trajectory trajectoryA = drive.trajectoryBuilder(poseEstimate).splineTo(new Vector2d(targetA.getX(), targetA.getY()), targetA.getHeading()).build();
                    drive.followTrajectoryAsync(trajectoryA);
                    currentMode = Mode.AUTO;
                } else if (gamepad1.right_bumper) {
                    Trajectory trajectoryB = drive.trajectoryBuilder(poseEstimate).splineTo(new Vector2d(targetB.getX(), targetB.getY()), targetB.getHeading()).build();
                    drive.followTrajectoryAsync(trajectoryB);
                    currentMode = Mode.AUTO;
                }

                break;
            case AUTO:
                if (gamepad1.x) {
                    drive.cancelFollowing();
                    currentMode = Mode.MANUAL;
                }

                if (!drive.isBusy()) {
                    currentMode = Mode.MANUAL;
                }

                break;
        }
    }

    @Override
    public void stop() {
        telemetry.addData("status", "stopped..");
    }

    enum Mode {
        MANUAL,
        AUTO,
    }
}
