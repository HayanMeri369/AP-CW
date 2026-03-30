/**
 * Predefined resource representing internal lecture content.
 */
public class LectureMaterial extends Resource<AccessScope> {
    public LectureMaterial() {
        super("Lecture Material", AccessScope.INTERNAL);
    }
}