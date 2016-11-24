package joe.game.base.effect;

import joe.classes.identifier.IMappable;

public interface IStates extends IMappable, IState {
	Object getPreviousValue(int previousCount, Object type) throws IndexOutOfBoundsException, IllegalStateException;
	<V> V getPreviousValue(int previousCount, IStateType<V> type) throws IndexOutOfBoundsException, IllegalStateException;
	
	int getPreviousStateCount();
}
