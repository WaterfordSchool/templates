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
import edu.wpi.first.wpilibj.Talon;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
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
	private Joystick m_driver;
	private WPI_TalonSRX leftMotor;
	//Talon leftMotor;
	private WPI_TalonSRX rightMotor;
	//Talon rightMotor;

	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private static final String kDriveStraight = "Drive Straight";
	
	public double leftSpeed = 0;
	public double rightSpeed;
	
	public int leftEncoderPos = 0;
	public int rightEncoderPos = 0;
	public int aveEncoderPos = 0;
	
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		//Left Drive Motor Initialization
		leftMotor = new WPI_TalonSRX(1);
		//leftMotor = new Talon(0);
		leftMotor.setInverted(false);
		leftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
		leftMotor.setSensorPhase(true);
		//leftMotor.setSafetyEnabled(false);
		System.out.println(leftMotor.isSafetyEnabled());
		
		//Right Drive Motor Initialization
		rightMotor = new WPI_TalonSRX(2);
		//rightMotor = new Talon(3);
		rightMotor.setInverted(true);
		rightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
		rightMotor.setSensorPhase(true);
		//rightMotor.setSafetyEnabled(false);
		System.out.println(rightMotor.isSafetyEnabled());

		//Drive Train Initialization
		m_myRobot = new DifferentialDrive(leftMotor, rightMotor);
		m_myRobot.setExpiration(0.1);
		
		//Joystick Initialization
		m_driver = new Joystick(0);
		
		//Smart Dashboard Initialization
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		m_chooser.addObject("Drive Straight", kDriveStraight);
		SmartDashboard.putData("Auto modes", m_chooser);


	}

	@Override
	public void autonomousInit() {
		encoderReset();
		aveEncoderPos = 0;
		
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
			case kDriveStraight:
				// Drive forward while average encoder position is less than 10000
				encoderReset();
				aveEncoderPos = 0;
				m_myRobot.tankDrive(0.0, 0.0);

				while(aveEncoderPos <= 1000) {
					m_myRobot.tankDrive(0.5, -0.5);
					aveEncoderPos = (-leftMotor.getSelectedSensorPosition() + -rightMotor.getSelectedSensorPosition())/2;
					SmartDashboard.putNumber("Left Motor Speed", leftMotor.get());
					SmartDashboard.putNumber("Right Motor Speed", rightMotor.get());
					SmartDashboard.putNumber("Average Encoder", aveEncoderPos);
					SmartDashboard.putNumber("Left Encoder", -leftMotor.getSelectedSensorPosition());
					SmartDashboard.putNumber("Right Encoder", -rightMotor.getSelectedSensorPosition());
				}
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

	public void autonomousPeriodic() {
		
	}
	
	@Override
	public void teleopInit() {
		encoderReset();
	}
	
	@Override
	public void teleopPeriodic() {
		leftSpeed = -m_driver.getY();
		rightSpeed = m_driver.getRawAxis(3);
		
		leftEncoderPos = -leftMotor.getSelectedSensorPosition();
		rightEncoderPos = -rightMotor.getSelectedSensorPosition();
		
		m_myRobot.tankDrive(leftSpeed, rightSpeed);
		
		SmartDashboard.putNumber("Left Motor Speed", leftMotor.get());
		SmartDashboard.putNumber("Right Motor Speed", rightMotor.get());
		
		SmartDashboard.putNumber("Left Encoder", leftEncoderPos);
		SmartDashboard.putNumber("Right Encoder", rightEncoderPos);
	}
	
	public void encoderReset() {
		leftMotor.setSelectedSensorPosition(0);
		rightMotor.setSelectedSensorPosition(0);
	}
}
