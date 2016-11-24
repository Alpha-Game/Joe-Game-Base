package joe.game.base.implementation.effect.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import joe.classes.base.FixedSizedList;
import joe.classes.identifier.AbstractIdentifier;
import joe.classes.identifier.IMappable;
import joe.game.base.effect.IStates;
import joe.game.base.effect.IState;
import joe.game.base.effect.IStateType;

public class State extends AbstractIdentifier implements IStates {
	private final int fPreviousStateCount;
	protected final Map<String, IStateType<?>> fTypes;
	protected final FixedSizedList<IState> fStates;
	
	public State(String identifier, int previousStateCount, IStateType<?>... types) throws IllegalArgumentException {
		this(identifier, previousStateCount);
		for (IStateType<?> type : types) {
			fTypes.put(type.getIdentifier(), type);
		}
	}

	public <V extends IStateType<?>> State(String identifier, int previousStateCount, Iterable<V> types) throws IllegalArgumentException {
		this(identifier, previousStateCount);
		for (IStateType<?> type : types) {
			fTypes.put(type.getIdentifier(), type);
		}
	}
	
	public State(String identifier, int previousStateCount) throws IllegalArgumentException {
		super(identifier);
		fPreviousStateCount = previousStateCount;
		fTypes = new LinkedHashMap<String, IStateType<?>>();
		fStates = new FixedSizedList<IState>(previousStateCount);
	}
	
	protected IState createStateVariable() {
		return new StateInternal(this);
	}
	
	/*
	 * Use State Internal Object directly after access check
	 */

	@Override
	public <V> boolean setValue(IMappable callingObject, IStateType<V> type, V value) {
		IStateType<?> realType = getEffectType(type);
		if (realType != null && realType.canSet()) {
			return fStates.get(0).setValue(callingObject, type, value);
		}
		return false;
	}
	
	@Override
	public boolean setValue(IMappable callingObject, Object type, Object value) {
		IStateType<?> realType = getEffectType(type);
		if (realType != null && realType.canSet()) {
			return fStates.get(0).setValue(callingObject, realType, value);
		}
		return false;
	}

	@Override
	public <V> V getValue(IStateType<V> type) {
		return getPreviousValue(0, type);
	}
	
	@Override
	public Object getValue(Object type) {
		return getPreviousValue(0, type);
	}

	@Override
	public Object getPreviousValue(int previousCount, Object type) throws IndexOutOfBoundsException, IllegalStateException {
		IState previousState = fStates.get(previousCount);
		if (previousState == null) {
			throw new IllegalStateException(String.format("There is currently no cached state at position <%d>.", previousCount));
		}
		
		IStateType<?> realType = getEffectType(type);
		if (realType != null && realType.canGet()) {
			return previousState.getValue(type);
		}
		return null;
	}

	@Override
	public <V> V getPreviousValue(int previousCount, IStateType<V> type) throws IndexOutOfBoundsException, IllegalStateException {
		return type.getType().cast(getPreviousValue(previousCount, (Object)type));
	}
	
	/*
	 * Class functions
	 */
	
	@Override
	public IStateType<?> getEffectType(Object effectTypeID) {
		if (effectTypeID instanceof String) {
			return fTypes.get(effectTypeID);
		} else if (effectTypeID instanceof IMappable) {
			return fTypes.get(((IMappable) effectTypeID).getIdentifier());
		} else if (effectTypeID != null) {
			return fTypes.get(effectTypeID.toString());
		}
		return null;
	}

	@Override
	public Collection<IStateType<?>> getEffectType(Object... effectTypeID) {
		Collection<IStateType<?>> returnTypes = new ArrayList<IStateType<?>>(effectTypeID.length);
		for(Object type : effectTypeID) {
			returnTypes.add(getEffectType(type));
		}
		return returnTypes;
	}

	@Override
	public <V> Collection<IStateType<?>> getEffectType(Collection<V> effectTypeID) {
		Collection<IStateType<?>> returnTypes = new ArrayList<IStateType<?>>(effectTypeID.size());
		for(Object type : effectTypeID) {
			returnTypes.add(getEffectType(type));
		}
		return returnTypes;
	}

	@Override
	public Collection<IStateType<?>> getEffectType() {
		return fTypes.values();
	}

	@Override
	public int getPreviousStateCount() {
		return fPreviousStateCount;
	}
}
