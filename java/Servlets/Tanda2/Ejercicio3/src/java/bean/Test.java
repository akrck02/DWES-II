package bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Test {

    private final static Question[] QUESTION_REGISTER;
    private int quantity;
    private ArrayList<Question> questions;
    
    private int current;
    private ArrayList<Integer> responses;
    private boolean hints;
    
    static {
        QUESTION_REGISTER = new Question[]{
            new Question("Capital de España", "No es Barcelona", new String[]{"Madrid", "Barcelona", "Zaragoza"}, 0),
            new Question("Primer juego de pokemon", "Salio en 1995", new String[]{"Pokemon plata", "Pokemon rojo", "Pokemon amarillo"}, 1),
            new Question("Lenguaje mas usado de Github", "No está tipado", new String[]{"Java", "Python", "Javascript"}, 2),
            new Question("Lordran es el reino de...", "Es el juego más oscuro", new String[]{"Dark souls", "The witcher", "Call of duty"}, 0),
            new Question("Los zora son una raza de...", "Agua jeje", new String[]{"Peces", "Ranas", "Pulpos"}, 0),
            new Question("Creador Material design", "Compañia grande", new String[]{"Apple", "Google", "Microsoft"}, 1),
            new Question("Propiedad de CSS parar aplicar filtros al fondo", "Es seminueva", new String[]{"Filter", "Backdrop-filter", "transform"}, 1),
            new Question("Barra de progreso en HTML", "W3s.org lo usa", new String[]{"progress", "progress-bar", "loader"}, 0)
        };

    }

    /**
     * Constructor.Gets question quantity and fills the question List with random Questions.
     * @param quantity Number of random questions
     * @param hints If hints are shown during the test
     */
    public Test(final int quantity, final boolean hints) {
        this.quantity = quantity;
        this.hints = hints;
        
        ArrayList<Question> localQuestions = new ArrayList<>(Arrays.asList(QUESTION_REGISTER));
        Collections.shuffle(localQuestions);

        final int max = quantity <= localQuestions.size() ? quantity : localQuestions.size();
        this.questions = new ArrayList<>(localQuestions.subList(0, max));
    
        this.current = 0;
        this.responses = new ArrayList<>();
    }
    
    /**
     * Check question responses 
     * @return number of successful checks
     */
    public int check() {
        
        int success = 0;
        
        for (int i = 0; i < this.questions.size(); i++) {
            final Question currentQuestion = this.questions.get(i);
            final String correct = currentQuestion.getCorrectAnswer();
            final String local = currentQuestion.getAnswer(responses.get(i));
            
            if(correct.equals(local))
                success ++;
        }
        
        return success;
    }

    public Question getCurrentQuestion() {
        try {
            return questions.get(current);
        } catch(IndexOutOfBoundsException e){
            return null;
        }
    }
    
    public boolean isFinal(){
        return this.current == this.questions.size() - 1 ;
    }
    
    public boolean hasHints() {
        return hints;
    }
    
    public void setCurrentAnswer(final int answer) {
        this.responses.add(this.current, answer);
    }
    
    public void next(){
        this.current++;
    }
    
    public int getTotalQuestions(){
        return this.questions.size();
    }
    
    public static int getTotal(int index){
        return index > Test.QUESTION_REGISTER.length ? Test.QUESTION_REGISTER.length : index;
    };
    
}
