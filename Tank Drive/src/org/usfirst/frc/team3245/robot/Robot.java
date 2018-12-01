/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3245.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends IterativeRobot {
	private DifferentialDrive m_myRobot;
	private Joystick m_operator;
	private WPI_TalonSRX leftMotor;
	private WPI_TalonSRX rightMotor;

	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		leftMotor  = new WPI_TalonSRX(1);
		leftMotor.setInverted(false);
		//leftMotor.setSafetyEnabled(false);
		System.out.println(leftMotor.isSafetyEnabled());
		
		rightMotor = new WPI_TalonSRX(2);
		rightMotor.setInverted(true);
		//rightMotor.setSafetyEnabled(false);
		System.out.println(rightMotor.isSafetyEnabled());
		m_myRobot = new DifferentialDrive(leftMotor, rightMotor);
		
		m_operator = new Joystick(0);
		
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto modes", m_chooser);
		
		m_myRobot.setExpiration(0.1);

	}

	public void autonoInit() {
		String autoSelected = m_chooser.getSelected();
		//String autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);

		// MotorSafety improves safety when motors are updated in loops
		// but is disabled here because motor updates are not looped in
		// this autonomous mode.
		m_myRobot.setSafetyEnabled(false);
		System.out.println(leftMotor.isSafetyEnabled());
		
		switch (autoSelected) {
			case kCustomAuto:
				// Spin at half speed for two seconds
				m_myRobot.tankDrive(0.0, 0.5);
				Timer.delay(2.0);

				// Stop robot
				m_myRobot.tankDrive(0.0, 0.0);
				break;
			case kDefaultAuto:
			default:
				// Drive forwards for two seconds
				m_myRobot.tankDrive(-0.5, 0.0);
				Timer.delay(2.0);

				// Stop robot
				m_myRobot.tankDrive(0.0, 0.0);
				break;
		}
		
	}

	@Override
	public void teleopPeriodic() {
		m_myRobot.tankDrive(-m_operator.getY(), m_operator.getRawAxis(3));
	}
}
