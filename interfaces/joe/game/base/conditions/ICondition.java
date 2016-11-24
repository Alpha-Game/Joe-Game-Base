package joe.game.base.conditions;

import joe.classes.identifier.IMappable;
import joe.game.base.IUpdateableObject;

public interface ICondition extends IMappable, IUpdateableObject {
	public boolean isTrue();
}
