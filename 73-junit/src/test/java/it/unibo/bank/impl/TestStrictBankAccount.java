package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    private static final int AMOUNT = 100;
    private static final int TOO_MUCH = 150;

    private final double FEE_AMOUNT = StrictBankAccount.TRANSACTION_FEE + StrictBankAccount.MANAGEMENT_FEE;
    private final double EXPECTED_BALANCE = AMOUNT - FEE_AMOUNT;

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        
        assertEquals(0, bankAccount.getTransactionsCount());
        bankAccount.deposit(mRossi.getUserID(), AMOUNT);

        assertEquals(1, bankAccount.getTransactionsCount());
        assertEquals(AMOUNT, bankAccount.getBalance());
        bankAccount.chargeManagementFees(mRossi.getUserID());

        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(EXPECTED_BALANCE, bankAccount.getBalance());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        
        bankAccount.deposit(mRossi.getUserID(), AMOUNT);

        try {
            bankAccount.withdraw(mRossi.getUserID(), -AMOUNT);
            Assertions.fail("Expected IllegalArgumentException not thrown");
        } catch (Exception e) {
            assertEquals("Cannot withdraw a negative amount", e.getMessage());
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        
        bankAccount.deposit(mRossi.getUserID(), AMOUNT);

        try {
            bankAccount.withdraw(mRossi.getUserID(), TOO_MUCH);
            fail();
        } catch (Exception e) {
            assertEquals("Insufficient Balance", e.getMessage());
        }
    }
}
