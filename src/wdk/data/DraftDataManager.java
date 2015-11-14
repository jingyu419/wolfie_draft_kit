
package wdk.data;

import java.util.ArrayList;
import wdk.file.DraftFileManager;

/**
 *This class is used to manage drafts
 * @author jingyu
 */
public class DraftDataManager {
    //This is the draft being edited
    Draft draft;
    
    //This is the UI, which must be updated whenever
    //our model's data changes
    DraftDataView view;
    
    //This help me to load things for my drafts
    DraftFileManager fileManager;
    
    //default value
    static String DEFAULT_DRAFT_NAME="<ENTER DRAFT NAME>";
    static String DEFAULT_CURRENT_TEAM=" ";
    ArrayList<Player> hittersPlayers;
    ArrayList<Player> pitchersPlayers;
    /**
     *This is the default constructor
     * @param initView
     * @param hitters
     * @param pitchers
     */
    public DraftDataManager (DraftDataView initView, ArrayList<Player> hitters, ArrayList<Player> pitchers){
        view=initView;
        draft=new Draft();
        draft.addPlayers(hitters);
        draft.addPlayers(pitchers);
        hittersPlayers=hitters;
        pitchersPlayers=pitchers;
    }
    
    /**
     *This method is used to get the draft.
     * @return Draft
     */
    public Draft getDraft(){
        return draft;
    }
    
    /**
     *This is used to get the JSON file manager
     * @return DraftFileManager
     */
    public DraftFileManager getFileManager(){
        return fileManager;
    }
    
    /**
     *This method reset the information inside the draft.
     */
    public void reset(){    
        draft.setDraftName(DEFAULT_DRAFT_NAME);
       draft.setCurrentTeam(DEFAULT_CURRENT_TEAM);
       draft.clearDatabase();
        draft.addPlayers(hittersPlayers);
        draft.addPlayers(pitchersPlayers);
        draft.clearFantasyTeams();
        draft.clearDraftedPlayers();
        view.reloadDraft(getDraft());
    }
}
