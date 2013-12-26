package lt.kutkaitis.pamokutvarkarastis.server.reading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import lt.kutkaitis.pamokutvarkarastis.client.teachers.Teacher;
import lt.kutkaitis.pamokutvarkarastis.server.ExcelReadingTools;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;


public class TeachersSheetReading {
	
	XSSFSheet sheet;
	
	public TeachersSheetReading(XSSFSheet sheet) {
		this.sheet = sheet;
		
	}
	
	private HashMap<String, ArrayList<Teacher>> teachersMap;
	
	public HashMap<String, ArrayList<Teacher>> readTeachearsSheet(){
		
			teachersMap = new LinkedHashMap<String, ArrayList<Teacher>>();
			
			for (Iterator<Row> rows = sheet.rowIterator(); rows.hasNext();) {
				XSSFRow row = ((XSSFRow) rows.next());
			
				settingData(row);
			}
		
		return teachersMap;
			
}	
	

	private void settingData(XSSFRow row) {
		
		if (row.getRowNum() >= 4 && row.getCell(0).getCellType() != 3) {
			
			// Prideda i mapa dalyka, jei tokio dar nera
			if (!teachersMap.containsKey(ExcelReadingTools.getCellValueAsString(row.getCell(1)))) {
				teachersMap.put(ExcelReadingTools.getCellValueAsString(row.getCell(1)), new ArrayList<Teacher>());
			}
				ArrayList<String> rooms = addRoomsToList(row);
				ArrayList<String> levels = addLevelsToList(row);
				ArrayList<String> clasToTeach = addClassesToList(row);
				Integer freeHours = countFreeHoursPerWeek(row);
				
				teachersMap.get(ExcelReadingTools.getCellValueAsString(row.getCell(1))).add(new Teacher(ExcelReadingTools.getCellValueAsString(row.getCell(2)), rooms, 
						levels, clasToTeach, freeHours, null, null));
			
		}
		
	}
	
	private Integer countFreeHoursPerWeek(XSSFRow row) {
		Integer freeHours = 0;
		for (Iterator<Cell> cells = row.cellIterator(); cells.hasNext();) {
			XSSFCell cell = ((XSSFCell) cells.next());
			if (cell.getColumnIndex() >= 6 && cell.getColumnIndex() <= 10 && cell.getCellType() != 3) {
				String stringCell = ExcelReadingTools.getCellValueAsString(cell);
				String [] result = stringCell.split("\\;");
				freeHours = freeHours + result.length;
			}
		}
		return freeHours;
	}
	
	private ArrayList<String> addClassesToList(XSSFRow row) {
		String cell = ExcelReadingTools.getCellValueAsString(row.getCell(5));
		ArrayList<String> classes = new ArrayList<String>();
		
		String [] result = cell.split("\\;");
	
		for (String data : result) {
			classes.add(data);
		}
		return classes;
	}

	private ArrayList<String> addLevelsToList(XSSFRow row) {
		String cell = ExcelReadingTools.getCellValueAsString(row.getCell(4));
		ArrayList<String> levels = new ArrayList<String>();
		
		String [] result = cell.split("\\;");
			
		for (String data : result) {
			levels.add(data);
		}
		return levels;
	}

	private ArrayList<String> addRoomsToList(XSSFRow row) {
		String cell = ExcelReadingTools.getCellValueAsString(row.getCell(3));
		ArrayList<String> rooms = new ArrayList<String>();
		
		String [] result = cell.split("\\;");
		
		for (String data : result) {
			rooms.add(data);
		}
		return rooms;
	}
}
