package org.usfirst.frc.team7116.robot.commands;

import org.usfirst.frc.team7116.robot.Robot;

import edu.wpi.first.wpilibj.GearTooth;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import vision.Vision;

/**
 *
 */
public class toggleVision extends Command {
	
	double createTime;
	boolean firstPress;
	boolean done;
	
    public toggleVision() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	firstPress = false;
    	createTime = Timer.getFPGATimestamp();
    	done = false;
    	
    }

	// Called just before this Command runs the first time
    protected void initialize() {
    	firstPress = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if(!firstPress){
    		createTime = Timer.getFPGATimestamp();
    		firstPress = true;
    	}
    	
    	//Robot.vision.toggleEnabled();
    	SmartDashboard.putNumber("Fast Create Time", createTime);
    	SmartDashboard.putBoolean("FirstPress", firstPress);
    	
    	if(Timer.getFPGATimestamp() - createTime >= 5){
    		Robot.driveTrain.tglSpeed();
    		createTime = Timer.getFPGATimestamp();
    		done = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	done = false;
    	firstPress = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
