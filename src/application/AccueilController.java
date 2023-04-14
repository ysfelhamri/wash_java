package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AccueilController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label cl;

    @FXML
    private Label em;

    @FXML
    private Label rev;
    
    @FXML
    private Label ser;

    @FXML
    void initialize() {
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT SUM(Prix_total) as prix FROM transact where MONTH(Date_tra) = MONTH(CURDATE())"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				rev.setText(results.getString("prix") + " DH");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT COUNT(DISTINCT ID_cl) as id FROM transact where MONTH(Date_tra) = MONTH(CURDATE()) and ID_cl not in (SELECT ID FROM client where id = 1)"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				cl.setText(results.getString("id") + " Clients");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
    	
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT COUNT(ID) as id FROM employe where ID not in (SELECT ID FROM employe where id = 1)"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				em.setText("Nombre d'employes : "+results.getString("id") + " Employes");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT COUNT(ID) as id FROM service where ID not in (SELECT ID FROM service where id = 1)"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				ser.setText("Nombre des services : "+results.getString("id") + " Services");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

    }

}
