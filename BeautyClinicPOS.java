// import for time and date formatting
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class BeautyClinicPOS {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


        String[] categories = {
            "FACIAL TREATMENTS",
            "ANTI-AGING TREATMENTS",
            "LASER TREATMENTS",
            "SKIN CARE ADD-ONS",
            "BODY TREATMENTS"
        };

        // Store the starting index of each category in the services array.
        int[] categoryStarts = {0, 8, 16, 25, 30};

        // Each service has a matching price at the same array index.
        String[] services = {
            "Basic Facial",
            "Deep Cleansing Facial",
            "Acne Facial",
            "Brightening Facial",
            "Hydrating Facial",
            "Dermaplaning",
            "Oxygen Facial",
            "Soothing Facial (Sensitive Skin)",
            "Anti-Aging Facial",
            "Radiofrequency Skin Tightening",
            "HIFU Face Lift",
            "Microneedling (Collagen Induction)",
            "PRP Facial (Vampire Facial)",
            "Chemical Peel",
            "Eye Rejuvenation Treatment",
            "Neck Firming Treatment",
            "Laser Hair Removal - Underarm",
            "Laser Hair Removal - Whole Legs",
            "Carbon Laser Peel",
            "Pico Laser (Pigmentation/Melasma)",
            "Fractional Laser (Acne Scars)",
            "Laser Mole/Wart Removal",
            "Laser Hair Removal - Upper Lip",
            "Laser Hair Removal - Bikini Line",
            "Laser Tattoo Removal (Small Area)",
            "Microdermabrasion",
            "LED Light Therapy",
            "Mesotherapy (Skin Booster)",
            "Hydrojelly Mask",
            "Blackhead/Whitehead Extraction",
            "Underarm Whitening",
            "Body Scrub & Whitening",
            "Cavitation Body Slimming"
        };

        double[] prices = {
            500, 700, 800, 750, 700, 900, 1800, 850,
            1500, 2500, 5500, 3000, 6000, 1800, 2200, 2800,
            1500, 4000, 2500, 4500, 5000, 1500, 800, 2000, 3500,
            1500, 800, 3500, 600, 500,
            1200, 1800, 2500
        };

        // The cart stores service numbers and quantities.
        int[] cartServices = new int[50];
        int[] cartQty = new int[50];
        // Service number 0 means services[0].
        int cartCount = 0;
        boolean running = true;

        while (running) {

            System.out.println("\n==================================================================");
            System.out.println("                        VELOURA AESTHETICS");
            System.out.println("                BEST BEAUTY CLINIC IN PHILIPPINES");
            System.out.println("==================================================================");
            System.out.println("------------------------- SERVICE MENU --------------------------");

            // Print the services and category headings.
            for (int i = 0; i < services.length; i++) {

                // Check whether the current service starts a category.
                for (int j = 0; j < categoryStarts.length; j++) {

                    if (i == categoryStarts[j]) {
                        System.out.println("\n-- " + categories[j] + " --");
                        break;
                    }
                }

                // Display the service number, name, and price.
                System.out.printf("%2d. %-48s %12s%n",
                        i + 1, services[i], peso(prices[i]));
            }

            System.out.println("\n34. View / Remove from Cart");
            System.out.println("35. Checkout");
            System.out.println("36. Cancel and Exit");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Please enter a number.");
                sc.next();
                continue;
            }

            int choice = sc.nextInt();

            // Add a service to the cart.
            if (choice >= 1 && choice <= services.length) {

                // Menu numbers start at 1; array indexes start at 0.
                int serviceIndex = choice - 1;
                int cartPosition = -1;

                // Check whether the service is already in the cart.
                for (int i = 0; i < cartCount; i++) {
                    if (cartServices[i] == serviceIndex) {
                        cartPosition = i;
                        break;
                    }
                }

                if (cartPosition >= 0) {
                    cartQty[cartPosition]++;
                } else if (cartCount < cartServices.length) {
                    cartServices[cartCount] = serviceIndex;
                    cartQty[cartCount] = 1;
                    cartCount++;
                } else {
                    System.out.println("Cart is full.");
                    continue;
                }
            }


            // View the cart and optionally remove an item.
            else if (choice == 34) {

                if (cartCount == 0) {
                    System.out.println("Your cart is empty.");
                } else {

                    System.out.println(
                            "\n--------------------------- YOUR CART ---------------------------");
                    System.out.printf("%-4s %-50s %8s %12s%n",
                            "No.", "SERVICE", "QTY", "AMOUNT");
                    System.out.println(
                            "-----------------------------------------------------------------");

                    double total = 0;

                    for (int i = 0; i < cartCount; i++) {

                        int serviceIndex = cartServices[i];
                        double lineTotal = prices[serviceIndex] * cartQty[i];
                        total += lineTotal;

                        System.out.printf("%-4d %-50s %8d %12s%n",
                                i + 1, services[serviceIndex],
                                cartQty[i], peso(lineTotal));
                    }

                    System.out.println(
                            "-----------------------------------------------------------------");
                    System.out.printf("%64s %12s%n", "TOTAL:", peso(total));

                    System.out.print(
                            "Enter item number to remove (0 to go back): ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Please enter a number.");
                        sc.next();
                        continue;
                    }

                    int remove = sc.nextInt();

                    if (remove >= 1 && remove <= cartCount) {

                        // Shift later cart entries left.
                        for (int i = remove - 1; i < cartCount - 1; i++) {
                            cartServices[i] = cartServices[i + 1];
                            cartQty[i] = cartQty[i + 1];
                        }

                        cartCount--;
                        System.out.println("Item removed.");

                    } else if (remove != 0) {
                        System.out.println("Invalid item number.");
                    }
                }
            }

            // Checkout calculates the total and prints the receipt.
            else if (choice == 35) {

                if (cartCount == 0) {
                    System.out.println(
                            "Your cart is empty. Add a service first.");
                    continue;
                }

                double total = 0;

                // Calculates the receipt
                for (int i = 0; i < cartCount; i++) {
                    total += prices[cartServices[i]] * cartQty[i];
                }

                System.out.println(
                        "\n===============================================================");
                System.out.println("                     VELOURA AESTHETICS");
                System.out.println("                       OFFICIAL RECEIPT");
                System.out.println(
                        "===============================================================");

                String date = LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"));

                System.out.println("Date: " + date);
                System.out.println(
                        "---------------------------------------------------------------");
                System.out.printf("%-52s %12s%n", "SERVICE", "AMOUNT");
                System.out.println(
                        "---------------------------------------------------------------");

                for (int i = 0; i < cartCount; i++) {

                    int serviceIndex = cartServices[i];
                    double lineTotal = prices[serviceIndex] * cartQty[i];
                    String item = services[serviceIndex] + " x" + cartQty[i];

                    System.out.printf("%-52s %12s%n",
                            item, peso(lineTotal));
                }

                System.out.println(
                        "---------------------------------------------------------------");
                System.out.printf("%-52s %12s%n", "TOTAL:", peso(total));

                double payment = 0;

                // Continue asking for payment until enough is received.
                while (payment < total) {

                    System.out.print("Amount received: P");

                    if (!sc.hasNextDouble()) {
                        System.out.println("Please enter a valid amount.");
                        sc.next();
                        continue;
                    }

                    payment = sc.nextDouble();

                    if (payment < total) {
                        System.out.println(
                                "Not enough. Amount still due: P"
                                + (total - payment));
                    }
                }

                System.out.println();
                System.out.printf("%-52s %12s%n",
                        "AMOUNT RECEIVED:", peso(payment));
                System.out.printf("%-52s %12s%n",
                        "CHANGE:", peso(payment - total));

                System.out.println(
                        "===============================================================");
                System.out.println("             Thank you! Please come again.");
                System.out.println(
                        "===============================================================");

                running = false;
            }

            // Cancel the transaction and exit.
            else if (choice == 36) {
                System.out.println("Transaction cancelled. Goodbye!");
                running = false;
            }

            else {
                System.out.println("Invalid choice. Please choose 1 to 36.");
            }
        }

        sc.close();
    }

    // Format a number as Philippine pesos.
    private static String peso(double amount) {
        return String.format("P%,.2f", amount);
    }
}