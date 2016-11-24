package joe.game.base.implementation;

import java.util.Collection;

import joe.classes.identifier.IMappable;
import joe.classes.query.FailureCompoundResult;
import joe.game.base.IAbstractMappableContainer;
import joe.game.base.ITypedContainer;

public class TypedContainerWrapper<V extends IMappable> implements IAbstractMappableContainer<V> {
	private Class<V> fClassName;
	private ITypedContainer fContainer;
	
	public TypedContainerWrapper(Class<V> className, ITypedContainer container) {
		fClassName = className;
		fContainer = container;
	}

	public boolean addObject(IMappable objects) {
		return fContainer.addObject(fClassName, objects);
	}

	public FailureCompoundResult<IMappable> addObject(IMappable... objects) {
		return fContainer.addObject(fClassName, objects);
	}

	public <V1 extends IMappable> FailureCompoundResult<IMappable> addObject(Collection<V1> objects) {
		return fContainer.addObject(fClassName, objects);
	}

	public V getObject(Object objects) {
		return fContainer.getObject(fClassName, objects);
	}

	public Collection<V> getObject(Object... objects) {
		return fContainer.getObject(fClassName, objects);
	}

	public Collection<V> getObject(Collection<Object> objects) {
		return fContainer.getObject(fClassName, objects);
	}

	public Collection<V> getObject() {
		return fContainer.getObject(fClassName);
	}

	public V removeObject(Object objects) {
		return fContainer.removeObject(fClassName, objects);
	}

	public Collection<V> removeObject(Object... objects) {
		return fContainer.removeObject(fClassName, objects);
	}

	public Collection<V> removeObject(Collection<Object> objects) {
		return fContainer.removeObject(fClassName, objects);
	}
	
	public Collection<V> clear() {
		return fContainer.removeObject(fClassName);
	}
}
