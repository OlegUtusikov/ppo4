package model;

import java.util.HashMap;
import java.util.Map;

public class User {
    public long id;
    public long money;
    public Map<Long, Long> stocks;

    public User(long id) {
        this.id = id;
        stocks = new HashMap<>();
    }

    public User(long id, long money) {
        this.id = id;
        this.money = money;
        stocks = new HashMap<>();
    }

    public void addStocks(long stockId, long cnt) {
        long curCnt = 0;
        if (stocks.containsKey(stockId)) {
            curCnt = stocks.get(stockId);
        }
        stocks.put(stockId, curCnt + cnt);
    }

    public boolean removeStocks(long stockId, long cnt) {
        if (stocks.containsKey(stockId)) {
            long curCnt = stocks.get(stockId);
            if (curCnt >= cnt) {
                stocks.put(stockId, curCnt - cnt);
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "User{id: " + id + ", money: " + money + "}";
    }
}
