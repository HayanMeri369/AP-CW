package security.model;

/** A publicly accessible printer — anyone can use it. */
public class Printer extends Resource<AccessScope> {
    public Printer() {
        super("Printer", AccessScope.PUBLIC);
    }
}
