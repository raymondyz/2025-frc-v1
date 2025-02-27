// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  // private static final String kDefaultAuto = "Default";
  // private static final String kCustomAuto = "My Auto";
  // private String m_autoSelected;
  // private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  // ========== Motors ==========
  private final PWMVictorSPX m_leftDrive = new PWMVictorSPX(0);
  private final PWMVictorSPX m_rightDrive = new PWMVictorSPX(1);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftDrive, m_rightDrive);

  private final PWMVictorSPX m_shooter = new PWMVictorSPX(2);
  private final PWMSparkMax m_hangWinch = new PWMSparkMax(6);
  private final PWMVictorSPX m_hangBelt = new PWMVictorSPX(5);

  private final Joystick joystick = new Joystick(0);


  private final Timer timer = new Timer();


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    // m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    // m_chooser.addOption("My Auto", kCustomAuto);
    // SmartDashboard.putData("Auto choices", m_chooser);

    m_rightDrive.setInverted(true);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    // m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    // System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // switch (m_autoSelected) {
    //   case kCustomAuto:
    //     // Put custom auto code here
    //     break;
    //   case kDefaultAuto:
    //   default:
    //     // Put default auto code here
    //     break;
    // }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    // ========== Drive Code ==========

    if (joystick.getRawButton(1)) {
      m_robotDrive.arcadeDrive(-joystick.getY(), -0.85*joystick.getTwist());
    }
    else {
      m_robotDrive.arcadeDrive(-0.7*joystick.getY(), -0.7*0.85*joystick.getTwist());
    }


    // ========== Shooter Code ==========
  
    if (joystick.getRawButton(3)) {
      m_shooter.set(0.5);
    }
    else if (joystick.getRawButton(5)) {
      m_shooter.set(0);
    }
    else {
      m_shooter.set(0.1);
    }
    
    // ========== Hang Code ========== //

    // Deploy arm
    if (joystick.getRawButton(6)) {
      m_hangBelt.set(0.3);
      m_hangWinch.set(0);
    }
    // Pull arm back
    else if (joystick.getRawButton(4)) {
      // Check if full engage
      if (joystick.getRawButton(1)) {
        m_hangBelt.set(-0.4);
        m_hangWinch.set(-0.9);
      }
      else {
        m_hangBelt.set(-0.4*0.6);
        m_hangWinch.set(0);
      }
    }
    else {
      m_hangBelt.set(0);
      m_hangWinch.set(0);
    }
  }



  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
