package security.model;
public class LectureMaterial extends Resource<AccessScope> {

    public LectureMaterial() {
        super("Lecture Material", AccessScope.INTERNAL);
       
    }
    
}
