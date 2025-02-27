import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;

public class OrderProcessor {
    public static List<Order> generateOrders(int numOrders) {
        List<Order> orders = new ArrayList<>();
        String[] statuses = {"completed", "pending", "cancelled"};
        Random rand = new Random();
        for (int i = 0; i < numOrders; i++) {
            double amount = Math.round(ThreadLocalRandom.current().nextDouble(10.0, 500.0) * 100.0) / 100.0;
            String status = statuses[rand.nextInt(statuses.length)];
            orders.add(new Order(genOrderID.apply(i), amount, status));
        }
        return orders;
    }

    public static Function<Integer, String> genOrderID = (i) -> String.format("ORD %d", (i + 1));

    public static List<Order> filterHighValueCompletedOrders(List<Order> orders) {
        return orders.stream()
                .filter(order -> "completed".equalsIgnoreCase(order.status()) && order.amount() > 200)
                .toList();

    }

    public static Supplier<String> reportTitle = () -> "Summary Report for High-Value Completed Orders:";

    // Updates a mutable summary Map with the count and total amount.
    public static Map<String, Object> createSummaryReport(List<Order> orders) {
        double totalAmount = orders.stream().map(Order::amount).reduce(0.0, Double::sum);
        Map<String, Object> summaryReport = new HashMap<>();
        summaryReport.put("count", orders.size());
        summaryReport.put("total_amount", MoneyFormatter.formatCurrency.apply(totalAmount));
        return summaryReport;

    }


    public static void main(String[] args) {
//        System.out.println(orderID.get());
        runApp();


    }

    private static void runApp() {
        final int NUM_ORDERS = 200;
        List<Order> orders = generateOrders(NUM_ORDERS);
        List<Order> highValueCompletedOrders = filterHighValueCompletedOrders(orders);

        Map<String, Object> summaryReport = createSummaryReport(highValueCompletedOrders);

        System.out.println(reportTitle.get());
        System.out.println(summaryReport);
        System.out.println("--------------------------- details ------------------------------");
        System.out.println(highValueCompletedOrders);
    }
}