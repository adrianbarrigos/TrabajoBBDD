
package menu;

import DAO.ClientesDAO;
import POJO.POJO;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author adrian
 */


public class Menu {
   
    static Scanner sc = new Scanner(System.in, "ISO-8859-1");
    static ClientesDAO clientes = new ClientesDAO();
    
    
    public static void menu(){
        Integer opcion = null;

            if (ClientesDAO.getConexion() == null) {
                System.err.println("Fin del programa. No se pudo conectar.");
                System.exit(1);
            }

            System.out.println("\t\tBIENVENIDO");
            System.out.println("\t\t-----------");
            
            
            while (true) {
                try {
                    System.out.println("  1. Visualizar la tabla");
                    System.out.println("  2. Buscar los datos.");
                    System.out.println("  3. Insertar los datos.");
                    System.out.println("  4. Actualizar los datos.");
                    System.out.println("  5. Eliminar los datos.\n");
                    System.out.print("Seleccione una opcion [Seleccione 0 si quiere salir]: ");
                    opcion = Integer.parseInt(sc.nextLine());

                    switch (opcion) {
                        case 0:
                            System.exit(0);
                            break;
                        case 1:
                            System.out.println("\nVISUALIZAR");
                            System.out.println("--------\n");
                            visualizar();
                            break;
                        case 2:
                            System.out.println("\nBUSCAR");
                            System.out.println("--------");
                            buscar();
                            break;
                        case 3:
                            System.out.println("\nINSERTAR");
                            System.out.println("---------");
                            insertar();
                            break;
                        case 4:
                            System.out.println("\nACTUALIZAR");
                            System.out.println("----------");
                            actualizar();
                            break;
                        case 5:
                            System.out.println("\nBORRAR");
                            System.out.println("-------");
                            borrar();
                            break;
                        default:
                            System.out.println("\nIntroduzca una opcion valida.");
                    }
                    System.out.println();
                } catch (NumberFormatException nfe) {
                    System.err.println("\nEsa Entrada no es válida " + nfe.getMessage() + "\n");
                }
            }
        }
        
        public static void visualizar(){
            Integer opcion = null;
            boolean salida;
            int start = 1;
            int fin = 10;
            
            while (true) {
                try {
                    
                    for (POJO cliente : clientes.listar(start,fin)){
                        System.out.println(cliente);
                    }
                    clientes.listar(start,fin);
                    System.out.println("1. Visualizar los 10 siguientes");
                    System.out.println("2. Visualizar los 10 anteriores");
                    System.out.print("\nElija opcion [Elija 0 para volver]: ");
                    opcion = Integer.parseInt(sc.nextLine());

                    switch(opcion){
                    case 0:
                        return;
                    case 1:
                        start+=10;
                        fin+=10;
                        break;
                    case 2:
                        if(start!=1){
                            start -= 10;
                            fin -= 10;
                        }else{
                            System.out.println("Has elegido visualizar los primeros registros");
                        }
                        break;
                    default:
                        System.out.println("Elija una opcion valida");
                        break;
                }
                } catch (NumberFormatException nfe) {
                    System.err.println("\nError: " + nfe.getMessage() + "\n");
                }
            }
        }
        
        public static POJO existe() {
            POJO cliente = null;

            System.out.print("Introduce el ID del empleado que quieres buscar: ");
            cliente = ClientesDAO.read(Integer.parseInt(sc.nextLine()));

            return cliente;
        }

        public static void buscar() {
            POJO cliente = existe();

            if (cliente != null) {
                System.out.println(cliente.toString());
            } else {
                System.err.println("El empleado que buscas no existe o no se puede leer.");
            }
        }

        public static void insertar() {
            POJO cliente = new POJO();
            
            System.out.printf("Introduce el codigo del cliente: ");
            cliente.setCodigo(sc.next());

            System.out.printf("Introduce la empresa del cliente: ");
            cliente.setEmpresa(sc.next());

            System.out.printf("Introducee el contacto del cliente: ");
            cliente.setContacto(sc.next());

            System.out.printf("Introduce el cargo de contacto del cliente: ");
            cliente.setCargo_contacto(sc.next());

            System.out.printf("Introduce la direccion del cliente: ");
            cliente.setDireccion(sc.next());

            System.out.printf("Introduce la ciudad del cliente: ");
            cliente.setCiudad(sc.next());

            System.out.printf("Introduce la region del cliente: ");
            cliente.setRegion(sc.next());

            System.out.printf("Introduce el codigo postal del cliente: ");
            cliente.setCp(sc.next());

            System.out.printf("Introduce el pais del cliente: ");
            cliente.setPais(sc.next());

            System.out.printf("Introduce el Telefono del cliente: ");
            cliente.setTelefono(sc.next());

            System.out.printf("Introduce el FAX del cliente: ");
            cliente.setFax(sc.next());


            if (clientes.insert(cliente)) {
                System.out.println("El cliente '" + cliente.getId() + " " + cliente.getCodigo() + " " + cliente.getEmpresa() + 
                        " " + cliente.getContacto() + " " + cliente.getCargo_contacto() + " " + cliente.getDireccion() + 
                        " " + cliente.getCiudad() + " " + cliente.getRegion() + " " + cliente.getCp() + " " + cliente.getPais() + 
                        " " + cliente.getTelefono() + " " + cliente.getFax() + "' ha sido añadido correctamente.");
            } else {
                System.err.println("El cliente que esta intentando insertar no es válido.\n");
            }
        }

        public static void actualizar() {
            POJO cliente = existe();

            if (cliente == null) {
                System.err.println("El empleado que buscas no existe o no se puede leer.");
                return;
            }

            while (true) {
                try {
                    System.out.println("\n" + cliente);

                    Integer opcion;

                    System.out.println("\nELIJA ALGUNA DE LAS OPCIONES PARA ACTUALIZAR\n");
                    System.out.println("  1. Codigo.");
                    System.out.println("  2. Empresa.");
                    System.out.println("  3. Contacto.");
                    System.out.println("  4. Cargo de Contacto.");
                    System.out.println("  5. Direccion.");
                    System.out.println("  6. Ciudad.");
                    System.out.println("  7. Region.");
                    System.out.println("  8. Codigo Postal.");
                    System.out.println("  9. Pais.");
                    System.out.println("  10. Telefono.");
                    System.out.println("  11. Fax.");
                    System.out.print("\nElija opcion [introduzca 0 para volver]: ");
                    opcion = Integer.parseInt(sc.nextLine());

                    if (opcion > 0 && opcion < 11) {
                        System.out.print("\nIndica el cambio que desea realizar: ");
                    }

                    switch (opcion) {
                        case 0:
                            return;
                        case 1:
                            cliente.setCodigo(sc.nextLine());
                            clientes.update(cliente);
                            break;
                        case 2:
                            cliente.setEmpresa(sc.nextLine());
                            clientes.update(cliente);
                            break;
                        case 3:
                            cliente.setContacto(sc.nextLine());
                            clientes.update(cliente);
                            break;
                        case 4:
                            cliente.setCargo_contacto(sc.nextLine());
                            clientes.update(cliente);
                            break;
                        case 5:
                            cliente.setDireccion(sc.nextLine());
                            clientes.update(cliente);
                            break;
                        case 6:
                            cliente.setCiudad(sc.nextLine());
                            clientes.update(cliente);
                            break;
                        case 7:
                            cliente.setRegion(sc.nextLine());
                            clientes.update(cliente);
                            break;
                        case 8:
                            cliente.setCp(sc.nextLine());
                            clientes.update(cliente);
                            break;
                        case 9:
                            cliente.setPais(sc.nextLine());
                            clientes.update(cliente);
                            break;
                        case 10:
                            cliente.setTelefono(sc.nextLine());
                            clientes.update(cliente);
                            break;
                        case 11:
                            cliente.setFax(sc.nextLine());
                            clientes.update(cliente);
                            break;
                        default:
                            System.out.println("\nIntroduzca una opcion valida.\n");
                    }
                } catch (NumberFormatException nfe) {
                    System.err.println("\nError: " + nfe.getMessage() + "\n");
                }
            }
        }

        public static void borrar() {
            POJO cliente = existe();
            String resp = null;

            if (cliente != null) {
                    System.out.println("\n¿Está seguro que desea eliminar al siguiente usuario?"
                            + "\n  " + cliente);
                    System.out.print("Su respuesta [Y/N]: ");
                    resp = sc.nextLine();

                    if (resp.equalsIgnoreCase("y")) {
                        clientes.delete(cliente.getId());
                        System.out.println("Entrada eliminada correctamente.");
                    } else {
                        System.out.println("Entrada no eliminada.");
                    }
            } else {
                System.err.println("El empleado que buscas no existe o no se puede leer.");
            }
        }
    
}