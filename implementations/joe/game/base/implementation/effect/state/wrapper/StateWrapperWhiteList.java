package joe.game.base.implementation.effect.state.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import joe.classes.identifier.IMappable;
import joe.game.base.effect.IState;
import joe.game.base.effect.IStateType;

public final class StateWrapperWhiteList implements IState {
	private final IState fParent;
	private final Set<IStateType<?>> fAllowedTypes;
	
	public StateWrapperWhiteList(IState parent) {
		fParent = parent;
		fAllowedTypes = new HashSet<IStateType<?>>();
	}
	
	public StateWrapperWhiteList(IState parent, Collection<IStateType<?>> allowedTypes) {
		this(parent);
		for (IStateType<?> allowedType : allowedTypes) {
			fAllowedTypes.add(allowedType);
		}
	}

	@Override
	public boolean setValue(IMappable callingObject, Object type, Object value) {
		IStateType<?> realType = getEffectType(type);
		if (realType != null && fAllowedTypes.contains(realType) && (value == null || realType.getType().isInstance(value))) {
			return fParent.setValue(callingObject, realType, value);
		}
		return false;
	}

	@Override
	public <V> boolean setValue(IMappable callingObject, IStateType<V> type, V value) {
		IStateType<?> realType = getEffectType(type);
		if (realType != null && fAllowedTypes.contains(realType) && (value == null || realType.getType().isInstance(value))) {
			return fParent.setValue(callingObject, realType, value);
		}
		return false;
	}

	@Override
	public Object getValue(Object type) throws IllegalStateException {
		return fParent.getValue(type);
	}

	@Override
	public <V> V getValue(IStateType<V> type) throws IllegalStateException {
		return fParent.getValue(type);
	}

	@Override
	public IStateType<?> getEffectType(Object effectTypeID) {
		return fParent.getEffectType(effectTypeID);
	}

	@Override
	public Collection<IStateType<?>> getEffectType(Object... effectTypeID) {
		return fParent.getEffectType(effectTypeID);
	}

	@Override
	public <V> Collection<IStateType<?>> getEffectType(Collection<V> effectTypeID) {
		return fParent.getEffectType(effectTypeID);
	}

	@Override
	public Collection<IStateType<?>> getEffectType() {
		return fParent.getEffectType();
	}
}
