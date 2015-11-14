package wdk.controller;

import javafx.collections.ObservableList;
import wdk.gui.MessageDialog;
import wdk.gui.PlayerDialog;
import wdk.gui.YesNoCancelDialog;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static wdk.WDK_PropertyType.FAIL_TO_ADD_PLAYER_MESSAGE;
import static wdk.WDK_PropertyType.FAIL_TO_ADD_PLAYER_MESSAGE_SAME_NAME;
import static wdk.WDK_PropertyType.FAIL_TO_ASSIGN_PLAYER_MESSAGE;
import static wdk.WDK_PropertyType.REMOVE_ITEM_MESSAGE;
import static wdk.WDK_PropertyType.PLAYER_STAY_IN_THE_FREE_AGENT_MESSAGE;
import wdk.data.Draft;
import wdk.data.DraftDataManager;
import wdk.data.Player;
import wdk.data.Team;
import wdk.gui.PlayerEditDialog;
import wdk.gui.TaxiPlayersDialog;
import wdk.gui.WDK_GUI;

/**
 *This class is used to open a dialog for user to add/remove/edit/move a team
 * @author jingyu
 */
public class PlayersEditController {
    PlayerDialog pd;
    PlayerEditDialog ped;
    TaxiPlayersDialog tpd;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
     // WE'LL USE THIS TO GET OUR VERIFICATION FEEDBACK
    PropertiesManager properties;
    
    
    /**
     *This is the constructor for the class
     * @param initPrimaryStage
     * @param draft
     * @param initMessageDialog
     * @param initYesNoCancelDialog
     */
    public PlayersEditController(Stage initPrimaryStage, Draft draft, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        pd = new PlayerDialog(initPrimaryStage, draft, initMessageDialog);
        ped = new PlayerEditDialog(initPrimaryStage, draft, initMessageDialog);
        tpd = new TaxiPlayersDialog(initPrimaryStage, draft, initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
         properties = PropertiesManager.getPropertiesManager();
     

    }
    
    /**
     *This methood is used to add a new player to the player list
     * @param gui
     */        
    public void handleAddPlayerRequest(WDK_GUI gui) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        pd.showAddPlayerDialog();
        
        // DID THE USER CONFIRM?
        if (pd.wasCompleteSelected()) {
            Boolean check_same_player= false;
            // GET THE Player ITEM
            Player player  = pd.getPlayer();
            for(Player pl : draft.getDataBase()){
                if(pl.getFirstName().equals(player.getFirstName()) && pl.getLastName().equals(player.getLastName()))
                    check_same_player=true;
            }
            if (player.getQualifyPositions().equals("")){
            // TELL THE USER THE COURSE HAS BEEN CREATED
            messageDialog.show(properties.getProperty(FAIL_TO_ADD_PLAYER_MESSAGE));
            }         
            else if(check_same_player){
             messageDialog.show(properties.getProperty(FAIL_TO_ADD_PLAYER_MESSAGE_SAME_NAME));
            }
                
            else{
            //AND add it to the draft
            draft.getDataBase().add(player);          
            }
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
             //WE DO NOTHING
       }
    }
    
    
    
    /**
     *THis method is used to remove a Player
     * @param gui
     * @param itemToRemove
     */
           
    public void handleRemovePlayerRequest(WDK_GUI gui, Player itemToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        Draft draft = gui.getDataManager().getDraft();
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) { 
            draft.getDataBase().remove(itemToRemove);         
        }
    }
    
      
   /**
     *THis method is used to move a player to a team
     * @param gui
     * @param itemToEdit
     */
           
    public void handleEditPlayerRequest(WDK_GUI gui, Player itemToEdit) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        ObservableList<Team> teams=draft.getTeams();        
         int totalPlayersNeeded=0;               
         for(Team t: teams)
            totalPlayersNeeded+=t.getPlayersNeeded();
       if(teams.size()!=0 && totalPlayersNeeded!=0){          
        ped.showEditPlayerDialog(itemToEdit);       
        // DID THE USER CONFIRM?
        if (ped.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Player player = ped.getPlayer();
            if (player.getContract().equals("NONE") | player.getSalary()==0
              | player.getAssignedPosition().equals("NONE")
              | player.getAssignedTeam().equals("NONE")){
            // TELL THE USER THE COURSE HAS BEEN CREATED
            messageDialog.show(properties.getProperty(FAIL_TO_ASSIGN_PLAYER_MESSAGE));
            }
            else
            {
              if(!player.getAssignedTeam().equals("Free Agent")){
              itemToEdit.setAssignedTeam(player.getAssignedTeam());
              itemToEdit.setAssignedPosition(player.getAssignedPosition());
              itemToEdit.setContract(player.getContract());
              itemToEdit.setSalary(player.getSalary()); 
            
              assignedToATeam(itemToEdit,draft); 
              
              if(itemToEdit.getContract().equals("S2"))
               draft.addPlayerToDraftedPlayers(itemToEdit);                     
              }
              else{messageDialog.show(properties.getProperty(PLAYER_STAY_IN_THE_FREE_AGENT_MESSAGE));}
         
            }
           
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
         }   
       }
       else if(teams.size()!=0 &&totalPlayersNeeded==0){//here handle taxi players
           tpd.showEditPlayerDialog(itemToEdit);
           
              if (tpd.wasCompleteSelected()) {        
                Player player = tpd.getPlayer();          
                        
              if(!player.getAssignedTeam().equals("Free Agent")&& draft.findTeam(player.getAssignedTeam()).getTaxiPlayersNumber()<8){
              itemToEdit.setAssignedTeam(player.getAssignedTeam());
              itemToEdit.setAssignedPosition(player.getAssignedPosition());
              itemToEdit.setContract(player.getContract());
              itemToEdit.setSalary(1); 
            
              assignedToTaxiPlayersForATeam(itemToEdit,draft); 
              
              if(itemToEdit.getContract().equals("X"))
               draft.addPlayerToDraftedPlayers(itemToEdit);                     
              }
              else{messageDialog.show(properties.getProperty(PLAYER_STAY_IN_THE_FREE_AGENT_MESSAGE));}                     
           
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
         }  
       }
    }        
    
       /**
     *THis method is used to move a player to a team or to free agent
     * @param gui
     * @param itemToEdit
     */
           
    public void handleMovePlayerRequest(WDK_GUI gui, Player itemToEdit) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        ped.showEditPlayerDialog(itemToEdit);       
        // DID THE USER CONFIRM?
        if (ped.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Player player = ped.getPlayer();
            if ((player.getContract().equals("NONE") | player.getSalary()==0
              | player.getAssignedPosition().equals("NONE")               
              | player.getAssignedTeam().equals("NONE")
                    ) &&!player.getAssignedTeam().equals("Free Agent") ){  
                
            // TELL THE USER THE COURSE HAS BEEN CREATED
            messageDialog.show(properties.getProperty(FAIL_TO_ASSIGN_PLAYER_MESSAGE));
            }
            else
            {
              if(player.getAssignedTeam().equals("Free Agent")){
                        
              deleteFromATeam(itemToEdit,draft);
                     
              draft.removePlayerFromDrafedPlayers(itemToEdit); 
              
              itemToEdit.setAssignedPosition("NONE");
              itemToEdit.setContract("NONE");
              itemToEdit.setSalary(0);  
              itemToEdit.setAssignedTeam("NONE");
               draft.getDataBase().add(itemToEdit);
           
              }
              else{
                           
              deleteFromATeam(itemToEdit,draft);
                       
              draft.removePlayerFromDrafedPlayers(itemToEdit);              
              
               itemToEdit.setAssignedPosition(player.getAssignedPosition());
              itemToEdit.setContract(player.getContract());
              itemToEdit.setSalary(player.getSalary()); 
                itemToEdit.setAssignedTeam(player.getAssignedTeam());
               assignedToATeam(itemToEdit,draft);                                  
                if(itemToEdit.getContract().equals("S2"))
                      draft.addPlayerToDraftedPlayers(itemToEdit);                                                       
              }
         
            }
           
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
         }        
    }        
       
    private void assignedToATeam(Player player,Draft draft){
            Team team=draft.findTeam(player.getAssignedTeam());
            if(team.get1BFull().equals(false)
               |team.getCIFull().equals(false)
               |team.get3BFull().equals(false)
               |team.get2BFull().equals(false)
               |team.getMIFull().equals(false)
               |team.getSSFull().equals(false)
               |team.getUFull().equals(false)
               |team.getCounterC()<2
               |team.getCounterOF()<5
               |team.getCounterP()<9){
             if(player.getAssignedPosition().equals("C"))
                 draft.assignedCToTeam(player.getAssignedTeam(),1);
            if(player.getAssignedPosition().equals("1B"))                                    
                 draft.assigned1BToTeam(player.getAssignedTeam(), true);                       
             if(player.getAssignedPosition().equals("CI"))                                
                 draft.assignedCIToTeam(player.getAssignedTeam(), true);            
             if(player.getAssignedPosition().equals("3B"))                              
                 draft.assigned3BToTeam(player.getAssignedTeam(), true);
             if(player.getAssignedPosition().equals("2B"))                              
                 draft.assigned2BToTeam(player.getAssignedTeam(), true);
             if(player.getAssignedPosition().equals("MI"))                              
                 draft.assignedMIToTeam(player.getAssignedTeam(), true);
             if(player.getAssignedPosition().equals("SS"))                              
                 draft.assignedSSToTeam(player.getAssignedTeam(), true);
             if(player.getAssignedPosition().equals("U"))                              
                 draft.assignedUToTeam(player.getAssignedTeam(), true);  
              if(player.getAssignedPosition().equals("OF"))
                 draft.assignedOFToTeam(player.getAssignedTeam(),1);
               if(player.getAssignedPosition().equals("P"))
                 draft.assignedPToTeam(player.getAssignedTeam(),1);
            team.addPlayer(player);               
          draft.getDataBase().remove(player);
            }
               
    }
    
    private void assignedToTaxiPlayersForATeam(Player player,Draft draft){
            Team team=draft.findTeam(player.getAssignedTeam());
          if(team.getTaxiPlayersNumber()<8){            
            team.addTaxiPlayer(player);
          draft.getDataBase().remove(player);
          }
            
               
    }
    private void deleteFromATeam(Player player,Draft draft){
           Team team=draft.findTeam(player.getAssignedTeam());
            if(player.getAssignedPosition().equals("C"))
                 draft.assignedCToTeam(player.getAssignedTeam(),-1);
             if(player.getAssignedPosition().equals("1B"))                                    
                 draft.assigned1BToTeam(player.getAssignedTeam(), false);                       
            if(player.getAssignedPosition().equals("CI"))                                
                 draft.assignedCIToTeam(player.getAssignedTeam(), false);            
             if(player.getAssignedPosition().equals("3B"))                              
                 draft.assigned3BToTeam(player.getAssignedTeam(), false);
             if(player.getAssignedPosition().equals("2B"))                              
                 draft.assigned2BToTeam(player.getAssignedTeam(), false);
             if(player.getAssignedPosition().equals("MI"))                              
                 draft.assignedMIToTeam(player.getAssignedTeam(), false);
             if(player.getAssignedPosition().equals("SS"))                              
                 draft.assignedSSToTeam(player.getAssignedTeam(), false);
             if(player.getAssignedPosition().equals("U"))                              
                 draft.assignedUToTeam(player.getAssignedTeam(), false);  
              if(player.getAssignedPosition().equals("OF"))
                 draft.assignedOFToTeam(player.getAssignedTeam(),-1);
               if(player.getAssignedPosition().equals("P"))
                 draft.assignedPToTeam(player.getAssignedTeam(),-1);
            team.removePlayer(player);
    }
    
           
}
