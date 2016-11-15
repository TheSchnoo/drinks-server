package controller;

import domain.Brewery;
import domain.Drink;
import org.json.JSONObject;

import java.net.URI;
import java.sql.*;
import java.util.*;

/**
 * Apathetic spawn of Wesb on 11/11/16.
 */
public class DatabaseController {

    private Connection connection;
    private DrinkConverter converter;

    public DatabaseController() {
        this.converter = new DrinkConverter();
    }

    private void openConnection() {
        System.out.println(System.getenv("CLEARDB_DATABASE_URL"));
        try {
            URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (Exception e) {
            System.out.println("Issue");
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Drink> select(String table, Map<String,String> filters) {
        String query;
        if(filters == null) {
            query = "SELECT * FROM " + table;
        } else {
            query = "SELECT * FROM " + table + " WHERE ";
        }
        int index = 0;
        assert filters != null;
        for(Map.Entry<String,String> filter : filters.entrySet()){
            if(index == 0) {
                query = query + filter.getKey() + "= '" + filter.getValue() + "'";
            } else {
                query = query + " AND " + filter.getKey() + "= '" + filter.getValue() + "'";
            }
            index++;
        }
        return executeQuery(query);
    }

    public List<Brewery> getBrewery(String breweryName) {
        List<Brewery> breweries = new ArrayList<>();
        String query = "SELECT * FROM brewery WHERE breweryName=" + breweryName;
        return executeBreweryQuery(query);
    }

    public int addDrink(Drink drink, String description, String breweryName) {
        int rowsAffected = 0;
        if(breweryName != null) {
            Brewery brewery = getBrewery(breweryName).get(0);
            drink.setBreweryId(brewery.getBreweryId());
        }
        String query = String.format("INSERT INTO drink VALUES (default, '%s', %2d, %2d, %f)",
                drink.getName(), drink.getBreweryId(), drink.getIbu(), drink.getAbv());
        rowsAffected = executeUpdate(query);
        if(description != null && !Objects.equals(description, "")) {
            int id = getDrink(drink.getName(), drink.getBreweryId()).getDrinkId();
            addTasting(id, description);
        }
        return rowsAffected;
    }

    private Drink getDrink(String name, int breweryId) {
        String query = String.format("SELECT * FROM drink WHERE drinkName='%s' AND breweryId=%2d", name, breweryId);
        return executeQuery(query).get(0);
    }

    private void addTasting(int drinkId, String description) {
        String query = String.format("INSERT INTO tasting VALUES (default, %2d, '%s')", drinkId, description);
        executeUpdate(query);
    }

    private List<Drink> executeQuery(String query) {
        openConnection();
        List<Drink> queryResults = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            queryResults = converter.convertResultSetToDrinkList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return queryResults;
    }

    private int executeUpdate(String query) {
        openConnection();
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return rowsAffected;
    }

    private List<Brewery> executeBreweryQuery(String query) {
        openConnection();
        List<Brewery> queryResults = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            queryResults = converter.convertResultSetToBreweryList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return queryResults;
    }

    public Map<String,Integer> selectFromPreferences() {
        openConnection();
        Map<String,Integer> preferences = new HashMap<>();
        String query = "SELECT * FROM preference";
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while(resultSet.next()) {
                preferences.put(resultSet.getString("tag"), resultSet.getInt("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return preferences;
    }


    public List<String> getTastingDescriptions(int drinkId) {
        String query = "SELECT * FROM tasting";
        if(drinkId == -1 || drinkId > 0) {
            query = query + "where drinkId=" + drinkId;
        }
        openConnection();
        List<String> descriptions = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while(resultSet.next()) {
                for(String description : Arrays.asList(resultSet.getString("tag").split(",\\s*"))){
                    descriptions.add(description);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return descriptions;
    }

    public int updatePreferences(Map<String, Integer> preferences) {
        List<String> queries = new ArrayList<>();
        for(Map.Entry<String,Integer> preference : preferences.entrySet()) {
            String query = "";
            if (preference.getValue() > 1) {
                query = String.format("UPDATE preference SET score=%2d WHERE tag='%s'",
                        preference.getValue(), preference.getKey());
            } else {
                query = String.format("INSERT INTO preference VALUES('%s',%2d)",
                        preference.getKey(), preference.getValue());
            }
            if (!query.equals("")){
                queries.add(query);
            }
        }
        openConnection();
        int status = 0;
        try {
            for(String query : queries) {
                status = connection.prepareStatement(query).executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return status;
    }
}
