import domain.Drink;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Apathetic spawn of Wesb on 11/14/16.
 */
public class UserServiceTest {

    private UserService userService;

    private Drink drink;

    @Before
    public void setUp() {
        userService = new UserService();
        drink = new Drink(0, "new drink", 2, 100, 6, 2);
    }

    @Test
    public void getDescriptors() {
        assertEquals(1, userService.getDescriptorsFromDescription("moki").size());
        assertEquals(4, userService.getDescriptorsFromDescription("moki").get(0).length());
    }

    @Test
    public void addDrinkPreference() {
        assertEquals(1, userService.addDrinkPreference(drink));
    }
}