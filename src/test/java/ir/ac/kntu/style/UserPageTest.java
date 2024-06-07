package ir.ac.kntu.style;

import ir.ac.kntu.Level;
import ir.ac.kntu.User;
import ir.ac.kntu.UserPage;
import javafx.stage.Stage;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UserPageTest {

    @Test
    public void testGetLevels() {
        UserPage userPage = new UserPage(new User("test", 0, 0));
        ArrayList<String> expectedLevels = new ArrayList<>();
        for (Level level : Level.values()) {
            expectedLevels.add(String.valueOf(level));
        }
        ArrayList<String> actualLevels = userPage.getLevels();
        assertEquals(expectedLevels, actualLevels);
    }

    @Test
    public void testSelectLevel() throws InterruptedException {
        UserPage userPage = new UserPage(new User("test", 0, 0));
        userPage.setLevel(Level.LEVEL_1);
        assertEquals(Level.LEVEL_1, userPage.getLevel());
    }

}