package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class EmployeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Employe, String> cadresse;

    @FXML
    private TableColumn<Employe, String> cdate;

    @FXML
    private TableColumn<Employe, String> cfct;

    @FXML
    private TableColumn<Employe, String> cid;

    @FXML
    private TableColumn<Employe, String> cnom;

    @FXML
    private TableColumn<Employe, String> cprenom;

    @FXML
    private TableColumn<Employe, String> csalaire;

    @FXML
    private Label erreur;

    @FXML
    private TableView<Employe> table;

    @FXML
    private TextField txtadresse;

    @FXML
    private TextField txtfct;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtprenom;

    @FXML
    private TextField txtsalaire;
    
    ObservableList<Employe> data;

    @FXML
    void ajouter(ActionEvent event) {
    		try {
    			String nom=txtnom.getText();
        		String prenom = txtprenom.getText();
        		String adresse = txtadresse.getText();
        		String fct = txtfct.getText();
        		if(nom == null || prenom == null || adresse == null || fct == null) return;
    			double salaire = Double.valueOf(txtsalaire.getText());	
    			if (salaire >0) {
    				Employe e = new Employe(nom,prenom,adresse,fct,salaire);
    				try {		
        				Connection connection = MysqlConnection.getDBConnection();
        					
        				String sql="INSERT INTO employe (Nom,Prenom,Adresse,Fonction,Salaire,Date_rec) VALUES (?,?,?,?,?,CURDATE())"; 
        				PreparedStatement ps=connection.prepareStatement(sql);
        				ps.setString(1, e.getNom());
        				ps.setString(2, e.getPrenom());
        				ps.setString(3, e.getAdresse());
        				ps.setString(4, e.getFct());
        				ps.setDouble(5,e.getSalaire());
        				ps.execute();
        			
        			} catch (Exception ex) {
        				ex.printStackTrace();
        			}
            	
        			txtnom.setText("");
            		txtprenom.setText("");
            		txtadresse.setText("");
            		txtfct.setText("");
            		txtsalaire.setText("");	
        			getAll();
    			}else {
    				erreur.setText("Salaire invalid");
    			}
			} catch (Exception ex) {
				// TODO: handle exception
				erreur.setText("Salaire invalid");
			}
    }

    @FXML
    void modifier(ActionEvent event) {
    	int index=table.getSelectionModel().getSelectedIndex();
    	if(index>=0) {
    		try {
    			String nom=txtnom.getText();
        		String prenom = txtprenom.getText();
        		String adresse = txtadresse.getText();
        		String fct = txtfct.getText();
        		if(nom == null || prenom == null || adresse == null || fct == null) return;
    			double salaire = Double.valueOf(txtsalaire.getText());
    			if (salaire >0) {
    				Employe e = table.getSelectionModel().getSelectedItem();
    				try {		
        				Connection connection = MysqlConnection.getDBConnection();
        					
        				String sql="UPDATE employe SET Nom = ?,Prenom = ?,Adresse = ?,Fonction = ?,Salaire = ? WHERE ID = ?"; 
        				PreparedStatement ps=connection.prepareStatement(sql);
        				ps.setString(1, nom);
        				ps.setString(2, prenom);
        				ps.setString(3, adresse);
        				ps.setString(4, fct);
        				ps.setDouble(5,salaire);
        				ps.setInt(6, e.getId());
        				ps.execute();
        			
        			} catch (Exception ex) {
        				ex.printStackTrace();
        			}
            	
        			txtnom.setText("");
            		txtprenom.setText("");
            		txtadresse.setText("");
            		txtfct.setText("");
            		txtsalaire.setText("");	
        			getAll();
    			}else {
    				erreur.setText("Salaire invalid");
    			}
			} catch (Exception ex) {
				// TODO: handle exception
				erreur.setText("Salaire invalid");
			}
    		
    	}
    }

    @FXML
    void select(MouseEvent event) {
    	Employe e=table.getSelectionModel().getSelectedItem();
    	if(e != null) {
    		txtnom.setText(e.getNom());
    		txtprenom.setText(e.getPrenom());
    		txtadresse.setText(e.getAdresse());
    		txtfct.setText(e.getFct());
    		txtsalaire.setText(e.getSalaire()+"");	
    	}
    }

    @FXML
    void supprimer(ActionEvent event) {
    	int index=table.getSelectionModel().getSelectedIndex();
    	if(index>=0) {
    		
    		Employe e=table.getSelectionModel().getSelectedItem();
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			String sql="UPDATE servit SET ID_em = 1 where ID_em = ?"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			ps.setInt(1, e.getId());

    			ps.execute();

    		} catch (Exception ex) {
    			ex.printStackTrace();
    		}
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			String sql="DELETE FROM employe WHERE ID = ?"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			ps.setInt(1, e.getId());

    			ps.execute();

    		} catch (Exception ex) {
    			ex.printStackTrace();
    		}
    		txtnom.setText("");
    		txtprenom.setText("");
    		txtadresse.setText("");
    		txtfct.setText("");
    		txtsalaire.setText("");	
    		data.remove(index);
    	}
    }

    @FXML
    void initialize() {
    	Platform.runLater(() -> {

    		data=FXCollections.observableArrayList();

       	 	cid.setCellValueFactory(new PropertyValueFactory<>("id"));
            cnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            cprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            cadresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            cfct.setCellValueFactory(new PropertyValueFactory<>("fct"));
            csalaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));
            cdate.setCellValueFactory(new PropertyValueFactory<>("date"));
            
            table.setItems(data);
            getAll();


        });
    	
    }
    
    public void getAll() {
    	data.clear();
    	erreur.setText("");
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT * FROM employe"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				int id=results.getInt("ID");
				String nom=results.getString("Nom");
				String prenom = results.getString("Prenom");
				String adresse = results.getString("Adresse");
				String fct = results.getString("Fonction");
				double salaire = results.getDouble("Salaire");
				String date = results.getString("Date_rec");
				if(id !=1)
					data.add(new Employe(id, nom,prenom,adresse,fct,salaire,date));	
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
}
