package joe.game.base.settings;

import java.util.Collection;
import java.util.Map;

public interface ISettings {
	ISettingType<?> getSettingType(Object settingType);
	Collection<ISettingType<?>> getSettingType(Object... settingType);
	Collection<ISettingType<?>> getSettingType(Collection<Object> settingType);
	Collection<ISettingType<?>> getSettingType();
	
	<V> V getSetting(ISettingType<V> settingType);
	Object getSetting(Object settingType);
	Collection<Object> getSetting(Object... settingType);
	Collection<Object> getSetting(Collection<Object> settingType);
	Map<ISettingType<?>, Object> getSetting();
}
