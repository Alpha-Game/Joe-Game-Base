package joe.game.base.implementation;

import java.util.ArrayList;
import java.util.Collection;

import joe.classes.base.TypedMapByValue;
import joe.classes.identifier.IMappable;
import joe.classes.query.FailureCompoundResult;
import joe.game.base.ITypedContainer;

public class TypedContainer implements ITypedContainer {
	private TypedMapByValue<String, IMappable> fMap;
	
	public TypedContainer() {
		fMap = new TypedMapByValue<String, IMappable>(IMappable.class);
	}

	@Override
	public <V1 extends IMappable> boolean addObject(Class<V1> type, IMappable objects) {
		if (type.isInstance(objects)) {
			fMap.put(objects.getIdentifier(), objects);
			return true;
		}
		return false;		
	}

	@Override
	public <V1 extends IMappable> FailureCompoundResult<IMappable> addObject(Class<V1> type, IMappable... objects) {
		FailureCompoundResult<IMappable> result = new FailureCompoundResult<IMappable>();
		for (IMappable object : objects) {
			result.addResult(object, addObject(type, object));
		}
		return result;
	}

	@Override
	public <V1 extends IMappable, V2 extends IMappable> FailureCompoundResult<IMappable> addObject(Class<V1> type, Collection<V2> objects) {
		FailureCompoundResult<IMappable> result = new FailureCompoundResult<IMappable>();
		for (IMappable object : objects) {
			result.addResult(object, addObject(type, object));
		}
		return result;
	}

	@Override
	public <V1 extends IMappable> V1 getObject(Class<V1> type, Object objects) {
		Object value = fMap.get(objects);
		if (type.isInstance(value)) {
			return type.cast(value);
		}
		return null;
	}

	@Override
	public <V1 extends IMappable> Collection<V1> getObject(Class<V1> type, Object... objects) {
		Collection<V1> result = new ArrayList<V1>(objects.length);
		for(Object key : objects) {
			result.add(getObject(type, key));
		}
		return result;
	}

	@Override
	public <V1 extends IMappable, V2> Collection<V1> getObject(Class<V1> type, Collection<V2> objects) {
		Collection<V1> result = new ArrayList<V1>(objects.size());
		for(Object key : objects) {
			result.add(getObject(type, key));
		}
		return result;
	}

	@Override
	public <V1 extends IMappable> Collection<V1> getObject(Class<V1> type) {
		return fMap.values(type);
	}

	@Override
	public <V1 extends IMappable> V1 removeObject(Class<V1> type, Object objects) {
		if (type.isInstance(fMap.get(objects))) {
			return type.cast(fMap.remove(objects));
		}
		return null;
	}

	@Override
	public <V1 extends IMappable> Collection<V1> removeObject(Class<V1> type, Object... objects) {
		Collection<V1> result = new ArrayList<V1>(objects.length);
		for(Object key : objects) {
			result.add(removeObject(type, key));
		}
		return result;
	}

	@Override
	public <V1 extends IMappable, V2> Collection<V1> removeObject(Class<V1> type, Collection<V2> objects) {
		Collection<V1> result = new ArrayList<V1>(objects.size());
		for(Object key : objects) {
			result.add(removeObject(type, key));
		}
		return result;
	}

	@Override
	public <V1 extends IMappable> Collection<V1> removeObject(Class<V1> type) {
		Collection<V1> oldValues = fMap.values(type);
		for (V1 value : oldValues) {
			fMap.remove(value);
		}
		return oldValues;
	}
}
