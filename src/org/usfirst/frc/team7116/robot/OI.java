/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7116.robot;

import org.usfirst.frc.team7116.robot.commands.DriveWithJoystick;
import org.usfirst.frc.team7116.robot.commands.MoteurCommand;
import org.usfirst.frc.team7116.robot.commands.PinceBaisser;
import org.usfirst.frc.team7116.robot.commands.PinceFermer;
import org.usfirst.frc.team7116.robot.commands.PinceLever;
import org.usfirst.frc.team7116.robot.commands.PinceOuvrir;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
	public Joystick stickleft = new Joystick(0);
	
	Button buttonX = new JoystickButton(stickleft, 3); // X - POUR TEST MOTEUR SIMPLE
	Button buttonLB = new JoystickButton(stickleft, 5); // Left button pour ouvrir pince
	Button buttonRB = new JoystickButton(stickleft, 6); // Right button pour fermer pince
	Button buttonA = new JoystickButton(stickleft, 1); // A - BAISSER bras de pince
	Button buttonB = new JoystickButton(stickleft, 2); // B - LEVER bras de pince
	
	
	
	
	
	
	public OI() {
		
		
		//Commande TEST pour activer un moteur avec boutton X
		buttonX.whileHeld(new MoteurCommand());
			
		//Commandes pour ouvrir et fermer la pince pneumatique (ports 1 et 2)
		buttonLB.whenPressed(new PinceOuvrir());
		buttonRB.whenPressed(new PinceFermer());
		
		//Commandes pour baisser et lever la pince pneumatique (ports 3 et 4)
		buttonA.whenPressed(new PinceBaisser());
		buttonB.whenPressed(new PinceLever());
		
		
	}
}
