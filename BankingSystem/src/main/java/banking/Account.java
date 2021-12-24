package banking;

/**
 * Abstract bank account class.<br>
 * <br>
 *
 * Private Variables:<br>
 * {@link #accountHolder}: AccountHolder<br>
 * {@link #accountNumber}: Long<br>
 * {@link #pin}: int<br>
 * {@link #balance}: double
 */
public abstract class Account extends AccountHolder implements AccountInterface {
	private AccountHolder accountHolder;
	private Long accountNumber;
	private int pin;
	private double balance;

	protected Account(AccountHolder accountHolder, Long accountNumber, int pin, double startingDeposit) {
		super(accountHolder.getIdNumber());
		this.accountHolder = accountHolder;
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.balance = startingDeposit;
	}

	public AccountHolder getAccountHolder() {
        return accountHolder;
	}

	public boolean validatePin(int attemptedPin) {
		// complete the function
		if(this.pin == attemptedPin)
			return true;
		else
			return false;
	}

	public double getBalance() {
        return balance;
	}

	public Long getAccountNumber() {
		// complete the function
        return accountNumber;
	}

	public void creditAccount(double amount) {
		// complete the function
		this.balance = this.balance + amount;
	}

	public boolean debitAccount(double amount) {
		// complete the function
		if(this.balance >= amount) {
			this.balance = this.balance - amount;
			return true;
		}else {
			return false;
		}
	}
}
