package lt.kutkaitis.pamokutvarkarastis.server.students;

public class StudentObject {
	
	private String name;
	private Integer creditsNumber;
	private String schoolClass;
	
	
	public StudentObject(String name, Integer creditsNumber, String schoolClass) {
		this.name = name;
		this.creditsNumber = creditsNumber;
		this.schoolClass = schoolClass;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCreditsNumber() {
		return creditsNumber;
	}
	public void setCreditsNumber(Integer creditsNumber) {
		this.creditsNumber = creditsNumber;
	}
	public String getSchoolClass() {
		return schoolClass;
	}
	public void setSchoolClass(String schoolClass) {
		this.schoolClass = schoolClass;
	}

}
