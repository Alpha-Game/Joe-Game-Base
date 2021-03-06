package joe.game.base.implementation.conditions;

import joe.game.base.conditions.ICondition;

public class OrCondition extends AbstractCondition {
	private final ICondition[] fClauses;
	
	private static String createIdentifier(ICondition... clauses) {
		StringBuilder sb = new StringBuilder();
		sb.append("{(");
		if (clauses.length > 0) {
			for (int i = 0; i < clauses.length - 1; i++) {
				sb.append(clauses[i].getIdentifier() + ") || (");
			}
			sb.append(clauses[clauses.length - 1].getIdentifier());
		}
		sb.append(")}");
		return sb.toString();
	}
	
	public OrCondition(ICondition... clauses) {
		this(createIdentifier(clauses), clauses);
	}
	
	public OrCondition(String identifier, ICondition... clauses) {
		super(identifier);
		fClauses = clauses;
	}

	@Override
	public boolean isTrue() {
		for(ICondition clause : fClauses) {
			if (clause.isTrue()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void update(long time) {
		for(ICondition clause : fClauses) {
			clause.update(time);
		}
	}
}
