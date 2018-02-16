package org.usfirst.frc.team7116.robot.subsystems;

import org.usfirst.frc.team7116.robot.RobotMap;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Camera extends Subsystem {

	
    Servo pan;
    Servo tilt;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public Camera() {
    	pan = new Servo(RobotMap.ServoPan);  //Elevation camera
    	tilt = new Servo(RobotMap.ServoTilt); //Rotation camera
    }
}

