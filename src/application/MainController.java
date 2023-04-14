package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.AnchorPane;


public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane show;
    
    @FXML
    void accueil(ActionEvent event) {
    	openfxml("Accueil.fxml");
    }
    @FXML
    void clients(ActionEvent event) {
    	openfxml("Client.fxml");
    }
    @FXML
    void services(ActionEvent event) {
    	openfxml("Service.fxml");
    }
    
    @FXML
    void employes(ActionEvent event) {
    	openfxml("Employe.fxml");
    }
    @FXML
    void transacts(ActionEvent event) {
    	openfxml("Transaction.fxml");
    }
    void openfxml (String fxml) {
    	show.getChildren().clear();
    	AnchorPane pane = null;
		try {
			pane = FXMLLoader.load(getClass().getResource(fxml));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
    	show.getChildren().add(pane);
    	
    }
    @FXML
    void initialize() {
    	openfxml("Accueil.fxml");
        
    }

}
