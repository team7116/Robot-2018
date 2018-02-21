/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7116.robot;

import org.usfirst.frc.team7116.robot.commands.AutoDrive;
import org.usfirst.frc.team7116.robot.commands.AutoDrive2018;
import org.usfirst.frc.team7116.robot.commands.DownAndRelease;
import org.usfirst.frc.team7116.robot.commands.DriveAutoCubeTracking;
import org.usfirst.frc.team7116.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team7116.robot.commands.LeverBaisserGrappin;
import org.usfirst.frc.team7116.robot.commands.PinceBaisser;
import org.usfirst.frc.team7116.robot.commands.PinceFermer;
import org.usfirst.frc.team7116.robot.commands.PinceLever;
import org.usfirst.frc.team7116.robot.commands.PinceOuvrir;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import vision.Vision;

public class OI {
	
	public XboxController stickleft = new XboxController(0);
	 
	Button buttonX = new JoystickButton(stickleft, 3); // X - Lever bras grappin
	Button buttonY = new JoystickButton(stickleft, 4); //Y - Baisser bras grappin
	Button buttonLB = new JoystickButton(stickleft, 5); // Left button pour ouvrir pince
	Button buttonRB = new JoystickButton(stickleft, 6); // Right button pour fermer pince
	Button buttonA = new JoystickButton(stickleft, 1); // A - BAISSER bras de pince
	Button buttonB = new JoystickButton(stickleft, 2); // B - LEVER bras de pince
	Button buttonSelect = new JoystickButton(stickleft, 7); // Back button;
	
	
	public OI() {
		
		
		//Commande pour baisser et lever le bras du grappin (ports 3 et 4)
		//buttonX.whileHeld(new LeverBaisserGrappin());
		buttonY.whenPressed(new DownAndRelease());
		
		//buttonX.whenPressed(new AutoDrive(16192, 16192));
		
		buttonX.whileHeld(new DriveAutoCubeTracking());
			
		//Commandes pour ouvrir et fermer la pince pneumatique (ports 1 et 2)
		buttonLB.whenPressed(new PinceOuvrir());
		buttonRB.whenPressed(new PinceFermer());
		
		//Commandes pour baisser et lever la pince pneumatique (ports 3 et 4)
		buttonA.whenPressed(new PinceBaisser());
		buttonB.whenPressed(new PinceLever());
		
		
	}
}
