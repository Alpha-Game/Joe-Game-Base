package joe.game.base.implementation.conditions;

import joe.game.base.conditions.ICondition;

public abstract class AbstractCondition implements ICondition {
	private final String fIdentifier;

	public AbstractCondition(String identifier) {
		fIdentifier = identifier;
	}

	@Override
	public String getIdentifier() {
		return fIdentifier;
	}
}
