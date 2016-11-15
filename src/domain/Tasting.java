package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Apathetic spawn of Wesb on 11/13/16.
 */
public class Tasting {

    private int tastingId;
    private int drinkId;
    private List<String> tags;

    public Tasting(int tastingId, int drinkId, List<String> tags) {
        this.tastingId = tastingId;
        this.drinkId = drinkId;
        this.tags = tags;
    }

    public Tasting(int tastingId, int drinkId) {
        this.tastingId = tastingId;
        this.drinkId = drinkId;
        this.tags = new ArrayList<>();
    }

    public int getTastingId() {
        return tastingId;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public void removeTag(String tag) {
        this.tags.remove(tag);
    }
}
