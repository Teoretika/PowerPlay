package org.firstinspires.ftc.teamcode.PowerPlay.AutonomousOPMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.PowerPlay.Lift.LiftHardwareMap;
import org.firstinspires.ftc.teamcode.PowerPlay.Lift.LiftAndClawCommands;


@Autonomous(name="TEST Encoders Lift", group="Robot")

public class TestEncoderLift extends LinearOpMode {

    /* Declare OpMode members. */
    LiftHardwareMap lift = new LiftHardwareMap();
    LiftAndClawCommands command = new LiftAndClawCommands();

    private ElapsedTime     runtime = new ElapsedTime();


    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;

    @Override
    public void runOpMode() {

        // Initialize the drive system variables.
        lift.init(hardwareMap);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

       command.LiftEncoder(hardwareMap, 6.0, 23.5);
       sleep(50000);
       command.LiftEncoder(hardwareMap, 6.0, -23.5);

        sleep(1000);
    }
}
