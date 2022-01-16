/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 *
 * @author aketz
 */
public class Account {

    private String owner;
    private Double balance;

    /**
     * Create an Account with 0 balance
     * @param owner The account owner
     */
    public Account(final String owner) {
       this.owner = owner;
       this.balance = 0d;
    }
    
    /**
     * Create an Account with given balance
     * @param owner The account owner
     * @param balance The starting balance
     */
    public Account(final String owner, final Double balance){
        this.owner = owner;
        this.balance =  balance;
    }
    
    /**
     * Deposit cash on current balance
     * @param cash The cash to add
     */
    public void deposit (final double cash) { 
        this.balance += cash;
    }
    
    /**
     * Spend given cash. Does not substract if cash exceed
     * the account cash
     * @param cash The cash to spend
     * @return If operation can be done.
     */
    public boolean spend(final double cash){
        if(this.balance - cash < 0){
            return false;
        }
        
        this.balance -= cash;
        return true;
    }
    
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }
    
    
}
