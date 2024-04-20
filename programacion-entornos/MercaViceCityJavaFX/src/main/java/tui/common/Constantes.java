package tui.common;

public class Constantes {


    public static final String SALIENDO = "Saliendo...";

    private Constantes() {
    }


    //Main
    public static final String BIENVENIDO_A_MERCA_VICE_CITY = "Bienvenido a MercaViceCity. ";
    public static final String ASCII_ART = "  __  __                     __     ___                 _ _         \n" +
            " |  \\/  | ___ _ __ ___ __ _  \\ \\   / (_) ___ ___    ___(_) |_ _   _ \n" +
            " | |\\/| |/ _ \\ '__/ __/ _` |  \\ \\ / /| |/ __/ _ \\  / __| | __| | | |\n" +
            " | |  | |  __/ | | (_| (_| |   \\ V / | | (_|  __/ | (__| | |_| |_| |\n" +
            " |_|  |_|\\___|_|  \\___\\__,_|    \\_/  |_|\\___\\___|  \\___|_|\\__|\\__, |\n" +
            "                                                               |___/  ";

    //UILogin
    public static final String ELIGE_UNA_OPCION = "Elige una opcion: ";
    public static final String REGISTRARSE_COMO_CLIENTE = "Registrarse como cliente. ";
    public static final String INDICA_TU_DNI = "Porfavor indica tu dni: ";
    public static final String ADMIN = "admin";
    public static final String DNI_YA_REGISTRADO = "DNI ya registrado";
    public static final String PORFAVOR_INDICA_TU_NOMBRE = "Porfavor indica tu nombre: ";
    public static final String REGISTRARSE = "1. Registrarse";
    public static final String INICIAR_SESION = "2. Iniciar sesión";
    public static final String INICIALIZAR_ARCHIVOS_JSON = "3. Inicializar archivos JSON";
    public static final String SESION_INICIADA = "Sesion iniciada";
    public static final String REGISTRADO_CORRECTAMENTE = "Registrado correctamente";

    //UIAdmin
    public static final String SALIR = "0. Salir";
    public static final String ANADIR_PRODUCTO = "1. Añadir producto";
    public static final String ELIMINAR_PRODUCTO = "2. Eliminar producto por ID";
    public static final String VER_PRODUCTOS = "3. Ver productos";
    public static final String BUSCAR_UN_PRODUCTO = "4. Buscar un producto";
    public static final String AUMENTAR_STOCK_PRODUCTO_POR_ID = "5. Aumentar stock producto por ID";
    public static final String DISMINUIR_STOCK_PRODUCTO_POR_ID = "6. Disminuir stock producto por ID";
    public static final String CAMBIAR_PRECIO_PRODUCTO_POR_ID = "7. Cambiar precio producto por ID";
    public static final String ANADIR_CLIENTE = "8. Añadir cliente";
    public static final String VER_LISTA_CLIENTES = "9. Ver lista clientes";
    public static final String ELIMINAR_CLIENTE_POR_DNI = "10. Eliminar cliente por DNI";
    public static final String CAMBIAR_NOMBRE_CLIENTE = "11. Cambiar nombre cliente.";
    public static final String VER_CLIENTES_ORDENADOS_POR_GASTO = "12. Ver clientes ordenados por gasto.";
    public static final String VER_PRODUCTOS_CON_INGREDIENTES_ADMIN = "13. Ver productos con ingredientes";
    public static final String CAMBIAR_PORCENTAJE_DE_DESCUENTO_CLIENTES_ESPACIALES = "14. Cambiar porcentaje de descuento clientes espaciales";
    public static final String BUSCAR_PRODUCTO_POR_INGREDIENTE = "15. Buscar producto por ingrediente";
    public static final String VER_PRODUCTOS_MAS_COMPRADOS = "16. Ver productos mas comprados";
    public static final String ERROR_ENTRADA_DE_MENU_NO_VALIDA = "Error entrada de menu no valida";

    public static final String INTRODUCE_UN_ID_DE_PRODUCTO = "Introduce un ID de producto: ";
    public static final String INTRODUCE_UN_NOMBRE_DE_PRODUCTO = "Introduce un nombre de producto: ";
    public static final String PRODUCTO_ANADIDO = "Producto añadido";
    public static final String PRODUCTO_NO_ANADIDO = "Producto no añadido revisa los datos";
    public static final String INTRODUCE_EL_PRECIO = "Introduce el precio: ";
    public static final String INTRODUCE_EL_STOCK = "Introduce el stock: ";
    public static final String INDICA_FECHA_DE_CADUCIDAD = "Indica fecha de caducidad [yyy-mm-ddThh:mm:ss]: ";
    public static final String NADA = "";

    public static final String PRODUCTO_ELIMINADO = "Producto eliminado";
    public static final String PRODUCTO_NO_ENCONTRADO = "Producto no encontrado";

    public static final String CANTIDAD_DE_UNIDADES_O_ID_NO_VALIDO = "Cantidad de unidades o id no valido";
    public static final String INDICAR_EL_NUMERO_DE_NUEVAS_UNIDADES = "Indicar el numero de nuevas unidades: ";
    public static final String UNIDADES_ANADIDAS = "Unidades añadidas";
    public static final String INTRODUCE_EL_NUMERO_DE_UNIDADES_A_RETIRAR = "Introduce el numero de unidades a retirar: ";
    public static final String UNIDADES_RETIRADAS = "Unidades retiradas";

    public static final String INTRODUCE_EL_NUEVO_PRECIO = "Introduce el nuevo precio: ";
    public static final String PRECIO_MODIFICADO = "Precio modificado";
    public static final String PRECIO_O_ID_INVALIDO = "Precio o id invalido";

    public static final String INTRODUCE_EL_DNI_DEL_CLIENTE_A_ELIMINAR = "Introduce el dni del cliente a eliminar: ";
    public static final String CLIENTE_ELIMINADO = "Cliente eliminado";
    public static final String CLIENTE_NO_ENCONTRADO = "Cliente no encontrado";

    public static final String REGISTRAR_NUEVO_CLIENTE = "Registrar nuevo cliente";
    public static final String INDICA_DNI_DEL_CLIENTE = "Indica DNI del cliente: ";
    public static final String INDICA_EL_NOMBRE_DEL_CLIENTE = "Indica el nombre del cliente: ";
    public static final String CLIENTE_ESPACIAL_INTRODUCE_1_SI_LO_ES = "¿Cliente espacial? Introduce 1 si lo es: ";

    public static final String LISTA_DE_PRODUCTOS = "Lista de productos ";

    public static final String INDICA_UN_NUEVO_PORCENTAJE_DE_DESCUENTO = "Indica un nuevo porcentaje de descuento: ";
    public static final String DESCUENTO_APLICADO_A_TODOS_LOS_CLIENTES_ESPACIALES = "Descuento aplicado a todos los clientes espaciales";
    public static final String DESCUENTO_INTRODUCIDO_NO_VALIDO = "Descuento introducido no valido ha de ser entre 5 y 90";

    //UICliente
    public static final String BIENVENIDO_DE_NUEVO = "Bienvenido de nuevo ";

    public static final String ANADIR_MONEDERO = "1. Añadir monedero";
    public static final String INTRODUCE_EL_NUMERO_DEL_MONEDERO = "Introduce el numero del monedero: ";
    public static final String INTRODUCE_EL_IMPORTE_DEL_MONEDERO = "Introduce el importe del monedero: ";
    public static final String MONEDERO_ANADIDO = "Monedero añadido";
    public static final String MONEDRO_NO_ANADIDO = "Monedro no añadido";

    public static final String ANADIR_PRODUCTO_AL_CARRITO_POR_ID = "2. Añadir producto al carrito por ID";
    public static final String CANTIDAD_DISPONIBLE = "Cantidad disponible: ";
    public static final String INTRODUCE_CANTIDAD = "Introduce cantidad: ";
    public static final String PRODUCTO_ANADIDO_AL_CARRITO = "Producto añadido al carrito";
    public static final String PRODUCTO_NO_ANADIDO_AL_CARRITO = "Producto no añadido al carrito";

    public static final String VER_PRODUCTOS_DISPONIBLES = "3. Ver productos disponibles";
    public static final String BUSCAR_PRODUCTOS_DISPONIBLES_POR_NOMBRE = "4. Buscar productos disponibles por nombre";

    public static final String VER_MONEDEROS = "6. Ver monederos";

    public static final String VER_LISTA_DE_LA_COMPRA = "5. Ver lista de la compra";

    public static final String REALIZAR_COMPRA = "7. Realizar compra";
    public static final String INICIANDO_PROCESO_DE_PAGO = "Iniciando proceso de pago.";
    public static final String COMPRA_REALIZADA = "Compra realizada";
    public static final String NO_SE_HA_PODIDO_REALIZAR_LA_COMPRA = "No se ha podido realizar la compra";

    public static final String CAMBIAR_NOMBRE = "8. Cambiar nombre";
    public static final String NOMBRE_CAMBIADO_CORRECTAMENTE = "Nombre cambiado correctamente";
    public static final String NO_SE_HA_PODIDO_CAMBIAR_EL_NOMBRE = "No se ha podido cambiar el nombre";

    public static final String ANADIR_ALERGENO = "9. Añadir alergeno";
    public static final String ALERGENO = "Alergeno ";
    public static final String ANADIDO = " añadido";

    public static final String VER_GASTO_TOTAL_DE_TODAS_LAS_COMPRAS = "10. Ver gasto total de todas las compras";
    public static final String SU_GASTO_TOTAL_ES_DE = "Su gasto total es de: ";
    public static final String EURO = "€";

    public static final String VER_PRODUCTOS_CON_INGREDIENTES_CLIENT = "11. Ver productos con ingredientes.";

    //UIIngredientes
    public static final String NO_ANADIR_MAS_INGREDIENTES = "0. No añadir mas ingredientes";
    public static final String ANADIR_INGREDIENTE = "1. Añadir ingrediente";
    public static final String INTRODUCE_UNA_OPCION = "Introduce una opcion: ";
    public static final String INDICA_NOMBRE_DE_INGREDIENTE = "Indica nombre de ingrediente: ";
}
