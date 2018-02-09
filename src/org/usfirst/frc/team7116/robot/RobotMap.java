/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7116.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	public static int TalonGauche = 10;
	public static int TalonDroite = 12;
	
	public static int SolenoidOuverture = 0;// ouverture pince
	public static int SolenoidFermeture = 1;// fermeture pince
	
	public static int SolenoidBaisserPince = 2;// baisser pince
	public static int SolenoidLeverPince = 3; // lever pince
	
	public static int pcmAdr = 20;
	
	public static int ServoPan = 0;
	public static int ServoTilt = 1;
	
}
