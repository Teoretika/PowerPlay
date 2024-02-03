package org.firstinspires.ftc.teamcode.PowerPlay.Lift;

import com.qualcomm.robotcore.hardware.HardwareMap;

public interface lift {

    void ClawPosition(HardwareMap hardwareMap, boolean isOpen);

    void LiftPower(HardwareMap hardwareMap, double speed);

    void LiftEncoder(HardwareMap hardwareMap, double speed, double CM);

}
