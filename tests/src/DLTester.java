import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//NOTE: This will not work on someone else's machine as it won't know where to find your xml file.
class DLTester {
        private DataLoader dl;


    DLTester() {
        dl = new DataLoader();
    }

    @Test
    void deletedLoaded(){
        DataBaseConnector db = new DataBaseConnector();
        db.clearTables();
        int expectedResult = 0;
        assertEquals(expectedResult,db.countPlayers());
    }


    @Test
    void testLoadAll(){
        boolean actualResult = dl.loadFile(new File("C:\\Users\\nobesmarg\\Documents\\SWEN502\\Testing\\premierLeaguePlayerNames.xml"));
        assertTrue(actualResult);
    }

}
