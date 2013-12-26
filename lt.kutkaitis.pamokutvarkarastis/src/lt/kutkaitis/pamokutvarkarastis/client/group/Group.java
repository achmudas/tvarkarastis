package lt.kutkaitis.pamokutvarkarastis.client.group;

import java.util.List;

import lt.kutkaitis.pamokutvarkarastis.client.Students;
import lt.kutkaitis.pamokutvarkarastis.client.teachers.Teacher;

public class Group {

	private String groupName;
	private String hours;
	private Teacher Teacher;
	private List<Students> studentsList;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public Teacher getTeacher() {
		return Teacher;
	}
	public void setTeacher(Teacher teacher) {
		Teacher = teacher;
	}
	public List<Students> getStudentsList() {
		return studentsList;
	}
	public void setStudentsList(List<Students> studentsList) {
		this.studentsList = studentsList;
	}
	
	
}
