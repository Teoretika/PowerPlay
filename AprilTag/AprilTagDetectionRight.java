package org.firstinspires.ftc.teamcode.PowerPlay.AprilTag;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain.DriveCommands;
import org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain.DriveLocations.DriveToLocationRight;
import org.firstinspires.ftc.teamcode.PowerPlay.Lift.LiftAndClawCommands;
import org.firstinspires.ftc.teamcode.PowerPlay.Lift.LiftHardwareMap;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Autonomous
public class AprilTagDetectionRight extends LinearOpMode
{
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;
    DriveCommands drive = new DriveCommands();
    DriveToLocationRight location = new DriveToLocationRight();

    LiftHardwareMap lift = new LiftHardwareMap();
    LiftAndClawCommands command = new LiftAndClawCommands();

    static final double FEET_PER_METER = 3.28084;

    //DRIVE STUFF
    static final double AHEAD = 58.0;
    static final double SIDE = 31.0;
    static final double CLOSE = 7.8;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    int LOC1 = 0;
    int LOC2 = 1;
    int LOC3 = 2;

    org.openftc.apriltag.AprilTagDetection tagOfInterest = null;

    @Override
    public void runOpMode()
    {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });

        telemetry.setMsTransmissionInterval(50);
        
    
        command.ClawPosition(hardwareMap, false);

        // Get tag loop
        while (!isStarted() && !isStopRequested())
        {
            ArrayList<org.openftc.apriltag.AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if(currentDetections.size() != 0)
            {
                boolean tagFound = false;

                for(org.openftc.apriltag.AprilTagDetection tag : currentDetections)
                {
                    if(tag.id == LOC1 || tag.id == LOC2 || tag.id == LOC3)
                    {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }

                if(tagFound)
                {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                }
                else
                {
                    telemetry.addLine("Don't see tag of interest :(");

                    if(tagOfInterest == null)
                    {
                        telemetry.addLine("(The tag has never been seen)");
                    }
                    else
                    {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            }
            else
            {
                telemetry.addLine("Don't see tag of interest :(");

                if(tagOfInterest == null)
                {
                    telemetry.addLine("(The tag has never been seen)");
                }
                else
                {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }

            telemetry.update();
            sleep(20);
        }



        // Start
        placeCone();


        if(tagOfInterest == null)
        {
            telemetry.addLine("Going to location 0");
            telemetry.update();
    
        }

        /* Actually do something useful */
        if(tagOfInterest.id == LOC1)
        {
            telemetry.addLine("Going to location 1");
            telemetry.update();
            location.Location1(hardwareMap);
        }
        else if (tagOfInterest.id == LOC2)
        {
            telemetry.addLine("Going to location 2");
            telemetry.update();
            location.Location2(hardwareMap);
        }
        else if (tagOfInterest.id == LOC3)
        {
            telemetry.addLine("Going to location 3");
            telemetry.update();
            command.ClawPosition(hardwareMap, false);
            location.Location3(hardwareMap);
        }


        /* You wouldn't have this in your autonomous, this is just to prevent the sample from ending */
        while (opModeIsActive()) {sleep(20);}
    }

    void tagToTelemetry(org.openftc.apriltag.AprilTagDetection detection)
    {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }

    void placeCone(){
        command.ClawPosition(hardwareMap, true);
        sleep(1000);
        command.LiftEncoder(hardwareMap, 0.6, 5);

        drive.MotorEncoder(hardwareMap, 0.8, 60, -60, -60, 60);
        drive.MotorEncoder(hardwareMap, 0.8, AHEAD, AHEAD, AHEAD, AHEAD);
        drive.MotorEncoder(hardwareMap, 0.8, -SIDE, SIDE, SIDE, -SIDE);

        command.LiftEncoder(hardwareMap, 0.7, 21.5);

        drive.MotorEncoder(hardwareMap, 0.8, CLOSE, CLOSE, CLOSE, CLOSE);

        command.ClawPosition(hardwareMap, false);

        sleep(1000);

        drive.MotorEncoder(hardwareMap, 0.8, -CLOSE, -CLOSE, -CLOSE, -CLOSE);

        command.LiftEncoder(hardwareMap, 0.7, -26.5);

        drive.MotorEncoder(hardwareMap, 0.8, -SIDE, SIDE, SIDE, -SIDE);

    }
}