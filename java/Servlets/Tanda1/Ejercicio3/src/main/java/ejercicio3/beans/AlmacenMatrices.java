package ejercicio3.beans;

import java.util.ArrayList;

/**
 * A class to store all the matrix 
 * @author akrck02
 */
public final class AlmacenMatrices {
    
    private final static ArrayList<int[][]> matrixBundle = new ArrayList<>();
    
    /**
     * Add a matrix to the static list
     * @param matrix The matrix to add
     */
    public static void addMatrix(int[][] matrix){
        matrixBundle.add(matrix);
    }
    
    /**
     * Get the static list of matrix
     * @return The list
     */
    public static ArrayList<int[][]> getMatrixBundle(){
        return matrixBundle;
    }
    
}
