package joe.game.base.statistics;

import java.util.Collection;
import java.util.Map;
public interface IStatistics {
	IStatisticsType<?> getStatisticType(Object statisticType);
	Collection<IStatisticsType<?>> getStatisticType(Object... statisticType);
	Collection<IStatisticsType<?>> getStatisticType(Collection<Object> statisticType);
	Collection<IStatisticsType<?>> getStatisticType();
	
	<V> V getStatistic(IStatisticsType<V> statisticType);
	Object getStatistic(Object statisticType);
	Collection<Object> getStatistic(Object... statisticType);
	Collection<Object> getStatistic(Collection<Object> statisticType);
	Map<IStatisticsType<?>, Object> getStatistic();
}
