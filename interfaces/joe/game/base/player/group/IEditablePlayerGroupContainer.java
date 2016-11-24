package joe.game.base.player.group;

import java.util.Collection;

import joe.classes.identifier.IMappable;
import joe.classes.query.FailureCompoundResult;

public interface IEditablePlayerGroupContainer {
	boolean addPlayerGroup(IMappable callingObject, IPlayerGroup objects);
	FailureCompoundResult<IPlayerGroup> addPlayerGroup(IMappable callingObject, IPlayerGroup... objects);
	<V1 extends IPlayerGroup> FailureCompoundResult<IPlayerGroup> addPlayerGroup(IMappable callingObject, Collection<V1> objects);
	
	IPlayerGroup removePlayerGroup(IMappable callingObject, Object objects);
	Collection<IPlayerGroup> removePlayerGroup(IMappable callingObject, Object... objects);
	Collection<IPlayerGroup> removePlayerGroup(IMappable callingObject, Collection<Object> objects);
	Collection<IPlayerGroup> clearPlayerGroups(IMappable callingObject);
}
