import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBTester {
//    private DataLoader dl;
    private DataBaseConnector db;

    public DBTester(){
//        dl = new DataLoader();
        db = new DataBaseConnector();
        Player.setNext_id(db.countPlayers()+1);
    }

    //test add player by count
    @Test
    void testSavePlayer(){
        Player test = new Player();
        test.setName("test");
        test.setClub(new Club("test", 000));
        test.setPosition("test");
        test.setAge(100);
        test.setMarket_value(100.5);
        test.setNationality("test");
        int expectedResult = db.countPlayers()+1;
        db.savePlayer(test);
        int acutalResult = db.countPlayers();
        db.removePlayer(db.getPlayer("test"));
        db.removeClub(db.findClub("test"));
        assertEquals(expectedResult,acutalResult);

    }

    @Test
    void testSaveDuplicate(){
        Player test = new Player();
        test.setName("test1");
        test.setClub(new Club("test1", 111));
        test.setPosition("test");
        test.setAge(101);
        test.setMarket_value(10.5);
        test.setNationality("test1");
        db.savePlayer(test);
        boolean result = db.savePlayer(test);
        db.removePlayer(db.getPlayer("test1"));
        db.removeClub(db.findClub("test1"));
        assertEquals(false,result);
    }

    //test added player by data integrity
    @Test
    void testPrintPlayer(){
        Player test = new Player();
        test.setName("test6");
        test.setClub(new Club("test6", 666));
        test.setPosition("test6");
        test.setAge(106);
        test.setMarket_value(6.5);
        test.setNationality("test6");
        db.savePlayer(test);
            String expectedResult = "Player: test6"
                    + ", club_id: 666"
                    + ", position: test6"
                    + ", age: 106"
                    + ", market value: 6.5";
            String actualResult = db.printPlayerByName("test6");
            db.removePlayer(test);
        db.removeClub(db.findClub("test6"));
            assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetPlayerName(){
        Player test = new Player();
        test.setName("test2");
        test.setClub(new Club("test2", 222));
        test.setPosition("test2");
        test.setAge(102);
        test.setMarket_value(102.5);
        test.setNationality("test2");
        db.savePlayer(test);
        int expectedResult = test.getPlayer_id();
        int actualResult = db.getPlayer("test2").getPlayer_id();
        db.removePlayer(db.getPlayer("test2"));
        db.removeClub(db.findClub("test2"));
        assertEquals(expectedResult,actualResult);
    }
    //test remove player by count
    @Test
    void testRemovePlayer(){
        Player test = new Player();
        test.setName("test5");
        test.setClub(new Club("test5", 555));
        test.setPosition("test5");
        test.setAge(103);
        test.setMarket_value(103.5);
        test.setNationality("test5");
        db.savePlayer(test);
        int expectedResult = db.countPlayers()-1;
        Player p = db.getPlayer("test5");
        db.removePlayer(p);
        db.removeClub(db.findClub("test5"));
        assertEquals(expectedResult,db.countPlayers());
    }

    //test remove player by data integrity
    @Test
     void testRemoveCorrect(){
        Player test = new Player();
        test.setName("test3");
        test.setClub(new Club("test3", 333));
        test.setPosition("test3");
        test.setAge(103);
        test.setMarket_value(103.5);
        test.setNationality("test3");
        db.savePlayer(test);
        db.removeClub(db.findClub("test3"));
        String expectedResult = "Player: " + test.getName() + " removed";
        assertEquals(expectedResult,db.removePlayer(test));
    }

    //test remove player by data integrity
    @Test
    void testRemoveIncorrect(){
        Player test = new Player();
        test.setName("no test");
        test.setClub(new Club("test4", 444));
        String expectedResult = "seems nothing was removed";
        assertEquals(expectedResult,db.removePlayer(test));
    }

    @Test
    void addClub(){
        Club test = new Club ("TestClub", 1111);
        int expectedResult = db.countClubs()+1;
        db.saveClub(test);
        int actualResult = db.countClubs();
        db.removeClub(test);
        assertEquals(expectedResult,actualResult);
    }

    @Test
    void removeClub(){
        Club test = new Club ("TestClub1", 1111);
        String expectedResult = "Club: TestClub1 removed";
        db.saveClub(test);
        assertEquals(expectedResult,db.removeClub(test));
    }
    //test UI trying to load correct file

    //test UI trying to load other files

    //test SQL injection

}
