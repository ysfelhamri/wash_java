package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

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

public class ClientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TableColumn<Client, String> cid;

    @FXML
    private TableColumn<Client, String> cnom;

    @FXML
    private TableColumn<Client, String> cnumvoi;

    @FXML
    private TableColumn<Client, String> cprenom;

    @FXML
    private Label erreur;
 
    @FXML
    private TableView<Client> table;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtnomvoi;

    @FXML
    private TextField txtprenom;

    ObservableList<Client> data;

    @FXML
    void ajouter(ActionEvent event) {
    	
    		String nom=txtnom.getText();
    		String prenom = txtprenom.getText();
    		String numvoi = txtnomvoi.getText();
    		if(nom == null || prenom == null || numvoi == null) return;
    		Client c=new Client(nom,prenom,numvoi);
        	

    			try {		
    				Connection connection = MysqlConnection.getDBConnection();
    					
    				String sql="INSERT INTO client (Nom,Prenom,Num_voi) VALUES (?,?,?)"; 
    				PreparedStatement ps=connection.prepareStatement(sql);
    				ps.setString(1, c.getNom());
    				ps.setString(2, c.getPrenom());
    				ps.setString(3, c.getNumvoi());
    				ps.execute();
    			
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
        	
    			txtnom.setText("");
    			txtprenom.setText("");
        		txtnomvoi.setText("");
    			getAll();
    
   
    }

    @FXML
    void modifier(ActionEvent event) {
    	int index=table.getSelectionModel().getSelectedIndex();
    	if(index>=0) {
    		String nom=txtnom.getText();
    		String prenom = txtprenom.getText();
    		String numvoi = txtnomvoi.getText();
    		if(nom == null || prenom == null || numvoi == null) return;
    		Client c=table.getSelectionModel().getSelectedItem();
        	

    			try {		
    				Connection connection = MysqlConnection.getDBConnection();
    					
    				String sql="UPDATE client SET Nom = ?,Prenom = ?,Num_voi = ? WHERE ID = ?"; 
    				PreparedStatement ps=connection.prepareStatement(sql);
    				ps.setString(1, nom);
    				ps.setString(2, prenom);
    				ps.setString(3, numvoi);
    				ps.setInt(4, c.getId());
    				ps.execute();
    			
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
        	
    			txtnom.setText("");
    			txtprenom.setText("");
        		txtnomvoi.setText("");
    			getAll();
    	}
    	
    }

    @FXML
    void select(MouseEvent event) {
    	Client c=table.getSelectionModel().getSelectedItem();
    	if(c != null) {
    		txtnom.setText(c.getNom());
    		txtprenom.setText(c.getPrenom());
    		txtnomvoi.setText(c.getNumvoi());
    	}
    }

    @FXML
    void supprimer(ActionEvent event) {
    	int index=table.getSelectionModel().getSelectedIndex();
    	if(index>=0) {
    		
    		Client c=table.getSelectionModel().getSelectedItem();
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			String sql="UPDATE transact SET ID_cl = 1 WHERE ID_cl = ?"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			ps.setInt(1, c.getId());

    			ps.execute();

    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			String sql="DELETE FROM client WHERE ID = ?"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			ps.setInt(1, c.getId());

    			ps.execute();

    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		txtnom.setText("");
			txtprenom.setText("");
    		txtnomvoi.setText("");
    		data.remove(index);
    	}
    }

    @FXML
    void initialize() {
    	data=FXCollections.observableArrayList();

   	 	cid.setCellValueFactory(new PropertyValueFactory<>("id"));
        cnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        cnumvoi.setCellValueFactory(new PropertyValueFactory<>("numvoi"));
        
        table.setItems(data);
        getAll();
    }

    public void getAll() {
    	data.clear();
    	erreur.setText("");
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT * FROM client"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				int id=results.getInt("ID");
				String nom=results.getString("Nom");
				String prenom = results.getString("Prenom");
				String numvoi = results.getString("Num_voi");
				if(id !=1)
					data.add(new Client(id, nom,prenom,numvoi));	
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
}
