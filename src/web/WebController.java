package web;

import controller.DatabaseController;
import controller.DrinkConverter;
import domain.Drink;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    private DrinkConverter converter = new DrinkConverter();

    @RequestMapping(value = "/drinks", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Drink> getDrinks() {
        DatabaseController databaseController = new DatabaseController();
        return databaseController.select("drink", null);
    }

    @RequestMapping(value = "/drinks", method = RequestMethod.POST)
    public
    @ResponseBody
    int postDrink(@RequestBody String body,
                  @RequestParam(value = "breweryName", required = false) String breweryName) {
        System.out.println(body);
        DatabaseController databaseController = new DatabaseController();
        String description = converter.convertBodyToDescription(body);
        Drink drink = converter.convertBodyToDrink(body);
        return databaseController.addDrink(drink, description, breweryName);
    }
}