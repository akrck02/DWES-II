/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author aketz
 */
public class Date {

    private int day;
    private int month;
    private int year;

    public Date(final int day, final int month, final int year) {
        this.day = day;
        this.month = month;
        this.year = year;        
    }
    
    public boolean correct() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        formatter.setLenient(false);
   
        try {    
            final java.util.Date d = formatter.parse( this.year + "/" + this.month + "/" + this.day);
            return true;
        } catch (ParseException ex) {
           return false;
        }        
    }
    
    
    public String toString() {
        return this.year + "/" + this.month + "/" + this.day;
    }
}
