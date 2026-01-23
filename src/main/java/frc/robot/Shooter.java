
package frc.robot;
//your imports should auto generate up here, if not try this:
//1. Make sure you have the library you need installed and listed in the 'vendordeps folder'
//2. Use quick fix to add the import
//3. Retype the class/object/variable that needs the import
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  //create a motor variable. The visability is public, the type is TalonFX and the name is shootMotor.
      //Remember the name can be anything you want. Everything else is case senstive.
  //Set the variable equal to a new instance of TalonFX
  public TalonFX shootMotor = new TalonFX(0);
  
  //This is the default function for this subsystem, it will run one time when the robot turns on.
  public Shooter() {
    //Set the default command for this subsystem to stop the motor. 
    //When no other command is scheduled, this will run.
    setDefaultCommand(runOnce(()->{
      //Call the motor variable you created above and set the speed to zero.
      shootMotor.set(0);
    }));
  }

  //Create a command function to shoot the motor.
  //To be able to use one function for any speed we will use a parameter.
  //The parameter for the shoot command is called a local variable, similar to other variables except it doesnt need a visabilty set.
  //The local variable's type is a double and its name is speed.
  public Command shoot(double speed){
    //When functions have a spesific type, they must return a value of that type.
    //This function's type is Command so it must return a command
    return run(()->{
      //call the motor variable you created above and set the speed to the local variable 'speed'
      shootMotor.set(speed);
      //whenever we use the shoot command we will give it a value and it will set the motor to that value
      //EX: shoot(0.8);
      //The above line would set the motor speed to 80%
      //To make the motor spin backwards, use a negitive sign.
    });
  }

  //ignore this, we wont really need it
  @Override
  public void periodic() {
  }
}
