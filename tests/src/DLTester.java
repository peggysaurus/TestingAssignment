import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DLTester {
        private DataLoader dl;


    public DLTester() {
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
        boolean expectedResult = true;
        boolean actualResult = dl.loadFile(new File("C:\\Users\\nobesmarg\\Documents\\SWEN502\\Testing\\premierLeaguePlayerNames.xml"));
        assertEquals(expectedResult, actualResult);
    }

//    @ParameterizedTest (name ="{index}: String Field {0} with value {1}")
//    @MethodSource("getStringValues")
//    void testSetValues(String field, String value){
//
//    }
//
//    @ParameterizedTest (name ="{index}: Int Field {0} with value {1}")
//    @MethodSource("getIntValues")
//    void testSetValues(String field, int value){
//
//    }
//
//    static Stream<Arguments> getStringValues() {
//        return Stream.of(
//                Arguments.of("name", "Unknown"),
//                Arguments.of("name", "Test Words"),
//                Arguments.of("club", "Test String"),
//                Arguments.of("position", "Test String"),
//                Arguments.of("nationality", "Test String"),
//                Arguments.of("fpl_sel", "Test String")
//        );
//    }
//
//    static Stream<Arguments> getIntValues() {
//        return Stream.of(
//                Arguments.of("name", "Unknown"),
//                Arguments.of("name", "Test Words"),
//                Arguments.of("club", "Test String"),
//                Arguments.of("position", "Test String"),
//                Arguments.of("nationality", "Test String"),
//                Arguments.of("fpl_sel", "Test String")
//        );
//    }
}
