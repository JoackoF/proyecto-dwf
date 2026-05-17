package udb.edu.sv.entity.enums;

public enum FlightStatus {
    SCHEDULED,
    BOARDING,
    DEPARTED,
    ARRIVED,
    DELAYED,
    CANCELLED;

    public boolean allowsReservations() {
        return this == SCHEDULED || this == DELAYED;
    }
}
