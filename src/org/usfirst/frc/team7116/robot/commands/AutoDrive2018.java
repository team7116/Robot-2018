package org.usfirst.frc.team7116.robot.commands;

import org.usfirst.frc.team7116.robot.Robot;
import org.usfirst.frc.team7116.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDrive2018 extends Command {
	
	private DriveTrain dt;
	
	private double leftGoal;
	private double rightGoal;
	
    public AutoDrive2018() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	initProperties();
    }
    
    public AutoDrive2018(double leftGoal, double rightGoal){
    	initProperties();
    	this.leftGoal = leftGoal;
    	this.rightGoal = rightGoal;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    private void initProperties() {
    	requires (Robot.driveTrain);
    	dt = Robot.driveTrain;
    	
    }
}
