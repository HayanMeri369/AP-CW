package security.demo;

import security.log.AccessResult;
import security.log.AuditLog;
import security.model.*;
import security.policy.AccessController;
import security.policy.DefaultAccessPolicy;

/**
 * Demonstrates the access control system with four users and three resources.
 *
 * Scenario:
 *   Users  : guest1 (GUEST), student1 (STUDENT), staff1 (STAFF), admin1 (ADMIN)
 *   Resources: Printer (PUBLIC), LectureMaterial (INTERNAL), ExamPaper (CONFIDENTIAL)
 *
 * Each user attempts both READ and WRITE on each resource. The output shows
 * which accesses are ALLOWED and which are REFUSED, followed by the full
 * audit log.
 */
public class Demo {

    public static void main(String[] args) {

        // -----------------------------------------------------------------------
        // Set up the access controller (Strategy pattern in action)
        // -----------------------------------------------------------------------
        AccessController controller = new AccessController(
                new DefaultAccessPolicy(),
                AuditLog.getInstance()
        );

        // -----------------------------------------------------------------------
        // Create users via the Builder pattern
        // -----------------------------------------------------------------------
        User guest   = new UserBuilder().setId("guest1").setRole(Role.GUEST).build();
        User student = new UserBuilder().setId("student1").setRole(Role.STUDENT).build();
        User staff   = new UserBuilder().setId("staff1").setRole(Role.STAFF).build();
        User admin   = new UserBuilder().setId("admin1").setRole(Role.ADMIN).build();

        // -----------------------------------------------------------------------
        // Create resources
        // -----------------------------------------------------------------------
        Resource<?> printer  = new Printer();
        Resource<?> lecture  = new LectureMaterial();
        Resource<?> examPaper = new ExamPaper();

        // -----------------------------------------------------------------------
        // Run the scenario
        // -----------------------------------------------------------------------
        System.out.println("============================================================");
        System.out.println("         ACCESS CONTROL SYSTEM — DEMO");
        System.out.println("============================================================\n");

        runUserScenario(controller, guest,   printer, lecture, examPaper);
        runUserScenario(controller, student, printer, lecture, examPaper);
        runUserScenario(controller, staff,   printer, lecture, examPaper);
        runUserScenario(controller, admin,   printer, lecture, examPaper);

        // -----------------------------------------------------------------------
        // Print the full audit log
        // -----------------------------------------------------------------------
        AuditLog.getInstance().printAll();
    }

    /**
     * Attempts READ and WRITE on each resource for the given user
     * and prints the result to the console.
     */
    private static void runUserScenario(AccessController controller,
                                        User user,
                                        Resource<?>... resources) {
        System.out.println("--- User: " + user + " ---");
        for (Resource<?> resource : resources) {
            attempt(controller, user, resource, Operation.READ);
            attempt(controller, user, resource, Operation.WRITE);
        }
        System.out.println();
    }

    private static void attempt(AccessController controller,
                                 User user,
                                 Resource<?> resource,
                                 Operation operation) {
        AccessResult result = controller.request(user, resource, operation);
        System.out.printf("  %-10s %-6s %-20s -> %s%n",
                user.getId(), operation, resource.getName(), result);
    }
}
