package joe.game.base.player;

import java.util.Collection;

public interface IPlayerContainer {
	IPlayer getPlayer(Object playerID);
	Collection<IPlayer> getPlayer(Object... playerIDs);
	Collection<IPlayer> getPlayer(Collection<Object> playerIDs);
	Collection<IPlayer> getPlayer();
}
