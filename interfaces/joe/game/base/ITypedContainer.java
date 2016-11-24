package joe.game.base;

import java.util.Collection;

import joe.classes.identifier.IMappable;
import joe.classes.query.FailureCompoundResult;

public interface ITypedContainer {
	<V1 extends IMappable> boolean addObject(Class<V1> type, IMappable objects);
	<V1 extends IMappable> FailureCompoundResult<IMappable> addObject(Class<V1> type, IMappable... objects);
	<V1 extends IMappable, V2 extends IMappable> FailureCompoundResult<IMappable> addObject(Class<V1> type, Collection<V2> objects);
	
	<V1 extends IMappable> V1 getObject(Class<V1> type, Object objects);
	<V1 extends IMappable> Collection<V1> getObject(Class<V1> type, Object... objects);
	<V1 extends IMappable, V2> Collection<V1> getObject(Class<V1> type, Collection<V2> objects);
	<V1 extends IMappable> Collection<V1> getObject(Class<V1> type);
	
	<V1 extends IMappable> V1 removeObject(Class<V1> type, Object objects);
	<V1 extends IMappable> Collection<V1> removeObject(Class<V1> type, Object... objects);
	<V1 extends IMappable, V2> Collection<V1> removeObject(Class<V1> type, Collection<V2> objects);
	<V1 extends IMappable> Collection<V1> removeObject(Class<V1> type);
}
