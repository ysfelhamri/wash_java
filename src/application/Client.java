package application;

public class Client {
	int id;
	String nom;
	String prenom;
	String numvoi;
	
	public Client(int id, String nom, String prenom, String numvoi) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.numvoi = numvoi;
	}
	

	@Override
	public String toString() {
		return  nom +" "+ prenom + " - "+numvoi ;
	}


	public Client(String nom, String prenom, String numvoi) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.numvoi = numvoi;
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
	public String getNumvoi() {
		return numvoi;
	}
	public void setNumvoi(String numvoi) {
		this.numvoi = numvoi;
	}
	
}
