package joe.game.base.settings;

import java.util.Collection;

import joe.classes.identifier.IMappable;

public interface ICreator<V1, R1> extends IMappable, ISettings {
	Object setSetting(Object settingType, Object setting);
	Object removeSetting(Object settingType);
	Collection<Object> removeSetting(Object... settingType);
	Collection<Object> removeSetting(Collection<Object> settingType);
	
	void clearSettings();
	
	R1 create(V1 callingObject);
}
