package package1;

import static package1.User.randomNumber;

public class UserTest {
    public static void main(String[] args) {
        User user1 = new User("Alexey", "Mujitsliy", 30);
        //user1.setAge(randomNumber());
        //user1.setFirstName("Alexey");
        //ser1.setLastName("Mujitskiy");

        user1.greet();
    }
}
