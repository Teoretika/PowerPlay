package org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain.DriveLocations;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain.DriveCommands;

public class DriveToLocationRight {
    DriveCommands drive = new DriveCommands();
    static final double MOVE1 = 62.0;
    static final double SIDE = 60.0;
    static final double TURN = 34;



    public void Location1(HardwareMap hardwareMap){
        drive.MotorEncoder(hardwareMap, 0.8, SIDE, -SIDE,-SIDE, SIDE);
    }

    public void Location2(HardwareMap hardwareMap){

    }

    public void Location3(HardwareMap hardwareMap){
        drive.MotorEncoder(hardwareMap, 0.8, 3, 3, 3, 3);
        drive.MotorEncoder(hardwareMap, 0.8, -SIDE, SIDE, SIDE, -SIDE);
    }

}
