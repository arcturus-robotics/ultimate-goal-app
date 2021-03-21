package org.firstinspires.ftc.teamcode.drive.opmode;

import java.util.List;
import org.firstinspires.ftc.teamcode.drive.ArcturusVision;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(group = "drive")
public class VisionAuto extends LinearOpMode {
    private ArcturusVision vision;
    int x;
    @Override
    public void runOpMode() {
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

        // for some reason in the original example this was inside an if statement with the exact same condition which is pretty weird tbh
        // so i removed it
        while (opModeIsActive()) {


            if (vision.tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made (quite unfortunate)
                List<Recognition> updatedRecognitions = vision.tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("object detected!", updatedRecognitions.size());
                    // step through the list of recognitions and display boundary info

                    int i = 0;
                    for (Recognition recognition : updatedRecognitions) {


                        if (recognition.getLabel() == "Single") {
                            telemetry.addData("There is a a single ring detected.", recognition.getLabel());
                        }

                        else if (recognition.getLabel() == "Quad") {
                            telemetry.addData("There is a stack of 4 rings", recognition.getLabel());
                        }


                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                        i += 1;
                    }

                    if(updatedRecognitions.size() == 0) {
                        telemetry.addData("There are no rings", 0);
                    }




                    telemetry.update();
                }
            }
        }

        if (vision.tfod != null) {
            vision.tfod.shutdown();
        }
    }
}