package joe.game.base.effect;


public interface ITimedEffect extends IEffect {
	/**
	 * How long until the effect expires
	 */
	long getDuration();
}
