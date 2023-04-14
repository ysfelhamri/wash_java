package application;

import java.net.URL;
import java.sql.*;
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

public class ServiceController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Label erreur;

    @FXML
    private TableColumn<Service, String> cid;

    @FXML
    private TableColumn<Service, String> cnom;

    @FXML
    private TableColumn<Service, String> cprix;

    @FXML
    private TableView<Service> table;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtprix;
    
    ObservableList<Service> data;

    @FXML
    void ajouter(ActionEvent event) {
    	
    	try {
    		String nom=txtnom.getText();
    		if(nom == null )return;
    		double prix=Double.valueOf(txtprix.getText());
    		Service s=new Service(nom,prix);
        	
    		if(prix >0) {

    			try {		
    				Connection connection = MysqlConnection.getDBConnection();
    					
    				String sql="INSERT INTO service (Nom_ser,Prix) VALUES (?,?)"; 
    				PreparedStatement ps=connection.prepareStatement(sql);
    				ps.setString(1, s.getNom());
    				ps.setDouble(2, s.getPrix());
    				ps.execute();
    			
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
        	
    			txtnom.setText("");
    			txtprix.setText("");
    			getAll();
    		}
    		else {
    			erreur.setText("Prix invalid");
    		}
    	}catch (Exception e) {
    		erreur.setText("Prix invalid");
    	}
    	
    	
    	
    }

    @FXML
    void modifier(ActionEvent event) {
    	int index=table.getSelectionModel().getSelectedIndex();
    	if(index>=0) {
    	try {
    		String nom=txtnom.getText();
    		if(nom == null )return;
	    	String prix=txtprix.getText();
	    	double p = Double.valueOf(txtprix.getText());
    		if(p >0) {
    			
    	    	Service s=table.getSelectionModel().getSelectedItem();
    	    	try {
    				
    				Connection connection = MysqlConnection.getDBConnection();
    				
    				
    				
    				String sql="UPDATE service SET Nom_ser = ?,Prix = ? WHERE ID = ?"; 
    				PreparedStatement ps=connection.prepareStatement(sql);
    				ps.setString(1, nom);
    				ps.setString(2, prix);
    				ps.setInt(3, s.getId());
    				ps.execute();
    				
    				
    				
    				
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    	    	
    	    	txtnom.setText("");
    	    	txtprix.setText("");
    	    	getAll();
    			
    		}else {
    			erreur.setText("Prix invalid");
    		}
    	}catch(Exception e){
    		erreur.setText("Prix invalid");
    	}
    	}
    	
    }

    @FXML
    void select(MouseEvent event) {
    	
    	Service s=table.getSelectionModel().getSelectedItem();
    	if(s != null) {
    		txtnom.setText(s.getNom());
    		txtprix.setText(s.getPrix()+"");
    	}
    	
    }

    @FXML
    void supprimer(ActionEvent event) {
    	int index=table.getSelectionModel().getSelectedIndex();
    	if(index>=0) {
    		
    		Service s=table.getSelectionModel().getSelectedItem();
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			String sql="UPDATE servit SET ID_ser = 1 WHERE ID_ser = ?"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			ps.setInt(1, s.getId());

    			ps.execute();

    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			String sql="DELETE FROM service WHERE ID = ?"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			ps.setInt(1, s.getId());

    			ps.execute();

    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		txtnom.setText("");
        	txtprix.setText("");
    		data.remove(index);
    	}
    	
    }
    

    @FXML
    void initialize() {
    	data=FXCollections.observableArrayList();

   	 	cid.setCellValueFactory(new PropertyValueFactory<>("id"));
        cnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        
        table.setItems(data);
        getAll();

    }
    
    
    public void getAll() {
    	data.clear();
    	erreur.setText("");
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT * FROM service"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				int id=results.getInt("ID");
				String nom=results.getString("Nom_ser");
				double prix = results.getDouble("Prix");
				if(id !=1)
					data.add(new Service(id, nom,prix));
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

}
