package joe.game.base.implementation.effect.state.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import joe.classes.identifier.IMappable;
import joe.game.base.effect.IStates;
import joe.game.base.effect.IStateType;

public final class StatesWrapperWhiteList implements IStates {
	private final IStates fParent;
	private final Set<IStateType<?>> fAllowedTypes;
	
	public StatesWrapperWhiteList(IStates parent) {
		fParent = parent;
		fAllowedTypes = new HashSet<IStateType<?>>();
	}
	
	public StatesWrapperWhiteList(IStates parent, Collection<IStateType<?>> allowedTypes) {
		this(parent);
		for (IStateType<?> allowedType : allowedTypes) {
			fAllowedTypes.add(allowedType);
		}
	}

	@Override
	public String getIdentifier() {
		return fParent.getIdentifier();
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

	@Override
	public Object getPreviousValue(int previousCount, Object type) throws IndexOutOfBoundsException, IllegalStateException {
		return fParent.getPreviousValue(previousCount, type);
	}

	@Override
	public <V> V getPreviousValue(int previousCount, IStateType<V> type) throws IndexOutOfBoundsException, IllegalStateException {
		return fParent.getPreviousValue(previousCount, type);
	}
	
	@Override
	public int getPreviousStateCount() {
		return fParent.getPreviousStateCount();
	}
}
