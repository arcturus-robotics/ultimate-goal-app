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
public class OneController extends OpMode{
    enum Mode {
        MANUAL,
        AUTO,
    }
    Mode currentMode = Mode.MANUAL;

    private ArcturusDrive drive;



    private DcMotorEx leftShooter, rightShooter, intake;
    private CRServo ringPusher;


    private static final Pose2d ORIGIN = new Pose2d(-63.0, -56.0, 0.0);
    private Pose2d targetA = new Pose2d(-5.0, -35.0, 0.0);
    private Pose2d targetB = new Pose2d(0.0, -12.0, Math.PI);

    //variable used for debugging
    double shooterSpeed = 0;

    @Override
    public void init() {
        drive = new ArcturusDrive(hardwareMap);

        intake = hardwareMap.get(DcMotorEx.class, "intake");

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
                    intake.setPower(1);

                } else if (gamepad1.b) {
                    intake.setPower(0);

                } else if (gamepad1.y) {
                    intake.setPower(-1);

                }

                //debugging motor speed due to the high motor speed making the gear connection poor
                if (gamepad1.dpad_down) {
                    shooterSpeed += -0.01;
                    shooterSpeed = Math.max(-1, Math.min(shooterSpeed, 1));
                }
                if (gamepad1.dpad_up) {
                    shooterSpeed += 0.01;
                    shooterSpeed = Math.max(-1, Math.min(shooterSpeed, 1));
                }

                if (gamepad1.x) {
                    leftShooter.setPower(-shooterSpeed);
                    rightShooter.setPower(shooterSpeed);
                }
                else {
                    leftShooter.setPower(0);
                    rightShooter.setPower(0);
                }

                //form of motor control for later uses
                /*
                if (gamepad1.x) {
                    leftShooter.setPower(-1);
                    rightShooter.setPower(1);
                }
                else {
                    leftShooter.setPower(0);
                    rightShooter.setPower(0);
                }
                */

                telemetry.addData("Motor Speed", shooterSpeed);

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
}
