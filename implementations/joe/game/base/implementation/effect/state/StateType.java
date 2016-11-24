package joe.game.base.implementation.effect.state;

import joe.classes.identifier.AbstractIdentifier;
import joe.game.base.effect.IStateType;

public class StateType<V> extends AbstractIdentifier implements IStateType<V> {
	private final Class<V> fType;
	private final boolean fCanGet;
	private final boolean fCanSet;
	private final boolean fCanAddInstantEffect;
	private final boolean fCanAddBaseEffect;
	private final boolean fCanAddConstantEffect;
	private final boolean fCanAddTimedEffect;
	
	public StateType(String identifier, Class<V> type, boolean canGet, boolean canSet, boolean canAddInstantEffect, 
			boolean canAddBaseEffect, boolean canAddConstantEffect, boolean canAddTimedEffect) {
		super(identifier);
		fType = type;
		fCanGet = canGet;
		fCanSet = canSet;
		fCanAddInstantEffect = canAddInstantEffect;
		fCanAddBaseEffect = canAddBaseEffect;
		fCanAddConstantEffect = canAddConstantEffect;
		fCanAddTimedEffect = canAddTimedEffect;
	}
	
	@Override
	public Class<V> getType() {
		return fType;
	}

	@Override
	public boolean canGet() {
		return fCanGet;
	}

	@Override
	public boolean canSet() {
		return fCanSet;
	}

	@Override
	public boolean canAddInstantEffect() {
		return fCanAddInstantEffect;
	}
	
	@Override
	public boolean canAddBaseEffect() {
		return fCanAddBaseEffect;
	}

	@Override
	public boolean canAddConstantEffect() {
		return fCanAddConstantEffect;
	}

	@Override
	public boolean canAddTimedEffect() {
		return fCanAddTimedEffect;
	}
}
