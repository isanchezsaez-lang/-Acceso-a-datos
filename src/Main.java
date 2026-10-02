import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<Pelicula> peliculas = leerPeliculas("datos/peliculas.txt");

        if (peliculas.isEmpty()) {
            System.out.println("No se pudieron cargar las películas.");
            return;
        }

        System.out.println("✅ Se cargaron " + peliculas.size() + " películas correctamente.\n");
        mostrarMenu(peliculas);
    }

    public static List<Pelicula> leerPeliculas(String nombreFichero) {
        List<Pelicula> peliculas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split("\\|");

                if (campos.length == 5) {
                    int id = Integer.parseInt(campos[0].trim());
                    String titulo = campos[1].trim();
                    String director = campos[2].trim();
                    int año = Integer.parseInt(campos[3].trim());
                    String genero = campos[4].trim();

                    Pelicula pelicula = new Pelicula(id, titulo, director, año, genero);
                    peliculas.add(pelicula);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("❌ Error: El archivo '" + nombreFichero + "' no fue encontrado.");
        } catch (IOException e) {
            System.out.println("❌ Error al leer el archivo: " + e.getMessage());
        }

        return peliculas;
    }

    public static void mostrarMenu(List<Pelicula> peliculas) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n📽  MENÚ DE PELÍCULAS");
            System.out.println("====================");
            System.out.println("1. Mostrar todas las películas");
            System.out.println("2. Buscar por género");
            System.out.println("3. Buscar por titulo");
            System.out.println("4. Buscar por director");
            System.out.println("5. Buscar por año");
            System.out.println("6. Exportar a XML");
            System.out.println("7. Importar XML");
            System.out.println("8. Salir");
            System.out.print("\nElige una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    mostrarTodasPeliculas(peliculas);
                    break;
                case 2:
                    buscarPorGenero(peliculas, scanner);
                    break;
                case 3:
                    buscarPorTitulo(peliculas, scanner);
                    break;
                case 4:
                    buscarPorDirector(peliculas, scanner);
                    break;
                case 5:
                    buscarPorAño(peliculas, scanner);
                    break;
                case 6:
                    exportarXML(peliculas, "peliculas.xml");
                    break;
                case 7:
                    List<Pelicula> peliculasImportadas = importarXML("peliculas.xml");
                    if (!peliculasImportadas.isEmpty()) {
                        peliculas.clear();
                        peliculas.addAll(peliculasImportadas);
                        System.out.println("✅ Se importaron " + peliculas.size() + " películas del XML");
                        mostrarTodasPeliculas(peliculas);
                    } else {
                        System.out.println("❌ No se pudieron importar películas del XML.");
                    }
                    break;
                case 8:
                    System.out.println("\n👋 ¡Hasta luego!");
                    salir = true;
                    break;
                default:
                    System.out.println("❌ Opción no válida. Intenta de nuevo.");
            }
        }
        scanner.close();
    }

    public static void mostrarTodasPeliculas(List<Pelicula> peliculas) {
        System.out.println("\n📽️  TODAS LAS PELÍCULAS");
        System.out.println("=".repeat(100));
        peliculas.forEach(p -> System.out.println(p));
        System.out.println("=".repeat(100));
    }

    public static void buscarPorGenero(List<Pelicula> peliculas, Scanner scanner) {
        System.out.print("\nIngresa el género a buscar: ");
        String genero = scanner.nextLine().trim().toLowerCase();

        List<Pelicula> resultados = new ArrayList<>();
        for (Pelicula p : peliculas) {
            if (p.getGenero().toLowerCase().contains(genero)) {
                resultados.add(p);
            }
        }

        if (resultados.isEmpty()) {
            System.out.println("❌ No se encontraron películas del género: " + genero);
        } else {
            System.out.println("\n🎬 Películas encontradas: " + resultados.size());
            System.out.println("-".repeat(100));
            resultados.forEach(p -> System.out.println(p));
        }
    }

    public static void buscarPorTitulo(List<Pelicula> peliculas, Scanner scanner) {
        System.out.print("\nIngresa el titulo a buscar: ");
        String titulo = scanner.nextLine().trim().toLowerCase();
        List<Pelicula> titulos = new ArrayList<>();
        for (Pelicula p : peliculas) {
            if (p.getTitulo().toLowerCase().contains(titulo)) {
                titulos.add(p);
            }
        }

        if (titulos.isEmpty()) {
            System.out.println("❌ No se encontraron películas del título: " + titulo);
        } else {
            System.out.println("\n🎬 Películas encontradas: " + titulos.size());
            System.out.println("-".repeat(100));
            titulos.forEach(p -> System.out.println(p));
        }
    }

    public static void buscarPorDirector(List<Pelicula> peliculas, Scanner scanner) {
        System.out.print("\nIngresa el director a buscar: ");
        String director = scanner.nextLine().trim().toLowerCase();
        List<Pelicula> resultados = new ArrayList<>();
        for (Pelicula p : peliculas) {
            if (p.getDirector().toLowerCase().contains(director)) {
                resultados.add(p);
            }
        }

        if (resultados.isEmpty()) {
            System.out.println("❌ No se encontraron películas del director: " + director);
        } else {
            System.out.println("\n🎬 Películas encontradas: " + resultados.size());
            System.out.println("-".repeat(100));
            resultados.forEach(p -> System.out.println(p));
        }
    }

    public static void buscarPorAño(List<Pelicula> peliculas, Scanner scanner) {
        System.out.print("\nIngresa el año a buscar: ");
        int año = scanner.nextInt();
        scanner.nextLine();

        List<Pelicula> resultados = new ArrayList<>();
        for (Pelicula p : peliculas) {
            if (p.getAño() == año) {
                resultados.add(p);
            }
        }

        if (resultados.isEmpty()) {
            System.out.println("❌ No se encontraron películas del año: " + año);
        } else {
            System.out.println("\n🎬 Películas encontradas: " + resultados.size());
            System.out.println("-".repeat(100));
            resultados.forEach(p -> System.out.println(p));
        }
    }

    public static void exportarXML(List<Pelicula> peliculas, String nombreFichero) {
        try (FileWriter fw = new FileWriter(nombreFichero)) {
            fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            fw.write("<peliculas>\n");
            for (Pelicula p : peliculas) {
                fw.write("        <pelicula>\n");
                fw.write("            <id>" + p.getId() + "</id>\n");
                fw.write("            <titulo>" + p.getTitulo() + "</titulo>\n");
                fw.write("            <director>" + p.getDirector() + "</director>\n");
                fw.write("            <año>" + p.getAño() + "</año>\n");
                fw.write("            <genero>" + p.getGenero() + "</genero>\n");
                fw.write("        </pelicula>\n");
            }
            fw.write("</peliculas>\n");
            System.out.println("✅ Archivo XML exportado a: " + nombreFichero);
        } catch (IOException e) {
            System.out.println("❌ Error al exportar XML: " + e.getMessage());

        }
    }

    public static List<Pelicula> importarXML(String nombreFichero) {
        List<Pelicula> peliculas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            int id = 0, año = 0;
            String titulo = "", director = "", genero = "";

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.contains("<id>")) {
                    id = Integer.parseInt(extraerDatos(linea, "id"));
                } else if (linea.contains("<titulo>")) {
                    titulo = extraerDatos(linea, "titulo");
                } else if (linea.contains("<director>")) {
                    director = extraerDatos(linea, "director");
                } else if (linea.contains("<año>")) {
                    año = Integer.parseInt(extraerDatos(linea, "año"));
                } else if (linea.contains("<genero>")) {
                    genero = extraerDatos(linea, "genero");
                    // Cuando tenemos el género, creamos la película
                    Pelicula p = new Pelicula(id, titulo, director, año, genero);
                    peliculas.add(p);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error al importar XML: " + e.getMessage());
        }
        return peliculas;
    }

    private static String extraerDatos(String linea, String etiqueta) {
        String inicio = "<" + etiqueta + ">";
        String fin = "</" + etiqueta + ">";
        int indexInicio = linea.indexOf(inicio) + inicio.length();
        int indexFin = linea.indexOf(fin);
        return linea.substring(indexInicio, indexFin);
    }
}