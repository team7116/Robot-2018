package org.usfirst.frc.team7116.robot.commands;

import org.usfirst.frc.team7116.robot.Robot;
import org.usfirst.frc.team7116.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveAutoCubeTracking extends Command {
	
	private DriveTrain dt;
	
	public double ErrorMargin = 30;
	
	float goalX;
	float errorX; 
	float offset;
	
	float vitesse = 0.1f;
	
	int width;
	
	boolean quit = false;
	
    public DriveAutoCubeTracking() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super();
    	initProperties();
    	

		
    }
    
    private void initProperties() {
    	requires (Robot.driveTrain);
    	dt = Robot.driveTrain;
    	System.out.println("DriveCube initProperties");
    	
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	
		width = Robot.vision.get_width();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	goalX = Robot.vision.get_blob_center();
    	
    	offset = goalX - (width/2);
		errorX = Math.abs(offset);
		
		if(errorX < ErrorMargin){
			quit = true;
			return;
		}
		
		if(offset > 0){
			dt.wheelLeft.set(vitesse);
			dt.wheelRight.set(vitesse);
		}else {
			dt.wheelLeft.set(-vitesse);
			dt.wheelRight.set(-vitesse);
		}
		
		//Left Inverse   ( >0 -> Backward    <0 -> Forward)
		//Right correct  ( >0 -> Forward    <0 -> Backward)
		
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return quit;
    }
    
    
    float clac_currentX(){
    	
    	
    	return Math.abs(goalX - (width/2));
    }
    

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
