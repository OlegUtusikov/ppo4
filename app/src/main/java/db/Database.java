package db;

import model.Stock;
import model.User;
import utils.Print;

import java.beans.JavaBean;
import java.util.HashMap;
import java.util.Map;

@JavaBean
public class Database {
    private final Print printer;
    private final Map<Long, User> users;
    private final Map<Long, Stock> stocks;

    public Database() {
        users = new HashMap<>();
        stocks = new HashMap<>();
        printer = new Print();
    }

    private boolean checkUser(long userId) {
        boolean res = users.containsKey(userId);
        if (!res) {
            printer.printError("User is absent. UserId = " + userId);
        }
        return res;
    }

    private boolean checkStock(long stockId) {
        boolean res = stocks.containsKey(stockId);
        if (!res) {
            printer.printError("Stock is absent. StockId = " + stockId);
        }
        return res;
    }


    public User getUser(long userId) {
        return users.get(userId);
    }

    public Stock getStock(long stockId) {
        return stocks.get(stockId);
    }

    private boolean checkUserStock(long userId, long stockId, String msg) {
        boolean res = checkUser(userId) && checkStock(stockId);
        if (!res) {
            printer.printError(msg);
        }
        return res;
    }

    public boolean createUser(long userId, long money) {
        if (users.containsKey(userId)) {
            printer.printError("User created yet!!!");
            return false;
        }
        if (money < 0) {
            printer.printError("Bad money value. Negative.");
            return false;
        }
        users.put(userId, new User(userId, money));
        printer.printLog("Created user = " + userId);
        return true;
    }

    public boolean addMoney(long userId, long money) {
        if (checkUser(userId)) {
            User curUser = users.get(userId);
            curUser.money += money;
            users.put(userId, curUser);
        }
        return true;
    }

    public boolean withdrawUserMoney(long userId, long money) {
        if (checkUser(userId)) {
            User curUser = users.get(userId);
            if (curUser.money >= money) {
                curUser.money -= money;
                users.put(userId, curUser);
                printer.printLog("Withdraw users money");
                return true;
            }
        }
        return false;
    }

    public boolean createStock(long stockId, long price, long cnt) {
        if (stocks.containsKey(stockId)) {
            printer.printError("Stock created yet!!!");
            return false;
        }
        if (price < 0) {
            printer.printError("Bad price value");
            return false;
        }
        if (cnt < 0) {
            printer.printError("Bad cnt value");
            return false;
        }
        stocks.put(stockId, new Stock(stockId, price, cnt));
        printer.printLog("Created stocks = " + stockId);
        return true;
    }

    public boolean addStocks(long stockId, long cnt) {
        if (cnt < 0) {
            printer.printError("Bad value negative cnt");
            return false;
        }
        if (checkStock(stockId)) {
            Stock curStock = stocks.get(stockId);
            curStock.cnt += cnt;
            stocks.put(stockId, curStock);
            printer.printLog("Stocks added");
            return true;
        }
        return false;
    }

    public boolean changeStockPrice(long stockId, long newPrice) {
        if (newPrice < 0) {
            printer.printError("Bad value new price");
            return true;
        }
        if (checkStock(stockId)) {
            Stock curStock = stocks.get(stockId);
            curStock.price = newPrice;
            stocks.put(stockId, curStock);
            printer.printLog("Price changed");
            return true;
        }
        return false;
    }

    public boolean buyStocksByUser(long userId, long stockId, long cnt) {
        if (checkUserStock(userId, stockId, "buy")) {
            User curUser = users.get(userId);
            Stock curStock = stocks.get(stockId);
            if (curUser.money >= curStock.price * cnt) {
                curUser.addStocks(stockId, cnt);
                curUser.money -= curStock.price * cnt;
                curStock.cnt -= cnt;
                stocks.put(stockId, curStock);
                users.put(userId, curUser);
                printer.printLog("Bought stocks by user. " + "UserId = " + userId + ", StockId = " + stockId + ", cnt = " + cnt);
                return true;
            }
        }
        return false;
    }

    public boolean sellUsersStock(long userId, long stockId, long cnt) {
        if (checkUserStock(userId, stockId, "sell")) {
            User curUser = users.get(userId);
            Stock curStock = stocks.get(stockId);
            if (curUser.removeStocks(stockId, cnt)) {
                curUser.money += curStock.price * cnt;
                curStock.cnt += cnt;
                stocks.put(stockId, curStock);
                users.put(userId, curUser);
                printer.printLog("Sold stocks by user. " + "UserId = " + userId + ", StockId = " + stockId + ", cnt = " + cnt);
                return true;
            }
        }
        return false;
    }

    public long getUserBalance(long userId) {
        if (checkUser(userId)) {
            User curUser = users.get(userId);
            long res = curUser.money;
            for (Map.Entry<Long, Long> entry : curUser.stocks.entrySet()) {
                if (stocks.containsKey(entry.getKey())) {
                    res += entry.getValue() * stocks.get(entry.getKey()).price;
                }
            }
            return res;
        }
        return 0L;
    }
}
