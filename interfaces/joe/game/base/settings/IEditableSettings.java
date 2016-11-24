package joe.game.base.settings;

import java.util.Collection;

import joe.game.base.statistics.IStatisticsType;

public interface IEditableSettings extends ISettings {
	public Object setSetting(Object settingType, Object setting);
	public <V> V setSetting(IStatisticsType<V> settingType, V setting);
	
	public Object removeSetting(Object settingType);
	public Collection<Object> removeSetting(Object... settingType);
	public Collection<Object> removeSetting(Collection<Object> settingType);
	public void clearSetting();
}
