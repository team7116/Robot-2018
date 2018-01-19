package org.usfirst.frc.team7116.robot.commands;

import org.usfirst.frc.team7116.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


public class MoteurCommand extends Command {

	static double vitesse = 0.5;
	
    public MoteurCommand() {
         requires(Robot.moteur);
         
    }

    protected void initialize() {
    	System.out.println("MoteurCommand : Init");
    	//Robot.m_oi.stickleft.getRawButton();
    	
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