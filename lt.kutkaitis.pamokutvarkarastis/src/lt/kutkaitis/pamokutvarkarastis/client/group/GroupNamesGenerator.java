package lt.kutkaitis.pamokutvarkarastis.client.group;

import java.util.ArrayList;

public class GroupNamesGenerator {
	
	private Integer groupsNumber;
	private String shortGroupName;
	private String paralel;
	private Integer grNum;

	public GroupNamesGenerator(Integer groupsNumber, String shortGroupName, String paralel, Integer grNum){
		this.groupsNumber = groupsNumber;
		this.shortGroupName = shortGroupName;
		this.paralel = paralel;
		this.grNum = grNum;
	}
	
	public GroupNamesGenerator() {
	}
	
	public ArrayList<String> generateGroups() {
		ArrayList<String> groups = new ArrayList<String>();
		
		while(groupsNumber > 0) {
			groups.add(shortGroupName + "_" + paralel + "_" + grNum);
			groupsNumber--;
			grNum++;
		}
		
		return groups;
	}

}
