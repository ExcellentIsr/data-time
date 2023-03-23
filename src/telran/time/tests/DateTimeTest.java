package telran.time.tests;

import static org.junit.Assert.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class DateTimeTest {

	@BeforeEach
	void setUp() throws Exception {

	}

	@Disabled
	@Test
	void localDateTest() {
		LocalDate birthDateAS = LocalDate.parse("1799-06-06");
		LocalDate barMizvaAS = birthDateAS.plusYears(13);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM, YYYY, d", Locale.ENGLISH);
		System.out.println(barMizvaAS.format(dtf));

		ChronoUnit unit = ChronoUnit.DAYS;
		System.out.printf("Number of %s bettwen %s and %s is %d", unit, birthDateAS, barMizvaAS,
				unit.between(birthDateAS, barMizvaAS));
	}

	@Test
	void barMizvaTest() {
		LocalDate current = LocalDate.now();

		assertEquals(current.plusYears(13), current.with(new BarMizvaAdjuster()));
	}

	@Test
	void displayCurrentDateTimeInTimezonesOfCanada() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm:ss | VV | zzzz | OOOO", Locale.CANADA);

		ZoneId.getAvailableZoneIds().stream().filter(zoneId -> zoneId.toLowerCase().contains("canada")).map(ZoneId::of)
				.map(item -> LocalDateTime.now(item).atZone(item).format(dtf)).sorted().forEach(System.out::println);
		System.out.println();
	}

	@Test
	void nextFr13Test() {
		LocalDate dateTime = LocalDate.of(2023, 3, 23);
		LocalDate dateTime1 = LocalDate.of(2022, 1, 1);

		System.out.println(LocalDate.of(2023, 10, 13) + "  " + LocalDate.of(2023, 10, 13).getDayOfWeek());
		System.out.println(LocalDate.of(2022, 5, 13) + "  " + LocalDate.of(2022, 5, 13).getDayOfWeek());
		System.out.println();

		assertEquals(LocalDate.of(2023, 10, 13), dateTime.with(new NextFr13()));
		assertEquals(LocalDate.of(2023, 10, 13).getDayOfWeek(), dateTime.with(new NextFr13()).getDayOfWeek());

		assertEquals(LocalDate.of(2022, 5, 13), dateTime1.with(new NextFr13()));
		assertEquals(LocalDate.of(2022, 5, 13).getDayOfWeek(), dateTime1.with(new NextFr13()).getDayOfWeek());
	}

	@Test
	void workingDaysTest() {
		LocalDate dateTime = LocalDate.of(2023, 3, 23);
		LocalDate dateTime1 = LocalDate.now();

		DayOfWeek[] daysOff = new DayOfWeek[] { DayOfWeek.FRIDAY, DayOfWeek.SATURDAY };
		DayOfWeek[] daysOff1 = new DayOfWeek[] { DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
				DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY };

		System.out.println(LocalDate.of(2023, 3, 26) + "  " + LocalDate.of(2023, 3, 26).getDayOfWeek());
		System.out.println();

		assertEquals(LocalDate.of(2023, 3, 23), dateTime.with(new WorkingDays(daysOff)));
		assertEquals(LocalDate.of(2023, 3, 23).getDayOfWeek(), dateTime.with(new WorkingDays(daysOff)).getDayOfWeek());

		assertEquals(LocalDate.now(), dateTime1.with(new WorkingDays(daysOff1)));
		assertEquals(LocalDate.now().getDayOfWeek(), dateTime1.with(new WorkingDays(daysOff1)).getDayOfWeek());
	}
}
