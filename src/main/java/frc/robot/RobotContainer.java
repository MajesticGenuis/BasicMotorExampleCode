package frc.robot;
//your imports should auto generate up here, if not try this:
//1. Make sure you have the library you need installed and listed in the 'vendordeps folder'
//2. Use quick fix to add the import
//3. Retype the class/object/variable that needs the import
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.ArmRotation.RotationPositions;

//The robot container class is what will control the robot.
//Here you add any logic or contols needed to operate the robot.
public class RobotContainer {
  //create a controller variable. The visability is public, the type is CommandXboxController and the name is controller.
        //Remember the name can be anything you want. Everything else is case senstive.
  //Set the variable equal to a new instance of CommandXboxController with a port ID of 1
  public CommandXboxController controller = new CommandXboxController(1);
  //create a subsystem variable for your shooter.
      //Imagine the code you previously wrote for the shooter as the instructions for the shooter 
      //and this variable is the actual shooter
  //The visability is public, the type is Shooter, and the name is shooterSub
    //The type is whatever you named the file with the shooter code
  public Shooter shooterSub = new Shooter();
  //create a subsystem variable for the arm rotation motor
  public ArmRotation armSub = new ArmRotation();
  //create a subsystem variable for the intake
  public Intake intake = new Intake();
  
  //this is the default function for robot container, it will run one time when the robot turns on.
  public RobotContainer() {
    //configureBindings is the function used to set all the controls and logic needed to operate the robot
    configureBindings();
  }

  //this function has 'void' instead of a variable type, so it does not need to return a value.
  //whenever this function is called it simply executes the code within it and ends.
  private void configureBindings() {
    //call the controller variable you created above
    //while the x button is true(held down) run the shoot command with a speed of 1
    //to call the shoot command you must call the shooter subsystem variable you created 
    //and use the shoot function from there
    controller.x().whileTrue(shooterSub.shoot(1));
    //call the controller variable
    //while the y button is true, run the shoot command with a speed of -1
    controller.y().whileTrue(shooterSub.shoot(-1));

    //call the controller variable you created above
    //when the a button is pressed run the rotate command once with the 'shootHigh' position
        //you might have to import the type 'RotationPositions'
        //Use quick fix if it doesn't auto generate at the top
    //to call the rotate command you must call the arm subsystem variable you created 
    //and use the roatate function from there
    controller.a().onTrue(armSub.rotate(RotationPositions.shootHigh));
    //call the controller variable you created above
    //when the b button is pressed run the rotate command once with the 'shootLow' position
    controller.b().onTrue(armSub.rotate(RotationPositions.shootLow));
    //call the controller variable you created above
    //when the right bumper is pressed run the rotate command once with the 'neutral' position
    controller.rightBumper().onTrue(armSub.rotate(RotationPositions.neutral));
  }

  //Don't worry about this for now, later we will use this to program the robot's auto
  //the code that runs the robot during the autonomous period of a match
  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
