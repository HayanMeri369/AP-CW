package security.model;
public class Printer extends Resource<AccessScope>{
    public Printer(){
        super("Printer", AccessScope.PUBLIC);
    }
    
}
//shows the printer access as low level