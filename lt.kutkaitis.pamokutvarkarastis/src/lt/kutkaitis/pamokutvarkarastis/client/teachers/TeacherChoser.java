package lt.kutkaitis.pamokutvarkarastis.client.teachers;

import java.util.ArrayList;
import java.util.HashMap;

import lt.kutkaitis.pamokutvarkarastis.client.subjects.Subject;

public class TeacherChoser {
	
	private static final int hoursOfTeacherPerWeek = 35;
	private Integer numOfGroups;
	private String subject;
	private String level;
	private HashMap<String, ArrayList<Teacher>> teachersMap;
	private ArrayList<Teacher> teachListAfterLvl;
	private HashMap<String, HashMap<String, Subject>> subjectsMap;
	private Boolean class12th;
	
	public TeacherChoser(Integer numOfGroups, String subject, String level, 
			HashMap<String, ArrayList<Teacher>> teachersMap, HashMap<String, HashMap<String, Subject>> subjectsMap) {
		this.numOfGroups = numOfGroups;
		this.subject = subject;
		this.level = level;
		this.teachersMap = teachersMap;
		this.subjectsMap = subjectsMap;
		this.class12th = false;
	}
	
	public TeacherChoser(Integer numOfGroups, String subject, String level, 
			HashMap<String, ArrayList<Teacher>> teachersMap, HashMap<String, HashMap<String, Subject>> subjectsMap, Boolean class12th) {
		this.numOfGroups = numOfGroups;
		this.subject = subject;
		this.level = level;
		this.teachersMap = teachersMap;
		this.subjectsMap = subjectsMap;
		this.class12th = class12th;
	}
	
	public TeacherChoser() {
	}
	
	public ArrayList<Teacher> chooseTeacher() {
		
		ArrayList<Teacher> teachersList = teachersMap.get(subject);
		teachListAfterLvl = new ArrayList<Teacher>();

			for (Teacher teach : teachersList) {
				if (numOfGroups >= 1) {
					
					if (teach.getLevels().contains(level)) {
						
						Integer teacherWorkHours = hoursOfTeacherPerWeek - teach.getFreeHours(); // 35 - valandu skaicius per savaite
						
						Integer hours11 = 0;
						Integer hours12 = 0;
						
						if (!subjectsMap.get(subject).get(level).getHoursOf11().equals("")){
							hours11 = Integer.parseInt(subjectsMap.get(subject).get(level).getHoursOf11());
						}
						if (!subjectsMap.get(subject).get(level).getHoursOf12().equals("")){
							hours12 = Integer.parseInt(subjectsMap.get(subject).get(level).getHoursOf12());
						}
						
						Integer grNumbersTeachAble11 = 0;
						Integer grNumbersTeachAble12 = 0;
						
						// Esme kad prie mokytojo turi rodyti tiek 11okus tiek tuos pacius 12 (ta prasme grupes)
						if (hours11 == hours12) {
							if (hours11 != 0) {
								while (teacherWorkHours >= hours11 && numOfGroups != 0) {
									teacherWorkHours = teacherWorkHours - hours11;
									numOfGroups--;
									grNumbersTeachAble11++;
									grNumbersTeachAble12++;
								}
								setGroupsTeachAble(teach, grNumbersTeachAble11, grNumbersTeachAble12);
								teachListAfterLvl.add(teach); 
								
							}
						}
		
						else {
							if (hours11 == 0 && hours12 != 0) {
								while (teacherWorkHours >= hours12 && numOfGroups != 0) {
									teacherWorkHours = teacherWorkHours - hours12;
									numOfGroups--;
									grNumbersTeachAble11++;
									grNumbersTeachAble12++;
								}
								
								setGroupsTeachAble(teach, grNumbersTeachAble11,	grNumbersTeachAble12);
								teachListAfterLvl.add(teach); 
							}
							
							if (hours11 != 0 && hours12 == 0) {
								while (teacherWorkHours >= hours12 && numOfGroups != 0) {
									teacherWorkHours = teacherWorkHours - hours11;
									numOfGroups--;
									grNumbersTeachAble11++;
									grNumbersTeachAble12++;
								}

								setGroupsTeachAble(teach, grNumbersTeachAble11,	grNumbersTeachAble12);
								teachListAfterLvl.add(teach); 
							}
							
							// Jei daugiau, tai reiskia kad mokytojas 12 klasej tures daugiau laisvo laiko
							if (hours11 > hours12 && hours12 != 0) {
								while (teacherWorkHours >= hours11 && numOfGroups != 0) {
									teacherWorkHours = teacherWorkHours - hours11;
									numOfGroups--;
									grNumbersTeachAble11++;
									grNumbersTeachAble12++;
								}

								setGroupsTeachAble(teach, grNumbersTeachAble11,	grNumbersTeachAble12);
								teachListAfterLvl.add(teach); 
								
							}
							// Ir atvirksciai
							if (hours11 < hours12 && hours11 != 0) {
								while (teacherWorkHours >= hours12 && numOfGroups != 0) {
									teacherWorkHours = teacherWorkHours - hours12;
									numOfGroups--;
									grNumbersTeachAble11++;
									grNumbersTeachAble12++;
								}

								setGroupsTeachAble(teach, grNumbersTeachAble11,	grNumbersTeachAble12);
								teachListAfterLvl.add(teach); 
								
							}
							
						}
				}
		}
			
	}
		return teachListAfterLvl;
	}

	private void setGroupsTeachAble(Teacher teach, Integer grNumbersTeachAble11, Integer grNumbersTeachAble12) {
		if (class12th != true) {
			teach.setGroupsTeachAble11(grNumbersTeachAble11);
		}
		else {
			teach.setGroupsTeachAble12(grNumbersTeachAble12);
		}
	}
}
