package application;

public class Service {
	int id;
	String nom;
	double prix;
	public Service(int id, String nom, double prix) {
		super();
		this.id = id;
		this.nom = nom;
		this.prix = prix;
	}
	
	public Service(String nom, double prix) {
		super();
		this.nom = nom;
		this.prix = prix;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	
}
