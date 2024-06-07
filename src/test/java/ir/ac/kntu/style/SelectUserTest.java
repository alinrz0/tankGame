package ir.ac.kntu.style;

import ir.ac.kntu.SelectUser;
import ir.ac.kntu.User;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class SelectUserTest {
    
    @Test
    public void testGetUsers() throws IOException, ClassNotFoundException {
        SelectUser selectUser = new SelectUser();
        ArrayList<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User("ali", 0, 0));
        expectedUsers.add(new User("a", 0, 0));
        expectedUsers.add(new User("b", 0, 0));
        ArrayList<User> actualUsers = selectUser.getUsers();
        assertEquals(expectedUsers.size(), actualUsers.size());
        for (int i = 0; i < expectedUsers.size(); i++) {
            assertEquals(expectedUsers.get(i).getName(), actualUsers.get(i).getName());
        }
    }
    
}