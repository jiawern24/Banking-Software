package bankingsoftware;

/**
 * Savings class that extends account type.
 * It has its own interest fee and monthly rate.
 *
 * @author Jia Wern Chong
 */
public class Savings extends Account{
    private final static double INTEREST_RATE_NON_LOYAL = 0.04;
    private final static double INTEREST_RATE_LOYAL = 0.0425;
    private final static double MONTHLY_FEE = 25.0;
    protected boolean isLoyal;

    /**
     * Parameterized constructor that initializes a Savings object with holder and
     * balance
     *
     * @param holder  Account owner
     * @param balance Balance of account
     * @param isLoyal Loyal status of account
     */
    public Savings(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance);
        this.isLoyal = isLoyal;
    }

    /**
     * Overloaded constructor for depositing and withdrawal methods.
     *
     * @param holder Profile of account holder
     * @param balance Balance to deposit/withdraw
     */
    public Savings(Profile holder, double balance) {
        super(holder, balance);
    }

    /**
     * Applies monthly interest to account balance
     *
     * @return New balance with accrued monthly interest
     */
    public double monthlyInterest() {
        if (isLoyal) {
            return balance += getInterest();
        } else {
            return balance += getInterest();
        }
    }
    /**
     * Charges monthly fee if balance is less than 500.0
     *
     * @return New balance with monthly fee taken out of account (if applicable)
     */
    public double monthlyFee() {
        if (balance >= 500.0) {
            return balance;
        } else {
            return balance -= MONTHLY_FEE;
        }
    }

    /**
     * Gets monthly interest of account
     * @return monthly interest
     */
    public double getInterest() {
        if (isLoyal) {
            return (INTEREST_RATE_LOYAL * balance) / 12;
        } else {
            return (INTEREST_RATE_NON_LOYAL * balance) / 12;
        }
    }

    /**
     * Gets monthly fee of account
     * @return monthly fee
     */
    public double getMonthlyFee() {
        if(balance >= 500.0) {
            return 0.0;
        }
        return MONTHLY_FEE;
    }

    /**
     * Gets loyalty status of account
     * @return True or false
     */
    public boolean getLoyaltyStatus() {
        return isLoyal;
    }

    /**
     * Getter method to return initial of account type.
     * 
     * @return Returns "C" for checking
     */
    public String getAccountTypeInitial() {
        return "S";
    }

    /**
     * Getter method to return full name of account type.
     * 
     * @return Returns "Checking"
     */
    public String getAccountType() {
        return "Savings";
    }

    /**
     * Returns a string for the account with all its information
     * 
     * @return String to represent the account
     */
    public String toString() {
        String balanceString = isLoyal ? String.format("Balance $%,.2f::is loyal", balance) : String.format("Balance $%,.2f", balance);
        return String.format("Savings::%s %s %s::%s", holder.getFname(), holder.getLname(), holder.getDOB().toString(), balanceString);
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
