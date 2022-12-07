import java.util.*;
import java.io.*;
public class Store {
    public static void main (String[] args) {
        Item[] items = new Item[]{
                new Item("Coca-Cola (single can)",1.49,10),
                new Item("Lays Original Chips (family size)",3.29,10),
                new Item("Banana Bunch (3 lbs)",2.49,10),
                new Item("Captain Crunch (large size)",4.19,10),
                new Item("Whole Wheat Bread",3.09,10),
                new Item("Unsalted Butter (16 oz)",4.49,10),
                new Item("Oven Roasted Turkey Breast (1 lb)",7.50,10),
                new Item("Fat Free Milk (1 gal)",2.99,10)
            };
        Item soda = items[0];
        Item chips = items[1];
        Item fruit = items[2];
        Item cereal = items[3];
        Item bread = items[4];
        Item butter = items[5];
        Item meat = items[6];
        Item milk = items[7];

        ArrayList<Item> cart = new ArrayList<Item>();

        boolean running = true;
        while(running) {
            Scanner input = new Scanner(System.in);
            System.out.println("Welcome to our store!");
            System.out.println("Would you like to: ");
            System.out.println("Add an item to your cart [press '1']\nView the items in your cart [press '2']\nView all items available for purchase [press '3']\nCheck out [press '4']\nExit [press '5']");
            int choice = 0;
            try{
                choice = input.nextInt();
                if(choice < 1 || choice > 5) {
                    throw new Exception("Invalid input, value outside acceptable range.");
                }
            }
            catch(InputMismatchException e) {
                System.out.println("Invalid value entered.");
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
            finally {
                input.nextLine();
            }
            if(choice == 1) {
                addItem(input, items, cart);
            }
            else if (choice == 2) {
                viewCart(input, cart);
            }
            else if (choice == 3) {
                viewItems(items);
            }
            else if (choice == 4) {
                System.out.println(4);
            }
            else if (choice == 5) {
                System.out.println("Goodbye! Come Again!");
                input.close();
                System.exit(0);
            }
        }

    }

    public static void addItem(Scanner input, Item[] itemArr, ArrayList cartItems) {
        try {
            System.out.println("Please enter the item you would like to add: ");
            String addedItem = input.nextLine();
            boolean yes = false;
            for(int i = 0; i < itemArr.length;i++){
                if(addedItem.equalsIgnoreCase(itemArr[i].getName())){
                    cartItems.add(itemArr[i]);
                    System.out.println("Item added to cart.");
                    itemArr[i].setStock(itemArr[i].getStock()-1);
                    yes = true;
                }
            }
            if (yes == false) {
                throw new Exception("The item you entered is not available. You can ingore its case but make sure you type the item correctly.");
            }
        }
        catch(InputMismatchException e) {
            System.out.println("The item you entered is not available or invalid.");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void viewCart(Scanner input, ArrayList cartItems) {
        int choice = 0;
        try{
            System.out.println("Would you like you cart sorted in descending order? (press '1' for yes and '2' for no)");
            choice = input.nextInt();
            if(choice < 1 || choice > 2) {
                throw new Exception("Please enter either '1' or '2'");
            }
        }
        catch(InputMismatchException e) {
            System.out.println("Invalid input.");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        if(choice == 2) {
            System.out.println("Your Cart:");
            for(int i = 0; i < cartItems.size(); i++) {
                System.out.println(cartItems.get(i));
                System.out.println("");
            }
            System.out.println("");
        }
        else if (choice == 1) {
            Collections.sort(cartItems, Comparator.comparing(Item::getPrice));
            Collections.reverse(cartItems);            
            System.out.println("Your Cart:");
            for(int i = 0; i < cartItems.size(); i++) {
                System.out.println(cartItems.get(i));
                System.out.println("");
            }
            System.out.println("");
        }
    }

    public static void viewItems(Item[] itemArr) {
        for(int i = 0; i < itemArr.length; i++) {
            System.out.println(itemArr[i].toString());
            System.out.println("");
        }
    }

    public static void checkout(Scanner input, Item[] itemArr) {

    }
}
