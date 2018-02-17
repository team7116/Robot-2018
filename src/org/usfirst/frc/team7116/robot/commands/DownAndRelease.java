package org.usfirst.frc.team7116.robot.commands;

import org.usfirst.frc.team7116.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DownAndRelease extends CommandGroup {

    public DownAndRelease() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	Robot.setMessage("DownAndRelease : Begin");
    	addSequential(new PinceBaisser());
    	
    	
    	addSequential(new PinceOuvrir(0.75));
    	addSequential(new PinceLever(0.25));

    	Robot.setMessage("DownAndRelease : End");
    }
}
