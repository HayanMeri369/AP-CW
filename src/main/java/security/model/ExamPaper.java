package security.model;
public class ExamPaper extends Resource <AccessScope>{
    public ExamPaper(){
        super("Exam Paper", AccessScope.CONFIDENTIAL);
    }
    
}
///shows the Exam paper access as medium level