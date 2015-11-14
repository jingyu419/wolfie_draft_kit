package wdk;

/**
 * These are properties that are to be loaded from properties.xml. They
 * will provide custom labels and other UI details for Wolfie Draft Kit
 * application. The reason for doing this is to swap out UI text and icons
 * easily without having to touch our code. It also allows for language
 * independence.
 * 
 * @author Jing Yu
 */
public enum WDK_PropertyType {
        // LOADED FROM properties.xml
        PROP_APP_TITLE,
        
        // APPLICATION ICONS
        NEW_DRAFT_ICON,
        LOAD_DRAFT_ICON,
        SAVE_DRAFT_ICON,
        EXPORT_DRAFT_ICON,
        EXIT_ICON,
        ADD_ICON,
        MINUS_ICON,
        EDIT_ICON,
        FANTASY_TEAMS_ICON,
        AVAILABLE_PLAYERS_ICON,
        FANTASY_STANDINGS_ICON,
        DRAFT_ICON,
        MLB_TEAMS_ICON,
        
        // APPLICATION TOOLTIPS FOR BUTTONS
        NEW_DRAFT_TOOLTIP,
        LOAD_DRAFT_TOOLTIP,
        SAVE_DRAFT_TOOLTIP,
        EXPORT_DRAFT_TOOLTIP,
        DELETE_TOOLTIP,
        EXIT_TOOLTIP,
        FANTASY_TEAMS_TOOLTIP,
        AVAILABLE_PLAYERS_TOOLTIP,
        FANTASY_STANDINGS_TOOLTIP,
        DRAFT_TOOLTIP,
        MLB_TEAMS_TOOLTIP,
        ADD_PLAYER_TOOLTIP,
        REMOVE_PLAYER_TOOLTIP,

        // SCREENS HEADINGS
        FANTASY_TEAMS_HEADING_LABEL,
        AVAILABLE_PLAYERS_HEADING_LABEL,
        FANTASY_STANDINGS_HEADING_LABEL,
        DRAFT_HEADING_LABEL,
        MLB_TEAMS_HEADING_LABEL,
        
        // ERROR DIALOG MESSAGES       
        
        // AND VERIFICATION MESSAGES
        NEW_DRAFT_CREATED_MESSAGE,
        DRAFT_LOADED_MESSAGE,
        DRAFT_SAVED_MESSAGE,
        DRAFT_EXPORTED_MESSAGE,
        SAVE_UNSAVED_WORK_MESSAGE,
        REMOVE_ITEM_MESSAGE,
        
        
        //AVAILABLE PLAYERS SCREEN
        PLAYERS_SEARCH_LABEL,
        
        //Fantasy team screen
        DRAFT_NAME_LABEL,
        SELECT_TEAM_LABEL,
        ADD_TEAM_TOOLTIP,
        REMOVE_TEAM_TOOLTIP,
        EDIT_TEAM_TOOLTIP,
        STARTING_LINEUP_LABEL,
        TAXI_SQUAD_LABEL,   
        FAIL_TO_ADD_PLAYER_MESSAGE,
        FAIL_TO_ASSIGN_PLAYER_MESSAGE,
        PLAYER_STAY_IN_THE_FREE_AGENT_MESSAGE,
        FAIL_TO_ASSIGN_TEAM_MESSAGE_SAME_TEAM_NAME,
        FAIL_TO_ASSIGN_TEAM_MESSAGE_SAME_OWNER_NAME,
         FAIL_TO_EDIT_TEAM_MESSAGE_SAME_TEAM_NAME,
        FAIL_TO_EDIT_TEAM_MESSAGE_SAME_OWNER_NAME,
        FAIL_TO_ADD_PLAYER_MESSAGE_SAME_NAME,
        
        //MLB TEAM screen
        MLB_TEAM_SELECTION_LABEL,
        
        //draft screen
          DRAFT_ONE_PLAYER_ICON,
          START_DRAFT_ICON,
          PAUSE_DRAFT_ICON,
         DRAFT_ONE_PLAYER_TOOLTIP,
         START_DRAFT_TOOLTIP,
         PAUSE_DRAFT_TOOLTIP,
        //useless stuff,just to take some spaces
        NONE
}
