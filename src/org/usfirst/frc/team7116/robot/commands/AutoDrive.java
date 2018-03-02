package org.usfirst.frc.team7116.robot.commands;

import org.usfirst.frc.team7116.robot.Robot;
import org.usfirst.frc.team7116.robot.RobotMap;
import org.usfirst.frc.team7116.robot.subsystems.DriveTrain;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.PIDController.Tolerance;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Classe permettant de lancer des instructions de deplacements simples et automatiques au
 * robot
 */
public class AutoDrive extends Command {

	private DriveTrain dt;
	
	private double valeur_de_un = 1; //Math
	private final double TWO_ROTATIONS = 8192 * 2;
	
	
	private double leftGoal;
	private double rightGoal;
	private double tolerance = 400;//1.5;
	private double rightSpeed = 0.0;
	private double leftSpeed = 0.0;
	private double rightDirection = 1.0;
	private double leftDirection = 1.0;
	private double rightMaxSpeed = 0.4;
	private double leftMaxSpeed = 0.4;
	
	private double maximumSpeed = 0.6;
	private double minimumSpeed = 0.25;
	
	private int hasStarted = 0;
	
	private double hiThresh = TWO_ROTATIONS * 4;
	private double loThresh = TWO_ROTATIONS;
	private double speedRange = maximumSpeed - minimumSpeed;
	
	
	private double leftRatio = 1; //Keep wheel on a 1/1 speed ratio and invert wheel (mirror)
	private double rightRatio = 1; //Keep wheel on a 1/1 speed ratio
	
	
	/**
	 * Taux d'acceleration maximum en voltage par seconde
	 */
	private double rampRate = 6.0;
	
    public AutoDrive() {
    	super();
        // Use requires() here to declare subsystem dependencies
    	initProperties();
    	
    }

    /**
     * Constructeur permettant de faire avancer le robot egalement
     * @param position
     */
    public AutoDrive(double position) {
    	super();
    	initProperties();
    	
    	leftGoal = position * valeur_de_un;
    	rightGoal = position * valeur_de_un;
    	
    	
    	rightDirection = position < 0 ? -1.0 : 1.0;
    	leftDirection = position < 0 ? -1.0 : 1.0;
    	
    }
    
    /**
     * Constructeur permettant de faire pivoter le robot en indiquant des positions
     * a chaque roue du robot
     * @param left Position de la roue gauche
     * @param right Position de la roue droite
     */
    public AutoDrive(double left, double right) {
    	super();
    	initProperties();
    	
    	leftGoal = left;
    	rightGoal = right;
    	
    	rightDirection = right < 0 ? -1.0 : 1.0;
    	leftDirection = left < 0 ? -1.0 : 1.0;
    	
    	dt.resetEncoders();
    }
    
    
    public AutoDrive(double left, double right, int countMode) {
    	super();
    	initProperties();
    	
    	leftGoal = left;
    	rightGoal = right;
    	
    	if (countMode == 1) {
    		leftGoal = left * dt.TICK_PER_ROTATION;
    		rightGoal = right * dt.TICK_PER_ROTATION;
    	}
    	
    	rightDirection = right < 0 ? -1.0 : 1.0;
    	leftDirection = left < 0 ? -1.0 : 1.0;
    	
    	dt.resetEncoders();
    }
    
    public AutoDrive(double left, double right, int countMode, double minSpeed) {
    	super();
    	initProperties();
    	
    	leftGoal = left;
    	rightGoal = right;
    	
    	if (countMode == 1) {
    		leftGoal = left * dt.TICK_PER_ROTATION;
    		rightGoal = right * dt.TICK_PER_ROTATION;
    	}
    	
    	rightDirection = right < 0 ? -1.0 : 1.0;
    	leftDirection = left < 0 ? -1.0 : 1.0;
    	
    	minimumSpeed = minSpeed;
    	
    	dt.resetEncoders();
    }

    private void initProperties() {
    	requires (Robot.driveTrain);
    	dt = Robot.driveTrain;
    	//this.setTimeout(5.0);
    	System.out.println("Initialization Autodrives");
    	
    }
    
	// Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("***** START AUTODRIVE *****");
    	System.out.println("OBJ G: " + leftGoal + " OBJ D: " + rightGoal);
    	
    	reset();
    }

    private void reset(){
    	erreurDroite = 0;
    	erreurGauche = 0;
    	    
    	erreurDroitePrec = 0;
    	erreurGauchePrec = 0;
    	dt.wheelLeft.getSensorCollection().setQuadraturePosition(0, RobotMap.kTimeoutMs);
    	dt.wheelRight.getSensorCollection().setQuadraturePosition(0, RobotMap.kTimeoutMs);;
    }
    
    double erreurDroite = 0;
    double erreurGauche = 0;
    
    double erreurDroitePrec = 0;
    double erreurGauchePrec = 0;
    
    double kp = 1.0/8192.0;
    double erreurDroiteAbs;
    double erreurGaucheAbs;
    
    double rightPos;
    double leftPos;
    
    boolean firstPass = true;
    double initialDistance;
    
    boolean rightDone = false;
    boolean leftDone = false;
    
    int rightDirCurrent;
    int leftDirCurrent;
    
    
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	rightPos = dt.getRightWheelPosition();
    	leftPos = dt.getLeftWheelPosition();
    	
    	// Calcul de l'erreur entre l'objectif et la position actuelle
    	erreurDroite = rightGoal -  rightPos;
    	erreurGauche = leftGoal -  leftPos;
    	
    	rightDirCurrent = erreurDroite >= 0 ? 1 : -1;
    	leftDirCurrent = erreurGauche >= 0 ? 1 : -1;
    	
    	erreurDroiteAbs = Math.abs(erreurDroite);
    	erreurGaucheAbs = Math.abs(erreurGauche);
    	
    	
    	// Si on a atteind lobjectif on stop!
    	if(erreurDroiteAbs <= tolerance && !rightDone)
    	{	
    		rightSpeed = 0;
    		rightDone = true;
    	}else{
    		rightSpeed = minimumSpeed * rightDirCurrent;
    	}
    	
    	if(erreurGaucheAbs <= tolerance && !leftDone)
    	{
    		leftSpeed = 0;
    		leftDone = true;
    	}else{
			leftSpeed = minimumSpeed * leftDirCurrent;	
   		
    	}
    	
    	dt.setRightSpeed(rightSpeed);
    	dt.setLeftSpeed(leftSpeed);						
    	
    	erreurDroitePrec = erreurDroite;																			
    	erreurGauchePrec = erreurGauche;
    	
    	hasStarted++;

    	if (hasStarted % 10 == 0) {
	    	SmartDashboard.putNumber("Right speed", rightSpeed);
	    	SmartDashboard.putNumber("Right error", erreurDroite);
	    	SmartDashboard.putNumber("Right error abs", erreurDroiteAbs);
	    	SmartDashboard.putNumber("Right Goal", rightGoal);
	    	SmartDashboard.putBoolean("Right isDone", rightDone);
			SmartDashboard.putNumber("Right pos", rightPos);
	    	
	    	SmartDashboard.putNumber("Left speed", leftSpeed);
	    	SmartDashboard.putNumber("Left error", erreurGauche);
	    	SmartDashboard.putNumber("Left goal", leftGoal);
	    	SmartDashboard.putBoolean("Left isDone", leftDone);
	    	SmartDashboard.putNumber("Left pos", leftPos);
    	}
    }
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//boolean result = erreurDroite <= 0 && erreurGauche <= 0;
    	boolean result = rightDone && leftDone;
    	//boolean result = erreurDroite <= tolerance && erreurGauche <= tolerance;
    	
    	if (result) {
    		SmartDashboard.putString("Message", "Autodrive end");

    	}

    	return result;
    }

    // Called once after isFinished returns true
    protected void end() {
    	reset();
    	try {
 			Thread.sleep(200);
 		} catch (InterruptedException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}

    	System.out.println("***** END AUTODRIVE *****");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("FOOOOOOOOOO*******************************");
    }
    
    /**
     * Permet au robot de pivoter a l'angle indique
     * @param angle Angle en radian. Si angle < 0 anti-horaire
     */
    public void rotate (double angle) {
    	double distanceParcourir = dt.getWheelToCentreDistance() * angle;
    	
    	//dt.roueDroite.changeControlMode(TalonControlMode.Position);
    	//dt.roueGauche.changeControlMode(TalonControlMode.Position);
    	
    }
    
    int loopCounter = 0;
    int loopLimit = 10;
    
    public void debug() {
    	if (loopCounter++ > loopLimit) {
    		loopCounter = 0;
    		
    		System.out.print("Vit G : " + leftSpeed);
    		System.out.println("\tVit D : " + rightSpeed);
    		
    		System.out.print("Roue G pos : " + dt.wheelLeft.getSensorCollection().getQuadraturePosition());
    		System.out.println("\tRoue D pos : " + dt.wheelRight.getSensorCollection().getQuadraturePosition());
    		
    		System.out.print("Erreur G : " + erreurGauche);
    		System.out.println("\tErreur D : " + erreurDroite);
    		
//    		System.out.print("Roue G spd : " + dt.roueGauche.getSpeed());
//    		System.out.println("Roue D spd : " + dt.roueDroite.getSpeed());
    	}
    	
    	
    }
}
