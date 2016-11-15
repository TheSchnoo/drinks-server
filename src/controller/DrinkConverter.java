package controller;

import domain.Brewery;
import domain.Drink;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Apathetic spawn of Wesb on 11/13/16.
 */
public class DrinkConverter {

    public List<Drink> convertResultSetToDrinkList(ResultSet resultSet) {
        List<Drink> drinkList = new ArrayList<>();
        try {
            while(resultSet.next()){
                int drinkId = resultSet.getInt("drinkId");
                String drinkName = resultSet.getString("drinkName");
                int breweryId = resultSet.getInt("breweryId");
                int ibu = resultSet.getInt("ibu");
                float abv = resultSet.getFloat("abv");

                Drink drink = new Drink(drinkId, drinkName, breweryId, ibu, abv, -1);
                drinkList.add(drink);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drinkList;
    }

    public List<Brewery> convertResultSetToBreweryList(ResultSet resultSet) {
        List<Brewery> drinkList = new ArrayList<>();
        try {
            while(resultSet.next()){
                int breweryId = resultSet.getInt("breweryId");
                String breweryName = resultSet.getString("breweryName");
                String city = resultSet.getString("city");

                Brewery brewery = new Brewery(breweryId, breweryName, city);
                drinkList.add(brewery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drinkList;
    }

    public Drink convertBodyToDrink(String body) {
        Drink drink = null;
        try {
            JSONObject bodyJSON = new JSONObject(body);

            String drinkName = bodyJSON.getString("drinkName");
            int breweryId = bodyJSON.getInt("breweryId");
            int ibu = bodyJSON.getInt("ibu");
            float abv = Float.parseFloat(bodyJSON.getString("abv"));

            drink = new Drink(0, drinkName, breweryId, ibu, abv, -1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return drink;
    }

    public String convertBodyToDescription(String body) {
        String description = "";
        try {
            JSONObject bodyJSON = new JSONObject(body);
            description = bodyJSON.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return description;
    }
}
