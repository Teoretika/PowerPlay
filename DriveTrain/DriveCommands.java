package org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveCommands implements drive {

    DriveHardwareMap motor = new DriveHardwareMap();

    //Encoder constants
    static final double COUNTS_PER_MOTOR_REV = 28 ;
    static final double DRIVE_GEAR_REDUCTION = 20.0 ;
    static final double WHEEL_DIAMETER_CM = 7.5 ;
    static final double COUNTS_PER_CM = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                        (WHEEL_DIAMETER_CM * 3.1415);


    public void MotorPower (HardwareMap hardwareMap, double LF_POW, double RF_POW, double LB_POW, double RB_POW) {
        motor.init(hardwareMap);

        motor.leftFront.setPower(LF_POW);
        motor.rightFront.setPower(RF_POW);
        motor.leftBack.setPower(LB_POW);
        motor.rightBack.setPower(RB_POW);

    }


    public void MotorPower (HardwareMap hardwareMap, double POW){

        MotorPower(hardwareMap, POW, POW, POW, POW);

    }


    public void MotorEncoder(HardwareMap hardwareMap, double speed, double LF_CM, double RF_CM, double LB_CM, double RB_CM) {
        motor.init(hardwareMap);
        
        int LFTarget;
        int RFTarget;
        int LBTarget;
        int RBTarget;

        LFTarget = motor.leftFront.getCurrentPosition() + (int)(LF_CM * COUNTS_PER_CM);
        RFTarget = motor.rightFront.getCurrentPosition() + (int)(RF_CM * COUNTS_PER_CM);
        LBTarget = motor.leftBack.getCurrentPosition() + (int)(LB_CM * COUNTS_PER_CM);
        RBTarget = motor.rightBack.getCurrentPosition() + (int)(RB_CM * COUNTS_PER_CM);

        motor.leftFront.setTargetPosition(LFTarget);
        motor.rightFront.setTargetPosition(RFTarget);
        motor.leftBack.setTargetPosition(LBTarget);
        motor.rightBack.setTargetPosition(RBTarget);

        // Turn On RUN_TO_POSITION
        motor.leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Start motion
        MotorPower(hardwareMap, Math.abs(speed));


        while (motor.leftFront.isBusy() && motor.rightFront.isBusy() &&
                motor.leftBack.isBusy() && motor.rightBack.isBusy()) {
        }

        // Stop all motion;
        MotorPower(hardwareMap, 0);

        // Turn off RUN_TO_POSITION
        motor.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}


