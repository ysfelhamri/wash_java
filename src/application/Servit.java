package application;

public class Servit {
	int id;
	String nom_cl;
	String nom_em;
	String nom_ser;
	double prix;
	public Servit(int id, String nom_cl, String nom_em, String nom_ser, double prix) {
		super();
		this.id = id;
		this.nom_cl = nom_cl;
		this.nom_em = nom_em;
		this.nom_ser = nom_ser;
		this.prix = prix;
	}
	public Servit(String nom_cl, String nom_em, String nom_ser, double prix) {
		super();
		this.nom_cl = nom_cl;
		this.nom_em = nom_em;
		this.nom_ser = nom_ser;
		this.prix = prix;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom_cl() {
		return nom_cl;
	}
	public void setNom_cl(String nom_cl) {
		this.nom_cl = nom_cl;
	}
	public String getNom_em() {
		return nom_em;
	}
	public void setNom_em(String nom_em) {
		this.nom_em = nom_em;
	}
	public String getNom_ser() {
		return nom_ser;
	}
	public void setNom_ser(String nom_ser) {
		this.nom_ser = nom_ser;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	
	
}
