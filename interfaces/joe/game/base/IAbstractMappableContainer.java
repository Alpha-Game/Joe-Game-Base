package joe.game.base;

import java.util.Collection;

import joe.classes.identifier.IMappable;
import joe.classes.query.FailureCompoundResult;

public interface IAbstractMappableContainer<V extends IMappable> {
	boolean addObject(V objects);
	FailureCompoundResult<IMappable> addObject(IMappable... objects);
	<V1 extends IMappable> FailureCompoundResult<IMappable> addObject(Collection<V1> objects);
	
	V getObject(Object objects);
	Collection<V> getObject(Object... objects);
	Collection<V> getObject(Collection<Object> objects);
	Collection<V> getObject();
	
	V removeObject(Object objects);
	Collection<V> removeObject(Object... objects);
	Collection<V> removeObject(Collection<Object> objects);
	Collection<V> clear();
}
