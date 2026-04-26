package package1;

public class User {
    private String firstName;
    private String lastName;
    private Integer age;

    public User(String firstName, String lastName, Integer age) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.age=age;
    }

    public static Integer randomNumber() {
        return (int) (Math.random()*(65-18+1))+18;
    }

    public void greet() {
        System.out.println("My name is "+getFirstName()+" "+getLastName()+". I'm "+getAge()+" years old.");

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName.length() > 2 && firstName.length() < 50) {
            this.firstName = firstName;
        } else {
            System.out.println("Your name isn't valid");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 18 && age <= 65) {
            this.age = age;
        } else {
            System.out.println("Your age is not suitable for our service");
        }
    }
}
