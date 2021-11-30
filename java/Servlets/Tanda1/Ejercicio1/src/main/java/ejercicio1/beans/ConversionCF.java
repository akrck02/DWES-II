/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicio1.beans;

/**
 * Converter of celsius and fahrengheit temperatures
 * @author akrck02
 */
public final class ConversionCF {
    
    private double celsius;
    private double fahrenheit; 
    
    /**
     * Constructor of the converter
     * gets value and temperature type ('f' or 'c')
     * if type does not match celsius is selected
     * @param temp temperature value
     * @param type temperature type
     */
    public ConversionCF(double temp, String type){
        
        switch (type) {
            case "f":
                this.fahrenheit = temp;
                this.celsius = fahrenheitToCelsius(temp);
                break;
            
            //if type does not match celsius is selected by default
            case "c":
            default:
                this.celsius = temp;
                this.fahrenheit = celsiusToFahrenheit(temp);
                break;
        }
        
    }

    /**
     * Get celsius temperature
     * @return celsius
     */
    public double getCelsius() {
        return celsius;
    }

    /**
     * Get fahrenheit temperature
     * @return fahrenheit
     */
    public double getFahrenheit() {
        return fahrenheit;
    }
    
    /**
     * Convert from celsius to fahrenheit
     * @param celsius 
     * @return fahrenheit temperature
     */
    private static double celsiusToFahrenheit( double celsius ){
        return (celsius * 9 / 5) + 32 ;
    }
    
    /**
     * Convert from fahrenheit to celsius
     * @param fahrenheit
     * @return celsius temperature
     */
    private static double fahrenheitToCelsius( double fahrenheit ){
        return (fahrenheit - 32) * 5 / 9;
    }
    
}
