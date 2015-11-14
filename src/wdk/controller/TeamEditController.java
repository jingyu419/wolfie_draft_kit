package wdk.controller;

import javafx.collections.ObservableList;
import static wdk.WDK_PropertyType.REMOVE_ITEM_MESSAGE;
import wdk.gui.MessageDialog;
import wdk.gui.TeamDialog;
import wdk.gui.YesNoCancelDialog;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static wdk.WDK_PropertyType.FAIL_TO_ASSIGN_TEAM_MESSAGE_SAME_TEAM_NAME;
import static wdk.WDK_PropertyType.FAIL_TO_ASSIGN_TEAM_MESSAGE_SAME_OWNER_NAME;
import wdk.data.Draft;
import wdk.data.DraftDataManager;
import wdk.data.Team;
import wdk.gui.WDK_GUI;

/**
 *This class is used to open a dialog for user to add/remove/edit/move a team
 * @author jingyu
 */
public class TeamEditController {
    TeamDialog td;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
     PropertiesManager properties;
    
    /**
     *This is the constructor for the class
     * @param initPrimaryStage
     * @param draft
     * @param initMessageDialog
     * @param initYesNoCancelDialog
     */
    public TeamEditController(Stage initPrimaryStage, Draft draft, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        td = new TeamDialog(initPrimaryStage, draft, initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
           properties = PropertiesManager.getPropertiesManager();
    }
    
    /**
     *This methood is used to add a new team to the team list
     * @param gui
     */
        
    public void handleAddTeamRequest(WDK_GUI gui) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        td.showAddTeamDialog();
        
        // DID THE USER CONFIRM?
        if (td.wasCompleteSelected()) {
            // GET THE team ITEM
            Team team  = td.getTeam();
            Boolean check_same_team_name=false;
            Boolean check_same_owner_name=false;
            //here I want to know does the user create a team
            //name which is the same the team names I have 
            //created. If that is true, I will not add it to my teams list.
            for(int i=0;i<draft.getTeams().size();i++){
                if(team.getTeamName().equals(draft.getTeams().get(i).getTeamName()))
                    check_same_team_name=true;
                if(team.getOwnerName().equals(draft.getTeams().get(i).getOwnerName()))
                    check_same_owner_name=true;
            }
            
           // AND ADD IT AS A ROW TO THE TABLE
            if(check_same_team_name==false && check_same_owner_name==false){
            draft.addTeam(team);
            draft.setCurrentTeam(team.getTeamName());
            }
            else if(check_same_team_name==true && check_same_owner_name==false){
               messageDialog.show(properties.getProperty(FAIL_TO_ASSIGN_TEAM_MESSAGE_SAME_TEAM_NAME)); 
            }                
            else if(check_same_team_name==false && check_same_owner_name==true){
               messageDialog.show(properties.getProperty(FAIL_TO_ASSIGN_TEAM_MESSAGE_SAME_OWNER_NAME));   
            }
            else{
                 messageDialog.show(properties.getProperty(FAIL_TO_ASSIGN_TEAM_MESSAGE_SAME_TEAM_NAME));
            }
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }
    
    /**
     *THis method is used to edit an existed team
     * @param gui
     * @param itemToEdit
     */
    public void handleEditTeamRequest(WDK_GUI gui, Team itemToEdit) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft= ddm.getDraft();
        
         String teamName=itemToEdit.getTeamName();
         ObservableList<Team> tempTeams=draft.getTeams();
         int temp=0;
            for(int i=0;i<tempTeams.size();i++){                
                if(tempTeams.get(i).getTeamName().equals(teamName))
                    temp=i;           
                    }
            
        td.showEditTeamDialog(itemToEdit);
        
        // DID THE USER CONFIRM?
        if (td.wasCompleteSelected()) {
             Boolean check_same_team_name=false;
            Boolean check_same_owner_name=false;
            // UPDATE THE SCHEDULE ITEM
            Team team = td.getTeam();              
            //here I want to know does the user create a team
            //name which is the same the team names I have 
            //created. If that is true, I will not add it to my teams list.
            for(int i=0;i<draft.getTeams().size();i++){
                if(team.getTeamName().equals(draft.getTeams().get(i).getTeamName()) && temp!=i)
                    check_same_team_name=true;
                if(team.getOwnerName().equals(draft.getTeams().get(i).getOwnerName()) && temp!=i)
                    check_same_owner_name=true;
            }
            
           // AND ADD IT AS A ROW TO THE TABLE
            if(check_same_team_name==false && check_same_owner_name==false){
           // AND ADD IT AS A ROW TO THE TABLE           
            itemToEdit.setTeamName(team.getTeamName());
            itemToEdit.setOwnerName(team.getOwnerName());
            tempTeams.get(temp).setTeamName(team.getTeamName());
            tempTeams.get(temp).setOwnerName(team.getOwnerName());
            draft.setCurrentTeam(team.getTeamName());     
            }      
            else if(check_same_team_name==true && check_same_owner_name==false){
               messageDialog.show(properties.getProperty(FAIL_TO_ASSIGN_TEAM_MESSAGE_SAME_TEAM_NAME)); 
            }                
            else if(check_same_team_name==false && check_same_owner_name==true){
               messageDialog.show(properties.getProperty(FAIL_TO_ASSIGN_TEAM_MESSAGE_SAME_OWNER_NAME));   
            }
            else{
                 messageDialog.show(properties.getProperty(FAIL_TO_ASSIGN_TEAM_MESSAGE_SAME_TEAM_NAME));
            }
        }
        else {
           
            tempTeams.get(temp).setTeamName(itemToEdit.getTeamName());
            tempTeams.get(temp).setOwnerName(itemToEdit.getOwnerName());
            draft.setCurrentTeam(itemToEdit.getTeamName());
        }        
    }
    
    /**
     *THis method is used to remove a team
     * @param gui
     * @param itemToRemove
     */
    public void handleRemoveTeamRequest(WDK_GUI gui, Team itemToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        Draft draft = gui.getDataManager().getDraft();
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) { 
            draft.removeTeam(itemToRemove);
            //here I put the players from the deleted team back to the free agent pool.
            for(int i=0;i<itemToRemove.getTeamPlayers().size();i++){
            draft.addPlayer(itemToRemove.getTeamPlayers().get(i));
                    }
            if(draft.getTeams().size()>0)
            draft.setCurrentTeam(draft.getTeams().get(0).getTeamName());
            else
                draft.setCurrentTeam("");
        }
    }
        
}
