package lt.kutkaitis.pamokutvarkarastis.client.subjects;

import java.io.Serializable;

public class Subject implements Serializable{
	
	private String subject;
	private String level;
	private String minNumber;
	private String maxNumber;
	private String shortGroupName;
	private String hoursOf11;
	private String hoursOf12;
	
	
	public Subject(String subject, String level, String minNumber, String maxNumber, String hoursOf11, String hoursOf12, String shortGroupName){
		this.subject = subject;
		this.level = level;
		this.minNumber = minNumber;
		this.maxNumber = maxNumber;
		this.shortGroupName = shortGroupName;
		this.hoursOf11 = hoursOf11;
		this.hoursOf12 = hoursOf12;
	}
	
	public Subject() {
		
	}
	
	public String getHoursOf11() {
		return hoursOf11;
	}

	public void setHoursOf11(String hoursOf11) {
		this.hoursOf11 = hoursOf11;
	}

	public String getHoursOf12() {
		return hoursOf12;
	}

	public void setHoursOf12(String hoursOf12) {
		this.hoursOf12 = hoursOf12;
	}

	public String getShortGroupName() {
		return shortGroupName;
	}

	public void setShortGroupName(String shortGroupName) {
		this.shortGroupName = shortGroupName;
	}
	
	public String getMinNumber() {
		return minNumber;
	}

	public void setMinNumber(String minNumber) {
		this.minNumber = minNumber;
	}

	public String getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(String maxNumber) {
		this.maxNumber = maxNumber;
	}

	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
