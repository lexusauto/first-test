package package1;

public class CartTest {

    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.addItem("Яйца", 140);
        cart.addItem("Курица", 240);
        
        System.out.println(cart.getTotalPrice());
    }
}
