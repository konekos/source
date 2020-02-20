package com.jasu.concurrent.jcia.chapter10;

/*****************************************
 * @author hjs
 * @date 2020-02-20 1:50
 *****************************************/
public class DynamicOrderingDeadLock {



    public void transferMoney(Account from, Account to, DollarAmount amount) throws InsufficientFundsException {
        synchronized (from) {
            synchronized (to) {
                if (from.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientFundsException();
                } else {
                    from.debit(amount);
                    to.credit(amount);
                }
            }
        }
    }
    private static final Object tieLock = new Object();
    public void transferMoney1(Account from, Account to, DollarAmount amount) throws InsufficientFundsException {
        class Helper{
            public void transfer() throws InsufficientFundsException {
                if (from.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientFundsException();
                } else {
                    from.debit(amount);
                    to.credit(amount);
                }
            }
        }

        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);

        if (fromHash < toHash) {
            synchronized (from) {
                synchronized (to) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (to) {
                synchronized (from) {
                    new Helper().transfer();
                }
            }
        } else {
            synchronized (tieLock) {
                synchronized (from) {
                    synchronized (to) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }

    private class Account {
        public DollarAmount getBalance() {
            return new DollarAmount();
        }

        public void debit(DollarAmount amount) {

        }

        public void credit(DollarAmount amount) {

        }
    }

    private class DollarAmount{

        public int compareTo(DollarAmount amount) {
            return 0;
        }
    }

    private class InsufficientFundsException extends Throwable {
    }
}
