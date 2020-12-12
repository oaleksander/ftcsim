package org.firstinspires.ftc.teamcode.robot;

public class WoENrobot {
    public static ThreeWheelOdometry odometry = new ThreeWheelOdometry();
    public static MecanumDrivetrain drivetrain = new MecanumDrivetrain();
    public static Movement movement = new Movement(odometry,drivetrain);
}


