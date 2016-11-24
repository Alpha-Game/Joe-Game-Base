package joe.game.base.action;

import joe.game.base.settings.ICreator;

public interface IActionCreator<R extends IAction> extends ICreator<IActionableObject, R> {
	
}
