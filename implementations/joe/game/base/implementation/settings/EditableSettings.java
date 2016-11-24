package joe.game.base.implementation.settings;

import java.util.Collection;
import java.util.Map;

import joe.classes.identifier.TypedValueMap;
import joe.game.base.settings.IEditableSettings;
import joe.game.base.settings.ISettingType;
import joe.game.base.statistics.IStatisticsType;

public class EditableSettings implements IEditableSettings {
	private final TypedValueMap<ISettingType<?>> fMap;
	
	public EditableSettings() {
		fMap = new TypedValueMap<ISettingType<?>>();
	}
	
	public EditableSettings(ISettingType<?>... types) {
		this();
		fMap.setType(types);
	}
	
	public EditableSettings(Map<ISettingType<?>, Object> settings) {
		this();
		for (Map.Entry<ISettingType<?>, Object> setting : settings.entrySet()) {
			fMap.setType(setting.getKey());
			fMap.setValue(setting.getKey(), setting.getValue());
		}
	}
	
	@Override
	public Object setSetting(Object settingType, Object setting) {
		return fMap.setValue(settingType, setting);
	}
	
	@Override
	public <V> V setSetting(IStatisticsType<V> settingType, V setting) {
		return fMap.setValue(settingType, setting);
	}
	
	@Override
	public Object removeSetting(Object settingType) {
		return fMap.removeValue(settingType);
	}
	
	@Override
	public Collection<Object> removeSetting(Object... settingType) {
		return fMap.removeValue(settingType);
	}
	
	@Override
	public Collection<Object> removeSetting(Collection<Object> settingType) {
		return fMap.removeValue(settingType);
	}
	
	@Override
	public void clearSetting() {
		fMap.clearValues();
	}
	
	@Override
	public ISettingType<?> getSettingType(Object settingType) {
		return fMap.getType(settingType);
	}
	
	@Override
	public Collection<ISettingType<?>> getSettingType(Object... settingType) {
		return fMap.getType(settingType);
	}

	@Override
	public Collection<ISettingType<?>> getSettingType(Collection<Object> settingType) {
		return fMap.getType(settingType);
	}

	@Override
	public Collection<ISettingType<?>> getSettingType() {
		return fMap.getType();
	}

	@Override
	public <V> V getSetting(ISettingType<V> settingType) {
		return fMap.getValue(settingType);
	}

	@Override
	public Object getSetting(Object settingType) {
		return fMap.getValue(settingType);
	}

	@Override
	public Collection<Object> getSetting(Object... settingType) {
		return fMap.getValue(settingType);
	}

	@Override
	public Collection<Object> getSetting(Collection<Object> settingType) {
		return fMap.getValue(settingType);
	}

	@Override
	public Map<ISettingType<?>, Object> getSetting() {
		return fMap.getValue();
	}
}
