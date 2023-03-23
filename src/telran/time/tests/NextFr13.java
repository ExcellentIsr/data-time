package telran.time.tests;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class NextFr13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		Temporal res = temporal;

		res = ((LocalDate) res).getDayOfMonth() < 13 
				? res.plus(13 - ((LocalDate) res).getDayOfMonth(), ChronoUnit.DAYS)
				: res.minus(((LocalDate) res).getDayOfMonth() - 13, ChronoUnit.DAYS);

		do {
			res = res.plus(1, ChronoUnit.MONTHS);
		} while (((LocalDate) res).getDayOfWeek().toString() != "FRIDAY");
		return res;
	}

}
