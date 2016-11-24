package joe.game.base.implementation.settings.wrapper;

import java.util.Collection;
import java.util.Map;

import joe.game.base.settings.ISettingType;
import joe.game.base.settings.ISettings;

public class SettingsWrapper implements ISettings {
	private final ISettings fParent;
	
	public SettingsWrapper(ISettings parent) {
		fParent = parent;
	}

	@Override
	public ISettingType<?> getSettingType(Object settingType) {
		return fParent.getSettingType(settingType);
	}

	@Override
	public Collection<ISettingType<?>> getSettingType(Object... settingType) {
		return fParent.getSettingType(settingType);
	}

	@Override
	public Collection<ISettingType<?>> getSettingType(Collection<Object> settingType) {
		return fParent.getSettingType(settingType);
	}

	@Override
	public Collection<ISettingType<?>> getSettingType() {
		return fParent.getSettingType();
	}

	@Override
	public <V> V getSetting(ISettingType<V> settingType) {
		return fParent.getSetting(settingType);
	}

	@Override
	public Object getSetting(Object settingType) {
		return fParent.getSetting(settingType);
	}

	@Override
	public Collection<Object> getSetting(Object... settingType) {
		return fParent.getSetting(settingType);
	}

	@Override
	public Collection<Object> getSetting(Collection<Object> settingType) {
		return fParent.getSetting(settingType);
	}

	@Override
	public Map<ISettingType<?>, Object> getSetting() {
		return fParent.getSetting();
	}
}
