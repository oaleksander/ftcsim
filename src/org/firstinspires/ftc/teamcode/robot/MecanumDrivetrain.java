package org.firstinspires.ftc.teamcode.robot;



import org.firstinspires.ftc.teamcode.math.Vector3D;
import org.firstinspires.ftc.teamcode.superclasses.Drivetrain;
import org.firstinspires.ftc.teamcode.superclasses.RobotModule;

import static RobotUtilities.MovementVars.movement_x;
import static RobotUtilities.MovementVars.movement_y;
import static RobotUtilities.MovementVars.movement_turn;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.signum;

public class MecanumDrivetrain implements RobotModule, Drivetrain {

    public void setRobotVelocity(double frontways, double sideways, double turn) {
        turn = -turn;
        double frontLeft = frontways + sideways + turn;
        double frontRight = frontways - sideways - turn;
        double rearLeft = frontways - sideways + turn;
        double rearRight = frontways + sideways - turn;
        double maxabs = max(max(abs(frontLeft), abs(frontRight)), max(abs(rearLeft), abs(rearRight)));
        if (maxabs > 1)
        {
            frontways/=maxabs;
            sideways/=maxabs;
            turn/=maxabs;
        }
        movement_x = sideways;
        movement_y = frontways;
        movement_turn = turn;
    }

    public void setRobotVelocity(Vector3D move) {
        setRobotVelocity(move.y, move.x, move.z);
    }


}