package org.usfirst.frc.team7116.robot.commands;

import org.usfirst.frc.team7116.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PinceOuvrir extends Command {

    public PinceOuvrir() {
        requires(Robot.pince);
        this.delay = 0;
    }
    
    public PinceOuvrir (double delay) {
    	PinceOuvrir.delay = delay;
    	requires(Robot.pince);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.pince.ouvrir();
    	SmartDashboard.putString("Grip state", "Grip opened");
    }

    static double delay = 0;
    static double accumulator = 0;
    boolean done = false;
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		accumulator += Robot.getDeltaTime();
		
		if (accumulator > delay) {
			accumulator = 0;
			Robot.pince.ouvrir();
			
			done = true;
		}
		
		Robot.setMessage("Waiting to open (" + accumulator + ")");
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
