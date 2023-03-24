package telran.time.application;

import java.time.*;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public class PrintCalendar {
	private static final String LANGUAGE_TAG = "en";
	private static final int YEAR_OFFSET = 9;
	private static final int WIDTH_FIELD = 4;
	private static Locale locale = Locale.forLanguageTag(LANGUAGE_TAG);
	private static DayOfWeek[] daysWeek = DayOfWeek.values();
	private static DayOfWeek firstDayWeek = DayOfWeek.MONDAY;

	public static void main(String[] args) {
		try {
			int monthYear[] = getMonthYear(args);
			firstDayWeek = getFirstDayWeek(args);
			restructurizeDaysWeek(firstDayWeek);
			printCalendar(monthYear[0], monthYear[1]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void restructurizeDaysWeek(DayOfWeek firstDayWeek) {
		for (int i = 0; i < daysWeek.length; i++) {
			daysWeek[i] = firstDayWeek.plus(i);
		}
	}

	private static DayOfWeek getFirstDayWeek(String[] args) throws Exception {
		return args.length < 2 ? getStandartFirstDayWeek(args) : getFirstDayWeekArgs(args);
	}

	private static DayOfWeek getFirstDayWeekArgs(String[] args) throws Exception {
		DayOfWeek res;
		try {
			res = DayOfWeek.valueOf(args[2].toUpperCase());
		} catch (Exception e) {
			throw new Exception("uncorrect first day of week");
		}
		return res;
	}

	private static DayOfWeek getStandartFirstDayWeek(String[] args) {
		return DayOfWeek.of(1);
	}

	private static void printCalendar(int month, int year) {
		printTitle(month, year);
		printWeekDays();
		printDates(month, year);
	}

	private static void printTitle(int month, int year) {
		System.out.printf("%s%d, %s\n", " ".repeat(YEAR_OFFSET), year,
				Month.of(month).getDisplayName(TextStyle.FULL, locale));
	}

	private static void printWeekDays() {
		System.out.print("  ");
		Arrays.stream(daysWeek).forEach(dw -> System.out.printf("%s ", dw.getDisplayName(TextStyle.SHORT, locale)));
		System.out.println();
	}

	private static void printDates(int month, int year) {
		int weekDayNumber = getFirstDay(month, year);
		int offset = getOffset(weekDayNumber);
		int nDays = YearMonth.of(year, month).lengthOfMonth();

		System.out.printf("%s", " ".repeat(offset));

		for (int date = 1; date <= nDays; date++) {
			System.out.printf("%4d", date);

			if (++weekDayNumber > 7) {
				System.out.println();
				weekDayNumber = 1;
			}
		}
	}

	private static int getOffset(int weekDayNumber) {
		return (weekDayNumber - 1) * WIDTH_FIELD;
	}

	private static int getFirstDay(int month, int year) {
		int res = 0;
		DayOfWeek firstDayMonth = LocalDate.of(year, month, 1).getDayOfWeek();
		for (DayOfWeek dayOfWeek : daysWeek) {
			if (firstDayMonth == dayOfWeek) {
				res = (dayOfWeek.getValue() + 1);
			}
		}
		return res > DayOfWeek.values().length ? res - DayOfWeek.values().length : res;
	}

	private static int[] getMonthYear(String[] args) throws Exception {
		return args.length == 0 ? getCurrentMonthYear() : getMonthYearArgs(args);
	}

	private static int[] getCurrentMonthYear() {
		LocalDate current = LocalDate.now();
		return new int[] { current.getMonth().getValue(), current.getYear() };
	}

	private static int[] getMonthYearArgs(String[] args) throws Exception {
		return new int[] { getMonthArgs(args), getYearArgs(args) };
	}

	private static int getYearArgs(String[] args) throws Exception {
		int res = LocalDate.now().getYear();
		if (args.length > 1) {
			try {
				res = Integer.parseInt(args[1]);
				if (res < 0) {
					throw new Exception("year must be a positive number");
				}
			} catch (NumberFormatException e) {
				throw new Exception("year must be a number");
			}
		}
		return res;
	}

	private static int getMonthArgs(String[] args) throws Exception {
		try {
			int res = Integer.parseInt(args[0]);
			if (res < 1 || res > 12) {
				throw new Exception("Month should be a number in the range [1-12]");
			}
			return res;
		} catch (NumberFormatException e) {
			throw new Exception("Month should be a number");
		}
	}
}