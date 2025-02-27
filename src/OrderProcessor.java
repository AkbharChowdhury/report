import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
public class OrderProcessor {
    public static List<Order> generateOrders(int numOrders) {
        List<Order> orders = new ArrayList<>();
        String[] statuses = {"completed", "pending", "cancelled"};
        Random rand = new Random();
        for (int i = 0; i < numOrders; i++) {
            String orderId = "ORD" + (i + 1);
            double amount = Math.round(ThreadLocalRandom.current().nextDouble(10.0, 500.0) * 100.0) / 100.0;
            String status = statuses[rand.nextInt(statuses.length)];
            orders.add(new Order(orderId, amount, status));
        }
        return orders;
    }

    public static List<Order> filterHighValueCompletedOrders(List<Order> orders) {
        return orders.stream().filter(order -> "completed".equalsIgnoreCase(order.status()) && order.amount() > 200)
//                .map(order ->  {
//                    return  new Order(order.orderId(), formatCurrency(order.amount()), order.status());
//                })
                .toList();

    }

    // Updates a mutable summary Map with the count and total amount.
    public static Map<String, Object> createSummaryReport(List<Order> orders) {
        double totalAmount = orders.stream().map(Order::amount).reduce(0.0, Double::sum);
        Map<String, Object> summaryReport = new HashMap<>();
        summaryReport.put("count", orders.size());
        summaryReport.put("total_amount", MoneyFormatter.formatCurrency(totalAmount));
        return summaryReport;

    }


    public static void main(String[] args) {

        final int NUM_ORDERS = 200;
        List<Order> orders = generateOrders(NUM_ORDERS);
        List<Order> highValueCompletedOrders = filterHighValueCompletedOrders(orders);

        Map<String, Object> summaryReport = createSummaryReport(highValueCompletedOrders);

        System.out.println("Summary Report for High-Value Completed Orders:");
        System.out.println(summaryReport);
        System.out.println("--------------------------- details ------------------------------");
        System.out.println(highValueCompletedOrders);
    }
}