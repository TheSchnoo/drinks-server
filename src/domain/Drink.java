package domain;

public class Drink {

    private int drinkId;
    private String name;
    private int breweryId;
    private int ibu;
    private float abv;
    private int typeId;

    public Drink(int drinkId, String name, int breweryId, int ibu, float abv, int typeId) {
        this.drinkId = drinkId;
        this.name = name;
        this.breweryId = breweryId;
        this.ibu = ibu;
        this.abv = abv;
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(int breweryId) {
        this.breweryId = breweryId;
    }

    public int getIbu() {
        return ibu;
    }

    public void setIbu(int ibu) {
        this.ibu = ibu;
    }

    public float getAbv() {
        return abv;
    }

    public void setAbv(float abv) {
        this.abv = abv;
    }
}
