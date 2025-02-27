import java.text.MessageFormat;

public record Order(String orderId, double amount, String status) {

    @Override
    public String toString() {
        return MessageFormat.format("Order[orderId={0}, amount={1}",
                orderId, MoneyFormatter.formatCurrency(amount));
    }
}
