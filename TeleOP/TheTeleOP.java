package org.firstinspires.ftc.teamcode.PowerPlay.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain.DriveCommands;
import org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain.DriveHardwareMap;
import org.firstinspires.ftc.teamcode.PowerPlay.Lift.LiftHardwareMap;
import org.firstinspires.ftc.teamcode.PowerPlay.Lift.LiftAndClawCommands;

@TeleOp(name="TheTeleOP", group="Robot")

public class TheTeleOP extends LinearOpMode {

    //Get hardwareMap
    DriveHardwareMap motor = new DriveHardwareMap();
    DriveCommands drive = new DriveCommands();

    LiftHardwareMap slider = new LiftHardwareMap();
    LiftAndClawCommands command = new LiftAndClawCommands();

    @Override
    public void runOpMode() {

        //Initialize hardware
        motor.init(hardwareMap);
        slider.init(hardwareMap);

        //Driving variables
        double y;
        double x;
        double rx;
        double y2;

        double denominator;
        double LF_POW;
        double LB_POW;
        double RF_POW;
        double RB_POW;


        //Send telemetry message to signify robot waiting
        telemetry.addData("Robot >", "Press Start");
        telemetry.update();
        command.ClawPosition(hardwareMap, false);


        waitForStart();

        while (opModeIsActive()) {

            // Controller 1
            // Moving
            y = -gamepad1.left_stick_y;
            x = -gamepad1.left_stick_x * 1.1;
            rx = -gamepad1.right_stick_x;

            denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            LF_POW = (y + x + rx) / denominator;
            LB_POW = (y - x + rx) / denominator;
            RF_POW = (y - x - rx) / denominator;
            RB_POW = (y + x - rx) / denominator;

            drive.MotorPower(hardwareMap, LF_POW, RF_POW, LB_POW, RB_POW);

            // Controller 2
            y2 = -gamepad2.left_stick_y;

            command.LiftPower(hardwareMap, y2);

            if(gamepad2.a){
                command.ClawPosition(hardwareMap, true);
            }
            if(gamepad2.b){
                command.ClawPosition(hardwareMap, false);
            }


            //sleep tight
            sleep(50);
        }
    }
}
