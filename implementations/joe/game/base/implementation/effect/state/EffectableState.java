package joe.game.base.implementation.effect.state;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import joe.classes.base.PriorityMap;
import joe.classes.identifier.IMappable;
import joe.classes.query.FailureCompoundResult;
import joe.game.base.IUpdateableObject;
import joe.game.base.effect.IEffect;
import joe.game.base.effect.IEffectableStates;
import joe.game.base.effect.IStates;
import joe.game.base.effect.IState;
import joe.game.base.effect.IStateType;
import joe.game.base.effect.ITimedEffect;
import joe.game.base.implementation.effect.state.wrapper.StateWrapperSettableWhiteList;
import joe.game.base.implementation.effect.state.wrapper.StateWrapperWhiteList;
import joe.game.base.implementation.effect.state.wrapper.StatesWrapperWhiteList;

public class EffectableState extends State implements IEffectableStates, IUpdateableObject {
	public static interface EffectCalculation<V1> {
		V1 nextState(IStateType<?> type, IState nextState, IStates allStates);
	}
	
	private static class EffectValues {
		private StateWrapperSettableWhiteList fState;
		private Long fTime;
		
		private EffectValues(StateWrapperSettableWhiteList state, Long time) {
			fState = state;
			fTime = time;
		}
	}
	
	protected final Set<IStateType<?>> fBaseTypes;
	protected final Map<IStateType<?>, EffectCalculation<?>> fCalculatedTypes;
	protected final Set<IStateType<?>> fInheritedTypes;
	protected final PriorityMap<IEffect, EffectValues> fEffects;
	protected final IState fBaseState;
	
	public EffectableState(String identifier, int previousStateCount) throws IllegalArgumentException {
		super(identifier, previousStateCount);
		fBaseState = createStateVariable();
		fBaseTypes = new HashSet<IStateType<?>>();
		fCalculatedTypes = new HashMap<IStateType<?>, EffectCalculation<?>>();
		fInheritedTypes = new HashSet<IStateType<?>>();
		fEffects = new PriorityMap<IEffect, EffectValues>();
	}
	
	public <V extends IStateType<?>> EffectableState(String identifier, int previousStateCount, Iterable<V> types) throws IllegalArgumentException {
		super(identifier, previousStateCount, types);
		fBaseState = createStateVariable();
		fBaseTypes = new HashSet<IStateType<?>>();
		fCalculatedTypes = new HashMap<IStateType<?>, EffectCalculation<?>>();
		fInheritedTypes = new HashSet<IStateType<?>>();
		fEffects = new PriorityMap<IEffect, EffectValues>();
	}
	
	public EffectableState(String identifier, int previousStateCount, IStateType<?>... types) throws IllegalArgumentException {
		super(identifier, previousStateCount, types);
		fBaseState = createStateVariable();
		fBaseTypes = new HashSet<IStateType<?>>();
		fCalculatedTypes = new HashMap<IStateType<?>, EffectCalculation<?>>();
		fInheritedTypes = new HashSet<IStateType<?>>();
		fEffects = new PriorityMap<IEffect, EffectValues>();
	}
	
	public <V1 extends IStateType<?>, V2 extends IStateType<?>, V3 extends IStateType<?>, V4 extends EffectCalculation<?>, V5 extends IStateType<?>, V6, V7>
		EffectableState(String identifier, int previousStateCount, Iterable<V1> types, Iterable<V2> baseTypes, Map<V3, V4> calculatedTypes, Iterable<V5> inheritedTypes, Map<V6, V7> startStates) throws IllegalArgumentException {
		this(identifier, previousStateCount, types);
		
		for (V2 baseType : baseTypes) {
			IStateType<?> realType = getEffectType(baseType);
			if (realType == null) {
				throw new IllegalArgumentException("Type cannot be null.");
			}
			fBaseTypes.add(realType);
		}
		
		for (Map.Entry<V3, V4> calculatedType : calculatedTypes.entrySet()) {
			IStateType<?> realType = getEffectType(calculatedType.getKey());
			if (realType == null) {
				throw new IllegalArgumentException("Type cannot be null.");
				
			} else if (calculatedType.getValue() == null) {
				throw new IllegalArgumentException("Calculation class cannot be null.");
			}
			fCalculatedTypes.put(realType, calculatedType.getValue());
		}
		
		for (V5 inheritedType : inheritedTypes) {
			IStateType<?> realType = getEffectType(inheritedType);
			if (realType == null) {
				throw new IllegalArgumentException("Type cannot be null.");
			}
			fInheritedTypes.add(realType);
		}
		
		IState startState = createStateVariable();
		for (Map.Entry<V6, V7> state : startStates.entrySet()) {
			IStateType<?> realType = getEffectType(state.getKey());
			V7 value = state.getValue();
			if (realType == null) {
				throw new IllegalArgumentException("Type cannot be null.");
			} else if ((value != null && !realType.getType().isInstance(value))) {
				throw new IllegalArgumentException("Value must match type.");
			}
			
			Object realValue = realType.getType().cast(value);
			if (fBaseTypes.contains(realType)) {
				fBaseState.setValue(this, realType, realValue);
			}
			startState.setValue(this, realType, realValue);
		}
		fStates.add(startState);
	}
	
	protected IState createState(IState baseState, IState lastState, IStates allStates) {
		IState nextState = createStateVariable();
		IState nextStateLocked = new StateWrapperWhiteList(nextState);
		IStates allStatesLocked = new StatesWrapperWhiteList(allStates);
		
		// Base states first
		for (IStateType<?> type : fBaseTypes) {
			IStateType<?> realType = nextState.getEffectType(type);
			if (realType != null) {
				nextState.setValue(this, realType, realType.getType().cast(baseState.getValue(type)));
			}
		}
		
		// Inherited states next
		for (IStateType<?> type : fInheritedTypes) {
			IStateType<?> realType = nextState.getEffectType(type);
			if (realType != null) {
				nextState.setValue(this, realType, realType.getType().cast(lastState.getValue(type)));
			}
		}
		
		// Calculated states last
		for (Map.Entry<IStateType<?>, EffectCalculation<?>> type : fCalculatedTypes.entrySet()) {
			IStateType<?> realType = nextState.getEffectType(type.getKey());
			if (realType != null) {
				nextState.setValue(this, realType, realType.getType().cast(type.getValue().nextState(realType, nextStateLocked, allStatesLocked)));
			}
		}
		
		return nextState;
	}
	
	
	protected void applyEffects(long time, IState nextState) {
		List<IEffect> expiredEffects = new LinkedList<IEffect>();
		
		for (Map.Entry<IEffect, EffectValues> effect : fEffects.entrySet()) {
			EffectValues value = effect.getValue();
			
			if (value.fTime != null) {
				value.fTime = value.fTime - time;
				if (value.fTime < 0) {
					expiredEffects.add(effect.getKey());
					continue;
				}
			}
			
			value.fState.setParent(nextState);
			effect.getKey().update(value.fTime == null ? IEffect.CONSTANT : time, value.fState);
		}
		
		removeEffect(this, expiredEffects);
	}
	
	@Override
	public void update(long time) {
		IState nextState = createState(fBaseState, fStates.get(0), this);
		applyEffects(time, nextState);
		fStates.add(nextState);
	}
	
	/*
	 * Modify Effect List
	 */
	protected boolean performEffect(IEffect effect, long time, IState state, Collection<IStateType<?>> allowedTypes) {
		return effect.update(time, new StateWrapperWhiteList(state, allowedTypes));
	}
	
	protected void addEffect(Long time, double priority, IEffect effect, Collection<IStateType<?>> allowedTypes) {
		fEffects.put(effect, priority, new EffectValues(new StateWrapperSettableWhiteList(null, allowedTypes), time));
	}
	
	protected void removeEffect(Object effect) {
		fEffects.remove(effect);
	}	
	
	/*
	 * Instant Effects
	 */

	@Override
	public boolean addInstantEffect(IEffect effect) {
		Collection<IStateType<?>> realTypes = getEffectType(effect.getTypesAffected());
		for (IStateType<?> type : realTypes) {
			if (!type.canAddInstantEffect()) {
				return false;
			}
		}
		return performEffect(effect, IEffect.INSTANTANEOUS, fStates.get(0), realTypes);
	}

	@Override
	public FailureCompoundResult<IEffect> addInstantEffect(IEffect... effects) {
		FailureCompoundResult<IEffect> result = new FailureCompoundResult<IEffect>();
		for (IEffect effect : effects) {
			result.addResult(effect, addInstantEffect(effect));
		}
		return result;
	}

	@Override
	public <V extends IEffect> FailureCompoundResult<IEffect> addInstantEffect(Collection<V> effects) {
		FailureCompoundResult<IEffect> result = new FailureCompoundResult<IEffect>();
		for (IEffect effect : effects) {
			result.addResult(effect, addInstantEffect(effect));
		}
		return result;
	}
	
	/*
	 * Base Effects
	 */

	@Override
	public boolean addBaseEffect(IEffect effect) {
		Collection<IStateType<?>> realTypes = getEffectType(effect.getTypesAffected());
		for (IStateType<?> type : realTypes) {
			if (!type.canAddBaseEffect()) {
				return false;
			}
		}
		return performEffect(effect, IEffect.BASE, fStates.get(0), realTypes);
	}

	@Override
	public FailureCompoundResult<IEffect> addBaseEffect(IEffect... effects) {
		FailureCompoundResult<IEffect> result = new FailureCompoundResult<IEffect>();
		for (IEffect effect : effects) {
			result.addResult(effect, addBaseEffect(effect));
		}
		return result;
	}

	@Override
	public <V extends IEffect> FailureCompoundResult<IEffect> addBaseEffect(Collection<V> effects) {
		FailureCompoundResult<IEffect> result = new FailureCompoundResult<IEffect>();
		for (IEffect effect : effects) {
			result.addResult(effect, addBaseEffect(effect));
		}
		return result;
	}
	
	/*
	 * Constant Effects
	 */

	@Override
	public boolean addConstantEffect(double priority, IEffect effect) {
		Collection<IStateType<?>> realTypes = getEffectType(effect.getTypesAffected());
		for (IStateType<?> type : realTypes) {
			if (!type.canAddConstantEffect()) {
				return false;
			}
		}
		addEffect(null, priority, effect, realTypes);
		return true;
	}

	@Override
	public FailureCompoundResult<IEffect> addConstantEffect(double priority, IEffect... effects) {
		FailureCompoundResult<IEffect> result = new FailureCompoundResult<IEffect>();
		for (IEffect effect : effects) {
			result.addResult(effect, addConstantEffect(priority, effect));
		}
		return result;
	}

	@Override
	public <V extends IEffect> FailureCompoundResult<IEffect> addConstantEffect(double priority, Collection<V> effects) {
		FailureCompoundResult<IEffect> result = new FailureCompoundResult<IEffect>();
		for (IEffect effect : effects) {
			result.addResult(effect, addConstantEffect(priority, effect));
		}
		return result;
	}
	
	/*
	 * Timed Effects
	 */

	@Override
	public boolean addTimedEffect(long time, double priority, IEffect effect) {
		Collection<IStateType<?>> realTypes = getEffectType(effect.getTypesAffected());
		for (IStateType<?> type : realTypes) {
			if (!type.canAddTimedEffect()) {
				return false;
			}
		}
		addEffect(time, priority, effect, realTypes);
		return true;
	}

	@Override
	public FailureCompoundResult<IEffect> addTimedEffect(long time, double priority, IEffect... effects) {
		FailureCompoundResult<IEffect> result = new FailureCompoundResult<IEffect>();
		for (IEffect effect : effects) {
			result.addResult(effect, addTimedEffect(time, priority, effect));
		}
		return result;
	}

	@Override
	public <V extends IEffect> FailureCompoundResult<IEffect> addTimedEffect(long time, double priority, Collection<V> effects) {
		FailureCompoundResult<IEffect> result = new FailureCompoundResult<IEffect>();
		for (IEffect effect : effects) {
			result.addResult(effect, addTimedEffect(time, priority, effect));
		}
		return result;
	}

	@Override
	public boolean addTimedEffect(double priority, ITimedEffect effect) {
		return addTimedEffect(effect.getDuration(), priority, effect);
	}

	@Override
	public FailureCompoundResult<ITimedEffect> addTimedEffect(double priority, ITimedEffect... effects) {
		FailureCompoundResult<ITimedEffect> result = new FailureCompoundResult<ITimedEffect>();
		for (ITimedEffect effect : effects) {
			result.addResult(effect, addTimedEffect(priority, effect));
		}
		return result;
	}

	@Override
	public <V extends ITimedEffect> FailureCompoundResult<ITimedEffect> addTimedEffect(double priority, Collection<V> effects) {
		FailureCompoundResult<ITimedEffect> result = new FailureCompoundResult<ITimedEffect>();
		for (ITimedEffect effect : effects) {
			result.addResult(effect, addTimedEffect(priority, effect));
		}
		return result;
	}
	
	/*
	 * Remove Effects
	 */

	@Override
	public boolean removeEffect(IMappable callingObject, Object effect) {
		removeEffect(effect);
		return true;
	}

	@Override
	public FailureCompoundResult<Object> removeEffect(IMappable callingObject, Object... effects) {
		FailureCompoundResult<Object> result = new FailureCompoundResult<Object>();
		for (Object effect : effects) {
			result.addResult(effect, removeEffect(callingObject, effect));
		}
		return result;
	}

	@Override
	public <V> FailureCompoundResult<V> removeEffect(IMappable callingObject, Collection<V> effects) {
		FailureCompoundResult<V> result = new FailureCompoundResult<V>();
		for (V effect : effects) {
			result.addResult(effect, removeEffect(callingObject, effect));
		}
		return result;
	}
}
