package org.firstinspires.ftc.teamcode.robot;

import com.company.ComputerDebugging;
import com.company.FloatPoint;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.math.PIDController;
import org.firstinspires.ftc.teamcode.math.Pose2D;
import org.firstinspires.ftc.teamcode.math.Vector2D;
import org.firstinspires.ftc.teamcode.math.Vector3D;
import static org.firstinspires.ftc.teamcode.math.MathUtil.*;
import org.firstinspires.ftc.teamcode.superclasses.Drivetrain;
import org.firstinspires.ftc.teamcode.superclasses.Odometry;
import org.firstinspires.ftc.teamcode.superclasses.RobotModule;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.signum;
import static java.lang.Math.sin;
import static org.firstinspires.ftc.teamcode.robot.WoENrobot.*;
import static org.firstinspires.ftc.teamcode.robot.WoENrobot.drivetrain;

public class Movement implements RobotModule {
    private static Odometry odometry;
    private static Drivetrain drivetrain;

    private static final double kP_distance = 0.02;
    private static final double kP_angle = 0.3;
    private static final double minError_distance = 4;
    private static final double minError_angle = Math.toRadians(1);


    public Movement(Odometry Odometry, Drivetrain Drivetrain) {
        odometry = Odometry;
        drivetrain = Drivetrain;
    }


    public void initialize() {}

    public boolean Pos(Pose2D target)
    {

        Pose2D error = getError(target);
        if (abs(error.heading) >= minError_angle || error.radius() >= minError_distance) {
            approachPosition(error, error.radius() * kP_distance, error.heading * kP_angle);
            return false;
        }
        drivetrain.setRobotVelocity(0, 0, 0);
        return true;
    }

    Pose2D errold = new Pose2D();
    Pose2D previousTarget = new Pose2D();

    public Pose2D getError(Pose2D target)
    {
        return target.substract(odometry.getRobotCoordinates());
    }

    public void approachPosition(Pose2D target, double linearVelocity, double angularVelocity) {

        linearVelocity = Range.clip(abs(linearVelocity), 0.0,1.0);
        angularVelocity = Range.clip(abs(angularVelocity), 0.0,1.0);

            Vector2D movementControl = new Vector2D(
                    target.x,
                    target.y).normalize().multiply(linearVelocity);

            Vector3D control = new Vector3D(movementControl,
                    angularVelocity*signum(target.heading));
            control = new Vector3D(control,control.z);



           holonomicMoveFC(control);
    }

    public boolean getPurePursuitPoint(Pose2D robotPosition, Vector2D originPoint, Vector2D targetPoint, double lookaheadRadius)
    {
        ComputerDebugging.sendLine(new FloatPoint(originPoint.x+356.0/2,originPoint.y+356.0/2),new FloatPoint(targetPoint.x+356.0/2,targetPoint.y+356.0/2));
        if(originPoint.equals(targetPoint))
            return true;
        double a = originPoint.y-targetPoint.y;
        double b = targetPoint.x-originPoint.x;
        double c = originPoint.x*targetPoint.y-originPoint.y*targetPoint.x;

        double angle = targetPoint.minus(originPoint).acot();



        Vector2D lookAheadPoint = new Vector2D(
                (b*(b*robotPosition.x-a*robotPosition.y)-a*c)/(a*a+b*b),
                (a*(-b*robotPosition.x+a*robotPosition.y)-b*c)/(a*a+b*b)
        ).add(new Vector2D(0,lookaheadRadius).rotatedCW(angle));

        if(abs(angleWrap(angle-robotPosition.heading))>Math.PI/2){angle += Math.PI;}

        ComputerDebugging.sendKeyPoint(new FloatPoint(lookAheadPoint.x+356.0/2,lookAheadPoint.y+356.0/2));

        Pose2D error = getError(new Pose2D(lookAheadPoint,angle));
        if(targetPoint.minus(originPoint).radius() <= lookAheadPoint.minus(originPoint).radius())
        {
        error = getError(new Pose2D(targetPoint,angle));
            if(error.radius() < lookaheadRadius/2)
                return true;
        approachPosition(error, error.radius() * kP_distance, error.heading * kP_angle);
        }
        else {
            approachPosition(error, 1, error.heading * kP_angle);
        }
        return false;
    }

    public void holonomicMoveFC(Vector3D move) {
        Vector2D coordinates = new Vector2D(move.x, move.y).rotatedCW(-odometry.getRobotCoordinates().heading);
        drivetrain.setRobotVelocity(coordinates.y, coordinates.x*0.5, move.z*10);
    }

    public void holonomicMovePolar(double heading, double speed, double turn) {
        turn = Range.clip(turn, -1.0, 1.0);
        speed = Range.clip(speed, -1.0, 1.0);
        double frontways = speed * cos(heading);
        double sideways = speed * sin(heading);

        drivetrain.setRobotVelocity(frontways, sideways, turn);
    }

}
