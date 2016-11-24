package joe.game.base.implementation.conditions;

public class TimedCondition extends AbstractCondition {
	private long fRemainingTime;
	
	private static String createIdentifier(long time) {
		return "time >= " + time;
	}
	
	public TimedCondition(long time) {
		super(createIdentifier(time));
		fRemainingTime = time;
	}

	@Override
	public void update(long time) {
		if (fRemainingTime > 0) {
			fRemainingTime -= time;
		}
	}

	@Override
	public boolean isTrue() {
		return fRemainingTime <= 0;
	}
}
