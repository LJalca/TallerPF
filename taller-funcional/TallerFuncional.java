import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class TallerFuncional {

    // Java 17+: record inmutable, ideal para programación funcional
    record Empleado(String nombre, double salario) {}

    public static void main(String[] args) {
        System.out.println("Taller de Programación Funcional iniciado");

        List<Empleado> empleados = List.of(
                new Empleado("Carlos", 2500.0),
                new Empleado("Maria", 1800.0),
                new Empleado("Pedro", 1200.0),
                new Empleado("Ana", 900.0),
                new Empleado("Luis", 750.0)
        );

        Predicate<Empleado> salarioAlto = e -> e.salario() > 1000;
        List<Empleado> filtrados = empleados.stream()
                .filter(salarioAlto)
                .collect(Collectors.toList());
        System.out.println("Empleados con salario > 1000: " + filtrados.size());

        List<Empleado> ordenados = filtrados.stream()
                .sorted(Comparator.comparingDouble(Empleado::salario).reversed())
                .collect(Collectors.toList());

        System.out.println("=== Empleados con salario > 1000 (ordenados desc.) ===");
        ordenados.forEach(e ->
                System.out.printf("%-8s-> %.1f%n",
                        e.nombre().toUpperCase(), e.salario())
        );

        double total = ordenados.stream()
                .map(Empleado::salario)
                .reduce(0.0, Double::sum);

        double promedio = ordenados.stream()
                .collect(Collectors.averagingDouble(Empleado::salario));

        System.out.println();
        System.out.println("Salario total del equipo filtrado: " + total);
        System.out.printf("Salario promedio: %.2f%n", promedio);

        Optional<Empleado> mejorPagado = ordenados.stream()
                .max(Comparator.comparingDouble(Empleado::salario));

        mejorPagado.ifPresentOrElse(
                e -> System.out.println("Empleado mejor pagado: " + e.nombre().toUpperCase()),
                () -> System.out.println("No hay empleados que cumplan el criterio")
        );

        // --- Reto opcional: agrupar TODOS los empleados por rango salarial ---
        System.out.println();
        System.out.println("=== Empleados agrupados por rango salarial ===");

        Map<String, List<Empleado>> porRango = empleados.stream()
                .collect(Collectors.groupingBy(e -> {
                    if (e.salario() >= 2000) return "ALTO (>= 2000)";
                    else if (e.salario() >= 1000) return "MEDIO (1000-1999)";
                    else return "BAJO (< 1000)";
                }));

        porRango.forEach((rango, lista) -> {
            String nombres = lista.stream()
                    .map(Empleado::nombre)
                    .collect(Collectors.joining(", "));
            System.out.println(rango + ": " + nombres);
        });
    }
}