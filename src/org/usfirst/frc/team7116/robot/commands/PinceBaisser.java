package org.usfirst.frc.team7116.robot.commands;

import org.usfirst.frc.team7116.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PinceBaisser extends Command {

    public PinceBaisser() {
        requires(Robot.pince);
    }
    
    double delay = 0;
    double accumulator = 0;
    
    public PinceBaisser(double delay) {
        requires(Robot.pince);
        this.delay = delay;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	if (delay == 0) {
    		Robot.pince.baisser();
    	}
    }

    boolean done = false;
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    
    	if (delay > 0) {
    		accumulator += Robot.dT;
    		
    		if (accumulator > delay) {
    			accumulator = 0;
    			Robot.pince.baisser();
    			
    			done = true;
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
