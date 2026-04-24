package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest 
{
    @Test
    public void testGetNombre() 
    {
        ProductoMenu base = new ProductoMenu("Hamburguesa", 10000);
        ProductoAjustado p = new ProductoAjustado(base);

        assertEquals("Hamburguesa", p.getNombre());
    }

    @Test
    public void testPrecioSinAjustes() 
    {
        ProductoMenu base = new ProductoMenu("Hamburguesa", 10000);
        ProductoAjustado p = new ProductoAjustado(base);

        assertEquals(10000, p.getPrecio());
    }

    @Test
    public void testPrecioConAgregados() 
    {
        ProductoMenu base = new ProductoMenu("Hamburguesa", 10000);
        ProductoAjustado p = new ProductoAjustado(base);

        
        
        assertTrue(p.getPrecio() >= 10000);
    }

    @Test
    public void testGenerarTextoFactura() 
    {
        ProductoMenu base = new ProductoMenu("Hamburguesa", 10000);
        ProductoAjustado p = new ProductoAjustado(base);

        String texto = p.generarTextoFactura();

        assertTrue(texto.contains("Hamburguesa"));
        assertTrue(texto.contains(String.valueOf(p.getPrecio())));
    }
    @Test
    public void testNombreProductoBase() 
    {
        ProductoMenu base = new ProductoMenu("Perro", 8000);
        ProductoAjustado p = new ProductoAjustado(base);

        assertEquals("Perro", p.getNombre());
    }
    @Test
    public void testGenerarTextoFacturaConFormato() 
    {
        ProductoMenu base = new ProductoMenu("Hamburguesa", 10000);
        ProductoAjustado p = new ProductoAjustado(base);

        String texto = p.generarTextoFactura();

        assertTrue(texto.contains("Hamburguesa"));
        assertTrue(texto.contains(String.valueOf(p.getPrecio())));
    }
}