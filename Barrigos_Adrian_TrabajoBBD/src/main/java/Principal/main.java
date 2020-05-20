package Principal;

import DAO.ClientesDAO;
import menu.MenuMetodos;

/**
 *
 * @author adrian
 */
public class main1 {

            static ClientesDAO clientes = new ClientesDAO();
            static MenuMetodos menu = new MenuMetodos();
         
    public static void main(String[] args) {
        if (clientes.getConexion() == null) {
            System.err.println("Fin del programa . No se pudo conectar.");
            System.exit(0);
        } else{
            System.out.println("Conexion realizada con exito\n");
        }
        menu.menu();
    }
    
}
