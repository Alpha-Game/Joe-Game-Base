package joe.game.base.implementation;

import java.util.Collection;

import joe.classes.identifier.IMappable;
import joe.classes.query.FailureCompoundResult;
import joe.game.base.IAbstractMappableContainer;
import joe.game.base.IMappableContainer;
import joe.game.base.ITypedContainer;

public class MappableContainer implements IMappableContainer {
	private IAbstractMappableContainer<IMappable> fContainer;
	
	public MappableContainer(ITypedContainer container) {
		fContainer = new TypedContainerWrapper<IMappable>(IMappable.class, container);
	}

	@Override
	public boolean addObject(IMappable callingObject, IMappable objects) {
		return fContainer.addObject(objects);
	}

	@Override
	public FailureCompoundResult<IMappable> addObject(IMappable callingObject, IMappable... objects) {
		return fContainer.addObject(objects);
	}

	@Override
	public <V1 extends IMappable> FailureCompoundResult<IMappable> addObject(IMappable callingObject, Collection<V1> objects) {
		return fContainer.addObject(objects);
	}

	@Override
	public IMappable getObject(Object objects) {
		return fContainer.getObject(objects);
	}

	@Override
	public Collection<IMappable> getObject(Object... objects) {
		return fContainer.getObject(objects);
	}

	@Override
	public Collection<IMappable> getObject(Collection<Object> objects) {
		return fContainer.getObject(objects);
	}

	@Override
	public Collection<IMappable> getObject() {
		return fContainer.getObject();
	}

	@Override
	public IMappable removeObject(IMappable callingObject, Object objects) {
		return fContainer.removeObject(objects);
	}

	@Override
	public Collection<IMappable> removeObject(IMappable callingObject, Object... objects) {
		return fContainer.removeObject(objects);
	}

	@Override
	public Collection<IMappable> removeObject(IMappable callingObject, Collection<Object> objects) {
		return fContainer.removeObject(objects);
	}

	@Override
	public Collection<IMappable> clear(IMappable callingObject) {
		return fContainer.clear();
	}
}
