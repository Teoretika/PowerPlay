package org.firstinspires.ftc.teamcode.PowerPlay.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain.DriveCommands;
import org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain.DriveHardwareMap;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TEST drive", group="Robot")

public class TestDrive extends LinearOpMode {

    //Get hardwareMap
    DriveHardwareMap motor = new DriveHardwareMap();
    DriveCommands drive = new DriveCommands();

    @Override
    public void runOpMode() {

        //Initialize hardware
        motor.init(hardwareMap);

        //Driving variables
        double y;
        double x;
        double rx;


        double denominator;
        double LF_POW;
        double LB_POW;
        double RF_POW;
        double RB_POW;


        //Send telemetry message to signify robot waiting
        telemetry.addData("Robot >", "Press Start");
        telemetry.update();


        waitForStart();

        while (opModeIsActive()) {


            //Controller 1
            //Moving
            y = -gamepad1.left_stick_y;
            x = gamepad1.left_stick_x * 1.1;
            rx = gamepad1.right_stick_x;

            denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            LF_POW = (y + x + rx) / denominator;
            LB_POW = (y - x + rx) / denominator;
            RF_POW = (y - x - rx) / denominator;
            RB_POW = (y + x - rx) / denominator;

            drive.MotorPower(hardwareMap, LF_POW, RF_POW, LB_POW, RB_POW);


            //sleep tight
            sleep(50);
        }
    }
}
