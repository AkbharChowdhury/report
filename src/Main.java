import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

// ref https://medium.com/@AlexanderObregon/javas-collectors-tomap-method-explained-f95999d4ebe6

public class Main {
    public static Supplier<List<Employee>> getEmployeeData = () -> List.of(
            new Employee(401, "Lily Smith", "Marketing"),
            new Employee(402, "James Morgan", "Marketing"),
            new Employee(403, "Morgan Smith", "IT"),
            new Employee(404, "Chris Evans", "IT"),
            new Employee(404, "Adam Johnson", "IT"),
            new Employee(403, "Morgan Smith", "HR"),
            new Employee(404, "Chris Evans", "HE")
    );
    public static void main(String[] args) {
        var employees = getEmployeeData.get();
        Map<String, List<String>> departmentMap = employees.stream().collect(Collectors.toMap(Employee::getDepartment,
                e -> new ArrayList<>(List.of(e.getName())), (existing, replacement) -> {
                    existing.addAll(replacement);
                    return existing;
                }));
        Set<String> departments = departmentMap.keySet();
        departments.forEach(department -> departmentMap.put(department, sortedNames.apply(departmentMap.get(department))));
        departmentMap.entrySet().forEach(System.out::println);
    }
    public static Function<List<String>, List<String>> sortedNames = names ->
            names.stream()
                    .map(n -> n.split(" "))
                    .sorted(Comparator.comparing(c -> c[1], Comparator.reverseOrder()))
                    .map(n -> n[0] + " " + n[1])
                    .toList();


}
