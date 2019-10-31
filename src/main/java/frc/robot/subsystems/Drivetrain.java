/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.States.DriveMode;
import frc.robot.States.Side;
import frc.robot.commands.Drive;

public class Drivetrain extends Subsystem {

  private static WPI_TalonSRX leftTalon, rightTalon;
  private static WPI_VictorSPX leftVictor1, leftVictor2, rightVictor1, rightVictor2;
  private static ADXRS450_Gyro gyro;
  private static SpeedControllerGroup mGroupLeft, mGroupRight;
  private static DifferentialDrive mDrive;
  private double m_thrust, m_rotation;

  // CONFIGURATION FUNCTIONS
  public void configureDrive(){
    leftTalon = new WPI_TalonSRX(RobotMap.drive_leftMasterTalon1);
    rightTalon = new WPI_TalonSRX(RobotMap.drive_rightMasterTalon4);
    
    configureMasterTalon(leftTalon, true);
    configureMasterTalon(rightTalon, false);
    
    leftVictor1 = new WPI_VictorSPX(RobotMap.drive_leftSlaveVictor2);
    leftVictor2 = new WPI_VictorSPX(RobotMap.drive_leftSlaveVictor3);
    rightVictor1 = new WPI_VictorSPX(RobotMap.drive_rightSlaveVictor5);
    rightVictor2 = new WPI_VictorSPX(RobotMap.drive_rightSlaveVictor6);

    mGroupLeft = new SpeedControllerGroup(leftTalon, leftVictor1, leftVictor2);
    mGroupRight = new SpeedControllerGroup(rightTalon, rightVictor1, rightVictor2);

    mDrive = new DifferentialDrive(mGroupLeft, mGroupRight);

    leftVictor1.configVoltageCompSaturation(9, Constants.kTimeoutMs);
    leftVictor2.configVoltageCompSaturation(9, Constants.kTimeoutMs);
    rightVictor1.configVoltageCompSaturation(9, Constants.kTimeoutMs);
    rightVictor2.configVoltageCompSaturation(9, Constants.kTimeoutMs);

    gyro = new ADXRS450_Gyro();
    gyro.reset();

    System.out.println("FRC Gyro Connectivity: " + gyro.isConnected());
    System.out.println("FRC Gyro Reading " + gyro.getAngle());
  }

  private void configureMasterTalon(TalonSRX talonID, boolean left){
    talonID.configFactoryDefault();
    talonID.setSensorPhase(true);
    talonID.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,Constants.kPIDLoopIdx,Constants.kTimeoutMs);
    talonID.configNominalOutputForward(0, Constants.kTimeoutMs);
    talonID.configNominalOutputReverse(0, Constants.kTimeoutMs);
    talonID.configPeakOutputForward(1, Constants.kTimeoutMs);
    talonID.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    talonID.configVoltageCompSaturation(9.0, Constants.kTimeoutMs);
    talonID.setSelectedSensorPosition(0);
  }

  private void driveVoltageCompensation(boolean value){
    leftTalon.enableVoltageCompensation(value);
    rightTalon.enableVoltageCompensation(value);
    leftVictor1.enableVoltageCompensation(value);
    leftVictor2.enableVoltageCompensation(value);
    rightVictor1.enableVoltageCompensation(value);
    rightVictor2.enableVoltageCompensation(value);
  }
  //END OF CONFIGURATION FUNCTIONS

  //DRIVE-MODE State Setting
  public void drive(DriveMode driveMode, double thrust, double rotation){
    switch(driveMode){
      case NORMAL: 
      driveVoltageCompensation(true);
      arcadeDrive(thrust, rotation);
      break;

      case TURBO:
      driveVoltageCompensation(false);
      curvatureDrive(thrust, rotation);
      break;

      case SYSTEM:
      driveVoltageCompensation(false);
      setDrive(thrust, rotation);
      break;
    }
  }
  
  //Drive Functions
  private void arcadeDrive(double thrust, double rotation){
    m_thrust = thrust; m_rotation = rotation;
    mDrive.arcadeDrive(m_thrust, m_rotation, true);
  }

  private void curvatureDrive(double thrust, double rotation){
    boolean quickTurn = false;
    m_thrust = thrust; m_rotation = rotation;
    if(Math.abs(thrust)<0.1 && Math.abs(rotation)>0.1){
      quickTurn = true;
    } else {
      quickTurn = false;
    }
    mDrive.curvatureDrive(m_thrust, m_rotation, quickTurn);
  }

  private void setDrive(double thrust, double rotation){
    m_thrust = thrust; m_rotation = rotation;
    mDrive.arcadeDrive(m_thrust, m_rotation);
  }

  //Telemetry Function
  public int getEncoderValue(Side encoderSide){
    if(encoderSide == Side.LEFT){
      return leftTalon.getSelectedSensorPosition();
    } else if(encoderSide == Side.RIGHT){
        return rightTalon.getSelectedSensorPosition();
    } else {
        return 0;
    }
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Drive());
  }
}

