package udb.edu.sv.exception;

public class PaymentFailedException extends RuntimeException {
    public PaymentFailedException(String mensaje) {
        super(mensaje);
    }
}
