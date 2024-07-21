package com.java.features;

public class Route {
    private int routeId;
    private String source;
    private String destination;
    private double ticketCost;

    // Constructors, getters, and setters
    public Route(String source, String destination, double ticketCost) {
        this.source = source;
        this.destination = destination;
        this.ticketCost = ticketCost;
    }

    // Getters and Setters
    public int getRouteId() { return routeId; }
    public void setRouteId(int routeId) { this.routeId = routeId; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public double getTicketCost() { return ticketCost; }
    public void setTicketCost(double ticketCost) { this.ticketCost = ticketCost; }
}
