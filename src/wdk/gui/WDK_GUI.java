package wdk.gui;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import static wdk.WDK_StartupConstants.*;
import wdk.WDK_PropertyType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import wdk.controller.DraftEditController;
import wdk.controller.DraftScreenController;
import wdk.controller.PlayersEditController;
import wdk.controller.TeamEditController;
import wdk.controller.TopToolbarController;
import wdk.data.Draft;
import wdk.data.DraftDataManager;
import wdk.data.DraftDataView;
import wdk.data.Player;
import wdk.data.Team;
import wdk.file.DraftFileManager;

/**
 * This class provides the Graphical User Interface for this application,
 * managing all the UI components for editing a Course and exporting it to a
 * site.
 *
 * @author Jing Yu
 */
public class WDK_GUI implements DraftDataView{

    // THESE CONSTANTS ARE FOR TYING THE PRESENTATION STYLE OF
    // THIS GUI'S COMPONENTS TO A STYLE SHEET THAT IT USES

    static final String PRIMARY_STYLE_SHEET = PATH_CSS + "csb_style.css";
    static final String CLASS_BORDERED_PANE = "bordered_pane"; 
    static final String SCREEN_PANE ="screen_pane";
    static final String CLASS_HEADING_LABEL = "heading_label";
    static final String CLASS_SUBHEADING_LABEL = "subheading_label";
    static final String CLASS_PROMPT_LABEL = "prompt_label";
    static final String EMPTY_TEXT = "";
    static final int LARGE_TEXT_FIELD_LENGTH = 75;
    static final int SMALL_TEXT_FIELD_LENGTH = 35;
    
    

     // THIS MANAGES ALL OF THE APPLICATION'S DATA
    DraftDataManager dataManager;

    // THIS MANAGES COURSE FILE I/O
    DraftFileManager draftFileManager;

    // THIS MANAGES EXPORTING OUR SITE PAGES
   // CourseSiteExporter siteExporter;

    // THIS HANDLES INTERACTIONS WITH FILE-RELATED CONTROLS
    TopToolbarController topToolbarController;
    
    //tHIS HANDLES INTERACTIONS WITH DRAFT INFO CONTROLS
    DraftEditController draftController;
    
    //Controller for the team screen
    TeamEditController teamController;
    
    //Controller to add/remove a players in players screen
    PlayersEditController playersController;
    
    //controller for the draft screen
    DraftScreenController draftScreenController;
    
    // THIS IS THE APPLICATION WINDOW
    Stage primaryStage;

    // THIS IS THE STAGE'S SCENE GRAPH
    Scene primaryScene;

    // THIS PANE ORGANIZES THE BIG PICTURE CONTAINERS FOR THE
    // APPLICATION GUI
    BorderPane wdkPane;
    
    // THIS IS THE TOP TOOLBAR AND ITS CONTROLS
    FlowPane topToolbarPane;
    Button newDraftButton;
    Button loadDraftButton;
    Button saveDraftButton;
    Button exportDraftButton;
    Button exitButton;

    // WE'LL ORGANIZE OUR WORKSPACE COMPONENTS USING A BORDER PANE
    BorderPane workspacePane;
    boolean workspaceActivated;
   
    //INSIDE THE workspacePane, we have the screenPane and the bottomToolbarPane
    BorderPane screenPane;
    
    //Here is the fantasyTeamsPane inside the screenPane
    VBox fantasyTeamsPane;
    ScrollPane fantasyTeamsScrollPane;   
    Label fantasyTeamsLabel;
    HBox draftNameBox;
    Label draftNameLabel;
    TextField draftNameTextField;
    HBox teamBox;
    Button addTeamButton;
    Button removeTeamButton;
    Button editTeamButton;
    Label selectTeamLabel;
    ComboBox selectTeamComboBox;
    VBox teamInfoPane;
    VBox startingLineupPane;
    Label startingLineupLabel;
    TableView<Player> startTeamTable;
    TableColumn assignedPositionColumn;
    TableColumn estimatedValueColumn;
    TableColumn contractColumn;
    TableColumn salaryColumn;
    VBox taxiSquadPane;
    Label taxiSquadLabel;
    TableView<Player> taxiSquadTable;
 
    //Here is the availabePlayersPane
    VBox availablePlayersPane;
    Label availablePlayersLabel;
    HBox playersToolbar;
    Button addPlayerButton;
    Button removePlayerButton;
    Label playersSearchLabel;
    TextField playersSearchTextField;
    HBox radioButtonsBox;
    RadioButton radioALL;
    RadioButton radioC;
    RadioButton radio1B;
    RadioButton radioCI;
    RadioButton radio3B;
    RadioButton radio2B;
    RadioButton radioMI;
    RadioButton radioSS;
    RadioButton radioOF;
    RadioButton radioU;
    RadioButton radioP;
    //Table for the players
    TableView<Player> playerDatabase;
    TableColumn firstNameColumn;
    TableColumn lastNameColumn;
    TableColumn proTeamColumn;
    TableColumn positionsColumn;
    TableColumn yearOfBirthColumn;
    TableColumn R_W_Column;
    TableColumn HR_SV_Column;
    TableColumn RBI_K_Column;
    TableColumn SB_ERA_Column;
    TableColumn BA_WHIP_Column;
    TableColumn valueColumn;
    TableColumn notesColumn;
    
    
    //Here is the fantasyStandingsPane
    FantasyStandingsScreen fantasyStandingsPane; 
    Label fantasyStandingsLabel;
     TableView<Team> fantasyStandingsTable;
     TableColumn teamName_Column;
     TableColumn playerNeeded_Column;
     TableColumn moneyLeft_Column;
     TableColumn pp_Column;
     TableColumn R_Column;
     TableColumn HR_Column;
     TableColumn RBI_Column;
     TableColumn SB_Column;
     TableColumn BA_Column;
     TableColumn W_Column;
     TableColumn K_Column;
     TableColumn SV_Column;
     TableColumn ERA_Column;
     TableColumn WHIP_Column;
     TableColumn totalPoints_Column;
              
    //Here is the draftPane
    DraftScreen draftPane;
    Label draftLabel;
    HBox draftButtonsHBox;
    Button draftOnePlayerButton;
    Button startDraftButton;
    Button pauseDraftButton;
    TableView<Player> draftTable;
    TableColumn draftPickNumberColumn;
    TableColumn assignedTeamColumn;
    
    //Here is the MLBTeamsPane
    MLBTeamsScreen MLBTeamsPane;
    Label MLBTeamsLabel;
    HBox MLBTeamSelectionHBox;
    Label MLBTeamSelectionLabel;
    ComboBox MLBTeamSelectionComboBox;
    TableView MLBTeamTable;
    
    FlowPane bottomToolbarPane;
    Button fantasyTeamsButton;
    Button availablePlayersButton;
    Button fantasyStandingsButton;
    Button draftButton;
    Button MLBTeamsButton;
    
    //And the table column desciption for player screen
    static final String COL_FIRSTNAME="First";
    static final String COL_LASTNAME="Last";
    static final String COL_PROTEAM="Pro Team";
    static final String COL_POSITIONS="Positions";
    static final String COL_POSITION="Position";
    static final String COL_YEAROFBIRTH="Year of Birth";
    static final String COL_R_W="R/W";
    static final String COL_R="R";
    static final String COL_W="W";
    static final String COL_HR_SV="HR/SV";
    static final String COL_HR="HR";
    static final String COL_SV="SV";
    static final String COL_RBI_K="RBI/K";
    static final String COL_RBI="RBI";
    static final String COL_K="K";
    static final String COL_SB_ERA="SB/ERA";
    static final String COL_SB="SB";
    static final String COL_ERA="ERA";
    static final String COL_BA_WHIP="BA/WHIP";
    static final String COL_BA="BA";
    static final String COL_WHIP="WHIP";
    static final String COL_VALUE="Estimated Value";
    static final String COL_NOTES="Notes";
    static final String COL_SALARY="Salary($)";
    static final String COL_CONTRACT="Contract";
    static final String COL_TEAMNAME ="Name";
    static final String COL_PLAYERNEEDED="Players Needed";
    static final String COL_MONEYLEFT="$Left";
    static final String COL_PP="$PP";
    static final String COL_TOTALPOINTS="Total Points";
    static final String COL_DRAFT_PICK_NUMBER= "Pick #";
    static final String COL_ASSIGNED_TEAM="Team";
    // HERE ARE OUR DIALOGS
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    
    //These numbers are used for check conditions
    static int teamNumber=0; //use in team combo Box
    
    
    /**
     * Constructor for making this GUI, note that it does not initialize the UI
     * controls. To do that, call initGUI.
     *
     * @param initPrimaryStage Window inside which the GUI will be displayed.
     */
    public WDK_GUI(Stage initPrimaryStage) {
        primaryStage = initPrimaryStage;
    }

     /**
     * Accessor method for the data manager.
     *
     * @return The CourseDataManager used by this UI.
     */
    public DraftDataManager getDataManager() {
        return dataManager;
    }

    /**
     * Accessor method for the file controller.
     *
     * @return The FileController used by this UI.
     */
    public TopToolbarController getTopToolbarController() {
        return topToolbarController;
    }

    /**
     * Accessor method for the course file manager.
     *
     * @return The CourseFileManager used by this UI.
     */
    public DraftFileManager getDraftFileManager() {
        return draftFileManager;
    }

    /**
     * Accessor method for the site exporter.
     *
     * @return The CourseSiteExporter used by this UI.
     */
  //  public CourseSiteExporter getSiteExporter() {
    //    return siteExporter;
    //}
    
    /**
     * Accessor method for the window (i.e. stage).
     *
     * @return The window (i.e. Stage) used by this UI.
     */
    public Stage getWindow() {
        return primaryStage;
    }
    
    /**
     *This is used to get a message dialog window
     * @return messageDialog
     */
    public MessageDialog getMessageDialog() {
        return messageDialog;
    }
    
  /**
     *This is used to get a yesNoCancelDialog window
     * @return yesNoCancelDialog
     */
    public YesNoCancelDialog getYesNoCancelDialog() {
        return yesNoCancelDialog;
    }
 /**
     * Mutator method for the data manager.
     *
     * @param initDataManager The CourseDataManager to be used by this UI.
     */
    public void setDataManager(DraftDataManager initDataManager) {
        dataManager = initDataManager;
    }

    /**
     * Mutator method for the course file manager.
     *
     * @param initCourseFileManager The CourseFileManager to be used by this UI.
     */
    public void setDraftFileManager(DraftFileManager initDraftFileManager) {
        draftFileManager = initDraftFileManager;
    }

    /**
     * Mutator method for the site exporter.
     *
     * @param initSiteExporter The CourseSiteExporter to be used by this UI.
     */
    //public void setSiteExporter(CourseSiteExporter initSiteExporter) {
      //  siteExporter = initSiteExporter;
    //}
    

    /**
     * This method fully initializes the user interface for use.
     *
     * @param windowTitle The text to appear in the UI window's title bar.
     * @param subjects The list of subjects to choose from.
     * @throws IOException Thrown if any initialization files fail to load.
     */
    public void initGUI(String windowTitle) throws IOException {
        // INIT THE DIALOGS
        initDialogs();
        
        // INIT THE TOOLBAR
        initTopToolbar();

        // INIT THE CENTER WORKSPACE CONTROLS BUT DON'T ADD THEM
        // TO THE WINDOW YET
     //   initWorkspace(subjects);
          initWorkspace();
          
        // NOW SETUP THE EVENT HANDLERS
        initEventHandlers();

        // AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
        initWindow(windowTitle);
    }

    /**
     * When called this function puts the workspace into the window,
     * revealing the controls for editing a Course.
     */
    public void activateWorkspace() {
        if (!workspaceActivated) {
            // PUT THE WORKSPACE IN THE GUI
            wdkPane.setCenter(workspacePane);
            workspaceActivated = true;
        }
    }

    //save draft need this
    @Override
    public void reloadDraft(Draft draftToReload) {
        // FIRST ACTIVATE THE WORKSPACE IF NECESSARY
        if (!workspaceActivated) {
            activateWorkspace();           
        }
        //WE do not want to respoond to events
        //force by our initialization selections
        
        //these two lines make sure
         initAvailablePlayersScreen();
            initPlayerScreenEventHandler();
        // the tables in the fantasy teams work correctly    
         initFantasyTeamsScreen();                
          updateTeamScreenButtons();
          loadTeamComboBox();
         draftController.enable(false);
        //draftNameTextField.setText(draftToReload.getDraftName());                 
          draftNameTextField.setText(dataManager.getDraft().getDraftName());         
          dataManager.getDraft().setCurrentTeam((String)selectTeamComboBox.getSelectionModel().getSelectedItem());        
         Team teamToDisplay = dataManager.getDraft().findTeam(dataManager.getDraft().getCurrentTeam());
        startTeamTable.setItems(teamToDisplay.getTeamPlayers());
        taxiSquadTable.setItems(teamToDisplay.getTaxiPlayers());
         initTeamScreenEventHandler();                                               
        startTeamTable.setItems(teamToDisplay.getTeamPlayers());
           initTeamScreenEventHandler();
        //set the correct team Size
           teamNumber=dataManager.getDraft().getTeams().size();          
          //close the auto draft thread
//           initDraftScreenEventHandler();
    //  draftScreenController = new DraftScreenController(primaryStage, dataManager.getDraft(), messageDialog, yesNoCancelDialog);   
    //       draftScreenController.setRunning(false);
         //Now we do want to respond when the user 
         //interface with the gui
         draftController.enable(true);
    }

    /**
     * This method is used to activate/deactivate toolbar buttons when
     * they can and cannot be used so as to provide foolproof design.
     * 
     * @param saved Describes whether the loaded Course has been saved or not.
     */
    public void updateTopToolbarControls(boolean saved) {
        // THIS TOGGLES WITH WHETHER THE CURRENT COURSE
        // HAS BEEN SAVED OR NOT
        saveDraftButton.setDisable(saved);

        // ALL THE OTHER BUTTONS ARE ALWAYS ENABLED
        // ONCE EDITING THAT FIRST COURSE BEGINS
        loadDraftButton.setDisable(false);
        exportDraftButton.setDisable(false);

        // NOTE THAT THE NEW, LOAD, AND EXIT BUTTONS
        // ARE NEVER DISABLED SO WE NEVER HAVE TO TOUCH THEM
    }

    /**
     * This function loads all the values currently in the user interface
     * into the draft argument.
     * 
     * @param draft The draft to be updated using the data from the UI controls.
     */
     public void updateDraftInfo(Draft draft) {
        draft.setDraftName(draftNameTextField.getText());
      //  draft.setCurrentTeam(positionComboBox);
        
    }
  

    /****************************************************************************/
    /* BELOW ARE ALL THE PRIVATE HELPER METHODS WE USE FOR INITIALIZING OUR GUI */
    /****************************************************************************/
    
    private void initDialogs(){
        messageDialog = new MessageDialog(primaryStage, CLOSE_BUTTON_LABEL);
        yesNoCancelDialog = new YesNoCancelDialog(primaryStage);
    }
    
    /**
     * This function initializes all the buttons in the toolbar at the top of
     * the application window. These are related to file management.
     */
    private void initTopToolbar() {
        topToolbarPane = new FlowPane();

        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
        // START AS ENABLED (false), WHILE OTHERS DISABLED (true)
        newDraftButton = initChildButton(topToolbarPane, WDK_PropertyType.NEW_DRAFT_ICON, WDK_PropertyType.NEW_DRAFT_TOOLTIP, false);
        loadDraftButton = initChildButton(topToolbarPane, WDK_PropertyType.LOAD_DRAFT_ICON, WDK_PropertyType.LOAD_DRAFT_TOOLTIP, false);
        saveDraftButton = initChildButton(topToolbarPane, WDK_PropertyType.SAVE_DRAFT_ICON, WDK_PropertyType.SAVE_DRAFT_TOOLTIP, true);
        exportDraftButton = initChildButton(topToolbarPane, WDK_PropertyType.EXPORT_DRAFT_ICON, WDK_PropertyType.EXPORT_DRAFT_TOOLTIP, true);
        exitButton = initChildButton(topToolbarPane, WDK_PropertyType.EXIT_ICON, WDK_PropertyType.EXIT_TOOLTIP, false);
    }
    
     private void initWorkspace(){
        //All five screens are here 
        initScreen();
       
        //Bottom toolbar control here
        initBottomToolbarControls();
        
            workspacePane = new BorderPane();
            workspacePane.setCenter(screenPane);
            workspacePane.setBottom(bottomToolbarPane);
            workspacePane.getStyleClass().add(CLASS_BORDERED_PANE);
           
            
        // NOTE THAT WE HAVE NOT PUT THE WORKSPACE INTO THE WINDOW,
        // THAT WILL BE DONE WHEN THE USER EITHER CREATES A NEW
        // COURSE OR LOADS AN EXISTING ONE FOR EDITING
        workspaceActivated = false;
    }
    
     //Screen are initialized here
     private void initScreen(){
         screenPane = new BorderPane();    
        //fantasy teams screen
        initFantasyTeamsScreen();
     }
      
     public void initFantasyTeamsScreen(){        
         //The top pane for this screen
         fantasyTeamsPane = new VBox();         
         fantasyTeamsPane.getStyleClass().add(SCREEN_PANE);                 
         fantasyTeamsLabel= initChildLabel(fantasyTeamsPane, WDK_PropertyType.FANTASY_TEAMS_HEADING_LABEL,CLASS_HEADING_LABEL);
         
         //draftName 
         draftNameBox = new HBox();
         draftNameLabel = initChildLabel(draftNameBox, WDK_PropertyType.DRAFT_NAME_LABEL,CLASS_PROMPT_LABEL);
         draftNameTextField = initTextField(draftNameBox, SMALL_TEXT_FIELD_LENGTH,EMPTY_TEXT,true);
         
          draftNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            draftController.handleDraftChangeRequest(this);
          });
          
         //add,remove,edit team
         teamBox = new HBox();
        addTeamButton = initChildButton(teamBox, WDK_PropertyType.ADD_ICON,WDK_PropertyType.ADD_TEAM_TOOLTIP,false);
        removeTeamButton = initChildButton(teamBox, WDK_PropertyType.MINUS_ICON,WDK_PropertyType.ADD_TEAM_TOOLTIP,true);
        editTeamButton = initChildButton(teamBox, WDK_PropertyType.EDIT_ICON,WDK_PropertyType.EDIT_TEAM_TOOLTIP,true);
        selectTeamLabel = initChildLabel(teamBox, WDK_PropertyType.SELECT_TEAM_LABEL,CLASS_PROMPT_LABEL);        
        selectTeamComboBox= initHBoxComboBox(teamBox);
        //Team information first table: starting lineup
        startingLineupPane = new VBox();   
        startingLineupPane.getStyleClass().add(CLASS_BORDERED_PANE);        
       
        startingLineupLabel = initChildLabel(startingLineupPane, WDK_PropertyType.STARTING_LINEUP_LABEL,CLASS_SUBHEADING_LABEL);
        //Now the table
         startTeamTable= new TableView();
         
         //startTeamTable.setEditable(true);  
         assignedPositionColumn=new TableColumn(COL_POSITION);      
         firstNameColumn=new TableColumn(COL_FIRSTNAME);
         lastNameColumn=new TableColumn(COL_LASTNAME);         	
         proTeamColumn=new TableColumn(COL_PROTEAM);
         positionsColumn=new TableColumn(COL_POSITIONS);     
         R_W_Column=new TableColumn(COL_R_W);
         HR_SV_Column=new TableColumn(COL_HR_SV);
         RBI_K_Column=new TableColumn(COL_RBI_K);
        SB_ERA_Column=new TableColumn(COL_SB_ERA);
        BA_WHIP_Column=new TableColumn(COL_BA_WHIP);              
        estimatedValueColumn=new TableColumn(COL_VALUE);
        salaryColumn=new TableColumn(COL_SALARY);
        contractColumn=new TableColumn(COL_CONTRACT);
        assignedPositionColumn.setSortable(false);
        firstNameColumn.setSortable(false);
        lastNameColumn.setSortable(false);
        proTeamColumn.setSortable(false);
        positionsColumn.setSortable(false);
        R_W_Column.setSortable(false);
        HR_SV_Column.setSortable(false);
        RBI_K_Column.setSortable(false);
        SB_ERA_Column.setSortable(false);
        BA_WHIP_Column.setSortable(false);
        estimatedValueColumn.setSortable(false);
        salaryColumn.setSortable(false);
        contractColumn.setSortable(false);
        //AND LINK THE COLUMN TO THE DATA   
        assignedPositionColumn.setCellValueFactory(new PropertyValueFactory<String,String>("assignedPosition"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<String,String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<String,String>("lastName"));          
        proTeamColumn.setCellValueFactory(new PropertyValueFactory<String,String>("proTeam"));
        positionsColumn.setCellValueFactory(new PropertyValueFactory<String,String>("qualifyPositions"));
        R_W_Column.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("runs_walk"));
        HR_SV_Column.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("homeRuns_saves"));
        RBI_K_Column.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("runsBattedIn_strikeouts"));
        SB_ERA_Column.setCellValueFactory(new PropertyValueFactory<Float,Float>("SB_ERA"));       
        BA_WHIP_Column.setCellValueFactory(new PropertyValueFactory<Float,Float>("BA_WHIP"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("salary"));
        estimatedValueColumn.setCellValueFactory(new PropertyValueFactory<String, String>("estimatedValue"));        
        contractColumn.setCellValueFactory(new PropertyValueFactory<String,String>("contract"));         
        
        //ADD THE COLUMN TO THE TABLE
       startTeamTable.getColumns().add(assignedPositionColumn);
       startTeamTable.getColumns().add(firstNameColumn);
       startTeamTable.getColumns().add(lastNameColumn);
       startTeamTable.getColumns().add(proTeamColumn);
       startTeamTable.getColumns().add(positionsColumn);       
       startTeamTable.getColumns().add(R_W_Column);
       startTeamTable.getColumns().add(HR_SV_Column);
       startTeamTable.getColumns().add(RBI_K_Column);
       startTeamTable.getColumns().add(SB_ERA_Column);
       startTeamTable.getColumns().add(BA_WHIP_Column);
       startTeamTable.getColumns().add(estimatedValueColumn);
       startTeamTable.getColumns().add(contractColumn);
       startTeamTable.getColumns().add(salaryColumn);
       Team teamToDisplay = dataManager.getDraft().findTeam(dataManager.getDraft().getCurrentTeam());
       startTeamTable.setItems(teamToDisplay.getTeamPlayers());
     //  startTeamTable.setItems(dataManager.getDraft().getDataBase());
       startingLineupPane.getChildren().add(startTeamTable);      
       
        //Second table: Taxi Squad
        taxiSquadPane = new VBox();
        
        taxiSquadPane.getStyleClass().add(CLASS_BORDERED_PANE);        
        taxiSquadLabel = initChildLabel(taxiSquadPane, WDK_PropertyType.TAXI_SQUAD_LABEL,CLASS_SUBHEADING_LABEL);
       taxiSquadTable();
        
        //add everthing to the main pane
       fantasyTeamsPane.getChildren().addAll(draftNameBox,teamBox,startingLineupPane,taxiSquadPane);
       
         fantasyTeamsScrollPane=new ScrollPane();
         fantasyTeamsScrollPane.setContent(fantasyTeamsPane);            
         fantasyTeamsScrollPane.setFitToWidth(true);
         screenPane.setCenter(fantasyTeamsScrollPane);
        
         
     }
       //Here is my availablePlayersScreen
     //In this screen, I have a player table which have all the information
     //about pitchers and hitters. I can add and remove players in HW6.
     //In Homework 5, I only have the textFiled search and radioButton filter
     //function for me to use. When User type any character in the textfiled,
     //the table should be able to update to get the first or last name of players
     //who start with this string.
     //Also, I can use the radiobuttons to filters the players.
     //For pitchers and hitters, the table columns are different.  
       private void initAvailablePlayersScreen(){ 
        //calculate the estimated value in the beginning
  //       dataManager.getDraft().calculateEstimatedValue();
         
         availablePlayersPane = new VBox();
         playersToolbar = new HBox();
         addPlayerButton = initChildButton(playersToolbar, WDK_PropertyType.ADD_ICON,WDK_PropertyType.ADD_PLAYER_TOOLTIP,false);
         removePlayerButton = initChildButton(playersToolbar, WDK_PropertyType.MINUS_ICON,WDK_PropertyType.REMOVE_PLAYER_TOOLTIP,false);
         playersToolbar.getChildren().add(new Label("   "));
         playersSearchLabel = initChildLabel(playersToolbar, WDK_PropertyType.PLAYERS_SEARCH_LABEL,CLASS_PROMPT_LABEL);
         playersSearchTextField = initTextField(playersToolbar, LARGE_TEXT_FIELD_LENGTH,EMPTY_TEXT,true);
         
       
         radioButtonsBox = new HBox(6);
        
         radioALL= initRadioButton(radioButtonsBox,"All");
         radioC=initRadioButton(radioButtonsBox,"C");
         radio1B=initRadioButton(radioButtonsBox,"1B");
         radioCI=initRadioButton(radioButtonsBox,"CI");
         radio3B=initRadioButton(radioButtonsBox,"3B");
         radio2B=initRadioButton(radioButtonsBox,"2B");
         radioMI=initRadioButton(radioButtonsBox,"MI");
         radioSS=initRadioButton(radioButtonsBox,"SS");
         radioOF=initRadioButton(radioButtonsBox,"OF");
         radioU=initRadioButton(radioButtonsBox,"U");
         radioP=initRadioButton(radioButtonsBox,"P");   
                 
         //set only one of radio buttons work at a time
         ToggleGroup group= new ToggleGroup();
         radioALL.setToggleGroup(group);
         radioALL.setSelected(true);
         radioC.setToggleGroup(group);
         radio1B.setToggleGroup(group);
         radioCI.setToggleGroup(group);
         radio3B.setToggleGroup(group);
         radio2B.setToggleGroup(group);
         radioMI.setToggleGroup(group);
         radioSS.setToggleGroup(group);
         radioOF.setToggleGroup(group);
         radioU.setToggleGroup(group);
         radioP.setToggleGroup(group);
        
                 
         radioButtonsBox.getStyleClass().add(CLASS_BORDERED_PANE);         
         //Add table here
         playerDatabase= new TableView();
         playerDatabase.setEditable(true);
         firstNameColumn=new TableColumn(COL_FIRSTNAME);
         lastNameColumn=new TableColumn(COL_LASTNAME);
         	
         proTeamColumn=new TableColumn(COL_PROTEAM);
         positionsColumn=new TableColumn(COL_POSITIONS);
         yearOfBirthColumn=new TableColumn(COL_YEAROFBIRTH);
         R_W_Column=new TableColumn(COL_R_W);
         HR_SV_Column=new TableColumn(COL_HR_SV);
         RBI_K_Column=new TableColumn(COL_RBI_K);
        SB_ERA_Column=new TableColumn(COL_SB_ERA);
        BA_WHIP_Column=new TableColumn(COL_BA_WHIP);
        valueColumn=new TableColumn(COL_VALUE);
        notesColumn=new TableColumn(COL_NOTES);
        
        //AND LINK THE COLUMN TO THE DATA
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<String,String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<String,String>("lastName"));   
       
        proTeamColumn.setCellValueFactory(new PropertyValueFactory<String,String>("proTeam"));
        positionsColumn.setCellValueFactory(new PropertyValueFactory<String,String>("qualifyPositions"));
        yearOfBirthColumn.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("yearOfBirth"));
        R_W_Column.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("runs_walk"));
        HR_SV_Column.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("homeRuns_saves"));
        RBI_K_Column.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("runsBattedIn_strikeouts"));
        SB_ERA_Column.setCellValueFactory(new PropertyValueFactory<Float,Float>("SB_ERA"));       
        BA_WHIP_Column.setCellValueFactory(new PropertyValueFactory<Float,Float>("BA_WHIP"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<Float,Float>("estimatedValue"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<String,String>("notes"));  
       //Make the notesColumn as a textfield, so I Can make some changes on it       
        notesColumn.setCellFactory(TextFieldTableCell.forTableColumn());      
        
        //ADD THE COLUMN TO THE TABLE
        playerDatabase.getColumns().add(firstNameColumn);
        playerDatabase.getColumns().add(lastNameColumn);
        playerDatabase.getColumns().add(proTeamColumn);
        playerDatabase.getColumns().add(positionsColumn);
        playerDatabase.getColumns().add(yearOfBirthColumn);
        playerDatabase.getColumns().add(R_W_Column);
        playerDatabase.getColumns().add(HR_SV_Column);
        playerDatabase.getColumns().add(RBI_K_Column);
        playerDatabase.getColumns().add(SB_ERA_Column);
        playerDatabase.getColumns().add(BA_WHIP_Column);
        playerDatabase.getColumns().add(valueColumn);
        playerDatabase.getColumns().add(notesColumn);
        
        
        //procedure for serach players
       // 1. Wrap the ObservableList(dataManager.getDraft().getDataBase()) in a FilteredList(filteredData)
        //2. Set the filter Predicate whenever the filter changes
        //3. Wrap the FilteredList in a SortedList
        //4. Bind the SortedList comparator to the TableView comparator
        //5. Add sorted (and filtered) data to the table
        //1.wrap the observable list into the filterList
         FilteredList<Player> filterData = new FilteredList<>(dataManager.getDraft().getDataBase(), p -> true);
         //I need to add to use two filterLists because when the user click
         //either radioButton or textField. The table will first update the filter
         //function first and then update the search function
          FilteredList<Player> filteredData = new FilteredList<>(filterData, p -> true);
        //2.add listener to the textfield and the radioButtons  
           registerTextFieldController(playersSearchTextField,filterData); 
  
           registerRadioButtonController(radioC,filteredData);
           registerRadioButtonController(radio1B,filteredData);
           registerRadioButtonController(radioCI,filteredData);
           registerRadioButtonController(radio3B,filteredData);
           registerRadioButtonController(radio2B,filteredData);
           registerRadioButtonController(radioMI,filteredData);
           registerRadioButtonController(radioSS,filteredData);
           registerRadioButtonController(radioOF,filteredData);
           registerRadioButtonController(radioU,filteredData);   
           registerRadioButtonController(radioP,filteredData);   
           registerRadioButtonController(radioALL,filteredData);
        
        // 3. Wrap the FilteredList in a SortedList. 
	SortedList<Player> sortedData = new SortedList<>(filteredData);		
	// 4. Bind the SortedList comparator to the TableView comparator.
	// Otherwise, sorting the TableView would have no effect.
	sortedData.comparatorProperty().bind(playerDatabase.comparatorProperty());
	// 5. Add sorted (and filtered) data to the table.
	playerDatabase.setItems(sortedData);
           
         availablePlayersPane.getStyleClass().add(SCREEN_PANE);                 
         availablePlayersLabel= initChildLabel(availablePlayersPane, WDK_PropertyType.AVAILABLE_PLAYERS_HEADING_LABEL,CLASS_HEADING_LABEL);
         availablePlayersPane.getChildren().add(playersToolbar);
         availablePlayersPane.getChildren().add(radioButtonsBox);
         availablePlayersPane.getChildren().add(playerDatabase);
         screenPane.setCenter(availablePlayersPane);
     }
     
       private void initFantasyStandingsScreen(){
   
         fantasyStandingsPane = new FantasyStandingsScreen();
         fantasyStandingsPane.getStyleClass().add(SCREEN_PANE);        
         fantasyStandingsLabel= initChildLabel(fantasyStandingsPane, WDK_PropertyType.FANTASY_STANDINGS_HEADING_LABEL,CLASS_HEADING_LABEL);
         fantasyStandingsTable= new TableView();
        //create the column       
         teamName_Column=new TableColumn(COL_TEAMNAME);
         playerNeeded_Column=new TableColumn(COL_PLAYERNEEDED);        
         moneyLeft_Column=new TableColumn(COL_MONEYLEFT);                
         pp_Column = new TableColumn(COL_PP);
         R_Column = new TableColumn(COL_R);
         HR_Column = new TableColumn(COL_HR);
         RBI_Column = new TableColumn(COL_RBI);
         SB_Column = new TableColumn(COL_SB);
         BA_Column = new TableColumn(COL_BA);
         W_Column = new TableColumn(COL_W);
         K_Column = new TableColumn(COL_K);
         SV_Column = new TableColumn(COL_SV);
         ERA_Column = new TableColumn(COL_ERA);
         ERA_Column.setSortType(TableColumn.SortType.DESCENDING);
         WHIP_Column = new TableColumn(COL_WHIP);
         WHIP_Column.setSortType(TableColumn.SortType.DESCENDING);
         totalPoints_Column = new TableColumn(COL_TOTALPOINTS);
        //AND LINK THE COLUMN TO THE DATA          
        teamName_Column.setCellValueFactory(new PropertyValueFactory<String,String>("teamName"));
        playerNeeded_Column.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("playersNeeded"));                 
        moneyLeft_Column.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("moneyLeft"));                   
        pp_Column.setCellValueFactory(new PropertyValueFactory<Float,Float>("personPerValue")); 
        R_Column.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("run")); 
        HR_Column.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("homeRuns")); 
        RBI_Column.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("RBI")); 
        SB_Column.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("SB"));
        BA_Column.setCellValueFactory(new PropertyValueFactory<Float,Float>("BA")); 
        W_Column.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("walk")); 
        K_Column.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("strikeouts")); 
        SV_Column.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("save")); 
        ERA_Column.setCellValueFactory(new PropertyValueFactory<Float,Float>("ERA")); 
        WHIP_Column.setCellValueFactory(new PropertyValueFactory<Float,Float>("whip")); 
        totalPoints_Column.setCellValueFactory(new PropertyValueFactory<Float,Float>("totalPoints")); 
        
      //add the columns to the table      
       fantasyStandingsTable.getColumns().add(teamName_Column);
       fantasyStandingsTable.getColumns().add(playerNeeded_Column);     
       fantasyStandingsTable.getColumns().add(moneyLeft_Column);   
       fantasyStandingsTable.getColumns().add(pp_Column); 
       fantasyStandingsTable.getColumns().add(R_Column); 
       fantasyStandingsTable.getColumns().add(HR_Column); 
       fantasyStandingsTable.getColumns().add(RBI_Column); 
       fantasyStandingsTable.getColumns().add(SB_Column); 
       fantasyStandingsTable.getColumns().add(BA_Column); 
       fantasyStandingsTable.getColumns().add(W_Column); 
       fantasyStandingsTable.getColumns().add(K_Column); 
       fantasyStandingsTable.getColumns().add(SV_Column); 
       fantasyStandingsTable.getColumns().add(ERA_Column);       
       fantasyStandingsTable.getColumns().add(WHIP_Column); 
       fantasyStandingsTable.getColumns().add(totalPoints_Column); 
       dataManager.getDraft().assignPointsToTeams();
      fantasyStandingsTable.setItems(dataManager.getDraft().getTeams());
       //MLBTeam get the itesm
       
       //MLBTeam screen get the table
       fantasyStandingsPane.getChildren().add(fantasyStandingsTable);
       screenPane.setCenter(fantasyStandingsPane);
     }
       
       private void initDraftScreen(){      
         draftPane= new DraftScreen();
         draftPane.getStyleClass().add(SCREEN_PANE);        
        draftLabel= initChildLabel(draftPane, WDK_PropertyType.DRAFT_HEADING_LABEL,CLASS_HEADING_LABEL);
        draftButtonsHBox = new HBox();
      draftOnePlayerButton = initChildButton(draftButtonsHBox, WDK_PropertyType.DRAFT_ONE_PLAYER_ICON,WDK_PropertyType.DRAFT_ONE_PLAYER_TOOLTIP,false);
      startDraftButton = initChildButton(draftButtonsHBox, WDK_PropertyType.START_DRAFT_ICON,WDK_PropertyType.START_DRAFT_TOOLTIP,false);
      pauseDraftButton = initChildButton(draftButtonsHBox, WDK_PropertyType.PAUSE_DRAFT_ICON,WDK_PropertyType.PAUSE_DRAFT_TOOLTIP,false);      
         
         //create the table
       draftTable = new TableView();
       //create the column   
         draftPickNumberColumn = new TableColumn(COL_DRAFT_PICK_NUMBER);
         firstNameColumn=new TableColumn(COL_FIRSTNAME);
         lastNameColumn=new TableColumn(COL_LASTNAME);         	                 
         assignedTeamColumn = new TableColumn(COL_ASSIGNED_TEAM);         
         contractColumn = new TableColumn(COL_CONTRACT);
         salaryColumn = new TableColumn(COL_SALARY);
         
         
        //AND LINK THE COLUMN TO THE DATA    
       // draftPickNumberColumn.
        draftPickNumberColumn.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("draftPickNumber"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<String,String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<String,String>("lastName")); 
        assignedTeamColumn.setCellValueFactory(new PropertyValueFactory<String,String>("assignedTeam"));
        contractColumn.setCellValueFactory(new PropertyValueFactory<String,String>("contract"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("salary"));
                         
      //add the columns to the table 
       draftTable.getColumns().add(draftPickNumberColumn);
       draftTable.getColumns().add(firstNameColumn);
       draftTable.getColumns().add(lastNameColumn);     
       draftTable.getColumns().add(assignedTeamColumn);
       draftTable.getColumns().add(contractColumn);
       draftTable.getColumns().add(salaryColumn);
       draftTable.setItems(dataManager.getDraft().getDraftedPlayers());
       //add them to the pane
       draftPane.getChildren().addAll(draftButtonsHBox,draftTable);
       screenPane.setCenter(draftPane);
     }
       
       private void initMLBTeamsScreen(){                 
         MLBTeamsPane = new MLBTeamsScreen();
         MLBTeamsPane.getStyleClass().add(SCREEN_PANE);        
         MLBTeamsLabel= initChildLabel(MLBTeamsPane, WDK_PropertyType.MLB_TEAMS_HEADING_LABEL,CLASS_HEADING_LABEL);      
         MLBTeamSelectionHBox =new HBox();
        MLBTeamSelectionLabel = initChildLabel(MLBTeamSelectionHBox, WDK_PropertyType.MLB_TEAM_SELECTION_LABEL,CLASS_PROMPT_LABEL);        
        MLBTeamSelectionComboBox= initHBoxComboBox(MLBTeamSelectionHBox);
        MLBTeamSelectionComboBox.getItems().addAll("ATL","AZ","CHC","CIN",
        "COL","LAD","MIA","MIL","NYM","PHI","PIT","SD","SF","STL","WSH");
       // MLBTeamSelectionComboBox.setValue("ATL");
       
        //create the table
         MLBTeamTable= new TableView();
        //create the column       
         firstNameColumn=new TableColumn(COL_FIRSTNAME);
         firstNameColumn.setSortable(false);
         lastNameColumn=new TableColumn(COL_LASTNAME); 
         lastNameColumn.setSortable(false);
         positionsColumn=new TableColumn(COL_POSITIONS);                
         positionsColumn.setSortable(false);
        //AND LINK THE COLUMN TO THE DATA          
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<String,String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<String,String>("lastName"));                 
        positionsColumn.setCellValueFactory(new PropertyValueFactory<String,String>("qualifyPositions"));                   
      //add the columns to the table      
       MLBTeamTable.getColumns().add(firstNameColumn);
       MLBTeamTable.getColumns().add(lastNameColumn);     
       MLBTeamTable.getColumns().add(positionsColumn);       
       //get the correct items for the table
       Collections.sort(dataManager.getDraft().getDataBase(), new Comparator<Player>(){
           String fullName1="",fullName2="";
           public int compare(Player p1, Player p2){
               fullName1=p1.getLastName()+p1.getFirstName();
               fullName2=p2.getLastName()+p2.getFirstName();
               return (fullName1.compareToIgnoreCase(fullName2));
           }
       });
        FilteredList<Player> filteredData = new FilteredList<>(dataManager.getDraft().getDataBase(), p -> true);
         registerMLBTeamComboBoxController(MLBTeamSelectionComboBox,filteredData); 
         SortedList<Player> sortedData = new SortedList<>(filteredData);			
	sortedData.comparatorProperty().bind(MLBTeamTable.comparatorProperty());
	MLBTeamTable.setItems(sortedData);
               
       //add to the pane              
        MLBTeamsPane.getChildren().addAll(MLBTeamSelectionHBox,MLBTeamTable);
         screenPane.setCenter(MLBTeamsPane);
     }
       
     //Bottom toolbar controls are initialized here
     private void initBottomToolbarControls(){         
         bottomToolbarPane= new FlowPane();         
         // HERE ARE OUR bottom TOOLBAR BUTTONS, NOTE THAT ENABLED (false)
       fantasyTeamsButton = initChildButton(bottomToolbarPane, WDK_PropertyType.FANTASY_TEAMS_ICON, WDK_PropertyType.FANTASY_TEAMS_TOOLTIP, false);
        availablePlayersButton = initChildButton(bottomToolbarPane, WDK_PropertyType.AVAILABLE_PLAYERS_ICON, WDK_PropertyType.AVAILABLE_PLAYERS_TOOLTIP, false);
        fantasyStandingsButton = initChildButton(bottomToolbarPane, WDK_PropertyType.FANTASY_STANDINGS_ICON, WDK_PropertyType.FANTASY_STANDINGS_TOOLTIP, false);
        draftButton = initChildButton(bottomToolbarPane, WDK_PropertyType.DRAFT_ICON, WDK_PropertyType.DRAFT_TOOLTIP, false);
        MLBTeamsButton = initChildButton(bottomToolbarPane, WDK_PropertyType.MLB_TEAMS_ICON, WDK_PropertyType.MLB_TEAMS_TOOLTIP, false);
        
     }
    // INITIALIZE THE WINDOW (i.e. STAGE) PUTTING ALL THE CONTROLS
    // THERE EXCEPT THE WORKSPACE, WHICH WILL BE ADDED THE FIRST
    // TIME A NEW Course IS CREATED OR LOADED
    private void initWindow(String windowTitle) {
        // SET THE WINDOW TITLE
        primaryStage.setTitle(windowTitle);

        // GET THE SIZE OF THE SCREEN
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // AND USE IT TO SIZE THE WINDOW
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // ADD THE TOOLBAR ONLY, NOTE THAT THE WORKSPACE
        // HAS BEEN CONSTRUCTED, BUT WON'T BE ADDED UNTIL
        // THE USER STARTS EDITING A COURSE
        wdkPane = new BorderPane();
        wdkPane.setTop(topToolbarPane);
      
        primaryScene = new Scene(wdkPane);

        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
        // WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
        primaryScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        primaryStage.setScene(primaryScene);
        primaryScene.setCursor(Cursor.HAND);
        primaryStage.show();
    }

    // INIT ALL THE EVENT HANDLERS
    private void initEventHandlers() throws IOException {
        
      
      topToolbarController = new TopToolbarController(messageDialog, yesNoCancelDialog, draftFileManager);
       //courseFileManager, siteExporter);                
         
        newDraftButton.setOnAction(e -> {
            topToolbarController.handleNewDraftRequest(this);
            initTeamScreenEventHandler();
        });  
        
        saveDraftButton.setOnAction(e -> {
            topToolbarController.handleSaveDraftRequest(this, dataManager.getDraft());
        });
        
         loadDraftButton.setOnAction(e -> {
            topToolbarController.handleLoadDraftRequest(this);
        });
         
         exitButton.setOnAction(e ->{
             topToolbarController.handleExitRequest(this);
         });
        
        //bottom toolbar controls
        availablePlayersButton.setOnAction(e -> {
            initAvailablePlayersScreen();
            initPlayerScreenEventHandler();
            dataManager.getDraft().calculateEstimatedValue();            
        });
        
        fantasyTeamsButton.setOnAction(e ->{            
            initFantasyTeamsScreen(); 
            draftNameTextField.setText(dataManager.getDraft().getDraftName());          
             updateTeamScreenButtons();
             loadTeamComboBox();
             dataManager.getDraft().setCurrentTeam((String)selectTeamComboBox.getSelectionModel().getSelectedItem());        
           Team teamToDisplay = dataManager.getDraft().findTeam(dataManager.getDraft().getCurrentTeam());
        startTeamTable.setItems(teamToDisplay.getTeamPlayers());
        taxiSquadTable.setItems(teamToDisplay.getTaxiPlayers());
           initTeamScreenEventHandler();
        });
        
        fantasyStandingsButton.setOnAction(e->{        
            initFantasyStandingsScreen();
        });
        
        draftButton.setOnAction(e->{
            initDraftScreen();
            initDraftScreenEventHandler();
           draftTable.setItems(dataManager.getDraft().getDraftedPlayers());
            dataManager.getDraft().calculateEstimatedValue();
        });
        
        MLBTeamsButton.setOnAction(e->{
            initMLBTeamsScreen();
        });                    
        
       
        }
  private void initTeamScreenEventHandler(){
          // AND NOW THE TEAM ITEM ADDING AND EDITING CONTROLS
       draftController = new DraftEditController();
        teamController = new TeamEditController(primaryStage, dataManager.getDraft(), messageDialog, yesNoCancelDialog);
        
        addTeamButton.setOnAction(e -> {      
            int tempSize=dataManager.getDraft().getTeams().size();
            teamController.handleAddTeamRequest(this); 
            if(tempSize!=dataManager.getDraft().getTeams().size()){
            draftController.handleDraftChangeRequest(this);
            updateTeamScreenButtons();
            updateSelectTeamComboBox();
            updateStartTeamTable(); 
            //try to stop the automatically draft 
          //  initDraftScreenEventHandler();
         //   draftScreenController.setRunning(false);
            }
                 
        });
        
        removeTeamButton.setOnAction(e -> {   
            int tempSize=dataManager.getDraft().getTeams().size();
            teamController.handleRemoveTeamRequest(this,findTeamYouSelected() );
            draftController.handleDraftChangeRequest(this);
            if(tempSize!=dataManager.getDraft().getTeams().size()){
             updateTeamScreenButtons();
             updateSelectTeamComboBox();
            }
        });
        
        editTeamButton.setOnAction(e -> {
             teamController.handleEditTeamRequest(this,findTeamYouSelected());             
             draftController.handleDraftChangeRequest(this);
             updateTeamScreenButtons();
            String teamName= dataManager.getDraft().getCurrentTeam();
            //updateSelectTeamComboBox();
             selectTeamComboBox.valueProperty().setValue(null);
             selectTeamComboBox.getSelectionModel().clearSelection();
             selectTeamComboBox.getItems().clear();
             loadTeamComboBox();             
           //  selectTeamComboBox.setValue(dataManager.getDraft().getCurrentTeam());
          //   selectTeamComboBox.getSelectionModel().selectLast();
             //someHow when selectTeamcomboBox clear evertything, dataManager lose its currentTeam
             selectTeamComboBox.setValue(teamName);
             dataManager.getDraft().setCurrentTeam(teamName);
         //  dataManager.getDraft().setCurrentTeam((String)selectTeamComboBox.getSelectionModel().getSelectedItem());        
             Team teamToDisplay = dataManager.getDraft().findTeam(dataManager.getDraft().getCurrentTeam());
             startTeamTable.setItems(teamToDisplay.getTeamPlayers());
        });
        selectTeamComboBox.setOnAction(e->{
            updateStartTeamTable();
        });
        
         // AND NOW THE startTeam TABLE
        startTeamTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                if(!startTeamTable.getSelectionModel().isEmpty()){
                // OPEN UP THE SCHEDULE ITEM EDITOR                
                playersController.handleMovePlayerRequest(this, startTeamTable.getSelectionModel().getSelectedItem() );
                draftController.handleDraftChangeRequest(this);
                 //update the estimated value for the players 
              //     dataManager.getDraft().calculateEstimatedValue();
                }                      
            }
        });
    }
  
  private void initPlayerScreenEventHandler(){
         // AND NOW THE TEAM ITEM ADDING AND EDITING CONTROLS
        draftController = new DraftEditController();
        playersController = new PlayersEditController(primaryStage, dataManager.getDraft(), messageDialog, yesNoCancelDialog);
        
        addPlayerButton.setOnAction(e -> {        
            playersController.handleAddPlayerRequest(this);     
            draftController.handleDraftChangeRequest(this);  
             //update the estimated value for the players 
             dataManager.getDraft().calculateEstimatedValue();
        });
        
        removePlayerButton.setOnAction(e -> {            
            playersController.handleRemovePlayerRequest(this, playerDatabase.getSelectionModel().getSelectedItem());
            draftController.handleDraftChangeRequest(this);   
               //update the estimated value for the players 
             dataManager.getDraft().calculateEstimatedValue();
        });
        
        // AND NOW THE playerDataBase TABLE
        playerDatabase.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                if(!playerDatabase.getSelectionModel().isEmpty()){
                // OPEN UP THE SCHEDULE ITEM EDITOR                                         
                playersController.handleEditPlayerRequest(this, playerDatabase.getSelectionModel().getSelectedItem() );
                draftController.handleDraftChangeRequest(this);
              //update the estimated value for the players 
             dataManager.getDraft().calculateEstimatedValue();
                }
            }
        });
        
        
  }
    
  private void initDraftScreenEventHandler(){
        // AND NOW THE TEAM ITEM ADDING AND EDITING CONTROLS
        draftController = new DraftEditController();
        draftScreenController = new DraftScreenController(primaryStage, dataManager.getDraft(), messageDialog, yesNoCancelDialog);
        
        draftOnePlayerButton.setOnAction(e -> {        
            draftScreenController.handleDraftOnePlayerRequest(this);     
            draftController.handleDraftChangeRequest(this);           
        });   
        startDraftButton.setOnAction(e -> {        
           draftScreenController.handleStartDraftRequest(this);     
            draftController.handleDraftChangeRequest(this);        
        });  
        pauseDraftButton.setOnAction(e -> { 
            draftScreenController.handlePauseDraftRequest();
          //  draftScreenController.handlePauseDraftRequest(this);     
            draftController.handleDraftChangeRequest(this);             
        });  
        
        
  }
    // INIT A BUTTON AND ADD IT TO A CONTAINER IN A TOOLBAR
    private Button initChildButton(Pane toolbar, WDK_PropertyType icon, WDK_PropertyType tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = "file:" + PATH_IMAGES + props.getProperty(icon.toString());
        Image buttonImage = new Image(imagePath);
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
        button.setTooltip(buttonTooltip);
        toolbar.getChildren().add(button);
        return button;
    }
    
    // INIT A LABEL AND SET IT'S STYLESHEET CLASS
    private Label initLabel(WDK_PropertyType labelProperty, String styleClass) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String labelText = props.getProperty(labelProperty);
        Label label = new Label(labelText);
        label.getStyleClass().add(styleClass);
        return label;
    }

    // INIT A LABEL AND PLACE IT IN A GridPane INIT ITS PROPER PLACE
    private Label initGridLabel(GridPane container, WDK_PropertyType labelProperty, String styleClass, int col, int row, int colSpan, int rowSpan) {
        Label label = initLabel(labelProperty, styleClass);
        container.add(label, col, row, colSpan, rowSpan);
        return label;
    }

    // INIT A LABEL AND PUT IT IN A TOOLBAR
    private Label initChildLabel(Pane container, WDK_PropertyType labelProperty, String styleClass) {
        Label label = initLabel(labelProperty, styleClass);
        container.getChildren().add(label);
        return label;
    }

    // INIT A COMBO BOX AND PUT IT IN A GridPane
    private ComboBox initGridComboBox(GridPane container, int col, int row, int colSpan, int rowSpan) throws IOException {
        ComboBox comboBox = new ComboBox();
        container.add(comboBox, col, row, colSpan, rowSpan);
        return comboBox;
    }
    
    private ComboBox initHBoxComboBox(Pane container){        
        ComboBox comboBox = new ComboBox();
        container.getChildren().add(comboBox);
        return comboBox;
    }

    // INIT A TEXT FIELD AND PUT IT IN A GridPane
    private TextField initGridTextField(GridPane container, int size, String initText, boolean editable, int col, int row, int colSpan, int rowSpan) {
        TextField tf = new TextField();
        tf.setPrefColumnCount(size);
        tf.setText(initText);
        tf.setEditable(editable);
        container.add(tf, col, row, colSpan, rowSpan);
        return tf;
    }
    
    private TextField initTextField(Pane container, int size, String initText, boolean editable) {
        TextField tf = new TextField();
        tf.setPrefColumnCount(size);
        tf.setText(initText);
        tf.setEditable(editable);
        container.getChildren().add(tf);
        return tf;
    }

    // INIT A CheckBox AND PUT IT IN A TOOLBAR
    private CheckBox initChildCheckBox(Pane container, String text) {
        CheckBox cB = new CheckBox(text);
        container.getChildren().add(cB);
        return cB;
    }

    private RadioButton initRadioButton(Pane container, String initText){
        RadioButton rb= new RadioButton();
        rb.setText(initText);
        container.getChildren().add(rb);
        return rb;
    }
    
    private void registerTextFieldController(TextField textField,FilteredList<Player> filteredData ){
          textField.textProperty().addListener((observable, oldValue,newValue)->{
         filteredData.setPredicate(player -> {
	 // If filter text is empty, display all persons.
          if (newValue == null || newValue.isEmpty()) {
               return true;}				
	 // Compare first name and last name of every person with filter text.
          String lowerCaseFilter = newValue.toLowerCase();				
		if (player.getFirstName().toLowerCase().charAt(0)==lowerCaseFilter.charAt(0) &&
                        player.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    
                    return true; // Filter matches first name.
		} else if (player.getLastName().toLowerCase().charAt(0)==lowerCaseFilter.charAt(0) &&
                        player.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
			}
 	         else return false; // Does not match.
		  });
         });
    }
    private void registerMLBTeamComboBoxController(ComboBox comboBox,FilteredList<Player> filteredData){
        comboBox.setOnAction(e->{
           filteredData.setPredicate(player ->{
               if(player.getProTeam().equals(comboBox.getSelectionModel().getSelectedItem()))
                   return true;
               else return false;
           });
        });
    }
    private void registerRadioButtonController(RadioButton radioButton, FilteredList<Player> filteredData){
         
         radioButton.setOnAction(e->{
          
         filteredData.setPredicate(player -> {
	     RadioButton button=(RadioButton) e.getSource();
             //select all, display all players
             if(button.getText().equals("All")){
                 R_W_Column.setText(COL_R_W);
                 HR_SV_Column.setText(COL_HR_SV);
                 RBI_K_Column.setText(COL_RBI_K);
                 SB_ERA_Column.setText(COL_SB_ERA);
                 BA_WHIP_Column.setText(COL_BA_WHIP);
                 return true;
             }
             if(button.getText().equals("P")&& player.getQualifyPositions().equals("P")){             
       R_W_Column.setText(COL_W);HR_SV_Column.setText(COL_SV);RBI_K_Column.setText(COL_K);
       SB_ERA_Column.setText(COL_ERA);BA_WHIP_Column.setText(COL_WHIP);
               return true;    
             }
             else if(button.getText().equals("C") && positionExisted(player.getQualifyPositions(), "C")){
         R_W_Column.setText(COL_R);HR_SV_Column.setText(COL_HR);RBI_K_Column.setText(COL_RBI);
       SB_ERA_Column.setText(COL_SB);BA_WHIP_Column.setText(COL_BA);
               return true;
             }
             else if(button.getText().equals("1B")&& positionExisted(player.getQualifyPositions(), "1B")){
                R_W_Column.setText(COL_R);HR_SV_Column.setText(COL_HR);RBI_K_Column.setText(COL_RBI);
       SB_ERA_Column.setText(COL_SB);BA_WHIP_Column.setText(COL_BA);

               return true;
             }
             else if(button.getText().equals("CI")&& positionExisted(player.getQualifyPositions(), "CI")){
              R_W_Column.setText(COL_R);HR_SV_Column.setText(COL_HR);RBI_K_Column.setText(COL_RBI);
       SB_ERA_Column.setText(COL_SB);BA_WHIP_Column.setText(COL_BA);

               return true;
             }
             else if(button.getText().equals("3B")&& positionExisted(player.getQualifyPositions(), "3B")){
         R_W_Column.setText(COL_R);HR_SV_Column.setText(COL_HR);RBI_K_Column.setText(COL_RBI);
       SB_ERA_Column.setText(COL_SB);BA_WHIP_Column.setText(COL_BA);

               return true;
             }
             else if(button.getText().equals("2B")&& positionExisted(player.getQualifyPositions(), "2B")){
           R_W_Column.setText(COL_R);HR_SV_Column.setText(COL_HR);RBI_K_Column.setText(COL_RBI);
       SB_ERA_Column.setText(COL_SB);BA_WHIP_Column.setText(COL_BA);
               return true;
             }
             else if(button.getText().equals("MI")&& positionExisted(player.getQualifyPositions(), "MI")){
                    R_W_Column.setText(COL_R);HR_SV_Column.setText(COL_HR);RBI_K_Column.setText(COL_RBI);
       SB_ERA_Column.setText(COL_SB);BA_WHIP_Column.setText(COL_BA);
               return true;
             }
             else if(button.getText().equals("SS")&& positionExisted(player.getQualifyPositions(), "SS")){
                  R_W_Column.setText(COL_R);HR_SV_Column.setText(COL_HR);RBI_K_Column.setText(COL_RBI);
       SB_ERA_Column.setText(COL_SB);BA_WHIP_Column.setText(COL_BA);

               return true;
             }
             else if(button.getText().equals("OF")&& positionExisted(player.getQualifyPositions(), "OF")){
                 R_W_Column.setText(COL_R);HR_SV_Column.setText(COL_HR);RBI_K_Column.setText(COL_RBI);
       SB_ERA_Column.setText(COL_SB);BA_WHIP_Column.setText(COL_BA);

               return true;
             }
             else if(button.getText().equals("U")&& positionExisted(player.getQualifyPositions(), "U")){
           R_W_Column.setText(COL_R);HR_SV_Column.setText(COL_HR);RBI_K_Column.setText(COL_RBI);
       SB_ERA_Column.setText(COL_SB);BA_WHIP_Column.setText(COL_BA);
               return true;             
             }
               else
               return false;	
		  });
         });
    }
    
    private boolean positionExisted(String positions, String value){
        boolean check=false;
        String sign="_";
        String[] tokens = positions.split(sign);
        for(int j=0; j<tokens.length;j++){
            if(tokens[j].equals(value))
                check=true;
        }
        return check;
    }
    
    //This method is used to update the three buttons in team screen
    private void updateTeamScreenButtons(){
        int size=dataManager.getDraft().getTeams().size();
        
        if(size>0 && size <12){
            removeTeamButton.setDisable(false);
            editTeamButton.setDisable(false);
             addTeamButton.setDisable(false);
            
        }
        
        else if(size>=12)
            addTeamButton.setDisable(true);
        
        else{
            addTeamButton.setDisable(false);
             removeTeamButton.setDisable(true);
            editTeamButton.setDisable(true);
        }
    }      
    
    //This function update information of ComboBox in the team screen
      private void updateSelectTeamComboBox(){
          ObservableList<Team> teams = dataManager.getDraft().getTeams();
          int size= teams.size();
         if(size>teamNumber){
             selectTeamComboBox.getItems().add(teams.get(size-1).getTeamName());
             teamNumber++;
         }          
         else if(size<teamNumber){
             selectTeamComboBox.getItems().remove(selectTeamComboBox.getSelectionModel().getSelectedItem());          
             teamNumber--;
         }
         else{
             selectTeamComboBox.getItems().remove(selectTeamComboBox.getSelectionModel().getSelectedItem());
             selectTeamComboBox.getItems().add(dataManager.getDraft().getCurrentTeam());
                     }
        
          selectTeamComboBox.setValue(dataManager.getDraft().getCurrentTeam());
         dataManager.getDraft().setCurrentTeam((String)selectTeamComboBox.getSelectionModel().getSelectedItem()); 
      }
      //When I go back to the team screen again, use this function to reload the Team ComboBox
      private void loadTeamComboBox(){
          ObservableList<Team> teams= dataManager.getDraft().getTeams();
          int size=teams.size();
          for(int i=0;i<size;i++){
              selectTeamComboBox.getItems().add(teams.get(i).getTeamName());
          }
          selectTeamComboBox.setValue(dataManager.getDraft().getCurrentTeam());
      }
    
      private Team findTeamYouSelected(){
          String teamName=selectTeamComboBox.getSelectionModel().getSelectedItem().toString();       
            ObservableList<Team> tempTeams=dataManager.getDraft().getTeams();
            Team temp=new Team();
            for(int i=0;i<tempTeams.size();i++){
                
                if(tempTeams.get(i).getTeamName().equals(teamName))
                    temp=tempTeams.get(i);
           
                    }
            return temp;
      }
      //Use to update the starting up table.
        private void updateStartTeamTable(){
            dataManager.getDraft().setCurrentTeam((String)selectTeamComboBox.getSelectionModel().getSelectedItem());
            Team teamToDisplay = dataManager.getDraft().findTeam((String)selectTeamComboBox.getSelectionModel().getSelectedItem());
            startTeamTable.setItems(teamToDisplay.getTeamPlayers());  
            taxiSquadTable.setItems(teamToDisplay.getTaxiPlayers());
            }
        
        //here we declare the taxisquadTable
        //create a new table, we need to do four things
        //first,create new table columns
        //second,set valueProperty for the columns
        //third,assign the columns to the table
        //at last, add the table to the pane
        private void taxiSquadTable(){
        
        taxiSquadTable= new TableView();
        
        assignedPositionColumn=new TableColumn(COL_POSITION);
         firstNameColumn=new TableColumn(COL_FIRSTNAME);
         lastNameColumn=new TableColumn(COL_LASTNAME);         	
         proTeamColumn=new TableColumn(COL_PROTEAM);
         positionsColumn=new TableColumn(COL_POSITIONS);     
         R_W_Column=new TableColumn(COL_R_W);
         HR_SV_Column=new TableColumn(COL_HR_SV);
         RBI_K_Column=new TableColumn(COL_RBI_K);
        SB_ERA_Column=new TableColumn(COL_SB_ERA);
        BA_WHIP_Column=new TableColumn(COL_BA_WHIP);              
        estimatedValueColumn=new TableColumn(COL_VALUE);
        salaryColumn=new TableColumn(COL_SALARY);
        contractColumn=new TableColumn(COL_CONTRACT);
        assignedPositionColumn.setSortable(false);
        firstNameColumn.setSortable(false);
        lastNameColumn.setSortable(false);
        proTeamColumn.setSortable(false);
        positionsColumn.setSortable(false);
        R_W_Column.setSortable(false);
        HR_SV_Column.setSortable(false);
        RBI_K_Column.setSortable(false);
        SB_ERA_Column.setSortable(false);
        BA_WHIP_Column.setSortable(false);
        estimatedValueColumn.setSortable(false);
        salaryColumn.setSortable(false);
        contractColumn.setSortable(false);
        //AND LINK THE COLUMN TO THE DATA   
        assignedPositionColumn.setCellValueFactory(new PropertyValueFactory<String,String>("assignedPosition"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<String,String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<String,String>("lastName"));          
        proTeamColumn.setCellValueFactory(new PropertyValueFactory<String,String>("proTeam"));
        positionsColumn.setCellValueFactory(new PropertyValueFactory<String,String>("qualifyPositions"));
        R_W_Column.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("runs_walk"));
        HR_SV_Column.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("homeRuns_saves"));
        RBI_K_Column.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("runsBattedIn_strikeouts"));
        SB_ERA_Column.setCellValueFactory(new PropertyValueFactory<Float,Float>("SB_ERA"));       
        BA_WHIP_Column.setCellValueFactory(new PropertyValueFactory<Float,Float>("BA_WHIP"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<Integer,Integer>("salary"));
        estimatedValueColumn.setCellValueFactory(new PropertyValueFactory<String, String>("estimatedValue"));        
        contractColumn.setCellValueFactory(new PropertyValueFactory<String,String>("contract"));         
          
      
        taxiSquadTable.getColumns().add(assignedPositionColumn);
       taxiSquadTable.getColumns().add(firstNameColumn);
       taxiSquadTable.getColumns().add(lastNameColumn);
       taxiSquadTable.getColumns().add(proTeamColumn);
       taxiSquadTable.getColumns().add(positionsColumn);       
       taxiSquadTable.getColumns().add(R_W_Column);
       taxiSquadTable.getColumns().add(HR_SV_Column);
       taxiSquadTable.getColumns().add(RBI_K_Column);
       taxiSquadTable.getColumns().add(SB_ERA_Column);
       taxiSquadTable.getColumns().add(BA_WHIP_Column);
       taxiSquadTable.getColumns().add(estimatedValueColumn);
       taxiSquadTable.getColumns().add(contractColumn);
       taxiSquadTable.getColumns().add(salaryColumn);
        Team teamToDisplay = dataManager.getDraft().findTeam(dataManager.getDraft().getCurrentTeam());
        taxiSquadTable.setItems(teamToDisplay.getTaxiPlayers());
       //add to the pane
       taxiSquadPane.getChildren().add(taxiSquadTable);
       
      
        }
      
}
