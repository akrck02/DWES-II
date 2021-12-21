/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 *
 * @author aketz
 */
public class AlmacenPalabras {
   
    public static String[] palabras = new String[] {
        "Inicio",
        "Reminiscente",
        "Onomatopeya",
        "Vivaracho",
        "Azucarero",
        "Resquemor",
        "Azafrán",
        "Lenguetazo",
        "Tentación",
        "Sirviente",
        "Alejandrino",
        "Dantesco",
        "Fotógrafo",
        "Respetuoso",
        "Cádiz",
        "Leónidas",
        "Anguila",
        "Refresco",
        "Vivaz",
        "Perspicaz",
        "Remilgo",
        "Atuendo",
        "Galán",
        "Artrosis",
        "Levitación",
        "Terraza",
        "Cachete",
        "Veraniego",
        "Esquimal",
        "Bufón",
        "Cortesía",
        "Determinante",
        "Tradición",
        "Canela",
        "Egipcio",
        "Anterior",
        "Población",
        "Frio",
        "Alce",
        "Luces",
        "Papaya",
        "Azteca",
        "Arruinado",
        "Final",
    }; 
    
    /**
     * Return a random word from static array
     * @return word
     */
    public static String randomWord() {      
        int index =  (int) (Math.random() * palabras.length);
        return palabras[index].toUpperCase();
    }
}
