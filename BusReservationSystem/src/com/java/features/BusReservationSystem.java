package com.java.features;

import java.sql.*;
import java.util.Scanner;

public class BusReservationSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kavya";
    private static final String USER = "root";
    private static final String PASS = "admin";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("\nBus Reservation System");
                System.out.println("1. Add Route");
                System.out.println("2. Add Passenger");
                System.out.println("3. Book Ticket");
                System.out.println("4. Cancel Ticket");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addRoute(conn, scanner);
                        break;
                    case 2:
                        addPassenger(conn, scanner);
                        break;
                    case 3:
                        bookTicket(conn, scanner);
                        break;
                    case 4:
                        cancelTicket(conn, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 5);
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addRoute(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter source: ");
        String source = scanner.next();
        System.out.print("Enter destination: ");
        String destination = scanner.next();
        System.out.print("Enter ticket cost: ");
        double ticketCost = scanner.nextDouble();

        String query = "INSERT INTO routes (source, destination, ticket_cost) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, source);
            pstmt.setString(2, destination);
            pstmt.setDouble(3, ticketCost);
            pstmt.executeUpdate();
            System.out.println("Route added successfully.");
        }
    }

    private static int addPassenger(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter name: ");
        String name = scanner.next();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        System.out.print("Enter gender: ");
        String gender = scanner.next();

        String query = "INSERT INTO passengers (name, age, gender) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int passengerId = rs.getInt(1);
                System.out.println("Passenger added successfully with ID: " + passengerId);
                return passengerId;
            }
        }
        return -1;
    }

    private static void bookTicket(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter source: ");
        String source = scanner.next();
        System.out.print("Enter destination: ");
        String destination = scanner.next();
        System.out.print("Enter passenger ID: ");
        int passengerId = scanner.nextInt();

        String query = "SELECT route_id, ticket_cost FROM routes WHERE source = ? AND destination = ?";
        int routeId = -1;
        double ticketCost = 0.0;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, source);
            pstmt.setString(2, destination);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                routeId = rs.getInt("route_id");
                ticketCost = rs.getDouble("ticket_cost");
                System.out.println("Total cost is:"+ticketCost);
            } else {
                System.out.println("No route found for the given source and destination.");
                return;
            }
        }

        String insertQuery = "INSERT INTO reservations (route_id, passenger_id, total_cost) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setInt(1, routeId);
            pstmt.setInt(2, passengerId);
            pstmt.setDouble(3, ticketCost);
            pstmt.executeUpdate();
            System.out.println("Ticket booked successfully.");
        }
    }

    private static void cancelTicket(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter reservation ID to cancel: ");
        int reservationId = scanner.nextInt();

        String query = "DELETE FROM reservations WHERE reservation_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, reservationId);
            pstmt.executeUpdate();
            System.out.println("Ticket canceled successfully.");
        }
    }
}
