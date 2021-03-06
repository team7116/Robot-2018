/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7116.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import vision.Vision;

import org.usfirst.frc.team7116.robot.commands.AutonomousCommands;
import org.usfirst.frc.team7116.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team7116.robot.subsystems.DriveTrain;
import org.usfirst.frc.team7116.robot.subsystems.Pince;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	//public static final DriveTrain driveTrain = new DriveTrain();


	
	public static OI m_oi;
	public static final DriveTrain driveTrain = new DriveTrain();
	public static final Pince pince = new Pince();

	Compressor c = new Compressor(0);
	
	AutonomousCommands autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	
	Thread visionThread;
	public static Vision vision;

	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//c.setClosedLoopControl(true);
		
		m_oi = new OI();
		m_chooser.addDefault("Default Auto", new DriveWithJoystick());
		// chooser.addObject("My Auto", new MyAutoCommand());
		//SmartDashboard.putData("Auto mode", m_chooser);
				
		driveTrain.resetEncoders();
		vision = new Vision();
		visionThread = new Thread(vision);
		
		visionThread.start();

	    
	}
	
	@Override
	public void robotPeriodic() {
		cT = Timer.getFPGATimestamp();
		dT = cT - pT;
		pT = cT;

		SmartDashboard.putNumber("Pince Y", pince.getY());
		SmartDashboard.putNumber("Time", cT);
		SmartDashboard.putNumber("delta Time", dT);

		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

		
	}

	@Override
	public void disabledPeriodic() {

		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = new AutonomousCommands();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		
		// Code permettant de recuperer les switchs pour le mode autonome.
		driveTrain.resetEncoders();
		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();

		
		if (gameData.length() > 0) {
			if (gameData.charAt(0) == 'L') {
				// Put left auto code here
			} else {
				//autonomousCommand = new AutonomousCommands(1);
			}
		}

		// schedule the autonomous command (example)
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		
		driveTrain.resetEncoders();
		
		setMessage("Teleop init");
	}

	double cT = Timer.getFPGATimestamp();
	public static double dT = 0;
	double pT = cT;
	
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {

		
		
		Scheduler.getInstance().run();
		
	}
	
	
	public static double getDeltaTime() {
		return dT;
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		
	}
	
	@Override
	public void testInit() {
		driveTrain.resetEncoders();
	}
	
	public static void setMessage(String message) {
		SmartDashboard.putString ("Message", message);
	}
}
