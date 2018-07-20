package sk.elct.user_management.gui;

import java.util.List;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.cglib.transform.impl.AddDelegateTransformer;

import com.mysql.cj.x.protobuf.Mysqlx.OkOrBuilder;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.elct.java.user_management.MysqlUserDao;
import sk.elct.java.user_management.User;
import sk.elct.java.user_management.UserDao;
import sk.elct.java.user_management.UserDaoFactory;

public class PrvyController {

	private UserDao userDao = UserDaoFactory.INSTANCE.getUserDao();

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private Button pridajButton;

	
	@FXML
	private Button stlac;

	@FXML
	private ListView<User> usersListView;


    @FXML
    private TableView<User> usersTableView;
	// MODELY
	private UserFxModel newUserModel = new UserFxModel();
	private ObservableList<User> observableUsers;
	
	
    @FXML
    private Label selectedUserLabel;
    
    private User selectedUser;
	

	@FXML
	void initialize() {
		
		loginTextField.textProperty().bindBidirectional(newUserModel.nameProperty());
		emailTextField.textProperty().bindBidirectional(newUserModel.emailProperty());
		pridajButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {	
				User user = newUserModel.getUser();
			userDao.add(user);	
			observableUsers.setAll(userDao.getAll());
			newUserModel.setName(null);
			newUserModel.setEmail("");
			
			}
						
		});
				
		stlac.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("allo");
				observableUsers.remove(4,5);
			}
		});
		List<User> users = userDao.getAll();
		this.observableUsers  = FXCollections.observableArrayList(users);
		usersListView.setItems(this.observableUsers);
       
		
		TableColumn<User, String> nameColumn = new TableColumn<>("Meno");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		usersTableView.getColumns().add(nameColumn);
		
		
		TableColumn<User, String> emailColumn = new TableColumn<>("SE-mail");
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		usersTableView.getColumns().add(emailColumn);
		
		usersTableView.setItems(this.observableUsers);
		
		
		
		
		usersTableView.getSelectionModel().selectedItemProperty()
		 .addListener(new ChangeListener<User>() {

			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				
				if (newValue != null) {
					
				
				selectedUserLabel.setText(newValue.getName());
				selectedUser = newValue;
			}else {
				selectedUserLabel.setText("Nikto");
				selectedUser = null;
			}
				
				}
		 });
			 
			 usersTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
				 
				 public void handle (MouseEvent event) {
					 if (event.getClickCount() == 2) {
						 try {
							EditUserController controller = new EditUserController(selectedUser);
								
								FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editUser.fxml"));
								fxmlLoader.setController(controller);
								Parent rootPane = fxmlLoader.load();
								
								
//							Parent rootPane = FXMLLoader.load(getClass().getResource("gui.fxml"));

								Scene scene = new Scene(rootPane);
								Stage stage = new Stage();
								stage.setTitle("Edit User");
								stage.setScene(scene);
								stage.initModality(Modality.APPLICATION_MODAL);
								stage.showAndWait();
								// toto sa vykona az po zatvoreni vyskoceneho okna
								observableUsers.setAll(userDao.getAll());
						 } catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
					}
				 }
				
			 
			 
		});
	}
}
