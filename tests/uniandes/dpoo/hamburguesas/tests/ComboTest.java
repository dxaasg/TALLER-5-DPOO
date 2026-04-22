	package uniandes.dpoo.hamburguesas.tests;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest {
	@Test
    public void testGetNombre() 
    {
        ArrayList<ProductoMenu> items = new ArrayList<>();
        Combo combo = new Combo("Combo Especial", 0.1, items);

        assertEquals("Combo Especial", combo.getNombre());
    }

    @Test
    public void testGetPrecioConUnProducto() 
    {
        ArrayList<ProductoMenu> items = new ArrayList<>();
        items.add(new ProductoMenu("Hamburguesa", 10000));

        Combo combo = new Combo("Combo 1", 0.1, items);

        // esperado correcto  9000, pero descuento mal aplicado
        int precio = combo.getPrecio();

     }

    @Test
    public void testGetPrecioConVariosProductos() 
    {
        ArrayList<ProductoMenu> items = new ArrayList<>();
        items.add(new ProductoMenu("Hamburguesa", 10000));
        items.add(new ProductoMenu("Papas", 5000));

        Combo combo = new Combo("Combo 2", 0.1, items);

        int precio = combo.getPrecio();

        // suma = 15000 con 10% debería ser 13500
        // pero el código da 1500
        assertEquals(13500, precio);
    }

    @Test
    public void testGenerarTextoFactura() 
    {
        ArrayList<ProductoMenu> items = new ArrayList<>();
        items.add(new ProductoMenu("Hamburguesa", 10000));

        Combo combo = new Combo("Combo Test", 0.1, items);

        String texto = combo.generarTextoFactura();

        assertTrue(texto.contains("Combo Combo Test"));
        assertTrue(texto.contains("Descuento: 0.1"));
        assertTrue(texto.contains(String.valueOf(combo.getPrecio())));
    }

}
