package com.java.features;

public class Reservation {
    private int reservationId;
    private int routeId;
    private int passengerId;
    private double totalCost;

    // Constructors, getters, and setters
    public Reservation(int routeId, int passengerId, double totalCost) {
        this.routeId = routeId;
        this.passengerId = passengerId;
        this.totalCost = totalCost;
    }

    // Getters and Setters
    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }
    public int getRouteId() { return routeId; }
    public void setRouteId(int routeId) { this.routeId = routeId; }
    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }
    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
}

