package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

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

    @AfterEach
    public void tearDown() 
    {
        // No es estrictamente necesario aquí,
        // pero queda como buena práctica por si luego creas archivos
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