package joe.game.base.effect;

import java.util.Collection;

import joe.classes.identifier.IMappable;
import joe.classes.query.FailureCompoundResult;

public interface IEffectableStates extends IStates {
	boolean addInstantEffect(IEffect effect);
	FailureCompoundResult<IEffect> addInstantEffect(IEffect... effects);
	<V extends IEffect> FailureCompoundResult<IEffect> addInstantEffect(Collection<V> effects);
	
	boolean addBaseEffect(IEffect effect);
	FailureCompoundResult<IEffect> addBaseEffect(IEffect... effects);
	<V extends IEffect> FailureCompoundResult<IEffect> addBaseEffect(Collection<V> effects);
	
	boolean addConstantEffect(double priority, IEffect effect);
	FailureCompoundResult<IEffect> addConstantEffect(double priority, IEffect... effects);
	<V extends IEffect> FailureCompoundResult<IEffect> addConstantEffect(double priority, Collection<V> effects);
	
	boolean addTimedEffect(long time, double priority, IEffect effect);
	FailureCompoundResult<IEffect> addTimedEffect(long time, double priority, IEffect... effects);
	<V extends IEffect> FailureCompoundResult<IEffect> addTimedEffect(long time, double priority, Collection<V> effects);
	
	boolean addTimedEffect(double priority, ITimedEffect effect);
	FailureCompoundResult<ITimedEffect> addTimedEffect(double priority, ITimedEffect... effects);
	<V extends ITimedEffect> FailureCompoundResult<ITimedEffect> addTimedEffect(double priority, Collection<V> effects);
	
	boolean removeEffect(IMappable callingObject, Object effect);
	FailureCompoundResult<Object> removeEffect(IMappable callingObject, Object... effects);
	<V> FailureCompoundResult<V> removeEffect(IMappable callingObject, Collection<V> effects);
}
