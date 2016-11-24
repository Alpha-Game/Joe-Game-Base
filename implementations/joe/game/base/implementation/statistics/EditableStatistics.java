package joe.game.base.implementation.statistics;

import java.util.Collection;
import java.util.Map;

import joe.classes.identifier.TypedValueMap;
import joe.game.base.statistics.IEditableStatistics;
import joe.game.base.statistics.IStatisticsType;

public class EditableStatistics implements IEditableStatistics {
	private final TypedValueMap<IStatisticsType<?>> fMap;
	
	public EditableStatistics() {
		fMap = new TypedValueMap<IStatisticsType<?>>();
	}
	
	public EditableStatistics(IStatisticsType<?>... statisticType) {
		this();
		fMap.setType(statisticType);
	}
	
	@Override
	public Object setStatistic(Object statisticType, Object statistic) {
		return fMap.setValue(statisticType, statistic);
	}
	
	@Override
	public <V> V setStatistic(IStatisticsType<V> statisticType, V statistic) {
		return fMap.setValue(statisticType, statistic);
	}
	
	@Override
	public Object removeStatistic(Object statisticType) {
		return fMap.removeValue(statisticType);
	}
	
	@Override
	public Collection<Object> removeStatistics(Object... statisticType) {
		return fMap.removeValue(statisticType);
	}
	
	@Override
	public Collection<Object> removeStatistics(Collection<Object> statisticType) {
		return fMap.removeValue(statisticType);
	}
	
	@Override
	public void clearStatistics() {
		fMap.clearValues();
	}
	
	@Override
	public IStatisticsType<?> getStatisticType(Object statisticType) {
		return fMap.getType(statisticType);
	}
	
	@Override
	public Collection<IStatisticsType<?>> getStatisticType(Object... statisticType) {
		return fMap.getType(statisticType);
	}

	@Override
	public Collection<IStatisticsType<?>> getStatisticType(Collection<Object> statisticType) {
		return fMap.getType(statisticType);
	}

	@Override
	public Collection<IStatisticsType<?>> getStatisticType() {
		return fMap.getType();
	}

	@Override
	public <V> V getStatistic(IStatisticsType<V> statisticType) {
		return fMap.getValue(statisticType);
	}

	@Override
	public Object getStatistic(Object statisticType) {
		return fMap.getValue(statisticType);
	}

	@Override
	public Collection<Object> getStatistic(Object... statisticType) {
		return fMap.getValue(statisticType);
	}

	@Override
	public Collection<Object> getStatistic(Collection<Object> statisticType) {
		return fMap.getValue(statisticType);
	}

	@Override
	public Map<IStatisticsType<?>, Object> getStatistic() {
		return fMap.getValue();
	}
}
