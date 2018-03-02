package org.usfirst.frc.team7116.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousCommands extends CommandGroup {

    public AutonomousCommands() {
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
    	
    	//1 Complete wheel rotation: 2048 ticks
    	
    	//addSequential(new AutoDrive(-40960, 40960));
    	addSequential(new AutoDrive(10.0, 10.0, 1));
    	//addSequential(new AutoDrive(.87, .87, 1));
    }
    
    public AutonomousCommands(int pathNumber) {
    	/**
    	 * 0 : Straight
    	 * 1 : Right switch
    	 * 2 : Left switch
    	 */
    	switch (pathNumber) {
    	case 0:
    		addSequential(new AutoDrive(8.0, 8.0, 1));
    		break;
    	case 1:
    		System.out.println("Seq Right Right");
    		addSequential(new AutoDrive(9.0, 9.0, 1));
    		System.out.println("Seq 1 done");
    		addSequential(new AutoDrive(-1.0, 1.0, 1));
    		System.out.println("Seq 2 done");
    		addSequential(new AutoDrive(2.0, 2.0, 1, 0.8));
    		System.out.println("Seq 3 done");
    		addSequential(new PinceBaisser(0.75));
    		System.out.println("Seq 4 done");
    		addSequential(new PinceOuvrir());
    		System.out.println("Seq 5 done");
    		break;
    	case 2:
    		break;
    	}
    }
}
