import java.util.*;
import java.io.*;
import java.time.*;
import java.text.*;
public class Store {
    public static void main (String[] args) {
        Item[] items = new Item[]{
                new Item("Coca-Cola Can",1.49,10),
                new Item("Lays Original Chips",3.29,10),
                new Item("Banana Bunch",2.49,10),
                new Item("Captain Crunch",4.19,10),
                new Item("Whole Wheat Bread",3.09,10),
                new Item("Unsalted Butter",4.49,10),
                new Item("Oven Roasted Turkey Breast",7.50,10),
                new Item("Fat Free Milk",2.99,10)
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

        DecimalFormat df = new DecimalFormat("#.00");
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyy HH:mm:ss");
        Date date = new Date();
        Duration timeUntilClose = Duration.between(LocalTime.now(), LocalDate.now().atTime(LocalTime.of(22,00,00)));
        System.out.printf("Current date and time: %s%n",formatter.format(date)); 
        if(timeUntilClose.toSecondsPart() < 0 ) {
            System.out.println("Sorry, our store is closed.\nWe will reopen at 8 AM.");
            System.exit(0);
        }
        else {
            System.out.format("Closing time is 10 o'clock PM. You have %d hours, %d minutes, and %d seconds to shop.%n",timeUntilClose.toHours(),timeUntilClose.toMinutesPart(),timeUntilClose.toSecondsPart());
        }
        boolean running = true;
        System.out.println("Welcome to ACM (all you can muster) Foods!");
        while(running) {
            Scanner input = new Scanner(System.in);

            System.out.println("Would you like to: ");
            System.out.println("Add an item to your cart [press '1']\nView the items in your cart [press '2']\nRemove an item from your cart [press '3']\nView all items available for purchase [press '4']\nCheck out [press '5']\nExit [press '6']");
            int choice = 0;
            try{
                choice = input.nextInt();
                if(choice < 1 || choice > 6) {
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
                removeItem(input, cart);
            }
            else if (choice == 4) {
                viewItems(items);
            }
            else if (choice == 5) {
                checkout(cart, input, df);
            }

            else if (choice == 6) {
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

    public static void removeItem(Scanner input, ArrayList cartItems) {
        if(cartItems.size() == 0) {
            System.out.println("Your cart is empty, no need to remove an item.");
            return;
        }
        else{
            try {
                System.out.println("Please enter the item you would like to add: ");
                String removedItem = input.nextLine();
                boolean yes = false;
                Item n = null;
                for(int i = 0; i < cartItems.size();i++){
                    n = (Item) cartItems.get(i);
                    if(removedItem.equalsIgnoreCase(n.getName())){
                        cartItems.remove(n);
                        System.out.println("Item removed from cart.");
                        n.setStock(n.getStock()+1);
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

    public static void checkout(ArrayList cartItems, Scanner input, DecimalFormat df) {
        File file = new File("receipt.txt");
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("ACM (All you Can Muster) Foods\n");
            writer.write("123 Maresso Lane Whitewater, Wisconsin\n");
            writer.write("1-(262)-420-6969\n");
            writer.write("_______________________________________\n\n");
            writer.write("RECEIPT\n");
            writer.write("_______________________________________\n\n");
            String total = null;
            double totalValue = 0;
            boolean discount = false;
            String[] codes = new String[]{"COLE10","AIDAN10","MITCH10","FREESTUFF","LOVEMARESSO"};
            List codesList = Arrays.asList(codes);
            try{
                System.out.println("Would you like to apply a discount code? [Press 'y' for yes, 'n' for no]");
                String answer = input.nextLine();
                if(answer.equalsIgnoreCase("n")){
                    for (int i = 0; i < cartItems.size(); i++){
                        writer.write(receiptFormat(cartItems, i, df));
                        writer.write("\n");
                        totalValue += receiptTotal(cartItems, i);
                    }
                }
                else if(answer.equalsIgnoreCase("y")){
                    System.out.println("Please enter a discount code:");
                    String enteredCode = input.nextLine();

                    if(codesList.contains(enteredCode)){
                        System.out.println("Discount applied");
                        for (int j = 0; j < cartItems.size(); j++){
                            writer.write(receiptFormatDiscounted(cartItems, j, df));
                            writer.write("\n");
                            totalValue += receiptTotalDiscounted(cartItems, j);
                        }
                        discount = true;
                    }

                    if(discount == false) {
                        System.out.println("Invalid discount code.");
                        for (int i = 0; i < cartItems.size(); i++){
                            writer.write(receiptFormat(cartItems, i, df));
                            writer.write("\n");
                            totalValue += receiptTotal(cartItems, i);
                        }
                    }
                }
                else{
                    throw new Exception("Please enter either 'y' or 'n'");
                }
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
                return;
            }
            total = String.valueOf(df.format(totalValue));
            writer.write("_______________________________________\n\n");
            writer.write("Total: \t\t\t\t $");
            writer.write(total);
            writer.write("\n");
            writer.write("_______________________________________\n\n");
            writer.write("Thank You For Shopping at\n");
            writer.write("ACM (All you Can Muster) Foods\n");
            writer.write("_______________________________________");
        }
        catch (IOException e) {
            System.out.println("Could not write to file");
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("I/O error occurred");
            }
        }
        printReceipt(file);
    }

    public static String receiptFormat (ArrayList cartItems, int i, DecimalFormat df){
        Item n = (Item) cartItems.get(i);
        String name = n.getName();
        String price = String.valueOf(df.format(n.getPrice()));
        return String.format("%s: \t\t $%s",name , price);
    }

    public static String receiptFormatDiscounted(ArrayList cartItems, int i, DecimalFormat df) {
        Item n = (Item) cartItems.get(i);
        String name = n.getName();
        String price = String.valueOf(df.format((n.getPrice()*0.9d)));
        return String.format("%s: \t\t $%s",name , price);
    }

    public static double receiptTotal(ArrayList cartItems, int i) {
        Item n = (Item) cartItems.get(i);
        double total = (n.getPrice());
        return total;
    }

    public static double receiptTotalDiscounted (ArrayList cartItems, int i){
        Item n = (Item) cartItems.get(i);
        double total = n.getPrice();
        total = total*0.9d;
        return total;
    }

    public static void printReceipt (File file) {
        Scanner reader = null;
        try{
            reader = new Scanner(file);

            while (reader.hasNextLine()){
                String line = reader.nextLine();
                System.out.println(line);
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("File was not found.");
        }
        catch(NoSuchElementException e) {
            System.out.println("Error encountered while reading from file.");
        }
        finally {
            if(reader != null){
                reader.close();
            }
        }
    }
}
