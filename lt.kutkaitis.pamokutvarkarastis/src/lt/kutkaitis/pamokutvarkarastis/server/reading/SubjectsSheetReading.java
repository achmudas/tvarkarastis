package lt.kutkaitis.pamokutvarkarastis.server.reading;

import java.util.HashMap;
import java.util.Iterator;

import lt.kutkaitis.pamokutvarkarastis.client.subjects.Subject;
import lt.kutkaitis.pamokutvarkarastis.server.ExcelReadingTools;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class SubjectsSheetReading {
	
	private XSSFSheet sheet;
	private HashMap<String, HashMap<String, Subject>> subjectsMap;

	
	public SubjectsSheetReading(XSSFSheet sheet) {
		this.sheet = sheet;
	}
	
	public HashMap<String, HashMap<String, Subject>> readSubjectSheet() {
		
		subjectsMap = new HashMap<String, HashMap<String, Subject>>();
		
		for (Iterator<Row> rows = sheet.rowIterator(); rows.hasNext();) {
			XSSFRow row = ((XSSFRow) rows.next());
		
			settingData(row);
		}
		
		return subjectsMap;
	}
	
	private void settingData(XSSFRow row) {
		
		if (row.getRowNum() >= 2 && row.getCell(0).getCellType() != 3) {
			
			if (!subjectsMap.containsKey(ExcelReadingTools.getCellValueAsString(row.getCell(1)))) {
				subjectsMap.put(ExcelReadingTools.getCellValueAsString(row.getCell(1)), new HashMap<String, Subject>());
				
				fillMap(row);
			}
			
			else {
				fillMap(row);
			}
		}
	}
	
	private void fillMap(XSSFRow row) {
		
		if (ExcelReadingTools.getCellValueAsString(row.getCell(2)).equals("A")) {
			subjectsMap.get(ExcelReadingTools.getCellValueAsString(row.getCell(1))).put("A", new Subject(ExcelReadingTools.getCellValueAsString(row.getCell(1)), 
					ExcelReadingTools.getCellValueAsString(row.getCell(2)), ExcelReadingTools.getCellValueAsString(row.getCell(3)), 
					ExcelReadingTools.getCellValueAsString(row.getCell(4)), ExcelReadingTools.getCellValueAsString(row.getCell(6)), 
					ExcelReadingTools.getCellValueAsString(row.getCell(7)), ExcelReadingTools.getCellValueAsString(row.getCell(8))));
		}
		
		if (ExcelReadingTools.getCellValueAsString(row.getCell(2)).equals("B")) {
			subjectsMap.get(ExcelReadingTools.getCellValueAsString(row.getCell(1))).put("B", new Subject(ExcelReadingTools.getCellValueAsString(row.getCell(1)), 
					ExcelReadingTools.getCellValueAsString(row.getCell(2)), ExcelReadingTools.getCellValueAsString(row.getCell(3)), 
					ExcelReadingTools.getCellValueAsString(row.getCell(4)), ExcelReadingTools.getCellValueAsString(row.getCell(6)),
					ExcelReadingTools.getCellValueAsString(row.getCell(7)), ExcelReadingTools.getCellValueAsString(row.getCell(8))));
		}
		
		if (ExcelReadingTools.getCellValueAsString(row.getCell(2)).equals("Be lygio")) {
			
			subjectsMap.get(ExcelReadingTools.getCellValueAsString(row.getCell(1))).put("Be lygio", new Subject(ExcelReadingTools.getCellValueAsString(row.getCell(1)), 
					"Be lygio", ExcelReadingTools.getCellValueAsString(row.getCell(3)), ExcelReadingTools.getCellValueAsString(row.getCell(4)),
					ExcelReadingTools.getCellValueAsString(row.getCell(6)), ExcelReadingTools.getCellValueAsString(row.getCell(7)),
					ExcelReadingTools.getCellValueAsString(row.getCell(8))));
		}
		
	}

}
