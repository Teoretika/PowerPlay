package org.firstinspires.ftc.teamcode.PowerPlay.Lift;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LiftAndClawCommands implements lift {

    LiftHardwareMap slider = new LiftHardwareMap();

    //Encoder constants
    static final double COUNTS_PER_MOTOR_REV = 28 ;
    static final double LIFT_GEAR_REDUCTION = 5.0 ;
    static final double LIFT_DIAMETER_CM = 0.5 ;
    static final double COUNTS_PER_CM = (COUNTS_PER_MOTOR_REV * LIFT_GEAR_REDUCTION) /
            (LIFT_DIAMETER_CM * 3.1415);

    static final double OPEN = 0.15;
    static final double CLOSE = 0.45;


    public void ClawPosition(HardwareMap hardwareMap, boolean isOpen){
        slider.init(hardwareMap);

        if(isOpen){
            slider.claw.setPosition(OPEN);
        }
        if (!isOpen){
            slider.claw.setPosition(CLOSE);
        }

    }

    public void LiftPower(HardwareMap hardwareMap, double speed){
        slider.init(hardwareMap);
        slider.lift.setPower(speed);
    }

    public void LiftEncoder(HardwareMap hardwareMap, double speed, double CM) {
        slider.init(hardwareMap);

        int NEWTarget;

        NEWTarget = slider.lift.getCurrentPosition() + (int)(CM * COUNTS_PER_CM);

        slider.lift.setTargetPosition(NEWTarget);

        // Turn On RUN_TO_POSITION
        slider.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Start motion
        slider.lift.setPower(Math.abs(speed));

        while (slider.lift.isBusy()) {
        }

        // Stop all motion;
        slider.lift.setPower(0);

        // Turn off RUN_TO_POSITION
        slider.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}


