package telran.time.tests;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DateTimeTest {

	@BeforeEach
	void setUp() throws Exception{
		
	}
	
	@Test
	void localDateTest() {
		LocalDate birthDateAS = LocalDate.parse("1799-06-06");
		LocalDate barMizvaAS = birthDateAS.plusYears(13);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM, YYYY, d", Locale.ENGLISH);
		System.out.println(barMizvaAS.format(dtf));
		
		ChronoUnit unit = ChronoUnit.DAYS;
		System.out.printf("Number of %s bettwen %s and %s is %d", unit, birthDateAS, barMizvaAS, unit.between(birthDateAS, barMizvaAS));
	}

	@Test
	void barMizvaTest() {
		LocalDate current = LocalDate.now();
		
		assertEquals(current.plusYears(13), current.with(new BarMizvaAdjuster()));
	}
	
	@Test
	void displayCurrentDateTimeInTimezonesOfCanada() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss VV", Locale.CANADA);
		
		ZoneId.getAvailableZoneIds().stream()
		.filter(zoneId -> zoneId.toLowerCase().contains("canada"))
		.map(ZoneId::of)
		.map(item -> LocalDateTime.now(item).atZone(item).format(dtf))
		.sorted()
		.forEach(System.out::println);
	}

}
