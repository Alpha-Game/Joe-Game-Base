package joe.game.base.implementation.effect.state.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import joe.classes.identifier.IMappable;
import joe.classes.query.FailureCompoundResult;
import joe.game.base.effect.IEffect;
import joe.game.base.effect.IEffectableStates;
import joe.game.base.effect.IStateType;
import joe.game.base.effect.ITimedEffect;

public final class EffectableStatesWrapperBlacklist implements IEffectableStates {
	private final IEffectableStates fParent;
	private final Set<IStateType<?>> fDisallowedTypes;
	
	public EffectableStatesWrapperBlacklist(IEffectableStates parent) {
		fParent = parent;
		fDisallowedTypes = new HashSet<IStateType<?>>();
	}
	
	public EffectableStatesWrapperBlacklist(IEffectableStates parent, Collection<IStateType<?>> disallowedTypes) {
		this(parent);
		for (IStateType<?> allowedType : disallowedTypes) {
			fDisallowedTypes.add(allowedType);
		}
	}
	
	@Override
	public String getIdentifier() {
		return fParent.getIdentifier();
	}

	@Override
	public boolean setValue(IMappable callingObject, Object type, Object value) {
		IStateType<?> realType = getEffectType(type);
		if (realType != null && !fDisallowedTypes.contains(realType) && (value == null || realType.getType().isInstance(value))) {
			return fParent.setValue(callingObject, realType, value);
		}
		return false;
	}

	@Override
	public <V> boolean setValue(IMappable callingObject, IStateType<V> type, V value) {
		IStateType<?> realType = getEffectType(type);
		if (realType != null && !fDisallowedTypes.contains(realType) && (value == null || realType.getType().isInstance(value))) {
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
	public boolean addInstantEffect(IEffect effect) {
		return fParent.addInstantEffect(effect);
	}

	@Override
	public FailureCompoundResult<IEffect> addInstantEffect(IEffect... effects) {
		return fParent.addInstantEffect(effects);
	}

	@Override
	public <V extends IEffect> FailureCompoundResult<IEffect> addInstantEffect(Collection<V> effects) {
		return fParent.addInstantEffect(effects);
	}

	@Override
	public boolean addBaseEffect(IEffect effect) {
		return fParent.addBaseEffect(effect);
	}

	@Override
	public FailureCompoundResult<IEffect> addBaseEffect(IEffect... effects) {
		return fParent.addBaseEffect(effects);
	}

	@Override
	public <V extends IEffect> FailureCompoundResult<IEffect> addBaseEffect(Collection<V> effects) {
		return fParent.addBaseEffect(effects);
	}

	@Override
	public boolean addConstantEffect(double priority, IEffect effect) {
		return fParent.addConstantEffect(priority, effect);
	}

	@Override
	public FailureCompoundResult<IEffect> addConstantEffect(double priority, IEffect... effects) {
		return fParent.addConstantEffect(priority, effects);
	}

	@Override
	public <V extends IEffect> FailureCompoundResult<IEffect> addConstantEffect(double priority, Collection<V> effects) {
		return fParent.addConstantEffect(priority, effects);
	}

	@Override
	public boolean addTimedEffect(long time, double priority, IEffect effect) {
		return fParent.addTimedEffect(time, priority, effect);
	}

	@Override
	public FailureCompoundResult<IEffect> addTimedEffect(long time, double priority, IEffect... effects) {
		return fParent.addTimedEffect(time, priority, effects);
	}

	@Override
	public <V extends IEffect> FailureCompoundResult<IEffect> addTimedEffect(long time, double priority, Collection<V> effects) {
		return fParent.addTimedEffect(time, priority, effects);
	}

	@Override
	public boolean addTimedEffect(double priority, ITimedEffect effect) {
		return fParent.addTimedEffect(priority, effect);
	}

	@Override
	public FailureCompoundResult<ITimedEffect> addTimedEffect(double priority, ITimedEffect... effects) {
		return fParent.addTimedEffect(priority, effects);
	}

	@Override
	public <V extends ITimedEffect> FailureCompoundResult<ITimedEffect> addTimedEffect(double priority, Collection<V> effects) {
		return fParent.addTimedEffect(priority, effects);
	}

	@Override
	public boolean removeEffect(IMappable callingObject, Object effect) {
		return fParent.removeEffect(callingObject, effect);
	}

	@Override
	public FailureCompoundResult<Object> removeEffect(IMappable callingObject, Object... effects) {
		return fParent.removeEffect(callingObject, effects);
	}

	@Override
	public <V> FailureCompoundResult<V> removeEffect(IMappable callingObject, Collection<V> effects) {
		return fParent.removeEffect(callingObject, effects);
	}

	@Override
	public int getPreviousStateCount() {
		return fParent.getPreviousStateCount();
	}
}
