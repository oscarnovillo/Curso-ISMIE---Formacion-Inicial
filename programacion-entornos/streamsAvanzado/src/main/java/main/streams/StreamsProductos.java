package main.streams;

import pedidos.dao.modelo.Producto;
import pedidos.servicios.ServiciosPedido;

import java.util.*;
import java.util.stream.Collectors;

public class StreamsProductos {
    ServiciosPedido sp = new ServiciosPedido();
    List<Producto> productos = sp.todosProductos();

    // con reduce y con sorted
    public void productoMasCaro() {
        System.out.println("\nProducto más caro --------------------------------------------------------------------------------------------------------------");
        System.out.println("Con reduce");
        Producto productoMasCaroReduce =
                productos.stream()
                        .reduce((p1, p2) -> p1.getPrecio() > p2.getPrecio()?p1:p2)
                        .orElse(null);
        System.out.println(productoMasCaroReduce);
        System.out.println("Con sorted");
        Producto productoMasCaroSorted =
                productos.stream().min((p1, p2) -> p2.getPrecio() - p1.getPrecio())
                        .orElse(null);
        System.out.println(productoMasCaroSorted);
    }

    //con reduce y con sorted
    public void productoMasBarato() {
        System.out.println("\nProducto más barato --------------------------------------------------------------------------------------------------------------");
        System.out.println("Con reduce");
        Producto productoMasBaratoReduce =
                productos.stream()
                        .reduce((p1, p2) -> p1.getPrecio() < p2.getPrecio()?p1:p2)
                        .orElse(null);
        System.out.println(productoMasBaratoReduce);
        System.out.println("Con sorted");
        Producto productoMasBaratoSorted =
                productos.stream()
                        .sorted((p1,p2) -> p1.getPrecio() - p2.getPrecio())
                        .findFirst()
                        .orElse(null);
        System.out.println(productoMasBaratoSorted);
    }


    public void mediaPrecioTodosLosProductos() {
        System.out.println("\nMedia precio de todos los productos --------------------------------------------------------------------------------------------------------------");
        double mediaPrecio = productos.stream()
                .mapToInt(Producto::getPrecio)
                .average().orElse(-1);
        System.out.println(mediaPrecio);
    }


    // un grupo de producto por cada franja de 10, de 0 a 10, 11 a 20, etc...
    public void productosAgrupadosPorRangoPrecio10en10() {
        System.out.println("\nProductos agrupados por rango de precio de 10 en 10 --------------------------------------------------------------------------------------------------------------");

        Map<Double, List<Producto>> resultMap = productos.stream()
                .sorted(Comparator.comparingInt(Producto::getPrecio))
                .collect(Collectors.groupingBy(p -> Math.ceil(p.getPrecio()/10.0),Collectors.toList()));

        resultMap = new TreeMap<>(resultMap);
        resultMap.forEach((k,v) ->{
            if(k != 0){
                System.out.println("Rango de precios desde " + (k*10-9) + " hasta " + (k*10));
            }else {
                System.out.println("Productos de precio 0");
            }
            System.out.println(v);
        });

    }

    // de los productos que tenga precio de 11 a 20, indicar cuales tienen stock mayor que 5
    public void productosConPrecio11a20YStockMayor5() {
        System.out.println("\nProductos de precio entre 11 y 20 que tienen un stock mayor que 5 --------------------------------------------------------------------------------------------------------------");

        List<Producto> productoList = productos
                .stream()
                .filter(p-> p.getPrecio() >= 11 && p.getPrecio() <= 20 && p.getStock() >5)
                .collect(Collectors.toList());
        System.out.println(productoList);
    }
}
