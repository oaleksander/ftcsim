package treamcode;

import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.math.Pose2D;
import org.firstinspires.ftc.teamcode.math.Vector2D;
import org.firstinspires.ftc.teamcode.superclasses.MotionTask;

import static java.lang.Math.toRadians;
import static org.firstinspires.ftc.teamcode.robot.WoENrobot.*;
import static org.firstinspires.ftc.teamcode.math.MathUtil.angleWrap;

import java.util.ArrayList;

public class MyOpMode extends OpMode {


    long startTime = 0;

    static byte xSign = 1;
    static byte sideSign = -1;

    public Pose2D getStartPosition() {
        return new Pose2D(93.75 * xSign + 31.25 * sideSign, -156.5, 0);

    }


    ArrayList<MotionTask> allPoints = new ArrayList<>();

    @Override
    public void init() {
        keyListener.main();

        odometry.setRobotCoordinates(getStartPosition());
        allPoints.add(new MotionTask(getStartPosition()));


        allPoints.add(new MotionTask(xSign * 25, 25));
        allPoints.add(new MotionTask(xSign * 60, 90));
        allPoints.add(new MotionTask(xSign * 110, 130, toRadians(60 * xSign)));
        allPoints.add(new MotionTask(xSign * 35, -30, toRadians(0 * xSign)));
        allPoints.add(new MotionTask(93.75 * 1 + 31.25 * (-1) + 10, 25, 0, ()->{
             System.out.println("yay 3!");
         }));
        movTime.reset();
    }

    ElapsedTime movTime = new ElapsedTime();
    int i = 1;
    boolean stop = false;

    @Override
    public void loop() {
        boolean success = false;
        if (i < allPoints.size()) {
            success = false;
            if (i == allPoints.size() - 1 || !Double.isNaN(allPoints.get(i).heading)) {
                if(movement.getPurePursuitPoint(odometry.getRobotCoordinates(), allPoints.get(i - 1), allPoints.get(i), 50)) {
                    success = movement.Pos(allPoints.get(i));
                }

                if (success) {
                    drivetrain.setRobotVelocity(0, 0, 0);
                    allPoints.get(i).actionOnConpletion.execute();
                    i++;
                }
            }
            else
            if (movement.getPurePursuitPoint(odometry.getRobotCoordinates(), allPoints.get(i - 1), allPoints.get(i), 50))
                i++;
        } else {

            if (!stop) {
                drivetrain.setRobotVelocity(0, 0, 0);
                stop = true;

                System.out.println(movTime.seconds());

            }
            //movement.Pos(allPoints.get(i-1));
           /// System.out.println(odometry.getRobotCoordinates());

        }
    }
}
