package org.firstinspires.ftc.teamcode.PowerPlay.AutonomousOPMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain.DriveCommands;
import org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain.DriveHardwareMap;


@Autonomous(name="TEST Encoders", group="Robot")

public class TESTEncoder extends LinearOpMode {

    /* Declare OpMode members. */
    DriveHardwareMap robot = new DriveHardwareMap();
    DriveCommands drive = new DriveCommands();

    private ElapsedTime     runtime = new ElapsedTime();


    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;

    @Override
    public void runOpMode() {

        // Initialize the drive system variables.
        robot.init(hardwareMap);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        drive.MotorEncoder(hardwareMap, 0.8, -34, 34, -34, 34);

        sleep(1000);
    }
}
