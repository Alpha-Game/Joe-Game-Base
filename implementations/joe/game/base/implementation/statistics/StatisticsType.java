package joe.game.base.implementation.statistics;

import joe.classes.identifier.AbstractNamedTypedIdentifier;
import joe.classes.identifier.INamable;
import joe.game.base.statistics.IStatisticsType;

public class StatisticsType<V1> extends AbstractNamedTypedIdentifier<V1> implements IStatisticsType<V1>, INamable {	
	public StatisticsType(String identifier, String nameIdentifier, Class<V1> type) {
		super(identifier, nameIdentifier, type);
	}
}