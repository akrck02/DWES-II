package bean;

public class Question {
    
    private String statement;
    private String hint;
    private String[] answers;
    private int correctAnswerIndex;

    public Question(final String statement, final String hint, final String[] answers, final int correct) {
        this.statement = statement;
        this.hint = hint;
        this.answers = answers;
        this.correctAnswerIndex = correct;
    }

    public String[] getAnswers() {
        return answers;
    }
    
    public String getCorrectAnswer() {
        return this.answers[correctAnswerIndex];
    }

    public String getAnswer(int index) {
        return this.answers[index];
    }

    
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
    
    public String getStatement() {
        return statement;
    }
    
    public String getHint() {
        return hint;
    }

    
}
