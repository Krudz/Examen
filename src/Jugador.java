
public class Jugador {
	
	
	private int ID;
	private String nombre;
	private String equipo;
	private int numero;
	private double altura;
	
	
	public Jugador() {
		super();
		this.ID = 0;
		// TODO Auto-generated constructor stub
	}


	public Jugador(int iD, String nombre, String equipo, int numero, double altura) {
		super();
		ID = iD;
		this.nombre = nombre;
		this.equipo = equipo;
		this.numero = numero;
		this.altura = altura;
	}


	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getEquipo() {
		return equipo;
	}


	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}


	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}


	public double getAltura() {
		return altura;
	}


	public void setAltura(double altura) {
		this.altura = altura;
	}


	@Override
	public String toString() {
		return "Jugador [ID=" + ID + ", nombre=" + nombre + ", equipo=" + equipo + ", numero=" + numero + ", altura="
				+ altura + "]";
	} 
	
	
	
	
	
	

}
