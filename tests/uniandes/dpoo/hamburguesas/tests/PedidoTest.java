package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest 
{
    @Test
    public void testCrearPedido() 
    {
        Pedido p = new Pedido("Daniel", "Calle 123");

        assertEquals("Daniel", p.getNombreCliente());
        assertTrue(p.getIdPedido() >= 0);
    }

    @Test
    public void testAgregarProductoYPrecioTotal() 
    {
        Pedido p = new Pedido("Daniel", "Calle 123");

        ProductoMenu prod = new ProductoMenu("Hamburguesa", 10000);
        p.agregarProducto(prod);

        int total = p.getPrecioTotalPedido();

        // 10000 + 19% = 11900
        assertEquals(11900, total);
    }

    @Test
    public void testPrecioConVariosProductos() 
    {
        Pedido p = new Pedido("Daniel", "Calle 123");

        p.agregarProducto(new ProductoMenu("Hamburguesa", 10000));
        p.agregarProducto(new ProductoMenu("Papas", 5000));

        // Neto = 15000
        // IVA = 2850
        // Total = 17850
        assertEquals(17850, p.getPrecioTotalPedido());
    }

    @Test
    public void testGenerarTextoFactura() 
    {
        Pedido p = new Pedido("Daniel", "Calle 123");
        p.agregarProducto(new ProductoMenu("Hamburguesa", 10000));

        String factura = p.generarTextoFactura();

        assertTrue(factura.contains("Cliente: Daniel"));
        assertTrue(factura.contains("Dirección: Calle 123"));
        assertTrue(factura.contains("Precio Neto"));
        assertTrue(factura.contains("IVA"));
        assertTrue(factura.contains("Precio Total"));
    }

    @Test
    public void testGuardarFactura() throws FileNotFoundException 
    {
        Pedido p = new Pedido("Daniel", "Calle 123");
        p.agregarProducto(new ProductoMenu("Hamburguesa", 10000));

        File archivo = new File("factura_test.txt");
        p.guardarFactura(archivo);

        // Leer archivo
        Scanner sc = new Scanner(archivo);
        String contenido = "";

        while (sc.hasNextLine()) {
            contenido += sc.nextLine() + "\n";
        }
        sc.close();

        assertTrue(contenido.contains("Daniel"));
        assertTrue(contenido.contains("Hamburguesa"));
        assertTrue(contenido.contains("11900"));
        // limpiar archivo después del test
        archivo.delete();
    }
    @Test
    public void testAgregarMultiplesProductosIncrementaPrecio() 
    {
        Pedido p = new Pedido("Daniel", "Calle 123");

        p.agregarProducto(new ProductoMenu("Hamburguesa", 10000));
        int total1 = p.getPrecioTotalPedido();

        p.agregarProducto(new ProductoMenu("Papas", 5000));
        int total2 = p.getPrecioTotalPedido();

        assertTrue(total2 > total1);
    }
    @Test
    public void testPedidoSinProductos() 
    {
        Pedido p = new Pedido("Daniel", "Calle 123");

        String factura = p.generarTextoFactura();

        assertTrue(factura.contains("Precio Neto:  0"));
        assertTrue(factura.contains("IVA:          0"));
        assertTrue(factura.contains("Precio Total: 0"));
    }
    @Test
    public void testGenerarTextoFacturaValoresCorrectos() 
    {
        Pedido p = new Pedido("Daniel", "Calle 123");
        p.agregarProducto(new ProductoMenu("Hamburguesa", 10000));

        String factura = p.generarTextoFactura();

        assertTrue(factura.contains("10000")); // neto
        assertTrue(factura.contains("1900"));  // IVA
        assertTrue(factura.contains("11900")); // total
    }
}