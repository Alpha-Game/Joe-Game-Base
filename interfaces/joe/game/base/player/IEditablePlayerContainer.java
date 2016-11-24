package joe.game.base.player;

import java.util.Collection;

import joe.classes.identifier.IMappable;
import joe.classes.query.FailureCompoundResult;

public interface IEditablePlayerContainer extends IPlayerContainer {
	boolean addPlayer(IMappable callingObject, IPlayer objects);
	FailureCompoundResult<IPlayer> addPlayer(IMappable callingObject, IPlayer... objects);
	<V1 extends IPlayer> FailureCompoundResult<IPlayer> addPlayer(IMappable callingObject, Collection<V1> objects);
	
	IPlayer removePlayer(IMappable callingObject, Object objects);
	Collection<IPlayer> removePlayer(IMappable callingObject, Object... objects);
	Collection<IPlayer> removePlayer(IMappable callingObject, Collection<Object> objects);
	Collection<IPlayer> clearPlayers(IMappable callingObject);
}
