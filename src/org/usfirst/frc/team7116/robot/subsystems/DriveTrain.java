/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7116.robot.subsystems;

import org.usfirst.frc.team7116.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class DriveTrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public TalonSRX roueGauche;
	public TalonSRX roueDroite;
	public DifferentialDrive drive;
	
	SpeedControllerGroup TalonGauche, TalonDroite;
	
	/**
	 * Distance entre le centre la roue du centre

	 */
	
	private double distanceCentreRoues = 25; // cm
	private double diametreRoues = 15.24;
	private double positionInDistance = 47.87787204070844895417068516118;
	private double circonferenceRoues = 47.87787;

	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public DriveTrain() {
		super();
		roueGauche = new TalonSRX(RobotMap.TalonGauche);
		roueDroite = new TalonSRX(RobotMap.TalonDroite);

		drive = new DifferentialDrive(TalonGauche, TalonDroite);
	}
	
	public void drive(Joystick stick){
		
		drive.arcadeDrive(stick.getY(), stick.getX());
		
	}
	
	public void stop() {
		drive.stopMotor();
	}
}
