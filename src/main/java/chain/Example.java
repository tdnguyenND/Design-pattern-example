package chain;

public class Example {

    // === Request object ===
    static class PurchaseRequest {
        final String description;
        final double amount;
        String approvedBy;

        PurchaseRequest(String description, double amount) {
            this.description = description;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "\"" + description + "\" ($" + String.format("%.0f", amount) + ")";
        }
    }

    // === Handler interface ===
    static abstract class Approver {
        private Approver next;
        private final String name;
        private final double limit;

        Approver(String name, double limit) {
            this.name = name;
            this.limit = limit;
        }

        Approver setNext(Approver next) {
            this.next = next;
            return next;
        }

        void handle(PurchaseRequest request) {
            if (request.amount <= limit) {
                request.approvedBy = name;
                System.out.println("  [" + name + "] APPROVED " + request
                        + " (limit: $" + String.format("%.0f", limit) + ")");
            } else if (next != null) {
                System.out.println("  [" + name + "] Cannot approve " + request
                        + " (limit: $" + String.format("%.0f", limit) + ") -> forwarding");
                next.handle(request);
            } else {
                System.out.println("  [" + name + "] REJECTED " + request
                        + " - exceeds all approval limits");
            }
        }
    }

    // === Concrete handlers ===
    static class TeamLead extends Approver {
        TeamLead() { super("Team Lead", 1000); }
    }

    static class Manager extends Approver {
        Manager() { super("Manager", 5000); }
    }

    static class Director extends Approver {
        Director() { super("Director", 20000); }
    }

    static class CEO extends Approver {
        CEO() { super("CEO", 100000); }
    }

    // === Demo ===
    public static void main(String[] args) {
        // Build the chain
        Approver teamLead = new TeamLead();
        teamLead.setNext(new Manager())
                .setNext(new Director())
                .setNext(new CEO());

        System.out.println("=== Chain: Team Lead -> Manager -> Director -> CEO ===\n");

        PurchaseRequest[] requests = {
            new PurchaseRequest("Office supplies", 500),
            new PurchaseRequest("New laptop", 2500),
            new PurchaseRequest("Team offsite", 15000),
            new PurchaseRequest("Server cluster", 75000),
            new PurchaseRequest("Building lease", 500000),
        };

        for (PurchaseRequest request : requests) {
            System.out.println("Processing: " + request);
            teamLead.handle(request);
            System.out.println();
        }

        System.out.println("=== Shorter chain: Manager -> Director ===\n");
        Approver shortChain = new Manager();
        shortChain.setNext(new Director());

        PurchaseRequest req = new PurchaseRequest("Conference tickets", 8000);
        System.out.println("Processing: " + req);
        shortChain.handle(req);
        System.out.println();

        PurchaseRequest bigReq = new PurchaseRequest("Annual budget", 50000);
        System.out.println("Processing: " + bigReq);
        shortChain.handle(bigReq);
    }
}
