package security.model;

/** Exam paper — confidential, restricted to admin and staff with write access. */
public class ExamPaper extends Resource<AccessScope> {
    public ExamPaper() {
        super("Exam Paper", AccessScope.CONFIDENTIAL);
    }
}
