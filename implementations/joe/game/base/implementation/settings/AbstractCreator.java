package joe.game.base.implementation.settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import joe.classes.identifier.IMappable;
import joe.game.base.settings.ICreator;
import joe.game.base.settings.ISettingType;

public abstract class AbstractCreator<V1, R1> extends EditableSettings implements ICreator<V1, R1> {
	private final String fIdentifier;
	private final Map<String, Object> fSettings;
	protected final Map<String, ISettingType<?>> fSettingTypes;
	
	protected AbstractCreator() {
		fIdentifier = IMappable.Mappable.generateUniqueIdentifier();
		fSettings = new HashMap<String, Object>();
		fSettingTypes = new HashMap<String, ISettingType<?>>();
	}
	
	protected AbstractCreator(String identifier) {
		fIdentifier = identifier;
		fSettings = new HashMap<String, Object>();
		fSettingTypes = new HashMap<String, ISettingType<?>>();
	}
	
	protected AbstractCreator(ICreator<?, ?> creator) {
		this();
		setSettingsFromCreator(creator);
	}
	
	protected AbstractCreator(String identifier, ICreator<?, ?> creator) {
		this(identifier);
		setSettingsFromCreator(creator);
	}
	
	protected void setSettingsFromCreator(ICreator<?, ?> creator) {
		for (Entry<ISettingType<?>, Object> setting : creator.getSetting().entrySet()) {
			ISettingType<?> type = getSettingType(setting.getKey().getIdentifier());
			if (type != null) {
				fSettings.put(type.getIdentifier(), setting.getValue());
			}
		}
	}

	@Override
	public String getIdentifier() {
		return fIdentifier;
	}

	@Override
	public abstract R1 create(V1 callingObject);
}
