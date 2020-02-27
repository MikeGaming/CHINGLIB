/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Map;

public class Drive extends SubsystemBase {
  
  WPI_VictorSPX leftFront = new WPI_VictorSPX(Map.portLeftFront);
  WPI_VictorSPX leftBack = new WPI_VictorSPX(Map.portLeftBack);
  WPI_VictorSPX rightFront = new WPI_VictorSPX(Map.portRightFront);
  WPI_VictorSPX rightBack = new WPI_VictorSPX(Map.portRightBack);
  XboxController controller = new XboxController(0);
  Encoder leftEnc = new Encoder(0, 1); //Change pin#
  Encoder rightEnc = new Encoder(0, 2);
  

  public Drive() {
    rightBack.follow(rightFront);
    leftBack.follow(leftFront);

    leftBack.configFactoryDefault();
    leftFront.configFactoryDefault();
    rightBack.configFactoryDefault();
    rightFront.configFactoryDefault();

    rightBack.setInverted(true);
    leftBack.setInverted(true);

  }

  public void setLeft(double speed){
    leftFront.set(speed);
  }

  public void setRight(double speed){
    rightFront.set(speed);
  }

  public void setSpeed(double speed){
    setRight(speed);
    setLeft(speed);
  }

  public void controlLeft(){
    setLeft(controller.getY(Hand.kLeft));
  }

  public void controlRight(){
    setRight(controller.getY(Hand.kRight));
  }

  public void tankDrive(){
    controlLeft();
    controlRight();
  }

  public void setDriveEncoders(){
    leftEnc.setDistancePerPulse(Map.wheelCirc / Map.encoderCPR);
    rightEnc.setDistancePerPulse(Map.wheelCirc / Map.encoderCPR);
  }

  public void driveForward(double dist){
    setSpeed(Map.defaultSpeed);
    
    double distLeft = leftEnc.getDistance();
    double distRight = rightEnc.getDistance();
    
    if(distRight >= dist || distLeft >= dist) {
      setRight(0);
      setLeft(0);
      rightEnc.reset();
      leftEnc.reset();
    }
  }

  public void turnLeft(double angle){
    double dist = (angle / 90) * (Map.fullTurnDistance / 4);

    setRight(Map.defaultSpeed);
    setLeft(-Map.defaultSpeed);
    
    double distLeft = leftEnc.getDistance();
    double distRight = rightEnc.getDistance();
    
    if(distRight >= dist || distLeft >= dist) {
      setRight(0);
      setLeft(0);
      rightEnc.reset();
      leftEnc.reset();
    }
  }

  public void turnRight(double angle){
    double dist = (angle / 90) * (Map.fullTurnDistance / 4);

    setRight(-Map.defaultSpeed);
    setLeft(Map.defaultSpeed);
    
    double distLeft = leftEnc.getDistance();
    double distRight = rightEnc.getDistance();
    
    if(distRight >= dist || distLeft >= dist) {
      setRight(0);
      setLeft(0);
      rightEnc.reset();
      leftEnc.reset();
    }
  }
}