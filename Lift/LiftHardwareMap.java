package org.firstinspires.ftc.teamcode.PowerPlay.Lift;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LiftHardwareMap {
    // HardwareMap initialization
    HardwareMap hardwareMap = null;

    // Motors
    DcMotor lift = null;

    // Servo
    Servo claw = null;

    public LiftHardwareMap() {
    }

    public void init(HardwareMap ahwMap) {

        hardwareMap = ahwMap;

        // Lift
        lift = hardwareMap.get(DcMotor.class, "lift");

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // Claw
        claw = hardwareMap.get(Servo.class, "claw");
        claw.setDirection(Servo.Direction.REVERSE);

    }
}
