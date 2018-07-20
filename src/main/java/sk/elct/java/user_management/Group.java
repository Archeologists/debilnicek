package sk.elct.java.user_management;

import java.util.ArrayList;
import java.util.List;

public class Group {

	private Long id;
	private String name;
	private List<String> privileges = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getPrivileges() {
		return privileges;
	}
	public void addPrivilege(String privilege) {
		this.privileges .add(privilege);
	}
	
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", privileges=" + privileges + "]";
	}
	
	
	
	
	//new version
}
