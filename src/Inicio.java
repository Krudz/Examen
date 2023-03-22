import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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
				
		// ------------------------Archivos
		String ruta = "C:\\Users\\Alumno-Tarde\\Desktop\\examenArchivos\\texto.txt";
		//----------------------------------
		
		
		Class.forName("org.sqlite.JDBC");
		
		String url = "jdbc:sqlite:C:\\Users\\Alumno-Tarde\\Desktop\\Examen\\examen";
		
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
						+ "\n 4 - Cerrar"
						+ "\n 5 - Leer de archivo a base de datos");
				
				opcion = Integer.parseInt(mensaje);
				
				if(opcion == 4) {
					JOptionPane.showMessageDialog(null, "Cerrando");
					sennal = false;
					
				}
				if(opcion <= 0 || opcion > 5) {
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
	
					pt.setInt(1, ID + resultSet.getInt(1)+1);
					pt.setString(2, nombre);
					pt.setString(3, equipo);
					pt.setInt(4, numero);
					pt.setDouble(5, altura);
					
					pt.executeUpdate();
					
					
					//Archivos-----------------------------------------------------------------------------
					BufferedWriter bw = new BufferedWriter(new FileWriter(ruta));
					jugadores.add(new Jugador(ID+=1, nombre, equipo, numero, altura));
					
					for (int i = 0 ; i < jugadores.size(); i++ ) {
						bw.write(jugadores.get(i).getID()+" "+jugadores.get(i).getNombre()+" "+jugadores.get(i).getEquipo()+" "+
					jugadores.get(i).getNumero()+" "+jugadores.get(i).getAltura());
						bw.newLine();
					}
					bw.close();

					//-------------------------------------------------------------------------------------
					
					
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
					
					//Archivos----------------------------------------------------------------------------------------
					
					try(BufferedReader br = new BufferedReader(new FileReader(ruta))) {
						String lineaActual;
						while((lineaActual  = br.readLine()) != null) {
							System.out.println(lineaActual);
						}
						
					} catch (IOException e) {
						System.out.println("Ha ocurrido un error al leer el archivo");
						e.printStackTrace();
					}
					
					
					//------------------------------------------------------------------------------------------------
				
				}
				if (opcion == 3) {
					
					int borrado = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el ID del jugador que deseas eliminar"));
					
					var pt = con.prepareStatement("DELETE FROM Jugadores WHERE id = ?");
					pt.setInt(1, borrado);
					pt.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "El jugador ha sido borrado con exito");
					
					
				}
				if (opcion == 5) {
					//-----------------------------------------------ultimo ---------------------------------
					BufferedReader br = new BufferedReader(new FileReader(ruta));
					
					
					
					String lineaActual;
					ArrayList<Jugador> personas = new ArrayList<>();
					
					String partes[];
					int id;
					String name;
					String team;
					int number;
					double high;

					
					while((lineaActual  = br.readLine()) != null) {
						
						partes = lineaActual.split(" ");
						
						for (int i = 0; i <= partes.length; i++) {
							id = Integer.parseInt(partes[i]);
							name = partes[i+1];
							team = partes[i+2];
							number = Integer.parseInt(partes[i+3]);
							high = Double.parseDouble(partes[i+4]);
							
							personas.add(new Jugador(id, name, team, number, high));
							System.out.println(personas);
							
						}
						
						
//						System.out.println(partes.toString());
						
					}
					
					//--------------------------------------------------------------------------------------
				}
				
			}while(sennal);
			
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "No me has ingresado un numero");
			e.printStackTrace();
		}

	}

}
