package bankingsoftware;

/**
 * Checking class that extends account type.
 * It has its own interest fee and monthly rate.
 * 
 * @author Frances Cortuna
 */
public class Checking extends Account {
    private final static double INTEREST_RATE = 0.01;
    private final static double MONTHLY_FEE = 12.0;

    /**
     * Parameterized constructor that initializes a Checking object with holder and
     * balance
     * 
     * @param holder  Account owner
     * @param balance Balance of account
     */
    public  Checking(Profile holder, double balance) {
        super(holder, balance);
    }

    /**
     * Applies monthly interest to account balance
     * 
     * @return New balance with accrued monthly interest
     */
    public double monthlyInterest() {
        return balance += getInterest();
    }

    /**
     * Takes out monthly fee if balance is less than 1000
     * 
     * @return New balance with monthly fee taken out of account (if applicable)
     */
    public double monthlyFee() {
        if (balance >= 1000.0) { // Monthly fee is waived if balance is >= 1000
            return balance;
        }
        return balance -= MONTHLY_FEE;
    }

    /**
     * Getter method to return initial of account type.
     * 
     * @return Returns "C" for checking
     */
    public String getAccountTypeInitial() {
        return "C";
    }

    /**
     * Getter method to return full name of account type.
     * 
     * @return Returns "Checking"
     */
    public String getAccountType() {
        return "Checking";
    }

    /**
     * Method that is a getter method to return monthly interest of account type
     * 
     * @return Monthly interest amount
     */
    public double getInterest() {
        return (INTEREST_RATE * balance) / 12;
    }

    /**
     * Method that is a getter method to return monthly fee for account type
     * 
     * @return Monthly fee amount
     */
    public double getMonthlyFee() {
        if(balance >= 1000) {
            return 0.0;
        }
        return MONTHLY_FEE;
    }

    /**
     * Returns a string for the account with all its information
     * 
     * @return String to represent the account
     */
    public String toString() {
        return String.format("Checking::%s %s %s::Balance $%,.2f", holder.getFname(), holder.getLname(), holder.getDOB().toString(), balance);
    }

    /**
     * Returns a string for the account with all its information
     * 
     * @return String to represent the account
     */
    public String toStringFeesInterest() {
        return toString() + String.format("::fee $%,.2f::monthly interest $%,.2f", getMonthlyFee(), getInterest());
    }
}
