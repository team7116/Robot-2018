/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7116.robot.subsystems;

import java.util.ArrayList;

import org.usfirst.frc.team7116.robot.Robot;
import org.usfirst.frc.team7116.robot.RobotMap;
import org.usfirst.frc.team7116.robot.RobotMap.JoyConfig;
import org.usfirst.frc.team7116.robot.commands.DriveWithJoystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
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
	
	public final int TICK_PER_ROTATION = 8092;
	private final double TICK_PER_CM = 169;
	
	private DigitalInput pin_8;
	private DigitalInput pin_9;
	
	private ArrayList<DigitalInput> dios;

	/**
	 * Distance entre le centre la roue du centre

	 */
	
	//auto switch: 8-9
	
	
	//=====Encoder PPR: 2048
	
	private double wheelToCentre = 25; // cm
	private double wheelDiametre = 15.24;
	private double positionInDistance = 47.87787204070844895417068516118;
	private double wheelCircumference = 47.87787;
	
	public double XFactor = 0.6;
	public double XThreshold = 0.9;
	
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
		
//		dios = new ArrayList<>();
//		
//		for (int i = 0; i < 10; i++) {
//			DigitalInput current = new DigitalInput(i);
//			
//			dios.add(current);
//		}
//		
		// Src : http://www.ctr-electronics.com/downloads/api/cpp/html/classctre_1_1phoenix_1_1motorcontrol_1_1can_1_1_talon_s_r_x.html#a2b15046cefe6828a27409584077c7397
		wheelLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, RobotMap.kTimeoutMs);
		wheelRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, RobotMap.kTimeoutMs);
		
		wheelLeft.configContinuousCurrentLimit(RobotMap.kCurrentLimit, RobotMap.kTimeoutMs);
		wheelLeft.configPeakCurrentDuration(0, RobotMap.kTimeoutMs);
		wheelLeft.enableCurrentLimit(true);
		
		wheelRight.configContinuousCurrentLimit(RobotMap.kCurrentLimit, RobotMap.kTimeoutMs);
		wheelRight.configPeakCurrentDuration(0, RobotMap.kTimeoutMs);
		wheelRight.enableCurrentLimit(true);
		
		// Before G18
		wheelRight.setInverted(true);
		//wheelLeft.setInverted(true);
		
		// Inversion des encodeurs
		//wheelLeft.setSensorPhase(false);
		//wheelRight.setSensorPhase(true);
		
		resetEncoders();

		
		wheelLeft.setSafetyEnabled(false);
		wheelRight.setSafetyEnabled(false);
		
		//wheelRight.config_kP(10, 0, RobotMap.kTimeoutMs);
		
		wheelRight.valueUpdated();
		wheelLeft.valueUpdated();
		

		
	}
	

	double msgAcc = 0;
	double msgInt = 250;
	
	public double remapx(double X){
		
		if(Math.abs(X) >= XThreshold) {
			XFactor = 0.9;
		}else {
			XFactor = 0.6;
		}
		
		X *= XFactor;
		
		return X;
		
	}
		
	public static JoyConfig driveConfig = RobotMap.JoyConfig.kTriggers;
	
	public void drive(XboxController stick){
		
		if (stick.getBackButtonReleased()) {
			setJoystick();
		}
		
		msgAcc += Robot.dT;
		
		double yAxis = -stick.getTriggerAxis(Hand.kLeft) + stick.getTriggerAxis(Hand.kRight);
		double xAxis = remapx(stick.getX(Hand.kLeft));

		switch(driveConfig){
		case kTriggers:
			yAxis = -stick.getTriggerAxis(Hand.kLeft) + stick.getTriggerAxis(Hand.kRight);
			SmartDashboard.putString("JoyStick Mode", "Triggers");
			break;
		case kleftStickOnly:
			yAxis = -stick.getY(Hand.kLeft);
			SmartDashboard.putString("JoyStick Mode", "Left Stick Only");
			break;
		case kBothSticks:
			yAxis = -stick.getY(Hand.kLeft);
			xAxis = remapx(stick.getX(Hand.kRight));
			SmartDashboard.putString("JoyStick Mode", "Both Sticks");
			break;
		}
		
		drive.arcadeDrive(yAxis, xAxis);
		
		if (msgAcc >= msgInt) {
			msgAcc = 0;
			
			System.out.println("DriveTrain.drive");
		}
	}
	
	public void setJoystick() {
		
		
    	switch (driveConfig) {
    	case kTriggers:
    		driveConfig = JoyConfig.kleftStickOnly;
    		break;
    	case kleftStickOnly:
    		driveConfig = JoyConfig.kBothSticks;
    		break;
    	case kBothSticks:
    		driveConfig = JoyConfig.kTriggers;
    		break;
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
	
	public void setRightSpeed(double speed) {
		wheelRight.set(-speed);
	}
	
	public void setLeftSpeed(double speed) {
		wheelLeft.set(speed);
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
	
	
//	// Retourne la valeur du DIO indique
//	public boolean readDIO(int channel) {
//		
//		if (dios != null)
//			return dios.get(channel).get();
//		
//		return false;
//	}
	
}
