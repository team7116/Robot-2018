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
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class DriveTrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public WPI_TalonSRX wheelLeft;
	public WPI_TalonSRX wheelRight;
	
	public TalonSRX wl;
	
	public DifferentialDrive drive;
	
	public Encoder encoderLeft;
	public Encoder encoderRight;
	
	
	private final double TICK_PER_CM = 42.75;

	/**
	 * Distance entre le centre la roue du centre

	 */
	
	private double wheelToCentre = 25; // cm
	private double wheelDiametre = 15.24;
	private double positionInDistance = 47.87787204070844895417068516118;
	private double wheelCircumference = 47.87787;
	
	public double XFactor = 0.6;
	public double XThreshold = 1;

	public SensorCollection lwSensors;

	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new DriveWithJoystick());
	}
	
	public DriveTrain() {
		super();
		wheelLeft = new WPI_TalonSRX(RobotMap.TalonGauche);
		wheelRight = new WPI_TalonSRX(RobotMap.TalonDroite);
	
		drive = new DifferentialDrive(wheelLeft, wheelRight);
		
		// Src : http://www.ctr-electronics.com/downloads/api/cpp/html/classctre_1_1phoenix_1_1motorcontrol_1_1can_1_1_talon_s_r_x.html#a2b15046cefe6828a27409584077c7397
		wheelLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, RobotMap.kTimeoutMs);
		wheelRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, RobotMap.kTimeoutMs);
		
		// Inversion des encodeurs
		wheelLeft.setSensorPhase(false);
		wheelRight.setSensorPhase(true);
		
		resetEncoders();

		
		wheelLeft.setSafetyEnabled(false);
		wheelRight.setSafetyEnabled(false);
		
		
		
	}
	

	double msgAcc = 0;
	double msgInt = 250;
	
	public double remapx(double X){
		
		if(Math.abs(X) >= XThreshold) {
			if(X > 0) {
				X = 1;
			}else {
				X = -1;
			}
		}else {
			X *= XFactor;
		}
		
		return X;
		
	}
		
	
	public void drive(XboxController stick){
		
		msgAcc += Robot.dT;
		
		drive.arcadeDrive(-stick.getY(Hand.kLeft), remapx(stick.getX(Hand.kRight)));
		
		
		if (msgAcc >= msgInt) {
			msgAcc = 0;
			
			System.out.println("DriveTrain.drive");
		}
	}
	
	public void stop() {
		drive.stopMotor();
	}

	public double getWheelToCentreDistance() {
		// TODO Auto-generated method stub
		return wheelToCentre;
	}
	
	public void setPosition(double cmLeft, double cmRight) {
		wheelLeft.set(ControlMode.Position, TICK_PER_CM * cmLeft);
		wheelRight.set(ControlMode.Position, TICK_PER_CM * cmRight);
	}
	
	public int getLeftWheelVelocity() {
		return wheelLeft.getSensorCollection().getQuadratureVelocity();
	}
	
	public int getRightWheelVelocity() {
		return wheelRight.getSensorCollection().getQuadratureVelocity();
	}
	
	public int getLeftWheelPosition() {
		return wheelLeft.getSensorCollection().getQuadraturePosition();
	}
	
	public int getRightWheelPosition() {
		return -wheelRight.getSensorCollection().getQuadraturePosition();
	}
	
	public void resetEncoders() {
		wheelLeft.setSelectedSensorPosition(0, 0, RobotMap.kTimeoutMs);
		wheelRight.setSelectedSensorPosition(0, 0, RobotMap.kTimeoutMs);
	}
}
