package main.streams;

import videoclub.dao.modelo.*;
import videoclub.servicios.ServiciosVideoclub;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamsVideoClub {
    ServiciosVideoclub sv = new ServiciosVideoclub();
    List<Socio> socios = sv.getTodosLosSocios();
    List<Producto> productos = sv.getTodosProductos();
    List<Pelicula> peliculas = sv.getTodasPeliculas();
    List<Videojuego> videojuegos = sv.getTodosVideoJuegos();
    List<Documental> documentales = sv.getTodosDocumentales();

    public void numeroSociosSancionados() {
        System.out.println("\nNumero de socios sancionados --------------------------------------------------------------------------------------------------------------");
        long numeroSociosSancionados = socios.stream()
                .filter(Socio::isSancionado)
                .count();
        System.out.println("Socios sancionados: " + numeroSociosSancionados);
    }

    public void mediaEdadDeSociosSancionados() {
        System.out.println("\nMedia de edad de los socios sancionados --------------------------------------------------------------------------------------------------------------");
        double mediaEdadSancionados = socios.stream()
                .filter(Socio::isSancionado)
                .mapToInt(Socio::getEdad)
                .average().orElse(-1);
        System.out.println("La media de los socios sancionados es: " + mediaEdadSancionados);
    }

    /**
     * DIFICIL
     **/
    public void listaDiezProductosMasAlquilados() {
        System.out.println("\nLista de los 10 productos más alquilados --------------------------------------------------------------------------------------------------------------");
        productos.stream()
                .sorted((o1, o2) -> {
                    double o1Alquilado = productos.stream().filter(p -> p.equals(o1)).mapToInt(Producto::getCantidadAlquilada).sum();
                    double o2Alquilado = productos.stream().filter(p -> p.equals(o2)).mapToInt(Producto::getCantidadAlquilada).sum();
                    return Double.compare(o2Alquilado, o1Alquilado);
                }).limit(10).forEach(System.out::println);
    }

    // numero de Peliculas Alquiladas, de Documentales y Videojuegos.
    public void numeroProductosAlquiladosPorTipo() {
        System.out.println("\nNumero de productos alquilados por tipo --------------------------------------------------------------------------------------------------------------");
        productos.stream()
                .map(Producto::getClass)
                .distinct()
                .forEach(s -> {
                    long contador = productos.stream()
                            .filter(p -> p.getClass().equals(s))
                            .mapToInt(Producto::getCantidadAlquilada)
                            .sum();
                    String[] typeSplit = s.toString().split("\\.");
                    String type = typeSplit[typeSplit.length - 1];
                    System.out.println(type + " : " + contador);
                });
    }

    public void todosLosActoresDistintosDeTodasLasPeliculas() {
        System.out.println("\nTodos los actores distintos de las peliculas --------------------------------------------------------------------------------------------------------------");
        peliculas.stream()
                .flatMap(p -> p.getActores().stream())
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }

    public void peliculaConMasActores() {
        System.out.println("\nPelícula con más actores --------------------------------------------------------------------------------------------------------------");
        Pelicula peliculaMasActores = peliculas.stream()
                .max(Comparator.comparingInt(p -> p.getActores().size())).orElse(null);
        if (peliculaMasActores != null) {
            System.out.println(peliculaMasActores + " actores: " + peliculaMasActores.getActores().size());
        } else {
            System.out.println("ERROR Lista de Actores es NULL");
        }
    }


    //el listado de productos ordenados de mayor a menor según su valoración media.
    public void productoConSuValoracionMediaOrdenadosDeMayoraMenor() {
        System.out.println("\nListado de productos ordenador de mayor a menor según su valoración media --------------------------------------------------------------------------------------------------------------");
        productos.stream()
                .sorted((o1, o2) -> Double.compare(o2.getValoracionMedia(), o1.getValoracionMedia()))
                .forEach(p -> System.out.println(p.getTitulo() + " valoración media: " + p.getValoracionMedia()));


    }

    public void las10PeliculasMejorValoradas() {
        System.out.println("\nLas 10 películas mejor valoradas --------------------------------------------------------------------------------------------------------------");
        peliculas.stream()
                .sorted((p1, p2) -> Double.compare(p2.getValoracionMedia(), p1.getValoracionMedia()))
                .limit(10)
                .forEach(p -> System.out.println(p.getTitulo() + " valoración media " + p.getValoracionMedia()));
    }

    public void los10VideoJuegosMejorValorados() {
        System.out.println("\nLas 10 videojuegos mejor valorados --------------------------------------------------------------------------------------------------------------");
        videojuegos.stream()
                .sorted((v1, v2) -> Double.compare(v2.getValoracionMedia(), v1.getValoracionMedia()))
                .limit(10)
                .forEach(p -> System.out.println(p.getTitulo() + " valoración media " + p.getValoracionMedia()));
    }


    // el numero de DVD y Videos que hay.
    public void numeroDocumentalesyPeliculasSegunSuFormato() {
        System.out.println("\nNumero de documentales y peliculas segun su formato --------------------------------------------------------------------------------------------------------------");
        Arrays.stream(FormatoPelicula.values()).
                forEach(f -> {
                            long peliculasEnFormato = peliculas.stream()
                                    .filter(p -> p.getFormato().equals(f))
                                    .count();
                            long documentalesEnFormato = documentales.stream()
                                    .filter(d -> d.getFormato().equals(f))
                                    .count();
                            System.out.println("Formato " + f + ": peliculas = " + peliculasEnFormato + ", documentales = " + documentalesEnFormato);
                        }
                );
    }

    // conseguir un String con todos los faricantes distintos de videojuegos separados por ,
    public void todosLosFabricantesDistintosDeVideoJuegosEnUnSoloString() {
        System.out.println("\nTodos los fabricantes distintos de videojuegos en un solo string --------------------------------------------------------------------------------------------------------------");
        String fabricantes = videojuegos.stream()
                .map(Videojuego::getFabricante)
                .collect(Collectors.joining(", "));
        System.out.println(fabricantes);
    }
}
