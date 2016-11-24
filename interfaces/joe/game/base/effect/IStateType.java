package joe.game.base.effect;

import joe.classes.identifier.IMappable;
import joe.classes.identifier.ITypable;

public interface IStateType<V1> extends IMappable, ITypable<V1> {
	boolean canGet();
	boolean canSet();
	boolean canAddInstantEffect();
	boolean canAddBaseEffect();
	boolean canAddConstantEffect();
	boolean canAddTimedEffect();
}
