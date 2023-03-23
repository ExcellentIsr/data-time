package telran.time.tests;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;

public class WorkingDays implements TemporalAdjuster {
	DayOfWeek[] daysOff;

	public WorkingDays(DayOfWeek[] daysOff) {
		this.daysOff = daysOff;
	}

	@Override
	public Temporal adjustInto(Temporal temporal) {
		Temporal res = temporal;

		if (daysOff.length != 7) {
			do {
				res.plus(1, ChronoUnit.DAYS);
			} while (Arrays.asList(daysOff).contains(((LocalDate) res).getDayOfWeek()));
		}
		return res;
	}

}
