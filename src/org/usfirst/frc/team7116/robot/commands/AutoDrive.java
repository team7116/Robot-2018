package org.usfirst.frc.team7116.robot.commands;

import org.usfirst.frc.team7116.robot.Robot;
import org.usfirst.frc.team7116.robot.RobotMap;
import org.usfirst.frc.team7116.robot.subsystems.DriveTrain;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Classe permettant de lancer des instructions de deplacements simples et automatiques au
 * robot
 */
public class AutoDrive extends Command {

	private DriveTrain dt;
	
	private double valeur_de_un = 1; //Math
	
	private double leftGoal;
	private double rightGoal;
	private double tolerance = 10;//1.5;
	private double vitesseDroite = 0.0;
	private double vitesseGauche = 0.0;
	private double directionDroite = 1.0;
	private double directionGauche = 1.0;
	private double vitesseMaximumDroite = 0.1;
	private double vitesseMaximumGauche = 0.1;
	private double vitesseMinimum = 0.2;
	
	private int hasStarted = 0;
	
	
	private double leftRatio = -1; //Keep wheel on a 1/1 speed ratio and invert wheel (mirror)
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
    	
    	leftGoal = position * valeur_de_un * rightRatio;
    	rightGoal = position * valeur_de_un * leftRatio;
    	
    	
    	directionDroite = position < 0 ? -1.0 : 1.0;
    	directionGauche = position < 0 ? -1.0 : 1.0;
    	
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
    	
    	leftGoal = left * valeur_de_un * leftRatio;
    	rightGoal = right * valeur_de_un * rightRatio;
    	
    	directionDroite = right < 0 ? -1.0 : 1.0;
    	directionGauche = left < 0 ? -1.0 : 1.0;
    	
    	// Pour faire un mouvement circulaire, la roue exterieur doit faire plus de tour que celle interieur
    	
    /*	if(Math.abs(leftGoal) != Math.abs(rightGoal)){
    		if(leftGoal < rightGoal){
    			vitesseMaximumDroite = .6;
    		}else{
    			vitesseMaximumGauche = .6;
    		}
    	}*/
    }

    private void initProperties() {
    	requires (Robot.driveTrain);
    	dt = Robot.driveTrain;
    	
    }
    
	// Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("***** START AUTODRIVE *****");
    	System.out.println("OBJ G: " + leftGoal + " OBJ D: " + rightGoal);
    	

/*    	dt.roueDroite.setVoltageRampRate(rampRate);
    	dt.roueDroite.enableBrakeMode(true);
    	dt.roueGauche.setVoltageRampRate(rampRate);*/
    	//dt.wheelLeft.setInverted(true);

    	//    	dt.roueDroite.setPID(1.5, .05, .1);
//    	dt.roueDroite.changeControlMode(TalonControlMode.Speed);
//    	dt.roueDroite.changeControlMode(TalonControlMode.PercentVbus);
    	
//    	dt.roueGauche.changeControlMode(TalonControlMode.Speed);
//    	dt.roueGauche.setPID(1.3, .015, 0);
//    	dt.reset();
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
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	// Calcul de l'erreur entre l'objectif et la position actuelle
    	erreurDroite = rightGoal -  dt.getRightWheelPosition();
    	erreurGauche = leftGoal -  dt.getLeftWheelPosition();
    	
    	double erreurDroiteAbs = Math.abs(erreurDroite);
    	double erreurGaucheAbs = Math.abs(erreurGauche);
    	
//    	if (hasStarted > 10) {
//	    	if (erreurDroite > erreurDroitePrec) {
//	    		directionDroite = -directionDroite;
//	    	}
//	    	
//	    	if (erreurGauche > erreurGauchePrec) {
//	    		directionGauche = -directionGauche;
//	    	}
//	    	
//    	}
    	
    	// Si on a atteind lobjectif on stop!
    	if(erreurDroiteAbs <= tolerance)
    	{	
    		vitesseDroite = 0;
    		//dt.roueDroite.enableBrakeMode(true);
    	}else{
	    	// Si on s'approche de l'objectif on ralentit
	    	if (erreurDroite < 0 ) {
	    		vitesseDroite = vitesseDroite - (0.1 * directionDroite);
	    		
	    		if (Math.abs(vitesseDroite) <= vitesseMinimum) {
	    			vitesseDroite = vitesseMinimum * directionDroite;
	    		}
	    		
	    	} else {
	    		vitesseDroite = vitesseDroite + (0.005 * directionDroite);
	    		
	    		if (Math.abs(vitesseDroite) >= vitesseMaximumDroite) {
	    			vitesseDroite = vitesseMaximumDroite * directionDroite;
	    		}
	
	    	}
    	}
    	

    	if(erreurGaucheAbs <= tolerance)
    	{
    		vitesseGauche = 0;
    		//dt.roueGauche.enableBrakeMode(true);
    	}else{
	    	if (erreurGauche < 0 ) {
	    		vitesseGauche = vitesseGauche - (0.1 * directionGauche);
	    		
	    		if (Math.abs(vitesseGauche) <= vitesseMinimum) {
	    			vitesseGauche = vitesseMinimum * directionGauche;
	    		}
	    		
	    	} else {
	    		vitesseGauche = vitesseGauche + (0.005 * directionGauche);
	    		
	    		if (Math.abs(vitesseGauche) >= vitesseMaximumGauche) {
	    			vitesseGauche = vitesseMaximumGauche * directionGauche;
	    		}
	
	    	}
    	}
    	
    	dt.wheelRight.set(vitesseDroite);
    	dt.wheelLeft.set(-vitesseGauche);
    	
    	
    	erreurDroitePrec = erreurDroite;
    	erreurGauchePrec = erreurGauche;
    	
    	hasStarted++;
    	/*
    	System.out.print("Roue G pos : " + dt.roueGauche.getPosition());
		System.out.println("\tRoue D pos : " + dt.roueDroite.getPosition());
		
		System.out.print("Erreur G : " + erreurGauche);
		System.out.println("\tErreur D : " + erreurDroite);
    	*/
    }
    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean result = erreurDroite <= 0 && erreurGauche <= 0;
    	
    	if (result) {
    		System.out.println("End autodrive");
    		//debug();
    		
    	}
    	
    	//System.out.println("Erreur : " + currentError);
        //return dt.roueDroite.getPosition() > rightGoal;
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
    		
    		System.out.print("Vit G : " + vitesseGauche);
    		System.out.println("\tVit D : " + vitesseDroite);
    		
    		System.out.print("Roue G pos : " + dt.wheelLeft.getSensorCollection().getQuadraturePosition());
    		System.out.println("\tRoue D pos : " + dt.wheelRight.getSensorCollection().getQuadraturePosition());
    		
    		System.out.print("Erreur G : " + erreurGauche);
    		System.out.println("\tErreur D : " + erreurDroite);
    		
//    		System.out.print("Roue G spd : " + dt.roueGauche.getSpeed());
//    		System.out.println("Roue D spd : " + dt.roueDroite.getSpeed());
    	}
    	
    	
    }
}
