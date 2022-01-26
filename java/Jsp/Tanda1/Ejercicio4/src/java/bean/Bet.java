/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

import exception.BadBetException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author aketz
 */
public class Bet {
    
    private String name;
    private int administration;
    private Integer[] numbers;
    private int drawback;
    
    public Bet(final String name, final int administration, final Integer[] numbers, final int drawback) throws BadBetException {
 
        if(numbers == null)
            throw new BadBetException("numbers not present");
        
        System.out.println(Arrays.toString(numbers));
        
        if(numbers.length != 6)
            throw new BadBetException("6 numbers need to be present");
        
        final ArrayList<Integer> inserted = new ArrayList<>();
        
        for (int n : numbers) {
            if(n > 0 && n < 50 && !inserted.contains(n))
                inserted.add(n);
        }
        
        if(inserted.size() != 6) {
            throw new BadBetException("6 not repeated numbers between 1 and 49 need to be present");
        } 
        
        if(drawback < 0 || drawback > 9)
            throw new BadBetException("Drawback must be a number between 0 and 9");
        
        
        this.name = name;
        this.administration = administration;
        this.numbers = numbers;
        this.drawback = drawback;
        
    }

    public int getAdministration() {
        return administration;
    }

    public int getDrawback() {
        return drawback;
    }

    public String getName() {
        return name;
    }

    public Integer[] getNumbers() {
        return numbers;
    }
    
    @Override
    public String toString() {    
        final StringBuilder string = new StringBuilder(); 
        
        
        
        
        return string.toString(); 
    }
    
    
    
    
}
