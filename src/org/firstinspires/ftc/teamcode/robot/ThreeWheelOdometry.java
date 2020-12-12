package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.teamcode.math.Pose2D;
import org.firstinspires.ftc.teamcode.math.Vector3D;
import org.firstinspires.ftc.teamcode.superclasses.Odometry;
import org.firstinspires.ftc.teamcode.superclasses.RobotModule;


import static com.company.Robot.*;
import static java.lang.Math.toRadians;
import static org.firstinspires.ftc.teamcode.math.MathUtil.angleWrap;

public class ThreeWheelOdometry implements Odometry, RobotModule {

    public Pose2D getRobotCoordinates() {
        return new Pose2D(worldXPosition-365/2, worldYPosition-365/2, -worldAngle_rad+toRadians(90));
    }

    public Vector3D getRobotVelocity()
    {
        double angularVelocity = 0;
        return new Vector3D(
                0,
                0,
                0);
    }

    public void setRobotCoordinates(Pose2D coordinates)
    {
        worldXPosition = coordinates.x+365/2;
        worldYPosition = coordinates.y+365/2;
        worldAngle_rad = angleWrap(-coordinates.heading+toRadians(90));
    }

}