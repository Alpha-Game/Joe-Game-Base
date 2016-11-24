package joe.game.base.player.group;

import java.util.Collection;

public interface IPlayerGroupContainer {
	IPlayerGroup getPlayerGroup(Object playerID);
	Collection<IPlayerGroup> getPlayerGroup(Object... playerIDs);
	Collection<IPlayerGroup> getPlayerGroup(Collection<Object> playerIDs);
	Collection<IPlayerGroup> getPlayerGroup();
}
