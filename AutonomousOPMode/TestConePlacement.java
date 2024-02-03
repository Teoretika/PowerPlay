package org.firstinspires.ftc.teamcode.PowerPlay.AutonomousOPMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain.DriveCommands;
import org.firstinspires.ftc.teamcode.PowerPlay.Lift.LiftAndClawCommands;

@Autonomous(name="TEST Cone Placement", group="Robot")

public class TestConePlacement extends LinearOpMode {
    DriveCommands drive = new DriveCommands();
    LiftAndClawCommands command = new LiftAndClawCommands();

    static final double AHEAD = 60.0;
    static final double SIDE = 30.0;
    static final double CLOSE = 7.5;

    @Override
    public void runOpMode() {
        waitForStart();
        
        command.ClawPosition(hardwareMap, true);
        sleep(1000);
        command.LiftEncoder(hardwareMap, 0.6, 5);

        drive.MotorEncoder(hardwareMap, 0.8, AHEAD, AHEAD, AHEAD, AHEAD);
        drive.MotorEncoder(hardwareMap, 0.8, -SIDE, SIDE, SIDE, -SIDE);
        
        command.LiftEncoder(hardwareMap, 0.7, 23);
        
        drive.MotorEncoder(hardwareMap, 0.8, CLOSE, CLOSE, CLOSE, CLOSE);
        
        command.ClawPosition(hardwareMap, false);
        
        sleep(1000);
        
        drive.MotorEncoder(hardwareMap, 0.8, -CLOSE, -CLOSE, -CLOSE, -CLOSE);
        
        command.LiftEncoder(hardwareMap, 0.7, -28);

    }
}
