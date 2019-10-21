/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
  public static XboxController xbox1 = new XboxController(0);
  public static Joystick xbox1_stick = new Joystick(0);

  public static XboxController xbox2 = new XboxController(1);
  public static Joystick xbox2_stick = new Joystick(1);

  Button xb1_A = new JoystickButton(xbox1_stick, 1);
  Button xb1_B = new JoystickButton(xbox1_stick, 2);
  Button xb1_X = new JoystickButton(xbox1_stick, 3);
  Button xb1_Y = new JoystickButton(xbox1_stick, 4);
  Button xb1_LB = new JoystickButton(xbox1_stick, 5);
  Button xb1_RB = new JoystickButton(xbox1_stick, 6);
  Button xb1_Select = new JoystickButton(xbox1_stick, 7);
  Button xb1_Start = new JoystickButton(xbox1_stick, 8);
  Button xb1_Lstick = new JoystickButton(xbox1_stick, 9);
  Button xb1_Rstick = new JoystickButton(xbox1_stick, 10);

  Button xb2_A = new JoystickButton(xbox2_stick, 1);
  Button xb2_B = new JoystickButton(xbox2_stick, 2);
  Button xb2_X = new JoystickButton(xbox2_stick, 3);
  Button xb2_Y = new JoystickButton(xbox2_stick, 4);
  Button xb2_LB = new JoystickButton(xbox2_stick, 5);
  Button xb2_RB = new JoystickButton(xbox2_stick, 6);
  Button xb2_Select = new JoystickButton(xbox2_stick, 7);
  Button xb2_Start = new JoystickButton(xbox2_stick, 8);
  Button xb2_Lstick = new JoystickButton(xbox2_stick, 9);
  Button xb2_Rstick = new JoystickButton(xbox2_stick, 10);
 
  public OI(){


}
}
