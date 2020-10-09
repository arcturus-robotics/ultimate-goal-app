package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    public DcMotor frontLeftDrive;
    public DcMotor frontRightDrive;
    public DcMotor backLeftDrive;
    public DcMotor backRightDrive;

    public Robot() {}

    public Robot(HardwareMap hardwareMap) {
        this.frontLeftDrive = hardwareMap.get(DcMotor.class, "front_left_drive");
        this.frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        this.frontLeftDrive.setPower(0);

        this.frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
        this.frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        this.frontRightDrive.setPower(0);

        this.backLeftDrive = hardwareMap.get(DcMotor.class, "back_left_drive");
        this.backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        this.backLeftDrive.setPower(0);

        this.backRightDrive = hardwareMap.get(DcMotor.class, "back_right_drive");
        this.backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        this.backRightDrive.setPower(0);
    }
}