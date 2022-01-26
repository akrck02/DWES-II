/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author aketz
 */
public class Lottery {
    
    private Date date;
    private Integer[] numbers;
    private int drawback;
    
    public Lottery(final Date date) {
        this.date = date;
        this.numbers = generateNumbers();  
        Arrays.sort(this.numbers);
        
        this.drawback = (int) (Math.random() * 10);
    }
    
    private Integer[] generateNumbers() {
        final HashSet<Integer> generated = new HashSet<>();
        
        while(generated.size() < 6){
            int random = (int) (1 + Math.random() * 49);
            generated.add(random);
        }
        
        return generated.toArray(new Integer[6]);
    }
    
    
    private boolean winner(final int number){
        
        final String serial = number + "";
        
        if(serial.length() < 6)
            return false;
        
        for (int i = 0; i < this.numbers.length; i++) {
            
            final int digit = Integer.parseInt(serial.charAt(i) + "");
            if(this.numbers[i] != digit)
                return false;
        }
        
        final int lastDigit = Integer.parseInt(serial.charAt(serial.length()-1) + "");
        
        if(lastDigit != this.drawback)
            return false;
        
        return true;
    }
    
    
    public String result(final Bet bet) {
        final StringBuilder result = new StringBuilder();
        result.append("Aciertos: ");
        
        for (int i = 0; i < this.numbers.length; i++) {
            final Integer current = bet.getNumbers()[i];
            if(current != null && current.equals(this.numbers[i]))
                result.append(" ").append(this.numbers[i]);                
        }
        
        if(bet.getDrawback() == this.drawback)
           result.append(" Con reintegro.");
        else
           result.append(" Sin reintegro.");
                
        return result.toString();
    }

    public Date getDate() {
        return date;
    }

    public int getDrawback() {
        return drawback;
    }

    public Integer[] getNumbers() {
        return numbers;
    }
    
    
}
