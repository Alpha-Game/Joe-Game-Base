package joe.game.base.effect;

import java.util.Collection;

import joe.classes.identifier.IMappable;

public interface IEffect extends IMappable {
	public static long BASE = -1;
	public static long CONSTANT = -2;
	public static long INSTANTANEOUS = -3;
	
	/**
	 * Gets the object which created the effect
	 */
	IMappable getCallingObject();
	
	/**
	 * Gets a list of all types which are affected by this effect
	 */
	Collection<IStateType<?>> getTypesAffected();
	
	/**
	 * Modifiers which change how the effect updates the object
	 */
	Collection<Object> getModifiers();
	
	/**
	 * Changes the state of an object based upon the effect
	 * 
	 * @param time Time in nanoseconds since last update if effect is timed, CONSTANT if effect is constant, INSTANTANEOUS if effect is instantaneous
	 * @param object The object to change the state of
	 */
	boolean update(Long time, IState object);
}
