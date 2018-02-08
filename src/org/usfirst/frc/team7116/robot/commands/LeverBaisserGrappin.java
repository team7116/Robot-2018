package org.usfirst.frc.team7116.robot.commands;

import org.usfirst.frc.team7116.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


public class LeverBaisserGrappin extends Command {

	static double vitesse = 0.5;
	
    public LeverBaisserGrappin() {
         requires(Robot.moteur);
         
    }

    protected void initialize() {
    	System.out.println("MoteurCommand : Init");
    	
    	if(Robot.m_oi.stickleft.getRawButton(3))
    	{
    		vitesse= 0.5;  
    	}
    	else
    	{
    		vitesse=-0.5;
    	}
    	
    	Robot.moteur.setVitesse(vitesse);
    }
    	
    protected void execute() {
    	
    }
    
    protected boolean isFinished() {
    	
    	if(Robot.moteur.switchA() && vitesse>0 || Robot.moteur.switchB() && vitesse<0) {
    		vitesse=-vitesse;
    		System.out.println("Limite atteinte");
    		return true;
    	}
    	
        return false;
    }

    protected void end() {
    	Robot.moteur.stop();
    }

    protected void interrupted() {
    	end();
    }
}