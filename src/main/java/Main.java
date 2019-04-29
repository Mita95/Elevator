/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Scanner;

/**
 * @author mj
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Elevator elevator = new Elevator();
        elevator.start();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Y to add destination:");
        String res = sc.nextLine();
        while (res.equalsIgnoreCase("y")) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            elevator.setRoute(from, to);
        }
    }
}
