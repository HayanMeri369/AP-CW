package security.model;

/** Lecture material — accessible to students and staff but not guests. */
public class LectureMaterial extends Resource<AccessScope> {
    public LectureMaterial() {
        super("Lecture Material", AccessScope.INTERNAL);
    }
}
