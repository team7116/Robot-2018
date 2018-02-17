package org.usfirst.frc.team7116.robot.commands;


import org.usfirst.frc.team7116.robot.Robot;
import org.usfirst.frc.team7116.robot.RobotMap;
import org.usfirst.frc.team7116.robot.RobotMap.JoyConfig;
import org.usfirst.frc.team7116.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoyStickConfigurator extends Command {

    public JoyStickConfigurator() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	switch (DriveTrain.driveConfig) {
    	case kTriggers:
    		DriveTrain.driveConfig = JoyConfig.kleftStickOnly;
    		break;
    	case kleftStickOnly:
    		DriveTrain.driveConfig = JoyConfig.kBothSticks;
    		break;
    	case kBothSticks:
    		DriveTrain.driveConfig = JoyConfig.kTriggers;
    		break;
    	}

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
}
