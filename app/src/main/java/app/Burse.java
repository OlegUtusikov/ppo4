package app;

import db.Database;
import model.Stock;
import model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class Burse {
    private final Database database;

    public Burse(Database database) {
        this.database = database;
    }

    @GetMapping("stock")
    public String getStock(@RequestParam Long id) {
        Stock curStock = database.getStock(id);
        return curStock == null ? "__null__" : curStock.toString();
    }

    @GetMapping("user")
    public String getUser(@RequestParam Long id) {
        User curUser = database.getUser(id);
        return curUser == null ? "__null__" : curUser.toString();
    }

    @GetMapping("ping")
    public String check() {
        return "ping-pong";
    }

    @PostMapping("createUser")
    public boolean createUser(@RequestParam Long id, @RequestParam Long money) {
        return database.createUser(id, money);
    }

    @PostMapping("createStock")
    public boolean createStock(@RequestParam Long id, @RequestParam Long price, @RequestParam Long cnt) {
        return database.createStock(id, price, cnt);
    }


    @PostMapping("buyStock")
    public boolean buyStock(@RequestParam Long userId, @RequestParam Long stockId, @RequestParam Long cnt) {
        return database.buyStocksByUser(userId, stockId, cnt);
    }

    @PostMapping("sellStock")
    public boolean sellStock(@RequestParam Long userId, @RequestParam Long stockId, @RequestParam Long cnt) {
        return database.sellUsersStock(userId, stockId, cnt);
    }

    @PostMapping("addUserMoney")
    public boolean addUserMoney(@RequestParam Long userId, @RequestParam Long money) {
        return database.addMoney(userId, money);
    }

    @PostMapping("withdrawUserMoney")
    public boolean withdrawUserMoney(@RequestParam Long userId, @RequestParam Long money) {
        return database.withdrawUserMoney(userId, money);
    }


    @PostMapping("addStocks")
    public boolean addStocks(@RequestParam Long stockId, @RequestParam Long cnt) {
        return database.addStocks(stockId, cnt);
    }

    @PostMapping("changePrice")
    public boolean changePrice(@RequestParam Long id, @RequestParam Long price){
        return database.changeStockPrice(id, price);
    }

    @GetMapping("balance")
    public long getUserBalance(@RequestParam Long userId) {
        return database.getUserBalance(userId);
    }
}
