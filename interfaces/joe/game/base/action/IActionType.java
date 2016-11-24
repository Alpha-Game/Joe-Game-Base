package joe.game.base.action;

import joe.classes.identifier.IMappable;

public interface IActionType extends IMappable {
	/**
	 * Determines if type is also another type. <br/> 
	 * <br/>
	 * For example: <br/>
	 * Punch.isType(Melee) == True <br/>
	 * Melee.isType(Punch) == False
	 */
	boolean isType(IActionType type);
}
