/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.autobuses.entidades;
import java.util.ArrayList;
import java.util.Scanner;
import mx.itson.autobuses.terminal.Terminales;
import java.util.HashSet;
import java.text.Normalizer;
    
/**
 * 
 * Representa un autobús que realiza un viaje entre varias terminales.
 * Permite la venta de boletos, gestión de asientos, y generación de reportes finales.
 * 
 * @author alex
 */
public class Autobus {
    private static final int TOTAL_ASIENTOS = 20;
    private boolean[] asientosDisponibles;
    private ArrayList<Pasajero> pasajeros;
    private ArrayList<Pasajero> pasajerosBajados;
    private double gananciaTotal;
    private HashSet<String> terminalesVisitadas;

     /**
     * Constructor que inicializa el autobús con asientos disponibles
     * y configura la lista de pasajeros, ganancias, y terminales visitadas.
     */
    public Autobus() {
        asientosDisponibles = new boolean[TOTAL_ASIENTOS];
        pasajeros = new ArrayList<>();
        pasajerosBajados = new ArrayList<>();
        gananciaTotal = 0.0;
        terminalesVisitadas = new HashSet<>();
        inicializarAsientos(); // Todos los asientos comienzan como disponibles
    }

    /**
     *Inicializa todos los asientos del autobús como disponibles (true).
     *Se utiliza al crear un objeto de la clase Autobus para garantizar que todos los asientos comiencen libres.
     */
    private void inicializarAsientos() {
        for (int i = 0; i < TOTAL_ASIENTOS; i++) {
            asientosDisponibles[i] = true;
        }
    }

    /**
     *Agrega una terminal visitada a un conjunto (HashSet) de terminales ya visitadas.
     * @param terminal 
     * Asegura que las terminales visitadas no sean seleccionadas como destinos válidos para nuevos pasajeros.
     */
    public void agregarTerminalVisitada(String terminal) {
        terminalesVisitadas.add(terminal);
    }

    /**
 * Muestra el estado actual de los asientos con un diseño que simula
 * la disposición real del autobús, incluyendo un pasillo central.
 */
public void mostrarAsientos() {
    System.out.println("Estado de los asientos:\n");

    // Iteramos por filas
    for (int fila = 0; fila < 5; fila++) {
        // Mostramos los dos asientos de la izquierda
        for (int columnaIzq = 0; columnaIzq < 2; columnaIzq++) {
            int indice = fila * 4 + columnaIzq; // Índice del asiento
            System.out.print(asientosDisponibles[indice] ? "[ ] " : "[X] ");
        }

        // Espacio para el pasillo
        System.out.print("   ");

        // Mostramos los dos asientos de la derecha
        for (int columnaDer = 2; columnaDer < 4; columnaDer++) {
            int indice = fila * 4 + columnaDer; // Índice del asiento
            System.out.print(asientosDisponibles[indice] ? "[ ] " : "[X] ");
        }

        // Salto de línea al terminar cada fila
        System.out.println();
    }

    System.out.println();
}

    /**
     *Gestiona la bajada de pasajeros en una terminal específica. Marca los asientos de estos pasajeros como disponibles y los registra en una lista de pasajeros que se bajaron.
     * @param terminalActual Nombre de la terminal donde se bajarán los pasajeros.
     * @return El número de pasajeros que se bajaron en esa terminal.
     * Se utiliza en cada parada para actualizar el estado del autobús.
     */
    public int bajarPasajeros(String terminalActual) {
        int pasajerosQueBajan = 0;
        ArrayList<Pasajero> pasajerosABajar = new ArrayList<>();
        for (Pasajero pasajero : pasajeros) {
            if (pasajero.getDestino().equals(terminalActual)) {
                pasajerosABajar.add(pasajero);
                asientosDisponibles[pasajero.getAsiento() - 1] = true;
                pasajerosQueBajan++;
            }
        }
        pasajeros.removeAll(pasajerosABajar);
        pasajerosBajados.addAll(pasajerosABajar); // Agregar a la lista de pasajeros bajados
        return pasajerosQueBajan;
    }

    /**
     *Permite vender un boleto a un pasajero, registrando su información (nombre, destino, precio y número de asiento) y marcando el asiento como ocupado.
     * @param sc Objeto Scanner para leer la entrada del usuario.
     * @param origen Nombre de la terminal actual (origen del pasajero).
     * Gestiona la venta de boletos asegurando que los destinos sean válidos y los asientos estén disponibles.
     */
    public void venderBoleto(Scanner sc, String origen) {
    System.out.print("Nombre del pasajero: ");
    String nombre = sc.nextLine();

    // Validar el destino ingresado
    String destino;
    while (true) {
        System.out.println("Destinos disponibles:");
        for (String terminal : Terminales.TERMINALES) {
            if (!terminalesVisitadas.contains(terminal) && !terminal.equalsIgnoreCase(origen)) {
                System.out.println("- " + terminal);
            }
        }
        System.out.print("Destino: ");
        destino = sc.nextLine()
                     .toLowerCase() // Convertir a minúsculas
                     .replaceAll("[^a-z0-9 ]", ""); // Eliminar caracteres especiales

        if (esDestinoValido(destino, origen)) {
            break; // Si el destino es válido, salir del bucle
        } else {
            System.out.println("Destino no válido. Por favor, seleccione uno de la lista.");
        }
    }

    System.out.print("Precio del boleto: ");
    double precio = sc.nextDouble();
    sc.nextLine(); // Limpiar el buffer

    System.out.println("Seleccione un asiento disponible:");
    mostrarAsientos();
    int asiento;
    while (true) {
        System.out.print("Número de asiento: ");
        asiento = sc.nextInt();
        sc.nextLine(); // Limpiar el buffer
        if (asiento > 0 && asiento <= TOTAL_ASIENTOS && asientosDisponibles[asiento - 1]) {
            asientosDisponibles[asiento - 1] = false; // Marcar como ocupado
            break;
        } else {
            System.out.println("Asiento inválido o no disponible. Intente de nuevo.");
        }
    }

    Pasajero nuevoPasajero = new Pasajero(nombre, origen, destino, precio, asiento);
    pasajeros.add(nuevoPasajero);
    gananciaTotal += precio;
    System.out.println("Boleto vendido con éxito a " + nombre);
}

/**
 *Verifica si un destino ingresado por el usuario es válido. Un destino es válido si no ha sido visitado aún, no es igual al origen, y está en la lista de terminales.
 * @param destino Nombre de la terminal destino.
 * @param origen Nombre de la terminal origen.
 * @return true si el destino es válido; de lo contrario, false.
 * Valida los destinos ingresados durante la venta de boletos.
 */
private boolean esDestinoValido(String destino, String origen) {
    destino = normalizarTexto(destino);
    origen = normalizarTexto(origen);

    for (String terminal : Terminales.TERMINALES) {
        if (normalizarTexto(terminal).equals(destino) && !normalizarTexto(terminal).equals(origen)
                && !terminalesVisitadas.contains(terminal)) {
            return true;
        }
    }
    return false;
}

/**
 *Elimina acentos y convierte un texto a minúsculas para una comparación más consistente y precisa.
 * @param texto El texto a normalizar.
 * @return El texto normalizado.
 * Facilita la comparación de nombres de terminales y destinos.
 */
private String normalizarTexto(String texto) {
    texto = Normalizer.normalize(texto, Normalizer.Form.NFD); // Normaliza eliminando caracteres especiales
    texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", ""); // Remueve acentos
    return texto.toLowerCase(); // Convierte a minúsculas
}

    /**
     *Genera y muestra un reporte final del viaje. Incluye:
     *Pasajeros que permanecen en el autobús, con su nombre, origen, destino, precio y asiento.
     * Pasajeros que se bajaron en terminales previas.
     * La ganancia total del viaje.
     * 
     * Se llama al finalizar el viaje para mostrar un resumen completo.
     */
     public void mostrarReporteFinal() {
        System.out.println("\n--- Reporte Final del Viaje ---");

        // Reporte de pasajeros actuales
        System.out.println("\nPasajeros aún en el autobús:");
        if (pasajeros.isEmpty()) {
            System.out.println("No quedan pasajeros en el autobús.");
        } else {
            System.out.println("-------------------------------------------------------");
            System.out.printf("%-20s %-15s %-15s %-10s %-10s\n",
                              "Nombre", "Origen", "Destino", "Precio", "Asiento");
            System.out.println("-------------------------------------------------------");
            for (Pasajero pasajero : pasajeros) {
                System.out.printf("%-20s %-15s %-15s $%-9.2f %-10d\n",
                                  pasajero.getNombre(),
                                  pasajero.getOrigen(),
                                  pasajero.getDestino(),
                                  pasajero.getPrecio(),
                                  pasajero.getAsiento());
            }
            System.out.println("-------------------------------------------------------");
        }

        // Reporte de pasajeros que se bajaron
        System.out.println("\nPasajeros que se bajaron en paradas anteriores:");
        if (pasajerosBajados.isEmpty()) {
            System.out.println("No hay registros de pasajeros que se hayan bajado.");
        } else {
            System.out.println("-------------------------------------------------------");
            System.out.printf("%-20s %-15s %-15s %-10s %-10s\n",
                              "Nombre", "Origen", "Destino", "Precio", "Asiento");
            System.out.println("-------------------------------------------------------");
            for (Pasajero pasajero : pasajerosBajados) {
                System.out.printf("%-20s %-15s %-15s $%-9.2f %-10d\n",
                                  pasajero.getNombre(),
                                  pasajero.getOrigen(),
                                  pasajero.getDestino(),
                                  pasajero.getPrecio(),
                                  pasajero.getAsiento());
            }
            System.out.println("-------------------------------------------------------");
        }

        // Ganancia total
        System.out.printf("Ganancia total del viaje: $%.2f\n", gananciaTotal);
        System.out.println("-------------------------------------------------------");
    }
}
