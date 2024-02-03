package org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain.DriveHardwareMap;
import org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain.DriveCommands;

public class DistanceForAuto {
    DriveHardwareMap motor = new DriveHardwareMap();
    DriveCommands drive = new DriveCommands();

    static final double AHEAD = 61.0;
    static final double SIDE = 30.0;
    static final double CLOSE = 6.0;


    public void toMedium(HardwareMap hardwareMap){
        drive.MotorEncoder(hardwareMap, 0.8, AHEAD, AHEAD, AHEAD, AHEAD);
        drive.MotorEncoder(hardwareMap, 0.8, SIDE, -SIDE, -SIDE, SIDE);
    }

}
