package bankingsoftware;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Date class that creates a date object
 * Has methods to compare 2 dates, to get today's date, check if is leap year, check if date is a valid calendar date, check if 2 dates are the same
 * @author Jia Wern Chong
 */

public class Date implements Comparable<Date> {

    public static final int MAX_MONTHS = 12;
    public static final int MIN_MONTHS = 1;

    public static final int MIN_DAYS = 1;
    public static final int MONTH_WITH_30DAYS = 30;
    public static final int MONTH_WITH_31DAYS = 31;
    public static final int DAYS_IN_FEB_NOT_LEAP_YEAR = 28;
    public static final int DAYS_IN_FEB_LEAP_YEAR = 29;

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    private int year;
    private int month;
    private int day;

    /**
     * Constructor that takes in string in the form of "mm/dd/yy" and separate dates into individual categories
     * @param date
     */
    public Date(String date) {
        StringTokenizer dateToken = new StringTokenizer(date, "/"); // Creates new StringTokenizer object to separate dates into tokens
        while (dateToken.hasMoreTokens()){ // Iterates through the tokens and cast string into integers for the dates
            month = Integer.parseInt(dateToken.nextToken());
            day = Integer.parseInt(dateToken.nextToken());
            year = Integer.parseInt(dateToken.nextToken());
        }
    }

    /**
     * Creates a date with today's date
     */
    public Date() {
        Calendar todayDate = Calendar.getInstance(); //Creates a calendar object to get today's date
        month = todayDate.get(Calendar.MONTH) + 1; // Get the current month from Calendar class
        day = todayDate.get(Calendar.DAY_OF_MONTH); // Get the current day from Calendar class
        year = todayDate.get(Calendar.YEAR); // Get the current year from Calendar class
    }

    /**
     * Compares 2 dates
     */
    @Override
    public int compareTo(Date other) {
        // Compare years
        int yearComparison = this.year - other.getYear();
        if (yearComparison != 0) {
            return yearComparison;
        }
        // Compare months
        int monthComparison = this.month - other.getMonth();
        if(monthComparison != 0) {
            return monthComparison;
        }

        // Compare days
        int dayComparison = this.day - other.getDay();
        return dayComparison;
    }

    /**
     * Returns today's date
     * @return date
     */
    public static Date today () {
        return new Date();
    }

    /**
     * Check if year is a leap year or not
     * @return boolean
     */
    public boolean isLeapYear() { // Method to check if it is a leap year
        if(year%QUADRENNIAL == 0){
            if(year%CENTENNIAL == 0){
                if(year%QUATERCENTENNIAL == 0){
                    return true; // If year is divisible by 4, 100 and 400, it's a leap year, returns true
                } else { return false;} // If year is divisible by 100 but not 400, it is not a leap year, returns false
            } else { return true; } // If year is only divisible by 4 and not divisible by 100, it is a leap year, returns true
        } else { return false; } // If year is not divisible by 4, it is not a leap year, returns false
    }

    /**
     * Check if a given date is a valid calendar date
     * @return boolean
     */
    public boolean isValid() {

        int month = getMonth();
        int day = getDay();

        int jan = Month.JANUARY.getMonthValue();
        int feb = Month.FEBRUARY.getMonthValue();
        int mar = Month.MARCH.getMonthValue();
        int apr = Month.APRIL.getMonthValue();
        int may = Month.MAY.getMonthValue();
        int june = Month.JUNE.getMonthValue();
        int july = Month.JULY.getMonthValue();
        int aug = Month.AUGUST.getMonthValue();
        int sept = Month.SEPTEMBER.getMonthValue();
        int oct = Month.OCTOBER.getMonthValue();
        int nov = Month.NOVEMBER.getMonthValue();
        int dec = Month.DECEMBER.getMonthValue();

        if(month >= MIN_MONTHS && month <= MAX_MONTHS){ // Checks if month is a valid month between 1(January) - 12(December)
            if(day >= MIN_DAYS) { // Checks if day is valid (>= 1) and not 0 or negative integer
                if (month == jan || month == mar || month == may || month == july ||
                        month == aug || month == oct || month == dec) {
                    if (day <= MONTH_WITH_31DAYS) {
                        return true; // If day is between 1-31 days, day is valid, return true
                    }

                } else if (month == apr || month == june || month == sept || month == nov) { // Check if month is a month with 30 days
                    if (day <= MONTH_WITH_30DAYS) {
                        return true; // If day is between 1-30 days, day is valid, return true
                    }

                } else if (month == feb) { //Check if month is February
                    if (isLeapYear() && day <= DAYS_IN_FEB_LEAP_YEAR) {
                        return true; // If it is a leap year and day is between 1-29, day is valid, return true
                    } else if (day <= DAYS_IN_FEB_NOT_LEAP_YEAR){
                        return true; // If it is not leap year and day is between 1-28, day is valid, return true
                    }
                } return false; // If any field is incorrect
            } return false; // If day is 0 or a negative integer, day is invalid, return false
        } return false; // If month is not a valid month between 1(January) - 12(December), return false
    }

    /**
     * Returns the textual representation of a Date object
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    /**
     * Getter method for day of a date
     * @return day
     */
    public int getDay() {
        return day;
    }

    /**
     * Getter method for month of a date
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Getter method for year of a date
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     *  Check if two dates are the same
     * @param obj Object to be compared to
     */
    @Override
    public boolean equals (Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Date other = (Date) obj;
        return ((getMonth() == other.getMonth()) && (getDay() == other.getDay())
                && (getYear() == other.getYear()));
    }

    /**
     *  Testbed main to exercise the isValid method.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        testDays_InFebNonLeapInvalid();
        testDays_InFebNonLeapValid();
        testDays_InFebLeapInvalid();
        testDays_InFebLeapValid();
        testDays_InMonthWith31DaysInvalid();
        testDays_InMonthWith31DaysValid();
        testDays_InMonthWith30DaysInvalid();
        testDays_InMonthWith30DaysValid();
        testDay_LessThan1Day();
        testMonth_MoreThan12Months();
        testMonth_LessThan1Month();
    }

    /**
     * Test case #1 for isValid(): Test Feb non leap year, date is invalid
     */
    private static void testDays_InFebNonLeapInvalid() {
        Date date = new Date("2/29/2011");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #1: # of days in Feb in a non-leap year is 28");
        testResult(expectedOutput, actualOutput);
    }

    /**
     * Test case #2 for isValid(): Test Feb non leap year, date is valid
     */
    private static void testDays_InFebNonLeapValid() {
        Date date = new Date("2/28/2011");
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #2: # of days in Feb in a non-leap year is 28");
        testResult(expectedOutput, actualOutput);
    }

    /**
     * Test case #3 for isValid(): Test Feb leap year, date is invalid
     */
    private static void testDays_InFebLeapInvalid() {
        Date date = new Date("2/30/2000");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #3: # of days in Feb. in a leap year is 29");
        testResult(expectedOutput, actualOutput);
    }

    /**
     * Test case #4 for isValid(): Test Feb leap year, date is valid
     */
    private static void testDays_InFebLeapValid() {
        Date date = new Date("2/29/2000");
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #4: # of days in Feb. in a leap year is 29");
        testResult(expectedOutput, actualOutput);
    }

    /**
     * Test case #5 for isValid(): Test days for month with 31 days, date is more than 31 days
     */
    private static void testDays_InMonthWith31DaysInvalid() {
        Date date = new Date("3/32/2011");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #5: More than 31 days for month with 31 days");
        testResult(expectedOutput, actualOutput);
    }

    /**
     * Test case #6 for isValid(): Test days for month with 31 days, less than or equals 31 days
     */
    private static void testDays_InMonthWith31DaysValid() {
        Date date = new Date("3/31/2011");
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #6: Less than or equals to 31 days for month with 31 days");
        testResult(expectedOutput, actualOutput);
    }

    /**
     * Test case #7 for isValid(): Test days for month with 30 days, date is more than 30 days
     */
    private static void testDays_InMonthWith30DaysInvalid() {
        Date date = new Date("6/31/2011");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #7: More than 30 days for month with 30 days");
        testResult(expectedOutput, actualOutput);
    }

    /**
     * Test case #8 for isValid(): Test days for month with 30 days, less than or equals 30 days
     */
    private static void testDays_InMonthWith30DaysValid() {
        Date date = new Date("6/30/2011");
        boolean expectedOutput = true;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #8: Less than or equals to 30 days for month with 30 days");
        testResult(expectedOutput, actualOutput);
    }

    /**
     * Test case #9 for isValid(): Test days, less than 1 day
     */
    private static void testDay_LessThan1Day() {
        Date date = new Date("4/0/2011");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #9: Days less than 1 day");
        testResult(expectedOutput, actualOutput);
    }

    /**
     * Test case #10 for isValid(): Test month more than 12 months
     */
    private static void testMonth_MoreThan12Months() {
        Date date = new Date("13/26/2011");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #10: Months more than 12 months");
        testResult(expectedOutput, actualOutput);
    }

    /**
     * Test case #11 for isValid(): Test month less than 1 month
     */
    private static void testMonth_LessThan1Month() {
        Date date = new Date("0/26/2011");
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        System.out.println("**Test case #11: Months less than 1 month");
        testResult(expectedOutput, actualOutput);
    }

    /**
     * Check if a given test case PASS or FAIL
     */
    private static void testResult(boolean expectedOutput, boolean actualOutput) {

        if (expectedOutput == actualOutput) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL");
        }
    }
}
