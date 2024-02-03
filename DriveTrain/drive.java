package org.firstinspires.ftc.teamcode.PowerPlay.DriveTrain;

import com.qualcomm.robotcore.hardware.HardwareMap;

public interface drive {

    void MotorPower (HardwareMap hardwareMap, double LF_POW, double RF_POW, double LB_POW, double RB_POW);

    void MotorEncoder(HardwareMap hardwareMap, double speed, double LF_CM, double RF_CM, double LB_CM, double RB_CM);

}
