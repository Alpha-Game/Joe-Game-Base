package joe.game.base.effect;

import java.util.Collection;

import joe.classes.identifier.IMappable;

public interface IState {
	boolean setValue(IMappable callingObject, Object type, Object value);
	<V> boolean setValue(IMappable callingObject, IStateType<V> type, V value);
	Object getValue(Object type) throws IllegalStateException;
	<V> V getValue(IStateType<V> type) throws IllegalStateException;
	
	IStateType<?> getEffectType(Object effectTypeID);
	Collection<IStateType<?>> getEffectType(Object... effectTypeID);
	<V> Collection<IStateType<?>> getEffectType(Collection<V> effectTypeID);
	Collection<IStateType<?>> getEffectType();
}
