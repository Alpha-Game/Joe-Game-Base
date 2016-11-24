package joe.game.base.implementation.conditions;

import joe.game.base.conditions.ICondition;

public class NotCondition extends AbstractCondition {
	private final ICondition fClause;
	
	private static String createIdentifier(ICondition clause) {
		StringBuilder sb = new StringBuilder();
		sb.append("!(");
		sb.append(clause.getIdentifier());
		sb.append(")");
		return sb.toString();
	}
	
	public NotCondition(ICondition clause) {
		this(createIdentifier(clause), clause);
	}
	
	public NotCondition(String identifier, ICondition clause) {
		super(identifier);
		fClause = clause;
	}

	@Override
	public boolean isTrue() {
		return !fClause.isTrue();
	}

	@Override
	public void update(long time) {
		fClause.update(time);
	}
}
