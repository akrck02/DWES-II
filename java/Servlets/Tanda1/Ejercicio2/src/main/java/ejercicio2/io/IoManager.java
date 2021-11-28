/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicio2.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;
import javax.servlet.ServletContext;

/**
 *
 * @author aketz
 */
public class IoManager {

    final static String FILES_DIRECTORY = "/files/";
    final static String SECTIONS_FILE_URL = FILES_DIRECTORY + "secciones.txt";
    final static String FAVORITE_SECTIONS_FILE_URL = FILES_DIRECTORY + "seccionesfavoritas.txt";

    public static void writeLine(final ServletContext context, final String content) {

        String realPath = context.getRealPath(FAVORITE_SECTIONS_FILE_URL);
        
        if(realPath == null){
            realPath = context.getRealPath(FILES_DIRECTORY) + "/seccionesfavoritas.txt";
        }
        
        System.out.println("URL : " + realPath);
        
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(realPath,true));
            writer.write(content);
            writer.newLine();
            writer.close();
        }catch(IOException | NullPointerException e){
            System.err.println(e);
        }
    }

    /** 
     * Returns an Optional Stream of lines read on the file
     *
     * @param context The servlet context to convert the URL
     * @return Optional Stream of Strings filled or empty
     */
    public static Optional<Stream<String>> getSections(final ServletContext context) {
        final String realPath = context.getRealPath(SECTIONS_FILE_URL);
        Optional<Stream<String>> lines = Optional.empty();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(realPath));
            lines = Optional.ofNullable(reader.lines());
        } catch (IOException | NullPointerException e) {
            System.err.println(e);
        }

        return lines;
    }

}
