package joe.game.base.action;

import java.util.Collection;

import joe.classes.identifier.IMappable;

public interface IActionableObject extends IMappable {
	boolean addAction(IMappable callingObject, IActionCreator<?>... actions);
	boolean addAction(IMappable callingObject, Collection<IActionCreator<?>> actions);
	
	boolean removeAction(IMappable callingObject, String... actionIDs);
	boolean removeAction(IMappable callingObject, IActionCreator<?>... actions);
	boolean removeAction(IMappable callingObject, Collection<Object> actionIDs);
	
	Collection<IAction> getActions(IActionType type);
	Collection<IAction> getCurrentActions();
	
	IAction getAction(String actionID);
	Collection<IAction> getActions(String... actionIDs);
	Collection<IAction> getActions(Collection<String> actionIDs);
	
	IActionType getActionType(String actionTypeID);
	Collection<IActionType> getActionTypes();
	Collection<IActionType> getActionTypes(String... actionTypeIDs);
	Collection<IActionType> getActionTypes(Collection<Object> actionTypeIDs);
}
