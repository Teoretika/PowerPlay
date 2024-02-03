package org.firstinspires.ftc.teamcode.PowerPlay.AprilTag;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain.DriveCommands;
import org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain.DriveLocations.DriveToLocation;
import org.firstinspires.ftc.teamcode.PowerPlay.Lift.LiftAndClawCommands;
import org.firstinspires.ftc.teamcode.PowerPlay.Lift.LiftHardwareMap;

import java.util.ArrayList;

@Autonomous
public class AprilTagDetection extends LinearOpMode
{
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;
    DriveCommands drive = new DriveCommands();
    DriveToLocation location = new DriveToLocation();
    LiftHardwareMap lift = new LiftHardwareMap();
    LiftAndClawCommands command = new LiftAndClawCommands();

    static final double FEET_PER_METER = 3.28084;

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
        command.ClawPosition(hardwareMap, true);
        sleep(1000);
        command.LiftEncoder(hardwareMap, 0.6, 5);

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
}
