/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST.ll Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3245.robot;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
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
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {

	}


	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {

	}
	

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
	  leftMotorSpeed = -drivercontroller.getY();
    rightMotorSpeed = -drivercontroller.getAxis(AxisType.kThrottle);
    tDrive.tankDrive(leftMotorSpeed * 1.0, rightMotorSpeed * 1.0);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
  
	}
	
}
