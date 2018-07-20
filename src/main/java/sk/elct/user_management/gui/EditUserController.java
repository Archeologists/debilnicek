package sk.elct.user_management.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sk.elct.java.user_management.DaoFactory;
import sk.elct.java.user_management.User;
import sk.elct.java.user_management.UserDao;

public class EditUserController {

	
	
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button saveButton;

    //Modely
    private UserFxModel userModel;
    
     public EditUserController(User user) {
	 userModel = new UserFxModel();
	 userModel.setUser(user);
	}
    
    
    @FXML
    void initialize() {
nameTextField.textProperty().bindBidirectional(userModel.nameProperty());
emailTextField.textProperty().bindBidirectional(userModel.emailProperty());

saveButton.setOnAction(new EventHandler<ActionEvent>() {
	
	@Override
	public void handle(ActionEvent event) {
		UserDao userDao = DaoFactory.INSTANCE.getUserDao();
		userDao.update(userModel.getUser());
		saveButton.getScene().getWindow().hide();
		
	}
});
    }
    
 
}
