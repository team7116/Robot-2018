/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7116.robot;

import org.usfirst.frc.team7116.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team7116.robot.commands.MoteurCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
	public Joystick stickleft = new Joystick(0);
	
	Button buttonA = new JoystickButton(stickleft, 1);
	
	public OI() {
		
		buttonA.whenPressed(new MoteurCommand());
		
		
	}
}
