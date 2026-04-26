package package1;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<String> products = new ArrayList<>();;
    private int totalPrice;

    public void addItem(String item, int price) {
        products.add(item);
        totalPrice+=price;
    }

    public int getTotalPrice(){
        return totalPrice;
    }
}
