package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.PrintWriter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.*;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

public class RestauranteTest 
{
    private Restaurante restaurante;

    private File ingredientes;
    private File menu;
    private File combos;

    @BeforeEach
    public void setUp() 
    {
        restaurante = new Restaurante();

        ingredientes = new File("data/ingredientes.txt");
        menu = new File("data/menu.txt");
        combos = new File("data/combos.txt");
    } 

    

    @Test
    public void testCargarInformacion() throws Exception 
    {
        restaurante.cargarInformacionRestaurante(ingredientes, menu, combos);

        assertEquals(15, restaurante.getIngredientes().size());
        assertEquals(22, restaurante.getMenuBase().size());
        assertEquals(4, restaurante.getMenuCombos().size());
    }

    @Test
    public void testGetters() 
    {
        assertNotNull(restaurante.getPedidos());
        assertNotNull(restaurante.getIngredientes());
        assertNotNull(restaurante.getMenuBase());
        assertNotNull(restaurante.getMenuCombos());
    }

    @Test
    public void testIniciarPedido() throws Exception 
    {
        restaurante.iniciarPedido("Daniel", "Calle 123");

        assertNotNull(restaurante.getPedidoEnCurso());
        assertEquals("Daniel", restaurante.getPedidoEnCurso().getNombreCliente());
    }

    @Test
    public void testYaHayPedido() throws Exception 
    {
        restaurante.iniciarPedido("Daniel", "Calle 123");

        assertThrows(YaHayUnPedidoEnCursoException.class, () -> {
            restaurante.iniciarPedido("Juan", "Otra calle");
        });
    }

    @Test
    public void testCerrarSinPedido() 
    {
        assertThrows(NoHayPedidoEnCursoException.class, () -> {
            restaurante.cerrarYGuardarPedido();
        });
    }

    @Test
    public void testFlujoCompleto() throws Exception 
    {
        restaurante.cargarInformacionRestaurante(ingredientes, menu, combos);

        restaurante.iniciarPedido("Daniel", "Calle 123");

        restaurante.getPedidoEnCurso().agregarProducto(
            restaurante.getMenuBase().get(0)
        );

        restaurante.cerrarYGuardarPedido();

        assertNull(restaurante.getPedidoEnCurso());
    }

    //  COMBOS
    @Test
    public void testCombosCargados() throws Exception 
    {
        restaurante.cargarInformacionRestaurante(ingredientes, menu, combos);

        assertEquals("combo corral", restaurante.getMenuCombos().get(0).getNombre());
    }

    @Test
    void testCargarIngredientesRepetidosLanzaExcepcion() throws Exception {
        File archivoIngredientesMalos = File.createTempFile("ingredientes_malos", ".txt");
        archivoIngredientesMalos.deleteOnExit();
        PrintWriter writerIng = new PrintWriter(archivoIngredientesMalos);
        writerIng.println("tomate;1000");
        writerIng.println("tomate;1500"); 
        writerIng.close(); 
        File archivoMenu = File.createTempFile("menu", ".txt"); 
        archivoMenu.deleteOnExit();
        File archivoCombos = File.createTempFile("combos", ".txt");
        archivoCombos.deleteOnExit();
        assertThrows(IngredienteRepetidoException.class, () -> {
            restaurante.cargarInformacionRestaurante(archivoIngredientesMalos, archivoMenu, archivoCombos);
        }, "El sistema permitió cargar un archivo txt con ingredientes repetidos y no lanzó la excepción.");
    }
    @Test
    void testCargarMenuRepetido() throws Exception {
        File menuMalo = File.createTempFile("menu_malo", ".txt");
        menuMalo.deleteOnExit();

        PrintWriter writer = new PrintWriter(menuMalo);
        writer.println("hamburguesa;10000");
        writer.println("hamburguesa;12000"); // repetido
        writer.close();

        File ingredientesOK = File.createTempFile("ing", ".txt");
        ingredientesOK.deleteOnExit();

        File combosOK = File.createTempFile("combos", ".txt");
        combosOK.deleteOnExit();

        assertThrows(ProductoRepetidoException.class, () -> {
            restaurante.cargarInformacionRestaurante(ingredientesOK, menuMalo, combosOK);
        });
    }
    @Test
    void testCombosRepetidos() throws Exception {
        File menuOK = File.createTempFile("menu", ".txt");
        menuOK.deleteOnExit();

        PrintWriter writerMenu = new PrintWriter(menuOK);
        writerMenu.println("hamburguesa;10000");
        writerMenu.close();

        File combosMalos = File.createTempFile("combos_malos", ".txt");
        combosMalos.deleteOnExit();

        PrintWriter writer = new PrintWriter(combosMalos);
        writer.println("combo1;10%;hamburguesa");
        writer.println("combo1;20%;hamburguesa"); // repetido
        writer.close();

        File ingredientesOK = File.createTempFile("ing", ".txt");
        ingredientesOK.deleteOnExit();

        assertThrows(ProductoRepetidoException.class, () -> {
            restaurante.cargarInformacionRestaurante(ingredientesOK, menuOK, combosMalos);
        });
    }

    
    @Test
    public void testPedidoNoSeGuarda() throws Exception 
    {
        restaurante.cargarInformacionRestaurante(ingredientes, menu, combos);

        restaurante.iniciarPedido("Daniel", "Calle 123");

        restaurante.getPedidoEnCurso().agregarProducto(
            restaurante.getMenuBase().get(0)
        );

        restaurante.cerrarYGuardarPedido();

        
        assertTrue(restaurante.getPedidos().size() > 0);
    }
}