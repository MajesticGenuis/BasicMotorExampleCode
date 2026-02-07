// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
 
  public TalonFX intakeMotor = new TalonFX(2);
  public Intake() {
    intakeMotor.setNeutralMode(NeutralModeValue.Brake);
    setDefaultCommand(runOnce(()->{
      intakeMotor.set(0);
    }));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
