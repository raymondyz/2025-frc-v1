// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;





public class Robot extends TimedRobot {

  // ========== Auto Chooser ==========
  private static final String kCenterAuto = "Center Auto";
  private static final String kRightAuto = "Right Auto";
  private static final String kLeftAuto = "Left Auto";

  private String selectedAuto;
  private final SendableChooser<String> autoChooser = new SendableChooser<>();
  
  // ========== Motors ==========
  private final PWMVictorSPX leftDrive = new PWMVictorSPX(0);
  private final PWMVictorSPX rightDrive = new PWMVictorSPX(1);
  private final DifferentialDrive robotDrive = new DifferentialDrive(leftDrive, rightDrive);

  private final PWMVictorSPX shooter = new PWMVictorSPX(2);
  private final PWMSparkMax hangWinch = new PWMSparkMax(6);
  private final PWMVictorSPX hangBelt = new PWMVictorSPX(5);

  // ========== Others ==========
  private final Joystick joystick = new Joystick(0);
  private final Timer timer = new Timer();



  public Robot() {
    // ========== Auto Chooser ========== //
    autoChooser.setDefaultOption("Center Auto", kCenterAuto);
    autoChooser.addOption("Left Auto", kLeftAuto);
    autoChooser.addOption("Right Auto", kRightAuto);

    SmartDashboard.putData("Auto choices", autoChooser);

    // ========== Drive Init ========== //
    rightDrive.setInverted(true);
  }


  @Override
  public void teleopInit() {
  }

  // ================================================== Telop Control ================================================== //


  @Override
  public void teleopPeriodic() {

    // ========== Drive Code ========== //

    if (joystick.getRawButton(1)) {
      robotDrive.arcadeDrive(-joystick.getY(), -0.85*joystick.getTwist());
    }
    else {
      robotDrive.arcadeDrive(-0.7*joystick.getY(), -0.7*0.85*joystick.getTwist());
    }


    // ========== Shooter Code ========== //
  
    if (joystick.getRawButton(3)) {
      shooter.set(0.55);
    }
    else if (joystick.getRawButton(5)) {
      shooter.set(-0.1);
    }
    else {
      shooter.set(0.1);
    }
    
    // ========== Hang Code ========== //

    // Deploy arm
    if (joystick.getRawButton(6)) {
      hangBelt.set(0.3);
      hangWinch.set(0);
    }
    // Pull arm back
    else if (joystick.getRawButton(4)) {
      // Check if full engage
      if (joystick.getRawButton(1)) {
        hangBelt.set(-0.3);
        hangWinch.set(-0.6);
      }
      else {
        hangBelt.set(-0.2);
        hangWinch.set(0);
      }
    }
    else {
      hangBelt.set(0);
      hangWinch.set(0);
    }
  }



  // ================================================== Auto Control ================================================== //


  @Override
  public void autonomousInit() {
    selectedAuto = autoChooser.getSelected();
    selectedAuto = SmartDashboard.getString("Auto Selector", kCenterAuto);
    System.out.println("Auto selected: " + selectedAuto);

    timer.reset();
  }

  @Override
  public void autonomousPeriodic() {
    switch (selectedAuto) {

      case kCenterAuto:
        centerAuto();
        break;

      case kRightAuto:
        rightAuto();
        break;

      case kLeftAuto:
        leftAuto();
        break;

      default:
        break;
    }
  }

  public void auto_drive(double startTime, double endTime, double speed, double rotation) {
    if (startTime <= timer.get() && timer.get() < endTime) {
      robotDrive.arcadeDrive(speed, rotation, false);
    }
  }

  public void centerAuto() {
    double speed = 0.25;
    double initialPauseTime = 3;
    double driveForwardTime = 2;

    // Drive forward
    auto_drive(initialPauseTime, initialPauseTime + driveForwardTime, speed, 0);

    robotDrive.stopMotor();
  }

  public void rightAuto() {
    return;
  }
  public void leftAuto() {
    return;
  }
}