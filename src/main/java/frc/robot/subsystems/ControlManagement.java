/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.OI;
import frc.robot.States.DriveMode;

public class ControlManagement extends Subsystem {

  public void controlLoops(){

  }
  
  private int driveState = 0;
  public DriveMode driveState(){
    switch(driveState){
      case 0:
        if(OI.xbox1.getAButtonPressed()){
          driveState = 1;
        }
      return DriveMode.NORMAL;
      
      case 1:
        if(OI.xbox1.getAButtonPressed()){
          driveState = 0;
        }
      return DriveMode.TURBO;
      
      default:
      return DriveMode.NORMAL;
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
