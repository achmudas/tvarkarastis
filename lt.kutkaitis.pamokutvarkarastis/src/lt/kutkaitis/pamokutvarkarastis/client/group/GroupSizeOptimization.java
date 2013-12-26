package lt.kutkaitis.pamokutvarkarastis.client.group;

import java.util.ArrayList;
import java.util.Collections;

public class GroupSizeOptimization {
	
	
	private Integer minNumber;
	private Integer maxNumber;
	private Integer studSize;
	public Integer numOfGroups;
	
	public GroupSizeOptimization(Integer minNumber, Integer maxNumber, Integer studSize) {
		this.minNumber = minNumber;
		this.maxNumber = maxNumber;
		this.studSize = studSize;
	}
	
	public GroupSizeOptimization() {
		
	}
	
	public ArrayList<Integer> findOptimalGroupSize() {
		
		if (studSize >= minNumber && studSize <= maxNumber) {
			numOfGroups = 1;
			ArrayList<Integer> studentsNumbers = new ArrayList<Integer>();
			studentsNumbers.add(studSize);
			return studentsNumbers;
		}
		else {
			if (studSize != 0) {
				
				// Naujas algoritmas grupiu optimizavimui
				numOfGroups = (studSize / maxNumber);
				Integer numOfGroupsLocal = ++numOfGroups;
				
				Integer studNumberBase = studSize % maxNumber;
				Integer withoutGroupStudents = studSize - (studNumberBase * numOfGroups);
				
				ArrayList<Integer> studentsNumbers = new ArrayList<Integer>();

				
				Integer [] groupSizes = new Integer[numOfGroups];
				
				for (int i = 0; i < numOfGroupsLocal; i++) {
					groupSizes[i] = studNumberBase;
				}
				
				while(withoutGroupStudents > 0) {
					for (int j = 0; j < numOfGroupsLocal; j++) {
						groupSizes[j] = groupSizes[j] + 1;
						withoutGroupStudents--;
						if (withoutGroupStudents == 0) {
							break;
						}
					}
				}
				
				Collections.addAll(studentsNumbers, groupSizes); 
				return studentsNumbers; // Grazina visa lista su atitinkamais klasiu dydziais
			}
			numOfGroups = 0;
			return new ArrayList<Integer>();
		}
	}
}
