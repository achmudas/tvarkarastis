package lt.kutkaitis.pamokutvarkarastis.client.teachers;

import java.io.Serializable;
import java.util.ArrayList;

public class Teacher implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<String> rooms;
	private ArrayList<String> levels;
	private ArrayList<String> clasToTeach;
	private Integer freeHours;
	private Integer groupsTeachAble11;
	private Integer groupsTeachAble12;


	public Teacher(String name, ArrayList<String> rooms, ArrayList<String> levels, ArrayList<String> clasToTeach, Integer freeHours, 
			Integer groupsTeachAble11, Integer groupsTeachAble12) {
		this.name = name;
		this.rooms = rooms;
		this.levels = levels;
		this.clasToTeach = clasToTeach;
		this.freeHours = freeHours;
		this.groupsTeachAble11 = groupsTeachAble11;
		this.groupsTeachAble12 = groupsTeachAble12;
	}
	
	public Teacher() {
	}
	
	public Integer getGroupsTeachAble11() {
		return groupsTeachAble11;
	}

	public void setGroupsTeachAble11(Integer groupsTeachAble11) {
		this.groupsTeachAble11 = groupsTeachAble11;
	}

	public Integer getGroupsTeachAble12() {
		return groupsTeachAble12;
	}

	public void setGroupsTeachAble12(Integer groupsTeachAble12) {
		this.groupsTeachAble12 = groupsTeachAble12;
	}

	public Integer getFreeHours() {
		return freeHours;
	}

	public void setFreeHours(Integer freeHours) {
		this.freeHours = freeHours;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<String> rooms) {
		this.rooms = rooms;
	}

	public ArrayList<String> getLevels() {
		return levels;
	}

	public void setLevels(ArrayList<String> levels) {
		this.levels = levels;
	}

	public ArrayList<String> getClasToTeach() {
		return clasToTeach;
	}

	public void setClasToTeach(ArrayList<String> clasToTeach) {
		this.clasToTeach = clasToTeach;
	}

}
