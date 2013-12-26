package lt.kutkaitis.pamokutvarkarastis.client.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lt.kutkaitis.pamokutvarkarastis.client.DataFilling;
import lt.kutkaitis.pamokutvarkarastis.client.Students;
import lt.kutkaitis.pamokutvarkarastis.client.teachers.Teacher;

import com.extjs.gxt.ui.client.widget.ListView;
import com.google.gwt.user.client.ui.ListBox;

public class GroupsSaving {
	
	private ListBox subject;
	private HashMap<String, ListView<Students>> list1Map;
	private DataFilling studFill;
	private ArrayList<Group> fullGroupsList;

	public GroupsSaving(ListBox subject, HashMap<String, ListView<Students>> list1Map, DataFilling studFill) {
		this.subject = subject;
		this.list1Map = list1Map;
		this.studFill = studFill;
	}
	

	public ArrayList<Group> saveGroups() {
		
		Integer subjectsCount = subject.getItemCount();
		subjectsCount--;  // Pamazinama del to jog, listboxas savy elementus saugo nuo 0
		fullGroupsList = new ArrayList<Group>();
		
		for (int subjectForInd = 0; subjectForInd <= subjectsCount;) {	
			
			ArrayList<String> level = studFill.getSubjectsLevels(subject.getItemText(subjectForInd));
			
			Integer levelCount = level.size();
			levelCount--;
			
			for (int levelForInd = 0; levelForInd <= levelCount;) {
				ArrayList<Teacher> teachersList = studFill.getTeachers(subject.getItemText(subjectForInd),level.get(levelForInd));
				Integer teacherCount = teachersList.size();
				teacherCount--;
				
				
				for (int teachForInd = 0; teachForInd <= teacherCount;){
					ArrayList<String> groupsTitles = studFill.getGroupTitles(subject.getItemText(subjectForInd), level.get(levelForInd),
							teachersList, teachersList.size(), teachForInd);
					
					Integer groupsCounts = groupsTitles.size();
					groupsCounts--; // Kaip visada, pamazinam per viena
					
					for (int groupForInd = 0; groupForInd <= groupsCounts;){
						Group group = new Group();
						group.setGroupName(groupsTitles.get(groupForInd)); // Uzsetinam name
						group.setTeacher(teachersList.get(teachForInd));
						
						if (groupsTitles.get(groupForInd).contains("11")) {
							group.setHours(studFill.settedResult.getSubjectsMap().get(subject.getItemText(subjectForInd)).get(level.get(levelForInd)).getHoursOf11());
						}
						
						if (groupsTitles.get(groupForInd).contains("12")) {
							group.setHours(studFill.settedResult.getSubjectsMap().get(subject.getItemText(subjectForInd)).get(level.get(levelForInd)).getHoursOf12());
						}
						
						if (list1Map.containsKey(groupsTitles.get(groupForInd))) {
							group.setStudentsList(list1Map.get(groupsTitles.get(groupForInd)).getStore().getModels());
						} 
						else {
							
							boolean class12 = false;
							
							if (groupsTitles.get(groupForInd).contains("12")) {
								class12 = true;
							}
							
							Integer selectedGroupIndex = groupForInd;
							
							if (teachForInd > 0) {
								selectedGroupIndex = studFill.returnStarterGroupNumber(teachersList, teachForInd) - 1;
								selectedGroupIndex = selectedGroupIndex + groupForInd;
							}
							
							Teacher teacherFromBox = teachersList.get(teachForInd);
							
							Integer groupSize = studFill.getGroupSize(subject.getItemText(subjectForInd), level.get(levelForInd), 
									selectedGroupIndex, class12, teacherFromBox);
							
							// Moksleiviu sarasa parodo
							List<Students> studList = studFill.getStudentListForGroup(subject.getItemText(subjectForInd), level.get(levelForInd), 
									selectedGroupIndex, groupsTitles.get(groupForInd), groupSize, class12, teacherFromBox);
							
							group.setStudentsList(studList);
							
							fullGroupsList.add(group);
							
						}
						
						groupForInd++;
					}
					
					teachForInd++;
				}
				
				levelForInd++;
			}
			
			subjectForInd++;
		}
		
		return fullGroupsList;
	}
	
	public ListBox getSubject() {
		return subject;
	}

	public void setSubject(ListBox subject) {
		this.subject = subject;
	}

	public HashMap<String, ListView<Students>> getList1Map() {
		return list1Map;
	}

	public void setList1Map(HashMap<String, ListView<Students>> list1Map) {
		this.list1Map = list1Map;
	}

}
