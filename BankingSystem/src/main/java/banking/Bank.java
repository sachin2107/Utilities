package banking;

import java.util.LinkedHashMap;

/**
 * Private Variables:<br>
 * {@link #accounts}: List&lt;Long, Account&gt;
 */
public class Bank implements BankInterface {
	private LinkedHashMap<Long, Account> accounts;

	public Bank() {
		// complete the function
		this.accounts = new LinkedHashMap<>();
	}

	private Account getAccount(Long accountNumber) {
        return accounts.get(accountNumber);
	}

	public Long openCommercialAccount(Company company, int pin, double startingDeposit) {
		// complete the function
		Long idNumber = Long.valueOf(company.getIdNumber());
		Account account = new CommercialAccount(company, idNumber, pin, startingDeposit);
		accounts.put(idNumber, account);
        return idNumber;
	}

	public Long openConsumerAccount(Person person, int pin, double startingDeposit) {
		// complete the function
		Long idNumber = Long.valueOf(person.getIdNumber());
		Account account = new ConsumerAccount(person, idNumber, pin, startingDeposit);
		accounts.put(idNumber, account);
        return idNumber;
	}

	public boolean authenticateUser(Long accountNumber, int pin) throws Exception {
		// complete the function
		Account account = accounts.get(accountNumber);
		if(null != account && account.validatePin(pin)) {
			return true;
		}else {
			throw new Exception();
		}
	}

	public double getBalance(Long accountNumber) {
		// complete the function
		Account account = accounts.get(accountNumber);
		if(null != account)
			return account.getBalance();
        return -1;
	}

	public void credit(Long accountNumber, double amount) {
		// complete the function
		Account account = accounts.get(accountNumber);
		if(null != account) {
			account.creditAccount(amount);
		}
	}

	public boolean debit(Long accountNumber, double amount) {
		// complete the function
		Account account = accounts.get(accountNumber);
		if(null != account) {
			return account.debitAccount(amount);
		}else {
			return false;
		}
	}
}
