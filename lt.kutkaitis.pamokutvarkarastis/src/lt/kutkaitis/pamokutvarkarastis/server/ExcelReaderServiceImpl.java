package lt.kutkaitis.pamokutvarkarastis.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import lt.kutkaitis.pamokutvarkarastis.client.ExcelReaderService;
import lt.kutkaitis.pamokutvarkarastis.client.returned.Data;
import lt.kutkaitis.pamokutvarkarastis.client.subjects.Subject;
import lt.kutkaitis.pamokutvarkarastis.client.teachers.Teacher;
import lt.kutkaitis.pamokutvarkarastis.server.reading.SubjectsSheetReading;
import lt.kutkaitis.pamokutvarkarastis.server.reading.TeachersSheetReading;
import lt.kutkaitis.pamokutvarkarastis.server.students.StudentObject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ExcelReaderServiceImpl extends RemoteServiceServlet implements ExcelReaderService{

	private FileInputStream fis;
	private XSSFWorkbook workbook;

	public HashMap<String, ArrayList<StudentObject>> studentsMapOfList;
	
	private HashMap<String, HashMap<String, HashMap<String, ArrayList<String>>>> studentsMap;
	private HashMap<Integer, String> columnIndexMap;
	
	private Integer lastColumnIndex;
	private int lastCellNum;
	private Integer lastRowNum;
	
	private HashMap<String, ArrayList<Teacher>> teachersMap;
	private HashMap<String, HashMap<String, Subject>> subjectsMap;
	
	private ArrayList<String> studentsList;
	
	public Data returnData() {
		
		studentsMap = new HashMap<String, HashMap<String,HashMap<String,ArrayList<String>>>>(); // Sukuria cia nauja, kad neoverridintu objectu kai per kitus sheetus varines
		teachersMap = new HashMap<String, ArrayList<Teacher>>();
		subjectsMap = new HashMap<String, HashMap<String, Subject>>();
		studentsList = new ArrayList<String>();
		
			try {

				fis = new FileInputStream("Moksleiviu_pasirinkimai_2012.xlsx");
				workbook = new XSSFWorkbook(fis);
				
				columnIndexMap = new HashMap<Integer, String>();
				
				for (Iterator<XSSFSheet> sheets = workbook.iterator(); sheets.hasNext();) {
					XSSFSheet sheet = sheets.next();
					
					if (sheet.getSheetName().equals("Mokytojai")) {
						
						TeachersSheetReading teach = new TeachersSheetReading(sheet);
						teachersMap = teach.readTeachearsSheet();
						
					}
					
					if (sheet.getSheetName().equals("Dalykai")) {
					
						SubjectsSheetReading subjSheetRead = new SubjectsSheetReading(sheet);
						subjectsMap = subjSheetRead.readSubjectSheet();
						
					}
						/**
						 * Isiraso constanta iki kiek pildyti mapus
						 */
					if (!sheet.getSheetName().equals("Dalykai") && !sheet.getSheetName().equals("Mokytojai")) {
					
						getLastColumnIndex(sheet);
						getLastRowIndex(sheet);
						
						for (Iterator<Row> rows = sheet.rowIterator(); rows.hasNext();) {
							XSSFRow row = ((XSSFRow) rows.next());
							
							for (Iterator<Cell> cells = row.cellIterator(); cells.hasNext();) {
								XSSFCell cell = (XSSFCell) cells.next();
								
							
								
								/**
								 * Uzpildo Dalyku pavadinimus, pagal kuriuos veliau bus pildomas mapas ir toliau
								 *
								 */
								if (cell.getColumnIndex() >= 2 && cell.getColumnIndex() < lastColumnIndex
										&& cell.getCellType() != 3 	&& cell.getRowIndex() == 5 && !studentsMap.containsKey(ExcelReadingTools.getCellValueAsString(cell))) {
									
									
									studentsMap.put(ExcelReadingTools.getCellValueAsString(cell), new HashMap<String, HashMap<String,ArrayList<String>>>());
									
									columnIndexMap.put(cell.getColumnIndex(), ExcelReadingTools.getCellValueAsString(cell)); // Uzpildo map'a su dalykais ir column indexais kad veliau butu
									columnIndexMap.put(cell.getColumnIndex() + 1, ExcelReadingTools.getCellValueAsString(cell));// galeciau issitraukt is fullMapo pagal dalykus ir ji uzpildyt
																												// vieneta prideda nes excely dalyku collumnas yra uzima du lengeliu kai sumergintas,
																												// bet kai poi skaito jis antra langeli ima kaip tuscia, o man tas netinka kadangi reikia kad dalykas butu su dviem collumnais
									
									
								}
								
								/**
								 * Cia vyksta klasiu pildymas, dabar konkreciai tik galima uzpildyt dviem klasemis, nes 
								 * columnIndexMap turi tik du tuos pacius dalykus uzpildytus, pagal exceli
								 */
								
								if (cell.getColumnIndex() >= 2 && cell.getColumnIndex() < lastColumnIndex
										&& cell.getCellType() != 3 	&& cell.getRowIndex() == 6) {
									
									if (!studentsMap.get(columnIndexMap.get(cell.getColumnIndex())).containsKey(ExcelReadingTools.getCellValueAsString(cell))) {
										
										
									studentsMap.get(columnIndexMap.get(cell.getColumnIndex())).put(ExcelReadingTools.getCellValueAsString(cell), new HashMap<String, ArrayList<String>>());
									
									studentsMap.get(columnIndexMap.get(cell.getColumnIndex())).get(ExcelReadingTools.getCellValueAsString(cell)).put("A", new ArrayList<String>());
									
									studentsMap.get(columnIndexMap.get(cell.getColumnIndex())).get(ExcelReadingTools.getCellValueAsString(cell)).put("B", new ArrayList<String>());
									studentsMap.get(columnIndexMap.get(cell.getColumnIndex())).get(ExcelReadingTools.getCellValueAsString(cell)).put("Be lygio", new ArrayList<String>());
									}
								}
								
								
								
								/**
								 * Cia vyksta studentu priskyrimas i atitinkamu mapus pagal ju pasirinkta dalyku lygi
								 */
								
								
								if (cell.getColumnIndex() >= 2 && cell.getColumnIndex() < lastColumnIndex
										&& cell.getCellType() != 3 	&& cell.getRowIndex() >= 7 &&  cell.getRowIndex() < lastRowNum) {
								
									/**
									 * Sudeda moksleivius pagal A lygi pasirinkusius dalykus
									 * getCellType() != 3 reiskia ne tuscia cell'e
									 * Su listais turetu veikti, bet shitty reikalas, kadangi pastoviai perdeda objekta, neefektyvu!!
									 */
									
									studentsList.add(ExcelReadingTools.getCellValueAsString(row.getCell(1)));
									
									
									if (ExcelReadingTools.getCellValueAsString(cell).contains("(A)") && cell.getCellType() != 3) {
										
										ArrayList<String> studWithA = studentsMap.get(columnIndexMap.get(cell.getColumnIndex())).get(ExcelReadingTools.getCellValueAsString(sheet.getRow(6).
												getCell(cell.getColumnIndex()))).get("A");
											
										studWithA.add(ExcelReadingTools.getCellValueAsString(row.getCell(1)));
										
										studentsMap.get(columnIndexMap.get(cell.getColumnIndex())).get(ExcelReadingTools.getCellValueAsString(sheet.getRow(6).
												getCell(cell.getColumnIndex()))).put("A", studWithA);
									
									}
									
									if (ExcelReadingTools.getCellValueAsString(cell).contains("(B)") && cell.getCellType() != 3) {
										
										ArrayList<String> studWithB = studentsMap.get(columnIndexMap.get(cell.getColumnIndex())).
												get(ExcelReadingTools.getCellValueAsString(sheet.getRow(6).getCell(cell.getColumnIndex()))).get("B");
										studWithB.add(ExcelReadingTools.getCellValueAsString(row.getCell(1)));
										
										studentsMap.get(columnIndexMap.get(cell.getColumnIndex())).
											get(ExcelReadingTools.getCellValueAsString(sheet.getRow(6).getCell(cell.getColumnIndex()))).put("B", studWithB);
									}
									else {
										
									}
									
									if (!(ExcelReadingTools.getCellValueAsString(cell).contains("(A)")) && !(ExcelReadingTools.getCellValueAsString(cell).contains("(B)")) 
											&& cell.getCellType() != 3 && !ExcelReadingTools.getCellValueAsString(cell).equals("")) {
										
										ArrayList<String> studWithoutLvl = studentsMap.get(columnIndexMap.get(cell.getColumnIndex())).
												get(ExcelReadingTools.getCellValueAsString(sheet.getRow(6).getCell(cell.getColumnIndex()))).get("Be lygio");
										studWithoutLvl.add(ExcelReadingTools.getCellValueAsString(row.getCell(1)));
										
										studentsMap.get(columnIndexMap.get(cell.getColumnIndex())).
											get(ExcelReadingTools.getCellValueAsString(sheet.getRow(6).getCell(cell.getColumnIndex()))).put("Be lygio", studWithoutLvl);
									}
									
									}
								}
	
							}
						}
					}
				

			} catch (IOException e) {
				e.printStackTrace();

			}
			
			return new Data(teachersMap, studentsMap, subjectsMap, studentsList);
		}
	
	private void getLastRowIndex(XSSFSheet sheet) {
		
		for (Iterator<Row> rows = sheet.rowIterator(); rows.hasNext();) {
			XSSFRow row = ((XSSFRow) rows.next());
			if (ExcelReadingTools.getCellValueAsString(row.getCell(0)).equals("Viso (A kursas):")) {
				lastRowNum = row.getRowNum();
			}
		}
	}
	
	
	private void getLastColumnIndex(XSSFSheet sheet) {
		
		if (!sheet.getSheetName().equals("Mokytojai")) {
			lastCellNum = sheet.getRow(5).getLastCellNum();
			lastColumnIndex = lastCellNum - 4;
		}
	}

}
