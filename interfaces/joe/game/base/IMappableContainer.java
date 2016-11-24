package joe.game.base;

import java.util.Collection;

import joe.classes.identifier.IMappable;
import joe.classes.query.FailureCompoundResult;

public interface IMappableContainer {
	boolean addObject(IMappable callingObject, IMappable objects);
	FailureCompoundResult<IMappable> addObject(IMappable callingObject, IMappable... objects);
	<IMappable1 extends IMappable> FailureCompoundResult<IMappable> addObject(IMappable callingObject, Collection<IMappable1> objects);
	
	IMappable getObject(Object objects);
	Collection<IMappable> getObject(Object... objects);
	Collection<IMappable> getObject(Collection<Object> objects);
	Collection<IMappable> getObject();
	
	IMappable removeObject(IMappable callingObject, Object objects);
	Collection<IMappable> removeObject(IMappable callingObject, Object... objects);
	Collection<IMappable> removeObject(IMappable callingObject, Collection<Object> objects);
	Collection<IMappable> clear(IMappable callingObject);
}
