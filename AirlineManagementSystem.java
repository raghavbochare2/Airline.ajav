
interface airline {
    void scheduleFlight();
    void cancelFlight();
    void updateFlightStatus();
}

 abstract class Flight implements airline {
    protected String flightNumber;
    protected String departure;
    protected String destination;
    protected String status;

    public Flight(String flightNumber, String departure, String destination) {
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.status = "Scheduled";
    }
 
 
    public void scheduleFlight() {
        System.out.println("Flight " + flightNumber + " scheduled from " + departure + " to " + destination);
    }

    
    public void cancelFlight() {
        this.status = "Cancelled";
        System.out.println("Flight " + flightNumber + " cancelled.");
    }


    public void updateFlightStatus() {
        System.out.println("Updating status for flight " + flightNumber);
    }

    public String getStatus() {
        return status;
    }
}


 class DomesticFlight extends Flight {
    public DomesticFlight(String flightNumber, String departure, String destination) {
      super(flightNumber, departure, destination);
    }


    public void updateFlightStatus() {
        Flight flight = this;
        flight.updateFlightStatus();
        System.out.println("Domestic flight " + flightNumber + " status updated.");
    }
}


 class InternationalFlight extends Flight {
    public InternationalFlight(String flightNumber, String departure, String destination) {
        super(flightNumber, departure, destination);
    }

 
    public void updateFlightStatus() {
        Flight flight = this;
        flight.updateFlightStatus();
        System.out.println("International flight " + flightNumber + " status updated.");
    }
}

 class AirlineManagementSystem {
    private Flight[] flights;
    private int flightCount;

    public AirlineManagementSystem(int capacity) {
        flights = new Flight[capacity];
        flightCount = 0;
    }

    public void addFlight(Flight flight) {
        if (flightCount < flights.length) {
            flights[flightCount++] = flight;
            flight.scheduleFlight();
        } else {
            System.out.println("Cannot add more flights. Capacity full.");
        }
    }

    public void cancelFlight(String flightNumber) {
        for (int i = 0; i < flightCount; i++) {
            if (flights[i].flightNumber.equals(flightNumber)) {
                flights[i].cancelFlight();
                // Shift remaining flights to the left
                for (int j = i; j < flightCount - 1; j++) {
                    flights[j] = flights[j + 1];
                }
                flights[--flightCount] = null;
                break;
            }
        }
    }

    public void updateFlightStatus(String flightNumber) {
        for (int i = 0; i < flightCount; i++) {
            if (flights[i].flightNumber.equals(flightNumber)) {
                flights[i].updateFlightStatus();
                break;
            }
        }
    }

    public void listFlights() {
        for (int i = 0; i < flightCount; i++) {
            System.out.println("Flight Number: " + flights[i].flightNumber + ", Status: " + flights[i].getStatus());
        }
    }

    public static void main(String[] args) {
        AirlineManagementSystem ams = new AirlineManagementSystem(10); // Capacity of 10 flights

        Flight flight1 = new DomesticFlight("DL123", "New York", "Chicago");
        Flight flight2 = new InternationalFlight("AI456", "Los Angeles", "London");

        ams.addFlight(flight1);
        ams.addFlight(flight2);

        ams.listFlights();

        ams.updateFlightStatus("DL123");
        ams.cancelFlight("AI456");

        ams.listFlights();
    }
}
