package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class DetailsTraController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Servit, String> cclient;

    @FXML
    private TableColumn<Servit, String> cemploye;

    @FXML
    private TableColumn<Servit, String> cid_tra;

    @FXML
    private TableColumn<Servit, String> cservice;

    @FXML
    private TableView<Servit> table;
    @FXML
    private TableColumn<Servit, String> cprix;
    
    ObservableList<Servit> data;

    
    int id_tra;
    public String getNom_cl() {
		return nom_cl;
	}

	public void setNom_cl(String nom_cl) {
		this.nom_cl = nom_cl;
	}

	String nom_cl;

    public int getId_tra() {
		return id_tra;
	}

	public void setId_tra(int id_tra) {
		this.id_tra = id_tra;
	}

	@FXML
    void select(MouseEvent event) {

    }

    @FXML
    void initialize() {
    	Platform.runLater(() -> {

    		data=FXCollections.observableArrayList();

       	 	cid_tra.setCellValueFactory(new PropertyValueFactory<>("id"));
            cclient.setCellValueFactory(new PropertyValueFactory<>("nom_cl"));
            cemploye.setCellValueFactory(new PropertyValueFactory<>("nom_em"));
            cservice.setCellValueFactory(new PropertyValueFactory<>("nom_ser"));
            cprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            
            table.setItems(data);
            getAll();

        });
    	
    }
    public void getAll() {
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT Em.Nom as Emnom,Em.Prenom as Emprenom,Nom_ser,Prix FROM Servit ser,Employe Em,Service s WHERE ser.ID_em = Em.ID and ser.ID_ser = s.ID and ID_tra = ?"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setInt(1, id_tra);
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				String employe=results.getString("Emnom") + " "+results.getString("Emprenom");
				String service = results.getString("Nom_ser");
				double prix = results.getDouble("Prix");
				data.add(new Servit(id_tra,nom_cl, employe,service,prix));	
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
    }

}
