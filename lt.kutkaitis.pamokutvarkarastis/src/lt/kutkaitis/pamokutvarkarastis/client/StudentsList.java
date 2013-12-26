package lt.kutkaitis.pamokutvarkarastis.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentsList {
	
	private String selectedLvlItem;
	private String selectedSubjItem;
	private HashMap<String, HashMap<String,HashMap<String,ArrayList<String>>>> studentsMap;
	
	public StudentsList(String selectedSubjItem, String selectedLvlItem, HashMap<String, HashMap<String,HashMap<String,ArrayList<String>>>> studentsMap) {
		this.selectedLvlItem = selectedLvlItem;
		this.selectedSubjItem = selectedSubjItem;
		this.studentsMap = studentsMap;
	}

public List<Students> getStudentList11() {
	List<Students> studList = new ArrayList<Students>();
	
	for (String stud : studentsMap.get(selectedSubjItem).get("11kl").get(selectedLvlItem)) {
			studList.add(new Students(stud));
	}
	return studList;
}

public List<Students> getStudentList12() {
	List<Students> studList = new ArrayList<Students>();
	
	for (String stud : studentsMap.get(selectedSubjItem).get("12kl").get(selectedLvlItem)) {
			studList.add(new Students(stud));
	}
	return studList;
}

public List<Students> getStudentListAll() {
	List<Students> studList = new ArrayList<Students>();
	
	for (String stud : studentsMap.get(selectedSubjItem).get("11kl").get(selectedLvlItem)) {
			studList.add(new Students(stud));
	}
	
	for (String stud : studentsMap.get(selectedSubjItem).get("12kl").get(selectedLvlItem)) {
		studList.add(new Students(stud));
}
	return studList;
}
				
}
