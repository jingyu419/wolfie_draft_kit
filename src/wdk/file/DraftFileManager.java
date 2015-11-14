package wdk.file;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import wdk.data.Draft;
import wdk.data.Player;

/**
 *
 * @author jingyu
 */
public interface DraftFileManager {

    public void                       saveDraft(Draft draftToSave) throws IOException;

    public void                       loadDraft(Draft draftToLoad, String draftPath) throws IOException;

   // public void                       savePlayers(List<Object> players, String filePath) throws IOException;

    public ArrayList<Player>          loadHitters(String filePath) throws IOException;
    
    public ArrayList<Player>          loadPitchers(String filePath) throws IOException;
}
