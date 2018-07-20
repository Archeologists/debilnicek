package sk.elct.user_management.gui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sk.elct.java.user_management.Group;
import sk.elct.java.user_management.User;

public class UserFxModel {

	private StringProperty name = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	private Long id;
	private String password;
	private LocalDateTime lastLogin;
	private List<Group> groups = new ArrayList<>();

	
	

	public User getUser() {
	User user = new User();
	user.setId(getId());
	user.setName(getName());
	user.setPassword(getPassword());
	user.setEmail(getEmail());
	user.setLastLogin(getLastLogin());
	user.setGroups(getGroups());
	return user;
	
	}
	
	
	public void setUser(User user) {
		setId(user.getId());
		setName(user.getName());
		setPassword(user.getPassword());
		setEmail(user.getEmail());
		setLastLogin(user.getLastLogin());
		setGroups(user.getGroups());
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public LocalDateTime getLastLogin() {
		return lastLogin;
	}


	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}


	public List<Group> getGroups() {
		return groups;
	}


	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}


	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);

	}

	public StringProperty nameProperty() {
		return name;

	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);

	}

	public StringProperty emailProperty() {
		return email;

	}






}
