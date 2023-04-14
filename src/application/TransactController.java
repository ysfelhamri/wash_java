package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class TransactController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Transact, String> cclient;

    @FXML
    private TableColumn<Transact, String> cdate;

    @FXML
    private Pane chks;

    @FXML
    private TableColumn<Transact, String> cid;

    @FXML
    private ComboBox<Client> client;

    @FXML
    private TableColumn<Transact, String> cprix;

    @FXML
    private ComboBox<Employe> employe;

    @FXML
    private Label erreur;

    @FXML
    private TableView<Transact> table;
    
    HashSet<CheckBox> ser;
    ObservableList<Transact> data;
    DirectoryChooser directoryChooser;
    
    @FXML
    void ajouter(ActionEvent event) {
    	if(client.getValue() == null ||employe.getValue()== null || ser.isEmpty()) {
    		erreur.setText("Selectionner dans tout les champs !");
    		return;
    	}
    	Client c = client.getValue();
    	Employe e = employe.getValue();
    	double prixtot = 0;
    	int id_tra =0;
    	for(CheckBox ch :ser) {
    		Service s =(Service) ch.getUserData();
    		prixtot += s.getPrix();
    	}
    	Transact t = new Transact (c.getId(),prixtot);
    	try {		
			Connection connection = MysqlConnection.getDBConnection();
				
			String sql="INSERT INTO transact (ID_cl,Prix_total,Date_tra) VALUES (?,?,CURDATE())"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setInt(1, t.getId_cl());
			ps.setDouble(2, t.getPrix());
			ps.execute();
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    	try {
			Connection connection1 = MysqlConnection.getDBConnection();
			
			String sql1="SELECT `AUTO_INCREMENT` "
					+ "FROM INFORMATION_SCHEMA.TABLES "
					+ "WHERE TABLE_SCHEMA = 'lavage' "
					+ "AND TABLE_NAME = 'transact';"; 
			PreparedStatement ps1=connection1.prepareStatement(sql1);
			ResultSet results1 = ps1.executeQuery();
			while (results1.next()) {
				id_tra = results1.getInt("AUTO_INCREMENT");
				id_tra-- ;
			}
		} catch (Exception ex) {
			// TODO: handle exception
		}
    	for(CheckBox ch :ser) {
    		Service s =(Service) ch.getUserData();
    		try {		
    			Connection connection = MysqlConnection.getDBConnection();
    				
    			String sql="INSERT INTO servit (ID_tra,ID_em,ID_ser) VALUES (?,?,?)"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			ps.setInt(1, id_tra);
    			ps.setInt(2, e.getId());
    			ps.setInt(3, s.getId());
    			ps.execute();
    		
    		} catch (Exception ex) {
    		System.out.println(ex.getMessage());
    		}
    	}
    	load();
    	getAll();
    }
    

    @FXML
    void details(ActionEvent event) {
    	int index=table.getSelectionModel().getSelectedIndex();
    	if(index>=0) {	
			try {
				int id_tra = table.getSelectionModel().getSelectedItem().getId();
				String nom_cl = table.getSelectionModel().getSelectedItem().getNom_cl();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("detailsTra.fxml"));
				Pane root = (Pane)loader.load();
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle("Details de transaction "+id_tra);
				primaryStage.setResizable(false);
				DetailsTraController controller =loader.<DetailsTraController>getController();
				controller.setId_tra(id_tra);
				controller.setNom_cl(nom_cl);
				primaryStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    	
    	}
    }

    @FXML
    void select(MouseEvent event) {

    }
    @FXML
    void recu(ActionEvent event) {
    	int index=table.getSelectionModel().getSelectedIndex();
    	if(index>=0) {
    		try  
    		{  
    			Stage stage = (Stage) chks.getScene().getWindow();
        		File selectedDirectory = directoryChooser.showDialog(stage);
        		
        		
                String path = selectedDirectory.getAbsolutePath();
        		Document doc = new Document(); 
        		int ldate = (int) (System.currentTimeMillis() / 1000L); 
    			Transact t=table.getSelectionModel().getSelectedItem();
    		
    		PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(path+"\\recu"+ldate+".pdf"));  
    		doc.setPageSize(new Rectangle(250, 350));
    		doc.setMargins(0, 0, 20, 20);
    		
    		doc.open();  
    		
    		Paragraph p = new Paragraph("---------------");
    		p.setAlignment(Element.ALIGN_CENTER);
    		doc.add(p);   
    		p = new Paragraph("La transaction : "+ t.getId());
    		p.setAlignment(Element.ALIGN_CENTER);
    		doc.add(p);  
    		p = new Paragraph("---------------");
    		p.setAlignment(Element.ALIGN_CENTER);
    		doc.add(p);  
    		p = new Paragraph("Client : "+ t.getNom_cl());
    		p.setAlignment(Element.ALIGN_CENTER);
    		doc.add(p);  
    		p = new Paragraph("---------------");
    		p.setAlignment(Element.ALIGN_CENTER);
    		doc.add(p);  
    		p = new Paragraph("Services :");
    		p.setAlignment(Element.ALIGN_CENTER);
    		doc.add(p); 
    		try {
				Connection connection1 = MysqlConnection.getDBConnection();
				
				String sql1="SELECT Nom_ser,Prix from transact tr,servit sert,service ser WHERE  sert.ID_tra = tr.ID and sert.ID_ser = ser.ID and tr.ID = ?"; 
				PreparedStatement ps1=connection1.prepareStatement(sql1);
				ps1.setInt(1, t.getId());
				ResultSet results1 = ps1.executeQuery();
				while (results1.next()) {
					String nom_ser=results1.getString("Nom_ser");
					String prix = results1.getString("Prix");
					p = new Paragraph(nom_ser +" - "+prix+" DH");
		    		p.setAlignment(Element.ALIGN_CENTER);
		    		doc.add(p); 
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
    		p = new Paragraph("---------------");
    		p.setAlignment(Element.ALIGN_CENTER);
    		doc.add(p);  
    		p = new Paragraph("Prix Total : "+ t.getPrix()+" DH");
    		p.setAlignment(Element.ALIGN_CENTER);
    		doc.add(p);  
    		p = new Paragraph("---------------");
    		p.setAlignment(Element.ALIGN_CENTER);
    		doc.add(p);  
    		
    		doc.close();  
    		
    		writer.close(); 
    		try {

    			if ((new File(path+"\\recu"+ldate+".pdf")).exists()) {

    				Process pro = Runtime
    				   .getRuntime()
    				   .exec("rundll32 url.dll,FileProtocolHandler "+path+"\\recu"+ldate+".pdf");
    				pro.waitFor();
    					
    			} 
    	  	  } catch (Exception ex) {
    		  }
    		}   
    		catch (Exception e)  
    		{  
    		}   
    		
    	}
    }

    @FXML
    void supprimer(ActionEvent event) {
    	int index=table.getSelectionModel().getSelectedIndex();
    	if(index>=0) {
    		
    		Transact t=table.getSelectionModel().getSelectedItem();
    		try {
    			Connection connection = MysqlConnection.getDBConnection();
    			String sql="DELETE FROM transact WHERE ID = ?"; 
    			PreparedStatement ps=connection.prepareStatement(sql);
    			ps.setInt(1, t.getId());

    			ps.execute();

    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		data.remove(index);
    	}
    	load();
    	getAll();
    }

    @FXML
    void initialize() {
    	directoryChooser = new DirectoryChooser();
    	data=FXCollections.observableArrayList();

   	 	cid.setCellValueFactory(new PropertyValueFactory<>("id"));
        cclient.setCellValueFactory(new PropertyValueFactory<>("nom_cl"));
        cprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        cdate.setCellValueFactory(new PropertyValueFactory<>("date"));

        
        table.setItems(data);
    	load();
    	getAll();
    }
    public void getAll() {
    	data.clear();
    	erreur.setText("");
    	try {
			Connection connection = MysqlConnection.getDBConnection();
			
			String sql="SELECT * FROM transact"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				int id=results.getInt("ID");
				int id_cl=results.getInt("ID_cl");
				String nom_cl = null;
				try {
					Connection connection1 = MysqlConnection.getDBConnection();
					
					String sql1="SELECT * FROM client WHERE ID = ?"; 
					PreparedStatement ps1=connection1.prepareStatement(sql1);
					ps1.setInt(1, id_cl);
					ResultSet results1 = ps1.executeQuery();
					while (results1.next()) {
						String nom=results1.getString("Nom");
						String prenom = results1.getString("Prenom");
						String numvoi = results1.getString("Num_voi");
						nom_cl = new Client(nom,prenom,numvoi).toString();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				double prix = results.getDouble("Prix_total");
				String date = results.getString("Date_tra");
				data.add(new Transact(id,id_cl,nom_cl,prix,date));	
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    void load() {
    	 ser = new HashSet<>();
    	 chks.getChildren().clear();
    	 client.getItems().clear();
    	 employe.getItems().clear();
         try {		
 			Connection connection = MysqlConnection.getDBConnection();
 				
 			String sql="SELECT * FROM service"; 
 			PreparedStatement ps=connection.prepareStatement(sql);
 			
 			ResultSet results = ps.executeQuery();
 			while (results.next()) {
 				int id=results.getInt("ID");
 				if(id !=1) {
 				String nom=results.getString("Nom_ser");
 				double prix = results.getDouble("Prix");
 				Service s = new Service(id, nom,prix);
 				CheckBox c = new CheckBox(s.getNom());
 				c.setUserData(s);
 				EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
 					  
 	                public void handle(ActionEvent e)
 	                {
 	                    if (c.isSelected())
 	                        ser.add(c);
 	                    else
 	                    	ser.remove(c);
 	                }
 	            };
 	            c.setOnAction(event);
 	            chks.getChildren().add(c);
 				}
 			}
 		
 		} catch (Exception ex) {
 			ex.printStackTrace();
 		}
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
					client.getItems().add(new Client(id, nom,prenom,numvoi));
  			}
  		
  		} catch (Exception ex) {
  			ex.printStackTrace();
  		}
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
					employe.getItems().add(new Employe(id, nom,prenom,adresse,fct,salaire,date));	
   			}
   		
   		} catch (Exception ex) {
   			ex.printStackTrace();
   		}
    }
}
