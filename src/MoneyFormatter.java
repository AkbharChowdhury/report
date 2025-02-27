import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Function;

public class MoneyFormatter {
    public static Function<Double, String> formatCurrency = amount -> NumberFormat.getCurrencyInstance(Locale.UK).format(amount);

}
