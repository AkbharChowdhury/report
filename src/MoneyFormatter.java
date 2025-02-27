import java.text.NumberFormat;
import java.util.Locale;

public class MoneyFormatter {
    public static String formatCurrency(double amount){
        return NumberFormat.getCurrencyInstance(Locale.UK).format(amount);

    }
}
