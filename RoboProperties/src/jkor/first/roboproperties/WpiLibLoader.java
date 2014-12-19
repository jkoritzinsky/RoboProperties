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
	 * Constructs a WpiLibLoader using an @link{jkor.first.roboproperties.AbstractPropertyLoader} as a base.
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
		default:
			throw new IllegalArgumentException("Unknown speed controller type specified:" + controllerType.toString());
		}
	}
	
	/**
	 * Loads a solenoid from the property loader.
	 * @param path The path to the data about the solenoid. Must include a "type" and "canID" field.
	 * @return The constructed solenoid based off the path.
	 */
	public Solenoid loadSolenoid(String... path) {
		String[] portPath = appendFieldToPath(path, "port");
		int port = baseLoader.getInt(portPath);
		String[] canIDPath = appendFieldToPath(path, "canID");
		int canID = baseLoader.getInt(canIDPath);
		return new Solenoid(port, canID);
	}
	
	/**
	 * Loads a relay from the property loader.
	 * @param path The path to the data about the relay.  Must include a "port" and "direction" field.
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

}
