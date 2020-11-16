package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
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

    private DcMotorEx leftShooter, rightShooter;
    private CRServo lowerIntake;
    private CRServo upperIntake;


    //variable used for debugging
    double shooterSpeed = 0;

    @Override
    public void init() {
        drive = new ArcturusDrive(hardwareMap);

        lowerIntake = hardwareMap.get(CRServo.class, "lowerIntake");
        upperIntake = hardwareMap.get(CRServo.class, "upperIntake");

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
        // calculate motor numbers (very important)
        double leftFront = -Range.clip(gamepad1.left_stick_y - gamepad1.left_stick_x, -1, 1);
        double leftRear = -Range.clip(  gamepad1.left_stick_y + gamepad1.right_stick_x, -1, 1);
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
            upperIntake.setPower(1);
            lowerIntake.setPower(-1);
        } else if (gamepad1.b) {
            upperIntake.setPower(0);
            lowerIntake.setPower(0);
        } else if (gamepad1.y) {
            upperIntake.setPower(-1);
            lowerIntake.setPower(1);
        }

        //debugging motor speed due to the high motor speed making the gear connection poor
        if (gamepad1.dpad_down){
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

        telemetry.addData("Motor Speed ", shooterSpeed ) ;

    }

    @Override
    public void stop() {
        telemetry.addData("status", "stopped..");
    }
}
