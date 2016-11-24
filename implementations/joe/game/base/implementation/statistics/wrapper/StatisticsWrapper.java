package joe.game.base.implementation.statistics.wrapper;

import java.util.Collection;
import java.util.Map;

import joe.game.base.statistics.IStatistics;
import joe.game.base.statistics.IStatisticsType;

public class StatisticsWrapper implements IStatistics {
	private final IStatistics fParent;
	
	public StatisticsWrapper(IStatistics parent) {
		fParent = parent;
	}

	@Override
	public IStatisticsType<?> getStatisticType(Object statisticType) {
		return fParent.getStatisticType(statisticType);
	}

	@Override
	public Collection<IStatisticsType<?>> getStatisticType(Object... statisticType) {
		return fParent.getStatisticType(statisticType);
	}

	@Override
	public Collection<IStatisticsType<?>> getStatisticType(Collection<Object> statisticType) {
		return fParent.getStatisticType(statisticType);
	}

	@Override
	public Collection<IStatisticsType<?>> getStatisticType() {
		return fParent.getStatisticType();
	}

	@Override
	public <V> V getStatistic(IStatisticsType<V> statisticType) {
		return fParent.getStatistic(statisticType);
	}

	@Override
	public Object getStatistic(Object statisticType) {
		return fParent.getStatistic(statisticType);
	}

	@Override
	public Collection<Object> getStatistic(Object... statisticType) {
		return fParent.getStatistic(statisticType);
	}

	@Override
	public Collection<Object> getStatistic(Collection<Object> statisticType) {
		return fParent.getStatistic(statisticType);
	}

	@Override
	public Map<IStatisticsType<?>, Object> getStatistic() {
		return fParent.getStatistic();
	}
}
