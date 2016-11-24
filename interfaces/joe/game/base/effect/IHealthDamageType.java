package joe.game.base.effect;

import joe.classes.identifier.IMappable;

public interface IHealthDamageType extends IMappable {
	/**
	 * Determines if type is also another type. <br/> 
	 * <br/>
	 * For example: <br/>
	 * Melee.isType(Physical) == True <br/>
	 * Physical.isType(Melee) == False
	 */
	boolean isType(IHealthDamageType type);
}
