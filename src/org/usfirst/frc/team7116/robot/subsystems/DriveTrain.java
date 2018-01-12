/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7116.robot.subsystems;

import org.usfirst.frc.team7116.robot.Robot;
import org.usfirst.frc.team7116.robot.RobotMap;
import org.usfirst.frc.team7116.robot.commands.DriveWithJoystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class DriveTrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public WPI_TalonSRX wheelLeft;
	public WPI_TalonSRX wheelRight;
	
	public DifferentialDrive drive;

	/**
	 * Distance entre le centre la roue du centre

	 */
	
	private double distanceCentreRoues = 25; // cm
	private double diametreRoues = 15.24;
	private double positionInDistance = 47.87787204070844895417068516118;
	private double circonferenceRoues = 47.87787;

	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new DriveWithJoystick());
	}
	
	public DriveTrain() {
		super();
		wheelLeft = new WPI_TalonSRX(RobotMap.TalonGauche);
		wheelRight = new WPI_TalonSRX(RobotMap.TalonDroite);
	
		drive = new DifferentialDrive(wheelLeft, wheelRight);
	}
	

	double msgAcc = 0;
	double msgInt = 250;
	
	public void drive(Joystick stick){
		
		msgAcc += Robot.dT;
		
		drive.arcadeDrive(stick.getY(), stick.getX());
		
		if (msgAcc >= msgInt) {
			msgAcc = 0;
			
			System.out.println("DriveTrain.drive");
		}
	}
	
	public void stop() {
		drive.stopMotor();
	}
}
