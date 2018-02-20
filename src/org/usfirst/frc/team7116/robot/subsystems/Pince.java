package org.usfirst.frc.team7116.robot.subsystems;

import org.usfirst.frc.team7116.robot.RobotMap;


import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pince extends Subsystem {

	Solenoid ouverture;
	Solenoid fermeture;
	Solenoid baisser;
	Solenoid lever;
	
	MPU6050 mpu6050;
	
	
	
	byte [] toSend = new byte[1];
	
	int [] accelData = new int[6];
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	
	public Pince() {
		
		ouverture = new Solenoid(RobotMap.pcmAdr, RobotMap.SolenoidOuverture);
		fermeture = new Solenoid(RobotMap.pcmAdr, RobotMap.SolenoidFermeture);
		baisser = new Solenoid(RobotMap.pcmAdr, RobotMap.SolenoidBaisserPince);
		lever = new Solenoid(RobotMap.pcmAdr, RobotMap.SolenoidLeverPince);
		
		mpu6050 = new MPU6050();
		mpu6050.reset();
	}
	
	
	public void ouvrir() {
		fermeture.set(false);
		ouverture.set(true);
	}
	
	public void fermer() {
		ouverture.set(false);
		fermeture.set(true);
	}
	
	public void baisser() {
		lever.set(false);
		baisser.set(true);
	}

	
	public void lever() {
		lever.set(true);
		baisser.set(false);
	}
	
	public double getY() {
		return mpu6050.getYAcceleration();
	}


}

