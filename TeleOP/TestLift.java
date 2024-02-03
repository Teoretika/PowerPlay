package org.firstinspires.ftc.teamcode.PowerPlay.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.PowerPlay.Lift.LiftAndClawCommands;
import org.firstinspires.ftc.teamcode.PowerPlay.Lift.LiftHardwareMap;

@TeleOp(name="Test Lift", group="Robot")

public class TestLift extends LinearOpMode {

    //Get hardwareMap
    LiftHardwareMap slider = new LiftHardwareMap();
    LiftAndClawCommands command = new LiftAndClawCommands();

    @Override
    public void runOpMode() {

        //Initialize hardware
        slider.init(hardwareMap);

        //Driving variables
        double y2;

        //Send telemetry message to signify robot waiting
        telemetry.addData("Robot >", "Press Start");
        telemetry.update();


        waitForStart();

        while (opModeIsActive()) {

            // Controller 2
            y2 = -gamepad2.left_stick_y;

            command.LiftPower(hardwareMap, y2);

            if(gamepad2.a){
                command.ClawPosition(hardwareMap, true);
            }
            if(gamepad2.a){
                command.ClawPosition(hardwareMap, false);
            }


            //sleep tight
            sleep(50);
        }
    }
}
