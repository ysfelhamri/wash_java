package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ChngPassController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label erreur;

    @FXML
    private PasswordField pass;

    @FXML
    private PasswordField pass1;
    @FXML
    void ret(ActionEvent event) {
    	Stage stage = (Stage) pass.getScene().getWindow();
    	try {
    		Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("Auth.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Authentification");
			primaryStage.setResizable(false);
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent e) {
			     Platform.exit();
			    }
			  });
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	stage.hide();
    }

    @FXML
    void chngpass(ActionEvent event) {
    	if(pass1.getText() == null || pass.getText() == null || pass1.getText() == "") {
    		erreur.setText("Saisir tout les champs");
    		return;
    	}
    	String passw= null;
    	try {		
  			Connection connection = MysqlConnection.getDBConnection();
  				
  			String sql="SELECT * FROM auth"; 
  			PreparedStatement ps=connection.prepareStatement(sql);
  			
  			ResultSet results = ps.executeQuery();
  			while (results.next()) {
				passw=results.getString("pass");			
  			}
  		
  		} catch (Exception ex) {
  			ex.printStackTrace();
  		}
    	if(pass.getText().compareTo(passw) !=0) {
    		erreur.setText("Mot de passe incorrect");
    		return;
    	}
    	try {		
  			Connection connection = MysqlConnection.getDBConnection();
  				
  			String sql="UPDATE auth SET pass = ? WHERE id = 1"; 
  			PreparedStatement ps=connection.prepareStatement(sql);
  			
  			ps.setString(1, pass1.getText());

			ps.execute();
  		
  		} catch (Exception ex) {
  			ex.printStackTrace();
  		}
    	Stage stage = (Stage) pass.getScene().getWindow();
    	try {
    		Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("Auth.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Authentification");
			primaryStage.setResizable(false);
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent e) {
			     Platform.exit();
			    }
			  });
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
    	stage.hide();
    }

    @FXML
    void initialize() {
       
    }

}
