package bankingsoftware;

/**
 * Money Market class that extends savings.
 * It has its own interest fee and monthly fee.
 *
 * @author Jia Wern Chong
 */
public class MoneyMarket extends Savings {
    private final static double INTEREST_RATE_NON_LOYAL = 0.045;
    private final static double INTEREST_RATE_LOYAL = 0.0475;
    private final static double MONTHLY_FEE = 25.0;
    private final static double WITHDRAWAL_FEE = 10.0;
    private int withdrawal;

    /**
     * Initializes Money Market object by calling on its super class and adds
     * its own parameter of withdrawal
     *
     * @param holder
     * @param balance
     * @param withdrawal
     */
    public MoneyMarket(Profile holder, double balance, boolean isLoyal, int withdrawal) {
        super(holder, balance, isLoyal);
        this.withdrawal = withdrawal;
    }

    /**
     * Overloaded constructor for depositing and withdrawal methods.
     *
     * @param holder Profile of account holder
     * @param balance Balance to deposit/withdraw
     */
    public MoneyMarket(Profile holder, double balance) {
        super(holder, balance);
    }

    /**
     * Applies monthly interest to account balance based on loyalty
     * @return New balance with accured monthly interest
     */
    @Override
    public double monthlyInterest() {
        if (isLoyal) {
            return balance += getInterest();
        } else {
            return balance += getInterest();
        }
    }

    /**
     * Charges monthly fee if balance is less than 2000.0
     * Charges withdrawal fee if number of withdrawal is more than 3
     * @return New balance with monthly fee and/or withdrawal fee is taken out of account (if applicable)
     */
    @Override
    public double monthlyFee() {
        if (balance >= 2000.0) {
            if (withdrawal > 3) {
                return balance -= WITHDRAWAL_FEE;
            } else {
                return balance;
            }
        } else {
            if (withdrawal > 3) {
                balance -= WITHDRAWAL_FEE;
                return balance -= MONTHLY_FEE;
            } else {
                return balance -= MONTHLY_FEE;
            }
        }
    }

    /**
     * Gets monthly fee of account
     * @return monthly fee
     */
    @Override
    public double getMonthlyFee() {
        boolean overWithdrawal = withdrawal > 3;

        if (balance >= 2000.0){
            return overWithdrawal ? 10.0 : 0.0;
        } else {
            return overWithdrawal ? 35.0 : 25.0;
        }
    }

    /**
     * Gets monthly interest of account
     * @return monthly interest
     */
    @Override
    public double getInterest() {
        if (isLoyal) {
            return (INTEREST_RATE_LOYAL * balance) / 12;
        } else {
            return (INTEREST_RATE_NON_LOYAL * balance) / 12;
        }
    }

    /**
     * Sets number of withdrawals of an account
     * @return withdrawal
     */
    public void setWithdrawal(int withdrawNum) {
        this.withdrawal = withdrawNum;
    }

    /**
     * Gets number of withdrawals of an account
     * @return withdrawal
     */
    public int getWithdrawal() {
        return withdrawal;
    }

    /**
     * Getter method to return initial of account type.
     * 
     * @return Returns "MM" for MoneyMarket
     */
    @Override
    public String getAccountTypeInitial() {
        return "MM";
    }

    /**
     * Getter method to return full name of account type.
     * 
     * @return Returns "Money Market"
     */
    @Override
    public String getAccountType() {
        return "Money Market";
    }

    /**
     * Returns a string for the account with all its information
     * 
     * @return String to represent the account
     */
    @Override
    public String toString() {
        String balanceString = isLoyal ? String.format("Balance $%,.2f::is loyal", balance) : String.format("Balance $%,.2f", balance);
        return String.format("Money Market::Savings::%s %s %s::%s::withdrawal: %d", holder.getFname(), holder.getLname(), holder.getDOB().toString(), balanceString, withdrawal);
    }
}
