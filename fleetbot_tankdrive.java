/*----------------------------------------------------------------------------*/
/* Simple tankdrive program to run fleetbots                                  */
/*----------------------------------------------------------------------------*/

/* Team number must match team number in Driver Station, on RoboRio, and on Radio*/
package org.usfirst.frc.team3245.robot;

/* Imported functions from wpi libraries */
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
Instantiate motor controllers, speed controller groups, differential drive systems, 
and controllers here
 */

public class Robot extends IterativeRobot {
	Talon leftDrive0 = new Talon(0);
	Talon leftDrive1 = new Talon(1);
	Talon rightDrive3 = new Talon(3);
	Talon rightDrive4 = new Talon(4);
	
	SpeedControllerGroup leftDrive = new SpeedControllerGroup (leftDrive0, leftDrive1);
	SpeedControllerGroup rightDrive = new SpeedControllerGroup (rightDrive3, rightDrive4);
	DifferentialDrive tDrive = new DifferentialDrive (leftDrive, rightDrive);
	
  	Joystick drivercontroller = new Joystick (0);
	
  
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
	}


	/**
	 * This function sets up which Autonomous Mode the robot will use.
	 * This is left blank for basic fleetbot operations as we won't be running auto.
	 */
	@Override
	public void autonomousInit() {

	}


	/**
	 * This function is called periodically during autonomous.
	 * Again, this is left blank for fleetbot operations. 
	 */
	@Override
	public void autonomousPeriodic() {

	}
	

	/**
	 * This function is called periodically during operator control.
	 * This is where we tell the fleetbot motors what percent speed to operate
	 * based on input from the driver's controller.
	 */
	@Override
	public void teleopPeriodic() {
		leftMotorSpeed = -drivercontroller.getY();
		rightMotorSpeed = -drivercontroller.getAxis(AxisType.kThrottle);
		tDrive.tankDrive(leftMotorSpeed * 1.0, rightMotorSpeed * 1.0);
	}

	/**
	 * This function is called periodically during test mode.
	 * Again, this is left blank for fleetbot operations.
	 */
	@Override
	public void testPeriodic() {
  
	}
	
}
