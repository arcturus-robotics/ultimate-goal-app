package org.firstinspires.ftc.teamcode.drive.opmode;

import java.util.List;
import java.util.Locale;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import org.firstinspires.ftc.teamcode.drive.ArcturusVision;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.ArcturusDrive;

@Autonomous(group = "drive")
public class VisionAuto extends LinearOpMode {
    private static final Pose2d ORIGIN = new Pose2d(-63.0, -56.0, 0.0);

    private ArcturusDrive drive;
    private ArcturusVision vision;

    @Override
    public void runOpMode() {
        // Dri
        drive = new ArcturusDrive(hardwareMap);
        // vision (eyeball) but awesome
        vision = new ArcturusVision(hardwareMap);

        // Activate TensorFlow Object Detection before we wait for the start command.
        // Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
        if (vision.tfod != null) {
            vision.tfod.activate();
            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 1.78 or 16/9).

            // Uncomment the following line if you want to adjust the magnification and/or the aspect ratio of the input images.
            //tfod.setZoom(2.5, 1.78);
        }

        telemetry.addData("sdjflkasfjsdlaf", "press play or you have 5 seconds to live");
        telemetry.update();

        waitForStart();

        drive.setPoseEstimate(ORIGIN);

        // none of these trajectory numbers are right
        Trajectory trajectory0 = drive.trajectoryBuilder(new Pose2d())
            .splineTo(new Vector2d(0, 0), 0)
            .build();
        drive.followTrajectory(trajectory0);

        // for some reason in the original example this was inside an if statement with the exact same condition which is pretty weird tbh
        // so i removed it
        while (opModeIsActive()) {
            if (vision.tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made (quite unfortunate)
                List<Recognition> updatedRecognitions = vision.tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("rings detected:", updatedRecognitions.size());

                    if (updatedRecognitions.isEmpty()) {
                        telemetry.addData("no rings detected", 0);

                        Trajectory trajectoryA = drive.trajectoryBuilder(new Pose2d())
                            .splineTo(new Vector2d(0, 0), 0)
                            .build();
                        drive.followTrajectory(trajectoryA);
                    } else {
                        // step through the list of recognitions and display boundary info
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel().equals("Single")) {
                                telemetry.addData("one ring detected", recognition.getLabel());

                                Trajectory trajectoryB = drive.trajectoryBuilder(new Pose2d())
                                    .splineTo(new Vector2d(0, 0), 0)
                                    .build();
                                drive.followTrajectory(trajectoryB);
                            } else if (recognition.getLabel().equals("Quad")) {
                                telemetry.addData("four rings detected", recognition.getLabel());
                                
                                Trajectory trajectoryC = drive.trajectoryBuilder(new Pose2d())
                                    .splineTo(new Vector2d(0, 0), 0)
                                    .build();
                                drive.followTrajectory(trajectoryC);
                            }

                            telemetry.addData(String.format(new Locale("en", "US"), "label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format(new Locale("en", "US"), "  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format(new Locale("en", "US"), "  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());

                            i++;
                        }
                    }

                    drive.turn(1.0);
                    drive.turn(-1.0);

                    telemetry.update();
                }
            }
        }

        if (vision.tfod != null) {
            vision.tfod.shutdown();
        }
    }
}