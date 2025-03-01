import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
// ref https://medium.com/@AlexanderObregon/javas-collectors-tomap-method-explained-f95999d4ebe6
class Product {
    private int id;
    private double price;


    public Product(int id, double price) {
        this.id = id;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }
}

public class Main {
    public static Supplier<List<Employee>> getEmployeeData = () -> List.of(
            new Employee(401, "Lily", "Marketing"),
            new Employee(402, "James", "Marketing"),
            new Employee(403, "Morgan", "IT"),
            new Employee(404, "Chris", "IT")
    );

    public static void main(String[] args) {
        var employees = getEmployeeData.get();
        Map<String, List<String>> departmentMap = employees.stream()
                .collect(Collectors.toMap(
                        Employee::getDepartment,
                        e -> new ArrayList<>(List.of(e.getName())),
                        (existing, replacement) -> {
                            existing.addAll(replacement);
                            return existing;
                        }));

        departmentMap.entrySet().forEach(i -> {
            System.out.println(i.getKey());
            System.out.println(i.getValue());
        });


    }
}
