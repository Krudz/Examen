import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Inicio {
	
	
	

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
				
		
		Class.forName("org.sqlite.JDBC");
		
		String url = "jdbc:sqlite:C:\\Users\\Alumno-Tarde\\Desktop\\Examen\\examen";
		
		//Archivos----------------------------------------------------------------------------------------//
		String ruta = "C:\\Users\\Alumno-Tarde\\Desktop\\examenArchivos\\texto.txt";
		File archivo = new File("C:\\Users\\Alumno-Tarde\\Desktop\\examenArchivos\\texto.txt");
		FileWriter fw = new FileWriter(archivo);
		BufferedWriter escritura = new BufferedWriter(fw);
		
		
		
		
		//-----------------------------------------------------------------------------------------------//
		
		
		ArrayList<Jugador> jugadores = new ArrayList<>();
		int ID =0;
		String nombre; 
		String equipo;
		int numero;
		double altura;
		
		int opcion;
		boolean sennal = true;
		
		try (Connection con = DriverManager.getConnection(url)){
			
			do {
				String mensaje = JOptionPane.showInputDialog("Por favor ingrese una opcion"
						+ "\n 1 - Añadir"
						+ "\n 2 - Listar"
						+ "\n 3 - Eliminar"
						+ "\n 4 - Cerrar");
				
				opcion = Integer.parseInt(mensaje);
				
				if(opcion == 4) {
					JOptionPane.showMessageDialog(null, "Cerrando");
					sennal = false;
					
				}
				if(opcion <= 0 || opcion > 4) {
					JOptionPane.showMessageDialog(null, "Por favor ingresa un numero del 1 al 4");
				}
				if (opcion == 1) {

					nombre = JOptionPane.showInputDialog("Ingresa su nombre");
					equipo = JOptionPane.showInputDialog("Ingresa su equipo");
					numero = Integer.parseInt(JOptionPane.showInputDialog("Ingresa su numero"));
					altura = Double.parseDouble(JOptionPane.showInputDialog("Ingresa su altura"));
					
					var ex = con.createStatement();
					var resultSet = ex.executeQuery("SELECT MAX(id) FROM Jugadores");
					
					var pt = con.prepareStatement("INSERT INTO Jugadores (id, Nombre, Equipo, Numero, Altura) values (?,?,?,?,?)");
//					var pt = con.prepareStatement("SELECT MAX(id) FROM Jugadores");
//					pt.executeQuery();
	
					pt.setInt(1, ID + resultSet.getInt(1)+1);
					pt.setString(2, nombre);
					pt.setString(3, equipo);
					pt.setInt(4, numero);
					pt.setDouble(5, altura);
					
					pt.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "El jugador ha sido añadido con exito!");
					
				}
				if (opcion == 2) {
					
					var pt = con.prepareStatement("SELECT * FROM Jugadores");
					var resultSet = pt.executeQuery();
					ArrayList<String> Listado = new ArrayList<>();
					
					while(resultSet.next()) {
						
						Listado.add(resultSet.getInt(1)+" "+resultSet.getString(2)+" "+resultSet.getString(3)+" "+
								resultSet.getInt(4)+" "+resultSet.getDouble(5)+"\n");

					}
					
					
					JOptionPane.showMessageDialog(null,"Los jugadores son: \n" +Listado);
				
				}
				if (opcion == 3) {
					
					int borrado = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el ID del jugador que deseas eliminar"));
					
					var pt = con.prepareStatement("DELETE FROM Jugadores WHERE id = ?");
					pt.setInt(1, borrado);
					pt.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "El jugador ha sido borrado con exito");
					
					
				}
				
			}while(sennal);
			
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "No me has ingresado un numero");
		}
		

	}

}
