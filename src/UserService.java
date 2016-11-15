import controller.DatabaseController;
import domain.Drink;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Apathetic spawn of Wesb on 11/14/16.
 */
public class UserService {

    private DatabaseController databaseController;

    public UserService() {
        databaseController = new DatabaseController();
    }

    public void setDatabaseController(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public List<String> getDescriptorsFromDescription(String description) {

        return Arrays.asList(description.split(",\\s*"));

//        System.out.println(descriptors.get(1).length());
    }

    public Map<String,Integer> getPreferenceMap() {
        return databaseController.selectFromPreferences();
    }

    public int addDrinkPreference(Drink drink) {
        List<String> tastingList = databaseController.getTastingDescriptions(drink.getDrinkId());
        Map<String,Integer> preferences = getPreferenceMap();
        tastingList.forEach(x -> mergePreference(x, preferences));
        return databaseController.updatePreferences(preferences);
    }

    private void mergePreference(String taste, Map<String,Integer> preference) {
        int score = 1;
        if (preference.containsKey(taste)){
            score = preference.get(taste) + 1;
        }
        preference.put(taste, score);
    }
}
