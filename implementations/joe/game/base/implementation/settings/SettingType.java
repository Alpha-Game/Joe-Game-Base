package joe.game.base.implementation.settings;

import joe.classes.identifier.AbstractNamedTypedIdentifier;
import joe.classes.identifier.INamable;
import joe.game.base.settings.ISettingType;

public class SettingType<V1> extends AbstractNamedTypedIdentifier<V1> implements ISettingType<V1>, INamable {	
	public SettingType(String identifier, String nameIdentifier, Class<V1> type) {
		super(identifier, nameIdentifier, type);
	}
}
