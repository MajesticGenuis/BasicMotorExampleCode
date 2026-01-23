package frc.robot;
//your imports should auto generate up here, if not try this:
//1. Make sure you have the library you need installed and listed in the 'vendordeps folder'
//2. Use quick fix to add the import
//3. Retype the class/object/variable that needs the import
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class ArmRotation extends SubsystemBase {
  //create a double variable. The visability is public, the type is TalonFX and the name is maxRotateSpeed
      //Remember the name can be anything you want. Everything else is case senstive.
  //set the max rotate speed to 1
  public double maxRotateSpeed = 1;
  //create a RotationPositions variable. the viability is public, the type is RotationPositions, and the name is motorState
      //This is a custom variable type called a enum. It can store a value from a list of preset options
      //you will code this variable and its options shortly
  public RotationPositions motorState;

  //create a motor variable. The visability is public, the type is TalonFX and the name is shootMotor.
  //Set the variable equal to a new instance of TalonFX with a motor ID of 1
  public TalonFX rotateMotor = new TalonFX(1);
  //create an encoder variable. The visability is public, the type is DutyCycleEncoder and the name is encoder.
  //Set the variable equal to a new instance of DutyCycleEncoder with an ID of 0
  public DutyCycleEncoder encoder = new DutyCycleEncoder(0);
  //create a PID variable. The visability is public, the type is PIDController and the name is PID.
  //Set the variable equal to a new instance of PIDController with a p value of 1, i value of 0, and a d value of 0
      //These value will be fine tuned to perfect the rotation of the arm
      //95% of the time you will be adjusting the p value
  public PIDController PID = new PIDController(1, 0, 0);
  //create a trigger variable. The visability is public, the type is Trigger and the name is positionReached.
      //Triggers work the same as a button input from a controller
      //They can be used to operate commands with a true/false condition instead of input from a controller
  //Set the trigger equal to a new instance of Trigger
  //The trigger will send out a signal when the absolute value of the encoder value minus the goal value is less that 0.01
      //basically it triggers when the motor is at the desired location within 0.01 degrees
  public Trigger positionReached = new Trigger(() -> Math.abs(getEncoderValue() - motorState.position) < 0.01);
  
  //This is the default function for this subsystem, it will run one time when the robot turns on.
  public ArmRotation() {
    //call the motor variable you created above and set its neutral mode to break
        //the neutral mode is what the motor will do when it is set to stop or set to a speed of 0
        //break mode ensures the motor will not move and it will use energy to ensure this
        //alternatively you can set the motor to go slack, it wont use any energy and will be easily pushed or pulled
    rotateMotor.setNeutralMode(NeutralModeValue.Brake);
    //Set the default command for this subsystem to stop the motor. 
    //When no other command is scheduled, this will run.
    setDefaultCommand(runOnce(()->{
      //Call the motor variable you created above and set the speed to zero.
      rotateMotor.set(0);
    }));
  }
  //this is the custom variable type we will use to set the motor's setpoint
  //The type can be whatever you want to call it, similar to a variable name
  public enum RotationPositions{
    //list the various value that a RotationPositions Variable can be set to
    //I made a neutral position with a value of 0
    //a shootHigh position with a value of 0.75
    //and a shootLow position with a value of 0.5
    neutral(0),
    shootHigh(0.75),
    shootLow(0.5);
    //when we set the value of a RotationPositions variable we want the word to also hold a number value
    //so we need a variable to hold that number, its visability is public final, the type is double, and the name is position
    public final double position;
    //this function sets the above variable to the number value associated with each option
    //for example whenever you set a RotationPositions variable to 'shootHigh' 
    //it runs the following function with the parameter value set to 0.75
    RotationPositions(double pos){
      //this sets the variable 'position' to the number associated with a given RotationPosition
      position = pos;
    }
  }

  //This is a double function
  //Since the function has a variable type of double, it has to return a double value
  public double getEncoderValue(){
    //call the encoder variable you created above
    //return the encoder's value
    return encoder.get();
  }
  //This is another double function, so it must return a double
  public double getRotateOutput(){
    //create a local double variable. a local variable is the same as a normal variable, but it doesn't have a visability
    //call the PID variable your created and use it to calculate the speed of the motor
    //to calculate the speed at a given moment the PID needs the motors current rotation and the goal rotation
    //We will use the getEncoderValue function we just wrote
        //whenever the below line of code runs, it will call the above function
        //getEncoderValue() will be equal to whatever the that function returns
    //to get the goal rotation call the RotationPositions variable you created and get the value of its 'position'
    double output = PID.calculate(getEncoderValue(), motorState.position);
    //somtimes the PID produces a value greater than our desired max value
    //to fix this we must clamp the value of 'output' between the max value and the max negative value

    //if the output if greater than the maximum rotate speed 
    if (output > maxRotateSpeed){
      //set the output to the max speed
      output = maxRotateSpeed;
    }
    //if the output is less than the maximum rotate speed in the inverse direction
    if (output < -maxRotateSpeed){
      //set the output to negative max rotate speed
      output = -maxRotateSpeed;
    }
    //return output
    return output;
  }
  //Create a command function to rotate the motor.
  //To be able to use one function for any position we will use a parameter.
  //The parameter's type is RotationPositions and its name is newState.
  public Command rotate(RotationPositions newState){
    //This function's type is command so it must return a command
    return new RunCommand(()->{
      //set the motor state variable to the parameter 'newState' which represents the new goal state of the motor
      motorState = newState;
      //call your motor variable and use the getRotateOutput function you made to set the speed.
      //everytime this command runs it will call that function and calculate the best speed for the motor
      //then the motor will be set to that speed
      rotateMotor.set(getRotateOutput());
      //the motor's speed will continue to update (about once every 0.2 miliseconds) until 'positionReached' is triggered
      //then the command will stop and the subsystem will run the deafult command again
    }).until(positionReached);
  }

  //ignore this, we wont really need it
  @Override
  public void periodic() {
  }
}
