package joe.game.base.implementation.effect.state;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import joe.classes.identifier.IMappable;
import joe.classes.query.FailureCompoundResult;
import joe.game.base.effect.IStates;
import joe.game.base.effect.IState;
import joe.game.base.effect.IStateType;

public class StateInternal implements IState {
	private final IStates fParent;
	private final Map<String, Object> fValues;
	
	public StateInternal(IStates parent) {
		fParent = parent;
		fValues = new LinkedHashMap<String, Object>();
	}
	
	public StateInternal(IStates parent, IState copyState) {
		this(parent);
		
		// Copy over all primitives
		if (copyState != null) {
			for (IStateType<?> type : getEffectType()) {
				Object value = copyState.getValue(type);
				if (value == null || ((value instanceof String || value instanceof Boolean || value instanceof Number || value.getClass().isPrimitive()) && (type.getType().isInstance(value))) ) {
					fValues.put(type.getIdentifier(), value);
				} else {
					throw new UnsupportedOperationException();
				}
			}
		}
	}
	
	public <V1 extends Object, V2 extends Object> FailureCompoundResult<V1> setAllValues(IMappable callingObject, Map<V1, V2> values) {
		FailureCompoundResult<V1> result = new FailureCompoundResult<V1>();
		for (Entry<V1, V2> entry : values.entrySet()) {
			result.addResult(entry.getKey(), setValue(callingObject, entry.getKey(), entry.getValue()));
		}
		return result;
	}

	@Override
	public boolean setValue(IMappable callingObject, Object type, Object value) {
		if (callingObject != null) {
			IStateType<?> realType = getEffectType(type);
			if (realType != null && (value == null || realType.getType().isInstance(value))) {
				fValues.put(realType.getIdentifier(), value);
				return true;
			}
		}
		return false;
	}

	@Override
	public <V> boolean setValue(IMappable callingObject, IStateType<V> type, V value) {
		return setValue(callingObject, (Object)type, (Object)value);
	}

	@Override
	public Object getValue(Object type) {
		return getValue(getEffectType(type));
	}

	@Override
	public <V> V getValue(IStateType<V> type) {
		return type.getType().cast(fValues.get(type.getIdentifier()));
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
