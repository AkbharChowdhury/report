import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class OrderProcessor {
    public static final double HIGH_VALUE_ORDER_AMOUNT = 200;

    public static List<Order> generateOrders(int numOrders) {
        List<Order> orders = new ArrayList<>();
        String[] statuses = {"completed", "pending", "cancelled"};
        Random rand = new Random();
        for (int i = 0; i < numOrders; i++) {
            double amount = getRandomAmount(10, 20);
            String status = statuses[rand.nextInt(statuses.length)];
            orders.add(new Order(genOrderID.apply(i), amount, status));
        }
        return orders;
    }

    private static double getRandomAmount(double min, double max) {
        return Math.round(ThreadLocalRandom.current().nextDouble(min, max) * 100.0) / 100.0;
    }

    public static BiFunction<String, Integer, String> greetPerson = (name, age) -> MessageFormat.format("Hello {0}. you are {1}", name, age);

    public static Function<Integer, String> genOrderID = (i) -> String.format("ORD%d", (i + 1));

    public static List<Order> filterHighValueCompletedOrders(List<Order> orders) {
        return orders.stream()
                .filter(order -> "completed".equalsIgnoreCase(order.status()) && order.amount() >= HIGH_VALUE_ORDER_AMOUNT)
                .toList();

    }


    public static Supplier<String> reportTitle = () -> MessageFormat.format("Summary Report for High-Value Completed Orders: {0}", MoneyFormatter.formatCurrency.apply(HIGH_VALUE_ORDER_AMOUNT));

    public static Map<String, Object> createSummaryReport(List<Order> orders) {
        return new HashMap<>() {{
            put("count", orders.size());
            put("total_amount", MoneyFormatter.formatCurrency.apply(orders.stream().map(Order::amount).reduce(0.0, Double::sum)));
        }};

    }


    public static void main(String[] args) {

        var amounts = IntStream.range(0, 300)
                .mapToDouble(_ -> getRandomAmount(10, 20))
                .toArray();
        Arrays.stream(amounts)
                .filter(i -> i >= 15)
                .sorted()
                .forEach(iv -> System.out.println(MoneyFormatter.formatCurrency.apply(iv)));


    }

    private static void runApp() {
        final int NUM_ORDERS = 25;
        List<Order> orders = generateOrders(NUM_ORDERS);
        List<Order> highValueCompletedOrders = filterHighValueCompletedOrders(orders);


        Map<String, Object> summaryReport = createSummaryReport(highValueCompletedOrders);

        System.out.println(reportTitle.get());
        System.out.println(summaryReport);
        System.out.println("--------------------------- details ------------------------------");
        System.out.println(highValueCompletedOrders);
    }
}