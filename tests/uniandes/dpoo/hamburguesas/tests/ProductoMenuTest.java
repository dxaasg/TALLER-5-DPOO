package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest 
{
    @Test
    public void testGetNombre() 
    {
        ProductoMenu p = new ProductoMenu("Hamburguesa", 10000);

        assertEquals("Hamburguesa", p.getNombre());
    }

    @Test
    public void testGetPrecio() 
    {
        ProductoMenu p = new ProductoMenu("Hamburguesa", 10000);

        assertEquals(10000, p.getPrecio());
    }

    @Test
    public void testGenerarTextoFactura() 
    {
        ProductoMenu p = new ProductoMenu("Hamburguesa", 10000);

        String texto = p.generarTextoFactura();

        assertTrue(texto.contains("Hamburguesa"));
        assertTrue(texto.contains("10000"));
    }

    @Test
    public void testProductoConPrecioCero() 
    {
        ProductoMenu p = new ProductoMenu("Agua", 0);

        assertEquals(0, p.getPrecio());
    }

    @Test
    public void testProductoConNombreVacio() 
    {
        ProductoMenu p = new ProductoMenu("", 5000);

        assertEquals("", p.getNombre());
    }
}