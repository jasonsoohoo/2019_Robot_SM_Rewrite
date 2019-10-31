/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.States.ShooterMode;

public class Shooter extends Subsystem {
  private TalonSRX m_ArmTalon;

  public void configureShooter(){
    System.out.println("[SHOOTER] Initialized.");
    
    m_ArmTalon= new TalonSRX(RobotMap.shooter_Talon_Arm);
    m_ArmTalon.configFactoryDefault();
    m_ArmTalon.set(ControlMode.PercentOutput, 0);
    m_ArmTalon.setInverted(true); //set this later
    m_ArmTalon.configVoltageCompSaturation(12.0, Constants.kTimeoutMs);
    m_ArmTalon.enableVoltageCompensation(true);
  
    m_ArmTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    m_ArmTalon.setSensorPhase(true); // set this later
    m_ArmTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
    m_ArmTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
    m_ArmTalon.configNominalOutputForward(0, Constants.kTimeoutMs);
    m_ArmTalon.configNominalOutputReverse(0, Constants.kTimeoutMs);
    m_ArmTalon.configPeakOutputForward(1, Constants.kTimeoutMs);
    m_ArmTalon.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    m_ArmTalon.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
    m_ArmTalon.config_kF(Constants.kSlotIdx, Constants.shooterArmPID.kF, Constants.kTimeoutMs);
    m_ArmTalon.config_kP(Constants.kSlotIdx, Constants.shooterArmPID.kP, Constants.kTimeoutMs);
    m_ArmTalon.config_kI(Constants.kSlotIdx, Constants.shooterArmPID.kI, Constants.kTimeoutMs);
    m_ArmTalon.config_kD(Constants.kSlotIdx, Constants.shooterArmPID.kD, Constants.kTimeoutMs);
    m_ArmTalon.configMotionCruiseVelocity(Constants.shooterCruiseVelocity, Constants.kTimeoutMs); 
    m_ArmTalon.configMotionAcceleration(Constants.shooterMaxAccel, Constants.kTimeoutMs); 
    m_ArmTalon.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    m_ArmTalon.setNeutralMode(NeutralMode.Brake);

    m_ArmTalon.configPeakCurrentLimit(15, 0);
    m_ArmTalon.configPeakCurrentDuration(100, 0);
  }

  public boolean armControl(ShooterMode value, double climbJoystick){
    switch(value){
      case STOW:
        m_ArmTalon.enableCurrentLimit(false);
        m_ArmTalon.set(ControlMode.MotionMagic, Constants.shooter_StowPosition);
      return m_ArmTalon.getSelectedSensorPosition()<Constants.overwatch_shooter_isStowed;

      case FORWARD_SHOT:
        m_ArmTalon.set(ControlMode.MotionMagic, Constants.shooter_ForwardShot);
      return false;
      

      case REAR_SHOT:
        m_ArmTalon.set(ControlMode.MotionMagic, Constants.shooter_RearShot);
      return false;

      case CLIMB:
        climbMode(climbJoystick);
      return false;

      default:
        m_ArmTalon.set(ControlMode.MotionMagic, m_ArmTalon.getSelectedSensorPosition());
      return false;
    }
  }

  private void climbMode(double climbInput){
    m_ArmTalon.set(ControlMode.PercentOutput, climbInput);
    
    int currentLimit;
    if(m_ArmTalon.getSelectedSensorPosition()<100){
      currentLimit = 15 - (5*(m_ArmTalon.getSelectedSensorPosition()/100));
      m_ArmTalon.configContinuousCurrentLimit(currentLimit);
      m_ArmTalon.enableCurrentLimit(true);
    } else {
      m_ArmTalon.enableCurrentLimit(false);
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
