package lt.kutkaitis.pamokutvarkarastis.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import lt.kutkaitis.pamokutvarkarastis.client.group.Group;
import lt.kutkaitis.pamokutvarkarastis.client.group.GroupsSaving;
import lt.kutkaitis.pamokutvarkarastis.client.teachers.Teacher;
import lt.kutkaitis.pamokutvarkarastis.shared.resources.LtConstants;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.dnd.ListViewDragSource;
import com.extjs.gxt.ui.client.dnd.ListViewDropTarget;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.StoreSorter;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author MkA
 *
 */
public class PamokuTvarkarastis implements EntryPoint {

	private static final int hoursInDay = 7;

	private LtConstants ltConst = GWT.<LtConstants> create(LtConstants.class);
	
	private HorizontalPanel firstPanel = new HorizontalPanel();
	private HorizontalPanel secondPanel = new HorizontalPanel();
	private VerticalPanel studentsPanel = new VerticalPanel();
	private HorizontalPanel mainPanel = new HorizontalPanel();
	private VerticalPanel buttonsNFirstP = new VerticalPanel();
	private HorizontalPanel buttonsPanel = new HorizontalPanel();
	private ContentPanel cp;
	private ListBox subject = new ListBox();
	private ListBox level = new ListBox();
	private ListBox teacher = new ListBox();
	private ListBox group = new ListBox();
	private TextBox studNumBox = new TextBox();
	private Button saveButton = new Button(ltConst.save());
	private Label disciplineLabel = new Label("Dalykas");
	private Label levelLabel = new Label("Lygis");
	private Label teacherLabel = new Label("Mokytojas");
	private Label numberStudents = new Label(ltConst.moksKiek());
	private Label groupLabel = new Label(ltConst.grupe());
	private VerticalPanel disciplinePanel = new VerticalPanel();
	private VerticalPanel levelPanel = new VerticalPanel();
	private VerticalPanel teacherPanel = new VerticalPanel();
	private VerticalPanel groupPanel = new VerticalPanel();
	private VerticalPanel studNumPanel = new VerticalPanel();
	private Button cancelButton = new Button(ltConst.cancel());
	
	private DataFilling studFill;
	
	private GroupsSaving grSaving;
	
	private ListView<Students> list1;
	private ListView<Students> list2;
	
	private HashMap<String, ListView<Students>> list1Map;
	
	private ArrayList<Group> fullGroupsList;
	
	private String studentas = new String();
	private Grid resultStudTable;
	private Grid resultMonday;
	private Grid resultTuesday;
	private Grid resultWednesday;
	private Grid resultThursday;
	private Grid resultFriday;
	private VerticalPanel mondayPanel = new VerticalPanel();
	private VerticalPanel tuesdayPanel = new VerticalPanel();
	private VerticalPanel wednesdayPanel = new VerticalPanel();
	private VerticalPanel thursdayPanel = new VerticalPanel();
	private VerticalPanel fridayPanel = new VerticalPanel();
	
	private Label monday = new Label("Pirmadienis");
	private Label tuesday = new Label("Antradienis");
	private Label wednesday = new Label(ltConst.trec());
	private Label thursday = new Label("Ketvirtadienis");
	private Label friday = new Label("Penktadienis");
	
	private Label studLabel = new Label("Moksleivis");
	private Label studLabel1 = new Label("Moksleivis");
	private Label studLabel2 = new Label("Moksleivis");
	private Label studLabel3 = new Label("Moksleivis");
	private Label studLabel4 = new Label("Moksleivis");
	
	private VerticalPanel finalFinalPanel = new VerticalPanel();
	private HashMap<String, ArrayList<String>> finalResult = new LinkedHashMap<String, ArrayList<String>>();
	private HorizontalPanel mondTabPanel = new HorizontalPanel();
	private HorizontalPanel tuesTabPanel = new HorizontalPanel();
	private HorizontalPanel wednTabPanel = new HorizontalPanel();
	private HorizontalPanel thurTabPanel = new HorizontalPanel();
	private HorizontalPanel fridTabPanel = new HorizontalPanel();
	
	private Grid resultStudTable1;
	private Grid resultStudTable2;
	private Grid resultStudTable3;
	private Grid resultStudTable4;
	
	private TabPanel tabPanel = new TabPanel();
	
	@Override
	public void onModuleLoad() {
		
		/**
		 * File upload langas
		 */
		final FormPanel form = new FormPanel();
		form.setAction(GWT.getModuleBaseURL() +	"UploadFileServlet");

	    // Because we're going to add a FileUpload widget, we'll need to set the
	    // form to use the POST method, and multipart MIME encoding.
	    form.setEncoding(FormPanel.ENCODING_MULTIPART);
	    form.setMethod(FormPanel.METHOD_POST);
	    
	    // Create a panel to hold all of the form widgets.
	    VerticalPanel panel = new VerticalPanel();
	    panel.setSize("100%", "100%");
	    panel.setSpacing(12);
//	    panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
	    panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    form.setWidget(panel);
	    
	    final Label uplLabel = new Label(ltConst.upload());
	    panel.add(uplLabel);
		
	    // Sukurti FileUpload widget'à.
	    final FileUpload upload = new FileUpload();
	    upload.setName("uploadFormElement");
	    panel.add(upload);
	    
	    // Add a 'submit' button.
	    Button submit = new Button(ltConst.uploadButton());
	    submit.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				form.submit();				
			}
		});
	    panel.add(submit);

	    
	//  Add an event handler to the form.
	    form.addSubmitHandler(new SubmitHandler() {
			
			@Override
			public void onSubmit(SubmitEvent event) {
				 if (upload.getFilename().isEmpty()) {
			        	
			        	Window.alert(ltConst.choseFile());
			        	event.cancel();
			        }
			}
	      });
	    
	      form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				Window.alert(event.getResults());
		          
		        RootPanel.get().clear();
		        
		      	studFill = new DataFilling(PamokuTvarkarastis.this);
				studFill.getData();
				
				list1Map = new HashMap<String, ListView<Students>>();
				
			}
		});

	      RootPanel.get().add(form);
	    
	}
	
public void updateDragDropList(List<Students> mokList) {

		ListStore<Students> tempList2 = list2.getStore();
	
		/**
		 * Refreshina moksleiviu lista
		 */
		if (cp != null) {
			studentsPanel.remove(cp);
		}
		
		cp = new ContentPanel();
		
		cp.setStyleAttribute("marginTop", "20px");
		cp.setStyleAttribute("marginRight", "100%");
		cp.setSize(500, 700);
		cp.setLayout(new RowLayout(Orientation.HORIZONTAL));
		cp.setHeading(ltConst.dndLabel());
		
		checkList1Existance(mokList);
		
		list2 = new ListView<Students>();
		list2.setDisplayProperty("name");
		list2.setStore(tempList2);

		new ListViewDragSource(list1);
		new ListViewDragSource(list2);

		new ListViewDropTarget(list1);
		new ListViewDropTarget(list2);

		RowData data = new RowData(.5, 1);
		data.setMargins(new Margins(15));
		cp.add(list1, data);
		cp.add(list2, data);
		
		// Isideda list1 i map'a kad veliau kai bus pasirinkta ta pati grupe duomenis imtu ne is excelio o is ten kas ir buvo keista
		if (list1Map.containsKey(group.getItemText(group.getSelectedIndex()))) {
			list1Map.remove(group.getItemText(group.getSelectedIndex()));
		}
		list1Map.put(group.getItemText(group.getSelectedIndex()), list1);
		studentsPanel.add(cp);

}

private void checkList1Existance(List<Students> mokList) {
	// Patikrina ar egzistuoja tokia grupe map'e, jei taip listas naudojamas is sito mapo
	if (list1Map.containsKey(group.getItemText(group.getSelectedIndex()))) {
		list1 = list1Map.get(group.getItemText(group.getSelectedIndex()));
	}
	
	else {
		list1 = new ListView<Students>();
		ListStore<Students> studentsList = new ListStore<Students>();
		list1.setDisplayProperty("name");
		studentsList.setStoreSorter(new StoreSorter<Students>());
		studentsList.add(mokList);
		list1.setStore(studentsList);
	}
}

	public void createDragDropList(List<Students> mokList) {
		
		/**
		 * Refreshina moksleiviu lista
		 */
		if (cp != null) {
			studentsPanel.remove(cp);
		}
		
		cp = new ContentPanel();
		
		list2 = new ListView<Students>(); // kad nullpointeris nekristu
		list2.setDisplayProperty("name");
		ListStore<Students> studentsList = new ListStore<Students>();
		studentsList = new ListStore<Students>();
		studentsList.setStoreSorter(new StoreSorter<Students>());
		list2.setStore(studentsList);
		
		new ListViewDragSource(list2);
		new ListViewDropTarget(list2);
		
		RowData data = new RowData(.5, 1);
		data.setMargins(new Margins(5));
		
		cp.add(list2, data);
		
		updateDragDropList(mokList);
		
		/**
		 * Cia tam kad du kartus be reikalo neperrinkinetu viso panelio, kas lb mazina programos greiti
		 */
		if (!mainPanel.isAttached()) {
			assembleView();
		}

	}

	private void assembleView() {
		disciplinePanel.add(disciplineLabel);
		
		ChangeHandler chHandler = new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				Integer selectedSubjectItemIndex = subject.getSelectedIndex();
				
				ArrayList<String> subjectsLvl = studFill.getSubjectsLevels(subject.getValue(selectedSubjectItemIndex));
				updateSubjectsLevel(subjectsLvl);
				
				level.setSelectedIndex(0); // Nes po default'u pats pirmas
				Integer selectedLevelItemIndex = level.getSelectedIndex();
				
				ArrayList<Teacher> teachersList = studFill.getTeachers(subject.getValue(selectedSubjectItemIndex), level.getValue(selectedLevelItemIndex));
				fillTeacherBox(teachersList);
				
				// Paskirstymas grupiu mokytojams
				Integer teacherSize = teachersList.size();
				
				ArrayList<String> groupsTitles = studFill.getGroupTitles(subject.getValue(selectedSubjectItemIndex), level.getValue(selectedLevelItemIndex),
						teachersList, teacherSize, teacher.getSelectedIndex());
				fillGroupBox(groupsTitles);
				
				group.setSelectedIndex(0); // Kadangi po defaultu jis visada buna nulinis, kai pakeiti tik dalyka
				Integer selectedGroupIndex = group.getSelectedIndex();
				
				boolean class12 = false;
				
				if (group.getValue(selectedGroupIndex).contains("12")) {
					class12 = true;
				}
				
				if (teacher.getSelectedIndex() > 0) {
					selectedGroupIndex = studFill.returnStarterGroupNumber(teachersList, teacher.getSelectedIndex()) - 1;
					selectedGroupIndex = selectedGroupIndex + group.getSelectedIndex();
				}
				
				Teacher teacherFromBox = getTeacherFromBox(teachersList);
				
				Integer groupSize = studFill.getGroupSize(subject.getValue(selectedSubjectItemIndex), level.getValue(selectedLevelItemIndex), 
						selectedGroupIndex, class12, teacherFromBox);
				fillStudNumBox(groupSize);
				
				// Moksleiviu sarasa parodo
				List<Students> studList = studFill.getStudentListForGroup(subject.getValue(selectedSubjectItemIndex), level.getValue(selectedLevelItemIndex), 
						selectedGroupIndex, group.getValue(group.getSelectedIndex()), groupSize, class12, teacherFromBox);
				
				// Papildo grupes issaugojimui
				updateDragDropList(studList);
			}
		};
		
		subject.addChangeHandler(chHandler);
		disciplinePanel.add(subject);
		
		

		levelPanel.add(levelLabel);
		level.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				Integer selectedSubjectItemIndex = subject.getSelectedIndex();
				Integer selectedLevelItemIndex = level.getSelectedIndex();
				
			
				ArrayList<Teacher> teachersList = studFill.getTeachers(subject.getValue(selectedSubjectItemIndex), level.getValue(selectedLevelItemIndex));
				fillTeacherBox(teachersList);
				
				// Paskirstymas grupiu mokytojams
				Integer teacherSize = teachersList.size();
				
				ArrayList<String> groupsTitles = studFill.getGroupTitles(subject.getValue(selectedSubjectItemIndex), level.getValue(selectedLevelItemIndex), teachersList, teacherSize, PamokuTvarkarastis.this.teacher.getSelectedIndex());
				fillGroupBox(groupsTitles);
				
				group.setSelectedIndex(0); // Kadangi po defaultu jis visada buna nulinis, kai pakeiti tik dalyka
				Integer selectedGroupIndex = group.getSelectedIndex();
				
				boolean class12 = false;
				
				if (group.getValue(selectedGroupIndex).contains("12")) {
					class12 = true;
					System.out.println("Dvylikta");
				}
				
				if (teacher.getSelectedIndex() > 0) {
					selectedGroupIndex = studFill.returnStarterGroupNumber(teachersList, teacher.getSelectedIndex()) - 1;
					selectedGroupIndex = selectedGroupIndex + group.getSelectedIndex();
				}
				
				Teacher teacherFromBox = getTeacherFromBox(teachersList);
				
				Integer groupSize = studFill.getGroupSize(subject.getValue(selectedSubjectItemIndex), level.getValue(selectedLevelItemIndex), 
						selectedGroupIndex, class12, teacherFromBox);
				fillStudNumBox(groupSize);
				
				// Moksleiviu sarasa parodo
				List<Students> studList = studFill.getStudentListForGroup(subject.getValue(selectedSubjectItemIndex), level.getValue(selectedLevelItemIndex), 
						selectedGroupIndex, group.getValue(group.getSelectedIndex()), groupSize, class12, teacherFromBox);
				updateDragDropList(studList);
					
			}
		});
		
		levelPanel.add(level);
		
		teacherPanel.add(teacherLabel);
		teacherPanel.add(teacher);
		teacher.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				Integer selectedSubjectItemIndex = subject.getSelectedIndex();
				Integer selectedLevelItemIndex = level.getSelectedIndex();
				
				// Mokytoju uzpildymas cia reikalingas kad grupes paskirstyt dviem skirtingams mokytojamas
				ArrayList<Teacher> teachersList = studFill.getTeachers(subject.getValue(selectedSubjectItemIndex), level.getValue(selectedLevelItemIndex));

				Integer teacherSize = teachersList.size();
				
				ArrayList<String> groupsTitles = studFill.getGroupTitles(subject.getValue(selectedSubjectItemIndex), level.getValue(selectedLevelItemIndex), 
						teachersList, teacherSize, teacher.getSelectedIndex());
				fillGroupBox(groupsTitles);
				
				group.setSelectedIndex(0); // Kadangi po defaultu jis visada buna nulinis, kai pakeiti tik mokytoja
				Integer selectedGroupIndex = group.getSelectedIndex();
				
				boolean class12 = false;
				
				if (group.getValue(selectedGroupIndex).contains("12")) {
					class12 = true;
				}
				
				if (teacher.getSelectedIndex() > 0) {
					selectedGroupIndex = studFill.returnStarterGroupNumber(teachersList, teacher.getSelectedIndex()) - 1;
					selectedGroupIndex = selectedGroupIndex + group.getSelectedIndex();
				}
				
				Teacher teacherFromBox = getTeacherFromBox(teachersList);
				
				Integer groupSize = studFill.getGroupSize(subject.getValue(selectedSubjectItemIndex), level.getValue(selectedLevelItemIndex), 
						selectedGroupIndex, class12, teacherFromBox);
				fillStudNumBox(groupSize);
				
				// Moksleiviu sarasa parodo
				List<Students> studList = studFill.getStudentListForGroup(subject.getValue(selectedSubjectItemIndex), level.getValue(selectedLevelItemIndex), 
						selectedGroupIndex, group.getValue(group.getSelectedIndex()), groupSize, class12, teacherFromBox);
				updateDragDropList(studList);
				
			}
		});
		
		studNumPanel.add(numberStudents);
		studNumBox.setMaxLength(2);
		studNumBox.setWidth("18px");
		
		studNumPanel.add(studNumBox);
		
		groupPanel.add(groupLabel);
		group.setVisibleItemCount(6);
		group.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				Integer selectedSubjectItemIndex = subject.getSelectedIndex();
				Integer selectedLevelItemIndex = level.getSelectedIndex();
				Integer selectedGroupIndex = group.getSelectedIndex();
				
				ArrayList<Teacher> teachersList = studFill.getTeachers(subject.getValue(selectedSubjectItemIndex), level.getValue(selectedLevelItemIndex));
				
				boolean class12 = false;
				
				if (group.getValue(selectedGroupIndex).contains("12")) {
					class12 = true;
				}
				
				// Moksleiviu sarasa parodo
				if (teacher.getSelectedIndex() > 0) {
					selectedGroupIndex = studFill.returnStarterGroupNumber(teachersList, teacher.getSelectedIndex()) - 1;
					selectedGroupIndex = selectedGroupIndex + group.getSelectedIndex();
				}
				
				Teacher teacherFromBox = getTeacherFromBox(teachersList);
				
				Integer groupSize = studFill.getGroupSize(subject.getValue(selectedSubjectItemIndex), level.getValue(selectedLevelItemIndex),
						selectedGroupIndex, class12, teacherFromBox);
				fillStudNumBox(groupSize);

				List<Students> studList = studFill.getStudentListForGroup(subject.getValue(selectedSubjectItemIndex), level.getValue(selectedLevelItemIndex), 
						selectedGroupIndex, group.getValue(group.getSelectedIndex()), groupSize, class12, teacherFromBox);
				updateDragDropList(studList);
			}
		});
		
		groupPanel.add(group);
		
		firstPanel.setSpacing(70);
		disciplinePanel.setSpacing(8);
		firstPanel.add(disciplinePanel);
		levelPanel.setSpacing(8);
		firstPanel.add(levelPanel);
		teacherPanel.setSpacing(8);
		firstPanel.add(teacherPanel);

		studNumPanel.setSpacing(8);
		secondPanel.add(studNumPanel);
		groupPanel.setSpacing(8);
		secondPanel.add(groupPanel);
		secondPanel.setSpacing(70);
		
		buttonsPanel.setHorizontalAlignment(HasAlignment.ALIGN_CENTER);
		buttonsPanel.setVerticalAlignment(HasAlignment.ALIGN_MIDDLE);
		
		saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				grSaving = new GroupsSaving(subject, list1Map, studFill);
				fullGroupsList = grSaving.saveGroups();
				
				RootPanel.get().clear();
				
				showResults();
				
			}

		});
		
		buttonsPanel.add(saveButton);
		buttonsPanel.add(cancelButton);
		secondPanel.add(buttonsPanel);

		buttonsNFirstP.add(firstPanel);
		buttonsNFirstP.add(secondPanel);

		mainPanel.add(buttonsNFirstP);
		mainPanel.add(studentsPanel);

		// Associate the Main panel with the HTML host page.
		RootPanel.get().add(mainPanel);
	}
	
	private void showResults() {
		
		HorizontalPanel resultPanel = new HorizontalPanel();
		resultPanel.setSize("100%", "100%");
		
		printingStudents();
		
		resultMonday.setBorderWidth(5);
		mondayPanel.add(monday);
		mondayPanel.add(resultMonday);
		mondayPanel.setSpacing(10);
		
		resultTuesday.setBorderWidth(5);
		tuesdayPanel.add(tuesday);
		tuesdayPanel.add(resultTuesday);
		tuesdayPanel.setSpacing(10);
		
		resultWednesday.setBorderWidth(5);
		wednesdayPanel.add(wednesday);
		wednesdayPanel.add(resultWednesday);
		wednesdayPanel.setSpacing(10);
		
		resultThursday.setBorderWidth(5);
		thursdayPanel.add(thursday);
		thursdayPanel.add(resultThursday);
		thursdayPanel.setSpacing(10);
		
		resultFriday.setBorderWidth(5);
		fridayPanel.add(friday);
		fridayPanel.add(resultFriday);
		fridayPanel.setSpacing(10);
		
		VerticalPanel moksPanel1 = new VerticalPanel();
		VerticalPanel moksPanel2 = new VerticalPanel();
		VerticalPanel moksPanel3 = new VerticalPanel();
		VerticalPanel moksPanel4 = new VerticalPanel();
		VerticalPanel moksPanel5 = new VerticalPanel();
		
		
		moksPanel1.add(studLabel);
		moksPanel1.setSpacing(10);
		resultStudTable.setBorderWidth(5);
		moksPanel1.add(resultStudTable);
		
		moksPanel2.add(studLabel1);
		moksPanel2.setSpacing(10);
		resultStudTable1.setBorderWidth(5);
		moksPanel2.add(resultStudTable1);
		
		moksPanel3.add(studLabel2);
		moksPanel3.setSpacing(10);
		resultStudTable2.setBorderWidth(5);
		moksPanel3.add(resultStudTable2);
		
		moksPanel4.add(studLabel3);
		moksPanel4.setSpacing(10);
		resultStudTable3.setBorderWidth(5);
		moksPanel4.add(resultStudTable3);
		
		moksPanel5.add(studLabel4);
		moksPanel5.setSpacing(10);
		resultStudTable4.setBorderWidth(5);
		moksPanel5.add(resultStudTable4);
		
		mondTabPanel.add(moksPanel1);
		mondTabPanel.add(mondayPanel);
		
		tuesTabPanel.add(moksPanel2);
		tuesTabPanel.add(tuesdayPanel);
		
		wednTabPanel.add(moksPanel3);
		wednTabPanel.add(wednesdayPanel);
		
		thurTabPanel.add(moksPanel4);
		thurTabPanel.add(thursdayPanel);
		
		fridTabPanel.add(moksPanel5);
		fridTabPanel.add(fridayPanel);
		
		tabPanel.add(mondTabPanel, "Pirmadienis");
		tabPanel.add(tuesTabPanel, "Antradienis");
		tabPanel.add(wednTabPanel, ltConst.trec());
		tabPanel.add(thurTabPanel, "Ketvirtadienis");
		tabPanel.add(fridTabPanel, "Penktadienis");
		
		tabPanel.selectTab(0);
		
		resultPanel.add(tabPanel);
		resultPanel.setSize("100", "100");
		finalFinalPanel.add(resultPanel);
		finalFinalPanel.setSize("100%", "100%");
		
		RootPanel.get().add(finalFinalPanel);
	}
	
	private String printingStudents() {
		ArrayList<String> studentsList = studFill.settedResult.getStudentsList();
		HashMap<String, String [][]> studMap = new LinkedHashMap<String, String[][]>();
		
		 for (String studentName : studentsList) {
			
			 if (!studMap.containsKey(studentName)) {
				 
						studMap.put(studentName, new String [5][hoursInDay]); 
						String [] [] arr = studMap.get(studentName);
						
						 for (Group group : fullGroupsList) {
							 ArrayList<String> studList = convertFromStudentToString(group);;
							 
							 if (studList.contains(studentName) && !group.getGroupName().contains("12")) { // Kad 12 klases nerodytu dabar
								 
									for (int i = 0; i < Integer.parseInt(group.getHours()); i++){ // Cia kad butu tiek kartu kiek valandu yra priskirta
										
											Random rand = new Random();
												if (!checkGroupExistance(studMap, studentas, group)) {
													int randDays = rand.nextInt(5);
													int randPam = rand.nextInt(hoursInDay);
													schedulingTimetable(arr, group, rand, randDays, randPam);
												} else {
													// Isgauti ta vieta kurioje saugo ir ideti i arr toj vietoj
													if (studMap.containsKey(studentas)) {
														String [][] checker = studMap.get(studentas);
														
														int z = 0;
														while (z < 5) {
															int j = 0;
																while (j < hoursInDay) {
																	if (checker[z][j] != null) {
																		if (checker[z][j].equals(group.getGroupName())) {
																			schedulingTimetable(arr, group, rand, z, j);
																			
																		}
																}
																j++;
															}
															z++;
														}
														
													}
										}
									}
			 				 }
						 }
						 
					ArrayList<String> timetable = fromArrayToList(studMap, studentName);
					finalResult.put(studentName, timetable);
			 
					// Issisaugo studencioka, kad patikrinti sekanti karta su kitu ar sutampa grupes
					studentas = studentName;
		 }
		 }
		 
		 Set<String> studentsSet = finalResult.keySet();
		
		 
		 ArrayList<String> studTime = finalResult.get(studentsSet.iterator().next());
		 Integer hoursPerDay = studTime.size() / 5;
		 
		 
		 resultStudTable = new Grid(finalResult.keySet().size(), 1); 
		 resultStudTable1 = new Grid(finalResult.keySet().size(), 1); 
		 resultStudTable2 = new Grid(finalResult.keySet().size(), 1); 
		 resultStudTable3 = new Grid(finalResult.keySet().size(), 1); 
		 resultStudTable4 = new Grid(finalResult.keySet().size(), 1); 
		 
		 resultMonday = new Grid(finalResult.keySet().size(), hoursPerDay);
		 resultTuesday = new Grid(finalResult.keySet().size(), hoursPerDay);
		 resultWednesday = new Grid(finalResult.keySet().size(), hoursPerDay);
		 resultThursday = new Grid(finalResult.keySet().size(), hoursPerDay);
		 resultFriday = new Grid(finalResult.keySet().size(), hoursPerDay);
		
		Integer row = 0;
		
		for (String stud : studentsSet) {
			Integer column = 0;
			resultStudTable.setText(row, column, stud);
			resultStudTable1.setText(row, column, stud);
			resultStudTable2.setText(row, column, stud);
			resultStudTable3.setText(row, column, stud);
			resultStudTable4.setText(row, column, stud);
			
			// Cia uzpildomas studento tvarkarastis
			ArrayList<String> studTimetable = finalResult.get(stud);
			
			for (Iterator<String> subjIter = studTimetable.iterator(); subjIter.hasNext();) {
				String subj = subjIter.next();
				
				if (column < hoursPerDay) { // "protingas" algoritmas kuris isdelioja i atskiras lenteles
					resultMonday.setText(row, column, subj);
				}
				
				if (column >= hoursPerDay  && column < 2 * hoursPerDay) {
					resultTuesday.setText(row, column - hoursPerDay, subj);
				}
				
				if (column >= 2 * hoursPerDay  && column < 3 * hoursPerDay) {
					resultWednesday.setText(row, column - 2 * hoursPerDay, subj);
				}
				
				if (column >= 3 * hoursPerDay  && column < 4 * hoursPerDay) {
					resultThursday.setText(row, column - 3 * hoursPerDay, subj);
				}
				
				if (column >= 4 * hoursPerDay  && column < 5 * hoursPerDay) {
					resultFriday.setText(row, column - 4 * hoursPerDay, subj);
				}
				
				column++;
			}
			row++;
		}
		return studentsSet.toString();
	}

	private void schedulingTimetable(String[][] arr, Group group, Random rand, int randDays, int randPam) {
		if (arr [randDays][randPam] == null) {
			arr [randDays][randPam] = group.getGroupName();
		} else {
			do {
				randDays = rand.nextInt(5);
				randPam = rand.nextInt(hoursInDay);
				
			} while (arr [randDays][randPam] == null);
			
			arr [randDays][randPam] = group.getGroupName();
		}
	}

	private boolean checkGroupExistance(HashMap<String, String[][]> studMap, String studentas2, Group group) {
		if (studMap.containsKey(studentas2)) {
			String [][] checker = studMap.get(studentas2);
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < hoursInDay; j++) {
					if (checker[i][j] != null) {
						if (checker[i][j].equals(group.getGroupName())) {
							return true;
						}
					}
				}
			}
			
		}
		
		return false;
	}

	private ArrayList<String> fromArrayToList(HashMap<String, String[][]> studMap, String studentName) {
		ArrayList<String> timetable = new ArrayList<String>();
		for (int o = 0; o < 5; o++) {
			Integer count = 1;
			for (int p = 0; p < hoursInDay; p++) {
				
				timetable.add(count ++ + ":" + studMap.get(studentName)[o][p]);
			}
		}
		
		return timetable;
	}

	private ArrayList<String> convertFromStudentToString(Group group) {
		 
		Object[] studs = group.getStudentsList().toArray();
		
		ArrayList<String> studentsList = new ArrayList<String>();
		for (Object stud : studs) {
			studentsList.add(stud.toString());
		}
		return studentsList;
	}

	private Teacher getTeacherFromBox(ArrayList<Teacher> teachersList) {
		return teachersList.get(teacher.getSelectedIndex());
	}
	
	public void fillGroupBox(ArrayList<String> groupsNames) {
		group.clear();
		for (String groupName : groupsNames) {
			group.addItem(groupName);
		}
	}
	
	public void fillTeacherBox(ArrayList<Teacher> teache) {
		teacher.clear();
		for (Teacher teach : teache) {
			teacher.addItem(teach.getName());
		}
	}
	
	public void fillStudNumBox(Integer studsNumber) {
		studNumBox.setValue(studsNumber.toString());
	}
	
	public void updateSubjectsLevel(ArrayList<String> subjLvl) {
		level.clear();
		for (String subjectLvlString : subjLvl) {
			level.addItem(subjectLvlString);
		}
	}
	
	public void fillSubjectDropBox(ArrayList<String> subjectsList){
		
		for (String subjectString : subjectsList) {
			subject.addItem(subjectString);
		}
		
		
	}
	
}
