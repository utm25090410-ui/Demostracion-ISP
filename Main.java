import java.util.Scanner;

// ==========================================
// 1. EL PROBLEMA: VIOLACIÓN DE ISP
// Interfaz "Gorda" o Monolítica
// ==========================================
interface DispositivoFat {
    void encender();
    void apagar();
    void imprimir(String texto);
    void escanear(String texto);
    void enviarFax(String texto);
}

class ImpresoraBasicaMala implements DispositivoFat {
    @Override
    public void encender() { System.out.println("[Básica] Encendida."); }

    @Override
    public void apagar() { System.out.println("[Básica] Apagada."); }

    @Override
    public void imprimir(String texto) {
        System.out.println("[Básica] Imprimiendo: " + texto);
    }

    @Override
    public void escanear(String texto) {
        // Lanzamos excepción porque el dispositivo físicamente NO tiene escáner
        throw new UnsupportedOperationException("ERROR: La impresora básica NO cuenta con escáner.");
    }

    @Override
    public void enviarFax(String texto) {
        throw new UnsupportedOperationException("ERROR: La impresora básica NO cuenta con Fax.");
    }
}


// ==========================================
// 2. LA SOLUCIÓN: CUMPLIENDO ISP
// Interfaces segregadas y enfocadas por rol
// ==========================================
interface Encendible {
    void encender();
    void apagar();
}

interface Impresora {
    void imprimir(String texto);
}

interface Escaner {
    void escanear(String texto);
}

interface Fax {
    void enviarFax(String texto);
}

// Implementa SOLO lo que necesita
class ImpresoraBasicaBuena implements Encendible, Impresora {
    @Override
    public void encender() { System.out.println("[Básica ISP] Encendida."); }

    @Override
    public void apagar() { System.out.println("[Básica ISP] Apagada."); }

    @Override
    public void imprimir(String texto) {
        System.out.println("[Básica ISP] Imprimiendo de forma segura: " + texto);
    }
}

// La impresora avanzada implementa todas las interfaces necesarias
class ImpresoraAvanzadaBuena implements Encendible, Impresora, Escaner, Fax {
    @Override
    public void encender() { System.out.println("[Multifuncional] Encendida."); }

    @Override
    public void apagar() { System.out.println("[Multifuncional] Apagada."); }

    @Override
    public void imprimir(String texto) {
        System.out.println("[Multifuncional] Imprimiendo: " + texto);
    }

    @Override
    public void escanear(String texto) {
        System.out.println("[Multifuncional] Escaneando a PDF: " + texto);
    }

    @Override
    public void enviarFax(String texto) {
        System.out.println("[Multifuncional] Enviando fax: " + texto);
    }
}


// ==========================================
// 3. MENÚ INTERACTIVO PARA LA PRESENTACIÓN
// ==========================================
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n=======================================================");
            System.out.println("  DEMOSTRACIÓN: INTERFACE SEGREGATION PRINCIPLE (ISP)  ");
            System.out.println("=======================================================");
            System.out.println("1. Ejecutar Caso con VIOLACIÓN de ISP (Falla)");
            System.out.println("2. Ejecutar Caso CUMPLIENDO ISP (Funciona)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            System.out.println();

            switch (opcion) {
                case 1:
                    probarViolacionISP();
                    break;
                case 2:
                    probarCumplimientoISP();
                    break;
                case 0:
                    System.out.println("Saliendo de la presentación...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        scanner.close();
    }

    private static void probarViolacionISP() {
        System.out.println("---> ESCENARIO 1: VIOLACIÓN DE ISP <---");
        System.out.println("Usando interfaz 'DispositivoFat'...");

        DispositivoFat impresoraMala = new ImpresoraBasicaMala();
        impresoraMala.encender();
        impresoraMala.imprimir("Documento_Escolar.pdf");

        System.out.println("\nIntentando invocar escanear()...");
        try {
            // Falla en tiempo de ejecución
            impresoraMala.escanear("Documento_Escolar.pdf");
        } catch (UnsupportedOperationException e) {
            System.out.println(">>> SE PRODUJO EXCEPCIÓN EN TIEMPO DE EJECUCIÓN <<<");
            System.out.println("Causa: " + e.getMessage());
        }
    }

    private static void probarCumplimientoISP() {
        System.out.println("---> ESCENARIO 2: CUMPLIMIENTO DE ISP <---");
        System.out.println("Usando interfaces segregadas (Impresora, Escaner, Fax)...");

        System.out.println("\n1. Impresora Básica:");
        ImpresoraBasicaBuena basica = new ImpresoraBasicaBuena();
        basica.encender();
        basica.imprimir("Documento_Escolar.pdf");
        // Nota: 'basica' no tiene escanear(), por lo tanto ni siquiera se puede escribir el código que falle.

        System.out.println("\n2. Impresora Multifuncional:");
        ImpresoraAvanzadaBuena avanzada = new ImpresoraAvanzadaBuena();
        avanzada.encender();
        avanzada.imprimir("Reporte_Anual.pdf");
        avanzada.escanear("Contrato_Firmado.pdf");
        avanzada.enviarFax("Factura.pdf");
    }
}