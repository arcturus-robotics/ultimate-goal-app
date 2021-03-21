package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.ArcturusDrive;
import org.firstinspires.ftc.teamcode.util.RingStackHeight;

@Autonomous(group = "drive")
public class LaunchAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ArcturusDrive drive = new ArcturusDrive(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        // WARNING: a mess for you

        // TODO: use lineTo() or something instead because accuracy
        Trajectory traj1 = drive.trajectoryBuilder(new Pose2d())
                // away from starting line
                .strafeRight(5)
                // forward to stack
                .forward(5)
                .build();

        drive.followTrajectory(traj1);

        // scanned height of the rings
        // 0 = first square (a)
        // 1 = second square (b)
        // 4 = third square (c)
        // TODO: dont hardcode this (see next TODO)
        RingStackHeight height = RingStackHeight.ZERO;

        // TODO: scan here (this is the one)

        // make your way downtown to the zones to drop off your rings in different ways depending on
        // the height of the ring stack or something
        boolean strafeRight = false;
        switch (height) {
            case ZERO:
            case FOUR:
                // strafe right to the wall (coolishly)
                strafeRight = true;
                break;
        }
        double forwardDistance = 0;
        switch (height) {
            case ZERO:
                // utilize your recently acquired passport and go to the a zone
                forwardDistance = 5;
                break;
            case ONE:
                // no strafing is required for this maneuver..
                // enter the b zone (highly illegal but it must be done)
                forwardDistance = 10;
                break;
            case FOUR:
                // you're going to brazil (c)
                forwardDistance = 15;
                break;
        }
        TrajectoryBuilder traj2Builder = drive.trajectoryBuilder(traj1.end(), true);
        if (strafeRight) {
            traj2Builder.strafeRight(5);
        }
        traj2Builder.forward(forwardDistance);
        Trajectory traj2 = traj2Builder.build();

        // cool!
        drive.followTrajectory(traj2);

        // TODO: drop rings here

        // distance from b to left wall
        double strafeLeftDistance = 15;
        if (strafeRight) {
            // distance from b to right wall or something
            strafeLeftDistance += 5;
        }
        // TODO: how much distance is between scan area and launch line, because that value goes somewhere in here
        double backwardDistance = forwardDistance - 3;
        // the threequel
        Trajectory traj3 = drive.trajectoryBuilder(traj2.end(), true)
                .strafeLeft(strafeLeftDistance)
                .back(backwardDistance)
                .build();

        // fun!
        drive.followTrajectory(traj3);
        // TODO: shoot?

        // this is used between each pew
        double strafeRightDistance = 2;
        // What does reversed mean. Help
        Trajectory pewTraj1 = drive.trajectoryBuilder(traj3.end(), true).strafeRight(strafeRightDistance).build();
        drive.followTrajectory(pewTraj1);
        // TODO: shoot...... \:grin:
        Trajectory pewTraj2 = drive.trajectoryBuilder(pewTraj1.end(), true).strafeRight(strafeRightDistance).build();
        drive.followTrajectory(pewTraj2);
        // TODO: shoot.
    }
}
