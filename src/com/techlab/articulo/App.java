package com.techlab.articulo;

import java.util.ArrayList;
import java.util.Scanner;

import com.techlab.articulo.model.Articulo;
import com.techlab.articulo.model.ArticuloElectronico;
import com.techlab.articulo.model.ArticuloAlimenticio;
import com.techlab.articulo.model.Categoria;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Articulo> articulos = new ArrayList<>();
        ArrayList<Categoria> categorias = new ArrayList<>();

        precargarCategorias(categorias);

        int opcion;

        do {
            System.out.println("======================================================");
            System.out.println("SISTEMA DE ARTÍCULOS - CLASE 4 (HERENCIA Y TO_STRING)");
            System.out.println("======================================================");
            System.out.println("1 - Ingresar artículo");
            System.out.println("2 - Listar artículos");
            System.out.println("3 - Consultar un artículo");
            System.out.println("4 - Modificar un artículo");
            System.out.println("5 - Eliminar un artículo");
            System.out.println("6 - Listar categorías");
            System.out.println("0 - Salir");
            System.out.println("======================================================");

            opcion = leerEntero(scanner, "Ingrese una opción: ");

            switch (opcion) {
                case 1:
                    ingresarArticulo(scanner, articulos, categorias);
                    break;
                case 2:
                    listarArticulos(articulos);
                    break;
                case 3:
                    consultarArticulo(scanner, articulos);
                    break;
                case 4:
                    modificarArticulo(scanner, articulos, categorias);
                    break;
                case 5:
                    eliminarArticulo(scanner, articulos);
                    break;
                case 6:
                    listarCategorias(categorias);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    public static void precargarCategorias(ArrayList<Categoria> categorias) {

        Categoria[] categoriasIniciales = {
            new Categoria(
                1,
                "Electrónica",
                "Productos tecnológicos y electrónicos"
            ),
            new Categoria(
                2,
                "Periféricos",
                "Accesorios para computadora"
            ),
            new Categoria(
                3,
                "Alimentos",
                "Productos alimenticios"
            ),
            new Categoria(
                4,
                "Limpieza",
                "Artículos de limpieza del hogar"
            )
        };

        for (Categoria categoria : categoriasIniciales) {
            categorias.add(categoria);
        }
    }

    public static Articulo buscarArticuloPorCodigo(
            ArrayList<Articulo> articulos,
            int codigo) {

        for (Articulo articulo : articulos) {
            if (articulo.getCodigo() == codigo) {
                return articulo;
            }
        }

        return null;
    }

    public static Categoria buscarCategoriaPorCodigo(
            ArrayList<Categoria> categorias,
            int codigo) {

        for (Categoria categoria : categorias) {
            if (categoria.getCodigo() == codigo) {
                return categoria;
            }
        }

        return null;
    }

    public static Categoria pedirCategoriaExistente(
            Scanner scanner,
            ArrayList<Categoria> categorias) {

        while (true) {
            int codigo = leerEntero(
                    scanner,
                    "Ingrese el código de la categoría: "
            );

            Categoria categoria =
                    buscarCategoriaPorCodigo(categorias, codigo);

            if (categoria != null) {
                return categoria;
            }

            System.out.println("La categoría no existe.");
        }
    }

    public static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String entrada = scanner.nextLine();
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número entero válido.");
            }
        }
    }

    public static int leerEnteroNoNegativo(Scanner scanner, String mensaje) {
        while (true) {
            int valor = leerEntero(scanner, mensaje);

            if (valor >= 0) {
                return valor;
            }

            System.out.println("El valor no puede ser negativo.");
        }
    }

    public static double leerDoubleNoNegativo(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String entrada = scanner.nextLine();

                double valor = Double.parseDouble(entrada);

                if (valor >= 0 && Double.isFinite(valor)) {
                    return valor;
                }

                System.out.println("Debe ingresar un número no negativo y finito.");

            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }

    public static String leerTextoNoVacio(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);

            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("El texto no puede estar vacío.");
        }
    }

    public static void ingresarArticulo(
            Scanner scanner,
            ArrayList<Articulo> articulos,
            ArrayList<Categoria> categorias) {

        System.out.println("--- INGRESAR ARTÍCULO ---");

        int tipo;

        do {
            System.out.println("1 - Artículo electrónico");
            System.out.println("2 - Artículo alimenticio");

            tipo = leerEntero(
                    scanner,
                    "Seleccione el tipo de artículo: "
            );

            if (tipo != 1 && tipo != 2) {
                System.out.println("Tipo de artículo inválido.");
            }

        } while (tipo != 1 && tipo != 2);

        int codigo = leerEnteroNoNegativo(
                scanner,
                "Ingrese el código del artículo: "
        );

        if (buscarArticuloPorCodigo(articulos, codigo) != null) {
            System.out.println("Ya existe un artículo con ese código.");
            return;
        }

        String nombre = leerTextoNoVacio(
                scanner,
                "Ingrese el nombre del artículo: "
        );

        double precio = leerDoubleNoNegativo(
                scanner,
                "Ingrese el precio del artículo: "
        );

        System.out.println("--- CATEGORÍAS DISPONIBLES ---");

        for (Categoria categoria : categorias) {
            System.out.println(
                    categoria.getCodigo()
                    + " - "
                    + categoria.getNombre()
            );
        }

        Categoria categoria =
                pedirCategoriaExistente(scanner, categorias);

        Articulo articulo;

        if (tipo == 1) {

            int garantiaMeses = leerEnteroNoNegativo(
                    scanner,
                    "Ingrese la garantía en meses: "
            );

            articulo = new ArticuloElectronico(
                    codigo,
                    nombre,
                    precio,
                    categoria,
                    garantiaMeses
            );

        } else {

            int diasParaVencimiento = leerEnteroNoNegativo(
                    scanner,
                    "Ingrese los días para vencimiento: "
            );

            articulo = new ArticuloAlimenticio(
                    codigo,
                    nombre,
                    precio,
                    categoria,
                    diasParaVencimiento
            );
        }

        articulos.add(articulo);

        System.out.println("Artículo ingresado correctamente.");
        System.out.println("Resumen del objeto creado:");
        System.out.println(articulo);
    }

    public static void listarArticulos(
            ArrayList<Articulo> articulos) {

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        for (Articulo articulo : articulos) {
            System.out.println(articulo);
        }
    }

    public static void consultarArticulo(
            Scanner scanner,
            ArrayList<Articulo> articulos) {

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEntero(
                scanner,
                "Ingrese el código del artículo a consultar: "
        );

        Articulo articulo = buscarArticuloPorCodigo(articulos, codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            return;
        }

        System.out.println(articulo);
        System.out.println(
                "Detalle específico: " + articulo.getDetalleEspecifico()
        );
    }

    public static void modificarArticulo(
            Scanner scanner,
            ArrayList<Articulo> articulos,
            ArrayList<Categoria> categorias) {

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEntero(
                scanner,
                "Ingrese el código del artículo a modificar: "
        );

        Articulo articulo = buscarArticuloPorCodigo(articulos, codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            return;
        }

        String nuevoNombre = leerTextoNoVacio(
                scanner,
                "Ingrese el nuevo nombre: "
        );

        double nuevoPrecio = leerDoubleNoNegativo(
                scanner,
                "Ingrese el nuevo precio: "
        );

        System.out.println("--- CATEGORÍAS DISPONIBLES ---");

        for (Categoria categoria : categorias) {
            System.out.println(
                    categoria.getCodigo()
                    + " - "
                    + categoria.getNombre()
            );
        }

        Categoria nuevaCategoria =
                pedirCategoriaExistente(scanner, categorias);

        articulo.setNombre(nuevoNombre);
        articulo.setPrecio(nuevoPrecio);
        articulo.setCategoria(nuevaCategoria);

        if (articulo instanceof ArticuloElectronico) {

            int nuevaGarantia = leerEnteroNoNegativo(
                    scanner,
                    "Ingrese la nueva garantía en meses: "
            );

            ArticuloElectronico electronico =
                    (ArticuloElectronico) articulo;

            electronico.setGarantiaMeses(nuevaGarantia);

        } else if (articulo instanceof ArticuloAlimenticio) {

            int nuevosDias = leerEnteroNoNegativo(
                    scanner,
                    "Ingrese los nuevos días para vencimiento: "
            );

            ArticuloAlimenticio alimenticio =
                    (ArticuloAlimenticio) articulo;

            alimenticio.setDiasParaVencimiento(nuevosDias);
        }

        System.out.println("Artículo modificado correctamente.");
    }

    public static void eliminarArticulo(
            Scanner scanner,
            ArrayList<Articulo> articulos) {

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos cargados.");
            return;
        }

        int codigo = leerEntero(
                scanner,
                "Ingrese el código del artículo a eliminar: "
        );

        Articulo articulo = buscarArticuloPorCodigo(articulos, codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            return;
        }

        articulos.remove(articulo);

        System.out.println("Artículo eliminado correctamente.");
    }

    public static void listarCategorias(
            ArrayList<Categoria> categorias) {

        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
    }
}