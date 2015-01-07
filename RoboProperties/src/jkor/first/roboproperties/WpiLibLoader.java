package jkor.first.roboproperties;

import jkor.first.roboproperties.wpilib.*;
import edu.wpi.first.wpilibj.*;

/**
 * @author Jeremy Koritzinsky
 * Loader for objects in the WPI Robotics Library
 *
 */
public class WpiLibLoader {
	
	private AbstractPropertyLoader baseLoader;
	
	/**
	 * Constructs a WpiLibLoader using an {@link jkor.first.roboproperties.AbstractPropertyLoader} as a base.
	 * @param loader The loader to use as a base.
	 */
	public WpiLibLoader(AbstractPropertyLoader loader) {
		Assertions.IsNotNull(loader, "loader");
		baseLoader = loader;
	}
	
	private String[] appendFieldToPath(String[] path, String field) {
		String[] extendedPath = new String[path.length + 1];
		System.arraycopy(path, 0, extendedPath, 0, path.length);
		extendedPath[path.length] = field;
		return extendedPath;
	}
	
	/**
	 * Loads a speed controller from the property loader.
	 * @param path The path to the data about the speed controller. Must include a "type" and "port" field.
	 * @return The constructed speed controller based off the path.
	 */
	public SpeedController loadSpeedController(String... path) {
		String[] typePath = appendFieldToPath(path, "type");
		SpeedControllerType controllerType = baseLoader.getEnum(SpeedControllerType.class, typePath);
		String[] portPath = appendFieldToPath(path, "port");
		int port = baseLoader.getInt(portPath);
		switch(controllerType) {
		case Jaguar:
			return new Jaguar(port);
		case Talon:
			return new Talon(port);
		case Victor:
			return new Victor(port);
		case VictorSP:
			return new VictorSP(port);
		case TalonSRX:
			return new TalonSRX(port);
		case CANJaguar:
			return new CANJaguar(port);
		case CANTalon:
			return new CANTalon(port);
		default:
			throw new IllegalArgumentException("Unknown speed controller type specified:" + controllerType.toString());
		}
	}
	
	/**
	 * Loads a solenoid from the property loader.
	 * @param path The path to the data about the solenoid. Must include a "port" field.  The "canID" field is optional.
	 * @return The constructed solenoid based off the path.
	 */
	public Solenoid loadSolenoid(String... path) {
		String[] portPath = appendFieldToPath(path, "port");
		int port = baseLoader.getInt(portPath);
		String[] canIDPath = appendFieldToPath(path, "canID");
		Integer canID = baseLoader.getInt(canIDPath);
		if(canID == null)
			return new Solenoid(port);
		else
			return new Solenoid(port, canID);
	}
	
	/**
	 * Loads a relay from the property loader.
	 * @param path The path to the data about the relay.  Must include a "port" field. May include a "direction" field.
	 * @return The constructed relay based off the path.
	 */
	public Relay loadRelay(String... path) {
		String[] portPath = appendFieldToPath(path, "port");
		int port = baseLoader.getInt(portPath);
		String[] directionPath = appendFieldToPath(path, "direction");
		Relay.Direction direction = baseLoader.getEnum(Relay.Direction.class, directionPath);
		if(direction == null) {
			direction = Relay.Direction.kBoth;
		}
		return new Relay(port, direction);
	}
	
	/**
	 * Loads a {@link edu.wpi.first.wpilibj.RobotDrive} from a properties file.
	 * @param path The path to the data about the drive system.  Must include a "numWheels" field (either 2 or 4) and the corresponding numbers for the wheels.
	 * 2 -> leftWheel, rightWheel
	 * 4 -> frontLeftWheel, rearLeftWheel,frontRightWheel,rearRightWheel
	 * @return The constructed drive based off the path.
	 */
	public RobotDrive loadDrive(String... path) {
		String[] numWheelsPath = appendFieldToPath(path, "numWheels");
		int numWheels = baseLoader.getInt(numWheelsPath);
		switch(numWheels) {
		case 2:
			String[] leftWheelPath = appendFieldToPath(path, "leftWheel");
			SpeedController leftWheel = loadSpeedController(leftWheelPath);
			String[] rightWheelPath = appendFieldToPath(path, "rightWheel");
			SpeedController rightWheel = loadSpeedController(rightWheelPath);
			return new RobotDrive(leftWheel,rightWheel);
		case 4:
			String[] frontLeftWheelPath = appendFieldToPath(path, "frontLeftWheel");
			SpeedController frontLeftWheel = loadSpeedController(frontLeftWheelPath);
			String[] rearLeftWheelPath = appendFieldToPath(path, "rearLeftWheel");
			SpeedController rearLeftWheel = loadSpeedController(rearLeftWheelPath);
			String[] frontRightWheelPath = appendFieldToPath(path, "frontRightWheel");
			SpeedController frontRightWheel = loadSpeedController(frontRightWheelPath);
			String[] rearRightWheelPath = appendFieldToPath(path, "rearRightWheel");
			SpeedController rearRightWheel = loadSpeedController(rearRightWheelPath);
			return new RobotDrive(frontLeftWheel, rearLeftWheel, frontRightWheel, rearRightWheel);
		default:
			throw new IllegalArgumentException("Invalid number of wheels specified:" + numWheels);
		}
	}
	
	/**
	 * Loads a {@link edu.wpi.first.wpilibj.Servo} from a properties file.
	 * @param path The path to the servo.  Must include a "port" field.
	 * @return The constructed servo based off the path.
	 */
	public Servo loadServo(String... path) {
		String[] portPath = appendFieldToPath(path, "port");
		int port = baseLoader.getInt(portPath);
		return new Servo(port);
	}
	
	/**
	 * Loads a {@link edu.wpi.first.wpilibj.Compressor} from a properties file.
	 * @param path The path to the compressor.  May include "canID" field.
	 * @return The constructed compressor based off the path.
	 */
	public Compressor loadCompressor(String... path) {
		String[] canIDPath = appendFieldToPath(path, "canID");
		Integer canID = baseLoader.getInt(canIDPath);
		if(canID == null)
			return new Compressor();
		else
			return new Compressor(canID);
	}
}
