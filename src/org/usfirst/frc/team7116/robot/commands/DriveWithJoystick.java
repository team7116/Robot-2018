/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7116.robot.commands;

import org.usfirst.frc.team7116.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveWithJoystick extends Command {
	public DriveWithJoystick() {
		
		requires(Robot.driveTrain);
	}
	
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		
		Robot.driveTrain.drive(Robot.m_oi.stickleft);
		
		SmartDashboard.putNumber("RPM Left", Robot.driveTrain.getLeftWheelVelocity());
		SmartDashboard.putNumber("RPM Right", Robot.driveTrain.getRightWheelVelocity());
		SmartDashboard.putNumber("Position Left", Robot.driveTrain.getLeftWheelPosition());
		SmartDashboard.putNumber("Position Right", Robot.driveTrain.getRightWheelPosition());
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	  Robot.driveTrain.stop();	
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
