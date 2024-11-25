/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package mx.itson.autobuses.ui;

/**
 *
 * @author alex
 */
import java.util.Scanner;
import mx.itson.autobuses.terminal.Terminales;
import mx.itson.autobuses.entidades.Autobus;

public class Main {
    public static void main(String[] args) {
        Autobus autobus = new Autobus();
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < Terminales.TERMINALES.length - 1; i++) {
            String terminalActual = Terminales.TERMINALES[i];
            System.out.println("\nLlegando a terminal: " + terminalActual);
            autobus.agregarTerminalVisitada(terminalActual);
            autobus.mostrarAsientos();

            // Bajada de pasajeros
            int pasajerosQueBajan = autobus.bajarPasajeros(terminalActual);
            System.out.println("Se bajan " + pasajerosQueBajan + " pasajero(s).");

            // Venta de boletos
            System.out.println("Venta de boletos en " + terminalActual);
            while (true) {
                 System.out.print("¿Desea vender un boleto? (si/no): ");
                String respuesta = sc.nextLine().toLowerCase(); // Convertir la respuesta a minúsculas para normalizar
                if (respuesta.equals("si")) {
                    // Vender boleto
                    autobus.venderBoleto(sc, terminalActual);
                } else if (respuesta.equals("no")) {
                    break; // Si el usuario responde "no", se sale del ciclo de venta
                } else {
                    System.out.println("Opción no válida. Por favor ingrese 'si' o 'no'."); // Mensaje de error
                }
            }
        }

       // Llegada a la última terminal
        String ultimaTerminal = Terminales.TERMINALES[Terminales.TERMINALES.length - 1];
        System.out.println("\nLlegando a la terminal final: " + ultimaTerminal);
        autobus.agregarTerminalVisitada(ultimaTerminal);

        // Mostrar el reporte final
        while (true) {
            System.out.print("¿Desea mostrar el reporte final del viaje? (si/no): ");
            String respuesta = sc.nextLine().toLowerCase();
            if (respuesta.equals("si")) {
                autobus.mostrarReporteFinal();
                break;
            } else if (respuesta.equals("no")) {
                System.out.println("Reporte final no mostrado.");
                break;
            } else {
                System.out.println("Opción no válida. Por favor ingrese 'si' o 'no'.");
            }
        }

        sc.close();
    }
}
