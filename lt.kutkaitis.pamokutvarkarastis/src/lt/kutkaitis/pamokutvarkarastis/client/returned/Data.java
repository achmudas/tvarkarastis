package lt.kutkaitis.pamokutvarkarastis.client.returned;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import lt.kutkaitis.pamokutvarkarastis.client.subjects.Subject;
import lt.kutkaitis.pamokutvarkarastis.client.teachers.Teacher;

public class Data implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private HashMap<String, ArrayList<Teacher>> teacherMap;
	private HashMap<String, HashMap<String,HashMap<String,ArrayList<String>>>> studentsMap;
	private HashMap<String, HashMap<String, Subject>> subjectsMap;
	private ArrayList<String> studentsList;
	
	public Data(HashMap<String, ArrayList<Teacher>> teacherMap, HashMap<String, HashMap<String,HashMap<String,ArrayList<String>>>> studentsMap,
			HashMap<String, HashMap<String, Subject>> subjectsMap, ArrayList<String> studentsList) {
		this.teacherMap = teacherMap;
		this.studentsMap = studentsMap;
		this.subjectsMap = subjectsMap;
		this.studentsList = studentsList;
	}
	
	public Data() {
		
	}
	
	public ArrayList<String> getStudentsList() {
		return studentsList;
	}



	public void setStudentsList(ArrayList<String> studentsList) {
		this.studentsList = studentsList;
	}



	public HashMap<String, HashMap<String, Subject>> getSubjectsMap() {
		return subjectsMap;
	}

	public void setSubjectsMap(HashMap<String, HashMap<String, Subject>> subjectsMap) {
		this.subjectsMap = subjectsMap;
	}

	public HashMap<String, ArrayList<Teacher>> getTeacherMap() {
		return teacherMap;
	}

	public void setTeacherMap(HashMap<String, ArrayList<Teacher>> teacherMap) {
		this.teacherMap = teacherMap;
	}

	public HashMap<String, HashMap<String, HashMap<String, ArrayList<String>>>> getStudentsMap() {
		return studentsMap;
	}

	public void setStudentsMap(
			HashMap<String, HashMap<String, HashMap<String, ArrayList<String>>>> studentsMap) {
		this.studentsMap = studentsMap;
	}

}
