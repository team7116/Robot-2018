package org.usfirst.frc.team7116.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class MoteurSimple extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Spark spark;
	
	public MoteurSimple() {
		spark = new Spark(0);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setVitesse(double v) {
    	spark.setSpeed(v);
    }
    
    public void stop( ) {
    	spark.stopMotor();
    }
}

