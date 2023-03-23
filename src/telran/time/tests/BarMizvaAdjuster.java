package telran.time.tests;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class BarMizvaAdjuster implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		Temporal res = temporal.plus(13, ChronoUnit.YEARS);
		return res;
	}

}