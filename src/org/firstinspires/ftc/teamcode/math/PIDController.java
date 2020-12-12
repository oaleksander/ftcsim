package org.firstinspires.ftc.teamcode.math;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {
    private double kP;
    private double kI;
    private double kD;
    private double antiWind=-1;
    private ElapsedTime PIDClock;
    private double prevError = 0;
    private double i = 0;
    private boolean started=false;

    public PIDController(double kP, double kI, double kD){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public PIDController(double kP, double kI, double kD, double antiWind){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.antiWind = antiWind;
    }

    public void start(double error){
        started=true;
        PIDClock = new ElapsedTime();
        prevError = error;
        i = 0;
    }
    public void clear(){
        started=false;
        PIDClock = new ElapsedTime();
        prevError = 0;
        i = 0;
    }
    public void changeConstants(double kP, double kI, double kD, double antiWind){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.antiWind = antiWind;
    }

    double target_old;

    public double getValue (double target, double actual) {
        if(target!=target_old)
            clear();
        target_old = target;
        return getValue(target - actual);
    }

    public double getValue (double error) {

        //throw (new Exception("NotStarted"));

        if (!started) {
            start(error);
        }
        double p = error;
        /*i += (error * PIDClock.seconds());
        double d = ((error - prevError) / PIDClock.seconds());
        prevError = error;
        PIDClock.reset();
        if (antiWind >= 0) {
            if (Math.abs(kI * i) >= antiWind) {
                i = antiWind / kI * (i/Math.abs(i));
                return kP * p + antiWind*(i/Math.abs(i)) + kD * d;
            } else {

                System.out.println("p:" + p*kP);
                System.out.println("i:" + i*kI);
                System.out.println("d:" + d*kD);
                return kP * p + kI * i + kD * d;

            }
        }
        return kP * p + kI * i + kD * d;*/
        return kP*p;
    }
}