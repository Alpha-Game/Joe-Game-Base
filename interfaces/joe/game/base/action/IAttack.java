package joe.game.base.action;

import joe.game.base.effect.IHealthDamageType;

public interface IAttack extends IAction {
	IHealthDamageType getDamageType();
}
