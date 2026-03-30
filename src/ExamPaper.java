/**
 * Predefined resource representing confidential exam content.
 */
public class ExamPaper extends Resource<AccessScope> {
    public ExamPaper() {
        super("Exam Paper", AccessScope.CONFIDENTIAL);
    }
}