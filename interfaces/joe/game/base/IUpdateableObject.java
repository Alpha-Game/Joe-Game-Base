package joe.game.base;

import joe.classes.identifier.IMappable;

public interface IUpdateableObject extends IMappable {
	/**
	 * Updates the object based upon the time.
	 * 
	 * @param time Time in nanoseconds since the object was last updated
	 */
	void update(long time);
}
