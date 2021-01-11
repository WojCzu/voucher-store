package pl.wojczu.voucherstore.sales.exceptions;

public class NotEnoughProductsException extends IllegalStateException {
    public NotEnoughProductsException(){
        super("There are not enough products available");
    }
}
