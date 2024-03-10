package bankingsoftware;

/**
 * Constants for months
 * @author Jia Wern Chong
 */
public enum Month {
    JANUARY(1),
    FEBRUARY(2),
    MARCH(3),
    APRIL(4),
    MAY(5),
    JUNE(6),
    JULY(7),
    AUGUST(8),
    SEPTEMBER(9),
    OCTOBER(10),
    NOVEMBER(11),
    DECEMBER(12);

    private int monthValue;

    /**
     * Month constant with a month value for each month
     * @param monthValue Month Value for each month
     */
    Month(int monthValue){
        this.monthValue = monthValue;
    }

    /**
     * Gets month value for the month
     * @return Month value of the month
     */
    public int getMonthValue(){
        return monthValue;
    }
}