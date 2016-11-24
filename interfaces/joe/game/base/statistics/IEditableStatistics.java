package joe.game.base.statistics;

import java.util.Collection;

public interface IEditableStatistics extends IStatistics {
	Object setStatistic(Object statisticType, Object statistic);	
	<V> V setStatistic(IStatisticsType<V> statisticType, V statistic);
	
	Object removeStatistic(Object statisticType);	
	Collection<Object> removeStatistics(Object... statisticType);	
	Collection<Object> removeStatistics(Collection<Object> statisticType);
	void clearStatistics();
}
