package application;

public class Transact {
	int id;
	int id_cl;
	String nom_cl;
	double prix;
	String date;
	public Transact(int id, int id_cl, String nom_cl, double prix, String date) {
		super();
		this.id = id;
		this.id_cl = id_cl;
		this.nom_cl = nom_cl;
		this.prix = prix;
		this.date = date;
	}
	
	public Transact(int id_cl, double prix) {
		super();
		this.id_cl = id_cl;
		this.prix = prix;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_cl() {
		return id_cl;
	}
	public void setId_cl(int id_cl) {
		this.id_cl = id_cl;
	}
	public String getNom_cl() {
		return nom_cl;
	}
	public void setNom_cl(String nom_cl) {
		this.nom_cl = nom_cl;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
