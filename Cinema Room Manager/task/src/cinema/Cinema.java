package cinema;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static final int REGULAR_PRICE = 10;
    public static final int BACK_SEAT_PRICE = 8;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows;
        int frontRows;
        int backRows;
        int seats;
        int rowNumber;
        int seatNumber;
        int totalSeats;
        int currentIncome = 0;
        int totalIncome;
        String[][] seatGrid;
        int commandNumber = -1;
        int purchasedTickets = 0;
        float percentagePurchased;


        // Asking user for the number of rows and seats in each row
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();

        // Calculating the total seats in room and the half front rows
        totalSeats = rows * seats;
        frontRows = rows / 2;
        percentagePurchased = (purchasedTickets / totalSeats) * 100;

        // If the number of rows is even the front rows and back rows are the same otherwise there will be 1 more back rows.
        if (rows % 2 == 0) {
            backRows = frontRows;
        }
        else {
            backRows = frontRows + (rows % 2);
        }

        //calculates total income depending on total seats
        if (totalSeats > 60) {
            totalIncome = calculateBigRoomIncome(frontRows, backRows, seats);
        }
        else {
            totalIncome = getTotalIncome(totalSeats, REGULAR_PRICE);
        }

        // Initializes the seat grid.
        seatGrid = initializeGrid(rows, seats);

        while (commandNumber != 0) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");

            commandNumber = scanner.nextInt();

            // Switch statement will manage the actions that the user chooses until the user inputs 0;
            switch (commandNumber) {
                case 0:
                    break;
                case 1:
                    printSeating(rows, seats, seatGrid);
                    break;
                case 2:
                    // Asking the user for the row number and seat number they want
                    rowNumber = getRowNumber(scanner);
                    seatNumber = getSeatNumber(scanner);

                    while (rowNumber > seatGrid.length || rowNumber < 1 || seatNumber > seatGrid[0].length || seatNumber < 1) {
                        System.out.println("Wrong input!");
                        rowNumber = getRowNumber(scanner);
                        seatNumber = getSeatNumber(scanner);
                    }

                    while (seatGrid[rowNumber - 1][seatNumber - 1].equals("B")) {
                        System.out.println("That ticket has already been purchased");
                        rowNumber = getRowNumber(scanner);
                        seatNumber = getSeatNumber(scanner);
                    }

                    //Prints the ticket price
                    System.out.printf("Ticket price: $%s\n", calculateSeatPrice(frontRows, rowNumber, totalSeats));

                    currentIncome += calculateSeatPrice(frontRows, rowNumber, totalSeats);
                    purchasedTickets++;
                    System.out.println((purchasedTickets / totalSeats) * 100);
                    percentagePurchased =  ((float) purchasedTickets / (float) totalSeats) * 100;


                    // Updates the seatGrid
                    seatGrid[rowNumber - 1][seatNumber - 1] = "B";
                    break;
                case 3:
                    System.out.println("Number of purchased tickets: " + purchasedTickets);
                    System.out.println("Percentage: " + df.format(percentagePurchased) + "%");
                    System.out.println("Current income: $" + currentIncome);
                    System.out.println("Total income: $" + totalIncome);
                    break;
                default:
                    System.out.println("Wrong input try again please");
            }
        }
    }

    private static int getSeatNumber(Scanner scanner) {
        int seatNumber;
        System.out.println("Enter a seat number in that row:");
        seatNumber = scanner.nextInt();
        return seatNumber;
    }

    private static int getRowNumber(Scanner scanner) {
        int rowNumber;
        System.out.println("Enter a row number:");
        rowNumber = scanner.nextInt();
        return rowNumber;
    }

    // Calculates the price depending on the total seats
    private static int calculateSeatPrice(int frontRows, int rowNumber, int totalSeats) {
        int ticketPrice;
        if (totalSeats <= 60) {
            ticketPrice = REGULAR_PRICE;
        }
        else if (rowNumber >= 1 && rowNumber <= frontRows) {
            ticketPrice = REGULAR_PRICE;
        }
        else {
            ticketPrice = BACK_SEAT_PRICE;
        }

        return ticketPrice;
    }

    // Creates the initial grid
    private static String[][] initializeGrid(int rows, int seats) {
        String[][] seatGrid;
        seatGrid =  new String[rows][seats];
        for (String[] row : seatGrid) {
            Arrays.fill(row, "S");
        }
        return seatGrid;
    }

    // calculates the total income
    private static int getTotalIncome(int totalSeats, int price) {
        return totalSeats * price;
    }

    // Calculates the total income if the room is larger than 60 seats
    private static int calculateBigRoomIncome(int frontRows, int backRows, int seats) {
        int totalFrontRowSeats;
        int totalBackRowSeats;

        totalFrontRowSeats = frontRows * seats;
        totalBackRowSeats = backRows * seats;

        return getTotalIncome(totalFrontRowSeats, REGULAR_PRICE) + getTotalIncome(totalBackRowSeats, BACK_SEAT_PRICE);
    }

    // Prints the seat grid
    private static void printSeating(int rows, int seats, String[][] seatGrid) {

        System.out.println("Cinema:");
        for (int i = 1; i <= seats; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < seats; j++) {
                System.out.print(" " + seatGrid[i][j]);
            }
            System.out.println();
        }
    }
}