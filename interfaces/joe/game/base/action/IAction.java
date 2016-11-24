package joe.game.base.action;


import joe.classes.identifier.IMappable;
import joe.game.base.IUpdateableObject;
import joe.game.base.settings.ISettings;

public interface IAction extends IMappable, IUpdateableObject, ISettings {
	/**
	 * Will always be the object performing the action
	 */
	IMappable getCallingObject();
	
	/**
	 * Gets the type of action this action is
	 */
	IActionType getType();
	
	/**
	 * How long the action takes to perform, NULL when action is instant (like movement)
	 */
	Long getDuration();
	
	/**
	 * Starts an action
	 * 
	 * @return False if an action cannot be started, true otherwise
	 */
	boolean startAction();
	
	/**
	 * Stops an action
	 * 
	 * @return False if an action cannot be stopped, true otherwise
	 */
	boolean stopAction();
	
	/**
	 * Determines if this action can be performed
	 * 
	 * @return True if the action can be performed at this time, false otherwise
	 */
	boolean canPerformAction();
	
	/**
	 * Determines if this action is currently being performed
	 * 
	 * @return True if the action is currently being performed, false otherwise
	 */
	boolean isPerformingAction();
}
