package lt.kutkaitis.pamokutvarkarastis.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lt.kutkaitis.pamokutvarkarastis.client.group.GroupNamesGenerator;
import lt.kutkaitis.pamokutvarkarastis.client.group.GroupSizeOptimization;
import lt.kutkaitis.pamokutvarkarastis.client.returned.Data;
import lt.kutkaitis.pamokutvarkarastis.client.teachers.Teacher;
import lt.kutkaitis.pamokutvarkarastis.client.teachers.TeacherChoser;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author MkA
 * 
 * 
 *         TODO here will be sorting I guess
 */
public class DataFilling {

//	private List<Students> moksSar;
	private PamokuTvarkarastis pamTvar;
	private ArrayList<String> subjectList;
	public Data settedResult;
	private ArrayList<String> allGroups;
	public Integer grNum;

	public DataFilling(PamokuTvarkarastis pamTvar) {
		this.pamTvar = pamTvar;
	}

	public DataFilling() {
	}

	public void getData() {

		ExcelReaderService.instance.getData().returnData(new AsyncCallback<Data>() {
			
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Data result) {
				
				setResult(result);
				
				subjectList = getSubjectList(result.getStudentsMap());
				
				/*
				 * Defaultinis kai uzsikrauna
				 */
				
				ArrayList<String> subLvlList = getSubjectsLevels(subjectList.get(0)); // default elementas buna - 0
				
				StudentsList studList = new StudentsList(subjectList.get(0), subLvlList.get(0), settedResult.getStudentsMap());
				/*
				 * defaultinis
				 */
				List<Students> studList11 = studList.getStudentList11(); 
				List<Students> studList12 = studList.getStudentList12(); 
				
				pamTvar.fillSubjectDropBox(subjectList);
				pamTvar.updateSubjectsLevel(subLvlList);
				
				/*
				 * Defaultinis
				 */
				Integer studSize11 = studList11.size();
				Integer studSize12 = studList12.size();
				
				Integer minNumber = Integer.parseInt(settedResult.getSubjectsMap().get(subjectList.get(0)).get(subLvlList.get(0)).getMinNumber());
				Integer maxNumber = Integer.parseInt(settedResult.getSubjectsMap().get(subjectList.get(0)).get(subLvlList.get(0)).getMaxNumber());
				
				GroupSizeOptimization grOpt11 = new GroupSizeOptimization(minNumber, maxNumber, studSize11);
				GroupSizeOptimization grOpt12 = new GroupSizeOptimization(minNumber, maxNumber, studSize12);
				
				ArrayList<Integer> groupSizesList11 = grOpt11.findOptimalGroupSize();
				
				defaultTeacherBoxFilling(subLvlList, grOpt11, grOpt12);
					
				defaultGroupBoxFill(grOpt11, subLvlList);
				
				pamTvar.fillStudNumBox(groupSizesList11.get(0)); //Vistiek pirmas buna parinktas, HARDCODE Rulezzzzz
				pamTvar.createDragDropList(studList11);
				
			}
			
			private void defaultGroupBoxFill(GroupSizeOptimization grOpt, ArrayList<String> subLvlList) {
				String shortName = settedResult.getSubjectsMap().get(subjectList.get(0)).get(subLvlList.get(0)).getShortGroupName();
				
				
				grNum = new Integer(1);
				
				GroupNamesGenerator grNamesGen11 = new GroupNamesGenerator(grOpt.numOfGroups, shortName, "11", grNum); 
				GroupNamesGenerator grNamesGen12 = new GroupNamesGenerator(grOpt.numOfGroups, shortName, "12", grNum); 
				
				ArrayList<String> groups11 = grNamesGen11.generateGroups();
				ArrayList<String> groups12 = grNamesGen12.generateGroups();
				
				allGroups = new ArrayList<String>();
				allGroups.addAll(groups11);
				allGroups.addAll(groups12);
					
				pamTvar.fillGroupBox(allGroups);
				
			}

			private void defaultTeacherBoxFilling(ArrayList<String> subLvlList,	GroupSizeOptimization grOpt11, GroupSizeOptimization grOpt12) {
				TeacherChoser teacherCho = new TeacherChoser(grOpt11.numOfGroups, subjectList.get(0), subLvlList.get(0), settedResult.getTeacherMap(), settedResult.getSubjectsMap());
				ArrayList<Teacher> teachers = teacherCho.chooseTeacher();
				
				pamTvar.fillTeacherBox(teachers);
			}
		
		});
			
	}
	
	public ArrayList<String> getGroupTitles(String subject, String lvl, ArrayList<Teacher> teacher, Integer teacherSize, 
			Integer selectedTeacherIndex) { // Del selected teacher klausimas dar
		
		grNum = new Integer(1);
		
		StudentsList studList = new StudentsList(subject, lvl, settedResult.getStudentsMap());
		
		List<Students> studList11 = studList.getStudentList11(); 
		List<Students> studList12 = studList.getStudentList12(); 
		
		Integer studSize11 = studList11.size();
		Integer studSize12 = studList12.size();
		
		Integer minNumber = Integer.parseInt(settedResult.getSubjectsMap().get(subject).get(lvl).getMinNumber());
		Integer maxNumber = Integer.parseInt(settedResult.getSubjectsMap().get(subject).get(lvl).getMaxNumber());
		
		GroupSizeOptimization grOpt11 = new GroupSizeOptimization(minNumber, maxNumber, studSize11);
		GroupSizeOptimization grOpt12 = new GroupSizeOptimization(minNumber, maxNumber, studSize12);
		
		ArrayList<Integer> groupSizesList11 = grOpt11.findOptimalGroupSize();
		ArrayList<Integer> groupSizesList12 = grOpt12.findOptimalGroupSize();
		
		
		String shortName = settedResult.getSubjectsMap().get(subject).get(lvl).getShortGroupName();
		
		// Cia grazina numeri, nuo kurio reikia pradeti sudarineti grupiu indexus
		grNum = returnStarterGroupNumber(teacher, selectedTeacherIndex);
		
		GroupNamesGenerator grNamesGen11 = new GroupNamesGenerator(teacher.get(selectedTeacherIndex).getGroupsTeachAble11(), shortName, "11", grNum); 
		GroupNamesGenerator grNamesGen12 = new GroupNamesGenerator(teacher.get(selectedTeacherIndex).getGroupsTeachAble12(), shortName, "12", grNum); 
		
		
		// Cia jei nera moksleiviu tarkim kurioj nors paralelej
		ArrayList<String> groups11 = new ArrayList<String>();
		ArrayList<String> groups12 = new ArrayList<String>();
		
		if (studSize11 > 0) {
			groups11 = grNamesGen11.generateGroups();
		}
		
		if (studSize12 > 0) {
			groups12 = grNamesGen12.generateGroups();
		}
		
		ArrayList<String> allGroups = new ArrayList<String>();
		allGroups.addAll(groups11);
		allGroups.addAll(groups12);
		
		return allGroups;
	}

	public Integer returnStarterGroupNumber(ArrayList<Teacher> teacher, Integer selectedTeacherIndex) {
		
		grNum = new Integer(1);
		if (selectedTeacherIndex > 0) {
			for(int i = 0; i < selectedTeacherIndex; i++) {
				grNum = grNum + teacher.get(i).getGroupsTeachAble11();
			}
		}
		return grNum;
	}
	
	public ArrayList<Integer> getNumberOfStudentsPerGroup(Integer studSize, String subject, String lvl) {
		Integer minNumber = Integer.parseInt(settedResult.getSubjectsMap().get(subject).get(lvl).getMinNumber());
		Integer maxNumber = Integer.parseInt(settedResult.getSubjectsMap().get(subject).get(lvl).getMaxNumber());
		
		GroupSizeOptimization grOpt = new GroupSizeOptimization(minNumber, maxNumber, studSize);
		ArrayList<Integer> optimalGroupSize = grOpt.findOptimalGroupSize();
		
		return optimalGroupSize;
	}
	
	
	public ArrayList<Teacher> getTeachers(String subject, String level){
		
		StudentsList studList = new StudentsList(subject, level, settedResult.getStudentsMap());
		
		List<Students> studList11 = studList.getStudentList11(); 
		List<Students> studList12 = studList.getStudentList12(); 
				
		Integer studSize11 = studList11.size();
		Integer studSize12 = studList12.size();
		
		Integer minNumber = Integer.parseInt(settedResult.getSubjectsMap().get(subject).get(level).getMinNumber());
		Integer maxNumber = Integer.parseInt(settedResult.getSubjectsMap().get(subject).get(level).getMaxNumber());
		
		GroupSizeOptimization grOpt11 = new GroupSizeOptimization(minNumber, maxNumber, studSize11);
		GroupSizeOptimization grOpt12 = new GroupSizeOptimization(minNumber, maxNumber, studSize12);
		
		ArrayList<Integer> groupSizesList11 = grOpt11.findOptimalGroupSize();
		ArrayList<Integer> groupSizesList12 = grOpt12.findOptimalGroupSize();
		
		Boolean class12th = true;
		
		TeacherChoser teacherCho11 = new TeacherChoser(grOpt11.numOfGroups, subject, level, settedResult.getTeacherMap(), settedResult.getSubjectsMap());
		TeacherChoser teacherCho12 = new TeacherChoser(grOpt12.numOfGroups, subject, level, settedResult.getTeacherMap(), settedResult.getSubjectsMap(), class12th);
		
		ArrayList<Teacher> teacher11 = teacherCho11.chooseTeacher();
		ArrayList<Teacher> teacher12 = teacherCho12.chooseTeacher();
		
		ArrayList<Teacher> allTeachers = new ArrayList<Teacher>();
		
		allTeachers.addAll(teacher11);
		
		for (Teacher teach : teacher12) {
			
			if (!allTeachers.contains(teach)) {
				allTeachers.add(teach);
			}
		}
		
		return allTeachers;
		
	}
	
	public Integer getGroupSize(String subject, String level, Integer groupChosen, boolean class12, Teacher teacher) { // Teacher istrinti, nes nereikia
		
		StudentsList studList = new StudentsList(subject, level, settedResult.getStudentsMap());
		
		List<Students> studList11 = studList.getStudentList11(); 
		List<Students> studList12 = studList.getStudentList12(); 
				
		Integer studSize11 = studList11.size();
		Integer studSize12 = studList12.size();
		
		Integer minNumber = Integer.parseInt(settedResult.getSubjectsMap().get(subject).get(level).getMinNumber());
		Integer maxNumber = Integer.parseInt(settedResult.getSubjectsMap().get(subject).get(level).getMaxNumber());
		
		GroupSizeOptimization grOpt11 = new GroupSizeOptimization(minNumber, maxNumber, studSize11);
		GroupSizeOptimization grOpt12 = new GroupSizeOptimization(minNumber, maxNumber, studSize12);
		
		ArrayList<Integer> groupSizesList11 = grOpt11.findOptimalGroupSize();
		ArrayList<Integer> groupSizesList12 = grOpt12.findOptimalGroupSize();

		// Kad kai dvyliktai klasei pasirenkama kad nebutu indexas didesnis nei array
		if (class12 == true) {
			// Cia tas atvejis kai nera 11 klases grupiu ir groupChosen jau yra 0 po defaultu
			if (grOpt11.numOfGroups != 0) {
				groupChosen = groupChosen - teacher.getGroupsTeachAble11(); 
			}
			else {
				groupChosen = groupChosen - grOpt11.numOfGroups;
			}
			
			return groupSizesList12.get(groupChosen);
		}
		return groupSizesList11.get(groupChosen);
		
	}
	
	public List<Students> getStudentListForGroup(String subject, String level, Integer selectedGroup, String groupTitle, Integer groupSize, boolean class12, Teacher teacher) {
		StudentsList studList = new StudentsList(subject, level, settedResult.getStudentsMap());
		
		List<Students> studList11 = studList.getStudentList11(); 
		List<Students> studList12 = studList.getStudentList12(); 
		
		Integer studSize11 = studList11.size();
		Integer studSize12 = studList12.size();
		
		Integer minNumber = Integer.parseInt(settedResult.getSubjectsMap().get(subject).get(level).getMinNumber());
		Integer maxNumber = Integer.parseInt(settedResult.getSubjectsMap().get(subject).get(level).getMaxNumber());
		
		GroupSizeOptimization grOpt11 = new GroupSizeOptimization(minNumber, maxNumber, studSize11);
		GroupSizeOptimization grOpt12 = new GroupSizeOptimization(minNumber, maxNumber, studSize12);
		
		ArrayList<Integer> groupSizesList11 = grOpt11.findOptimalGroupSize();
		ArrayList<Integer> groupSizesList12 = grOpt12.findOptimalGroupSize();
		
		if (class12 == true) {
			if (grOpt11.numOfGroups != 0) {
				selectedGroup = selectedGroup - teacher.getGroupsTeachAble11(); 
			}
			else {
				selectedGroup = selectedGroup - grOpt11.numOfGroups;
			}
		}
			
		if (groupTitle.contains("11")) {
			List<Students> studForGroup = new ArrayList<Students>();
			Integer i = choseStartingPointOfList(selectedGroup, groupSizesList11);
			int j = 1;
			
			while (groupSize > 0 ) {
				studForGroup.add(studList11.get(i));
				i++;
				groupSize--;
			}
			
			return studForGroup;
		}
		

		if (groupTitle.contains("12")) {
			List<Students> studForGroup = new ArrayList<Students>();
			Integer i = choseStartingPointOfList(selectedGroup, groupSizesList12);
			while (groupSize > 0 ) { //kadangi nulis buna tai ir gaunasi kad 12 paskui iesko
				studForGroup.add(studList12.get(i));
				i++;
				groupSize--;
			}
			
			return studForGroup;
		}
		
		return new ArrayList<Students>();
	}
	
	
	private Integer choseStartingPointOfList(Integer selectedGroup, ArrayList<Integer> groupSizesList) {
		Integer increment = 0;
		while (selectedGroup > 0) {
			increment = increment + groupSizesList.get(--selectedGroup);
		}
		return increment;
	}

	public ArrayList<String> getSubjectsLevels(String selectedValue) {
		return new ArrayList<String>(settedResult.getSubjectsMap().get(selectedValue).keySet());
	}
	
	public static ArrayList<String> getSubjectList(HashMap<String, HashMap<String, HashMap<String, ArrayList<String>>>> studentsMap) {
		return new ArrayList<String>(studentsMap.keySet());
	}

	public void setResult(Data result) {
		this.settedResult = result;
	}

}
