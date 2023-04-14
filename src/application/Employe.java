package application;

public class Employe {
	int id;
	String nom;
	String prenom;
	String adresse;
	String fct;
	@Override
	public String toString() {
		return  nom +" "+ prenom ;
	}
	double salaire;
	String date;
	public Employe(int id, String nom, String prenom, String adresse, String fct, double salaire, String date) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.fct = fct;
		this.salaire = salaire;
		this.date = date;
	}
	public Employe(String nom, String prenom, String adresse, String fct, double salaire) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.fct = fct;
		this.salaire = salaire;
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
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getFct() {
		return fct;
	}
	public void setFct(String fct) {
		this.fct = fct;
	}
	public double getSalaire() {
		return salaire;
	}
	public void setSalaire(double salaire) {
		this.salaire = salaire;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
