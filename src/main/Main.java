package main;

import java.util.Scanner;

public class Main extends Menu{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        Start();
        while(true) {
            Pilihan();
            int pilihan = scan.nextInt(); 
            switch (pilihan) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    System.out.println("Progam ditutup...");
                    scan.close();
                        return;
            }
        }

    }
}
