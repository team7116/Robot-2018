package org.usfirst.frc.team7116.robot.subsystems;

import org.usfirst.frc.team7116.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class MoteurSimple extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Spark spark;
	
	public WPI_TalonSRX wheelRight;
	
	
	public MoteurSimple() {
		spark = new Spark(0);
		wheelRight = new WPI_TalonSRX(RobotMap.TalonDroite);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setVitesse(double v) {
    	spark.setSpeed(v);
    	wheelRight.set(v);
    }
    
    public void stop( ) {
    	spark.stopMotor();
    	wheelRight.stopMotor();
    }
}

