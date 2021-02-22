package pl.wojczu.payu;

import org.junit.Test;
import pl.wojczu.payu.exceptions.PayUException;
import pl.wojczu.payu.http.JavaHttpPayUApiClient;
import pl.wojczu.payu.model.Buyer;
import pl.wojczu.payu.model.CreateOrderResponse;
import pl.wojczu.payu.model.OrderCreateRequest;
import pl.wojczu.payu.model.Product;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.UUID;

public class PayUTest {
    @Test
    public void itAllowsToRegisterOrder()  throws PayUException {
        var payu = thereIsPayU();
        var mySystemOrderId = UUID.randomUUID().toString();
        var exampleOrderCreateRequest = thereIsExampleOrderCreate(mySystemOrderId);

        CreateOrderResponse r = payu.handle(exampleOrderCreateRequest);

        assertThat(r.getExtOrderId()).isEqualTo(mySystemOrderId);
        assertThat(r.getOrderId()).isNotNull();
        assertThat(r.getRedirectUri()).isNotNull();
    }

    private PayU thereIsPayU() {
        return new PayU(
                PayUCredentials.sandbox(),
                new JavaHttpPayUApiClient()
        );
    }

    private OrderCreateRequest thereIsExampleOrderCreate(String mySystemOrderId) {
        return OrderCreateRequest.builder()
                .notifyUrl("https://your.eshop.com/notify")
                .customerIp("127.0.0.1")
                .description("RTV market")
                .currencyCode("PLN")
                .totalAmount(21000)
                .extOrderId(mySystemOrderId)
                .buyer(Buyer.builder()
                        .email("john.doe@example.com")
                        .phone("654111654")
                        .firstName("John")
                        .lastName("Doe")
                        .language("pl")
                        .build())
                .products(Arrays.asList(
                        new Product("Wireless Mouse for Laptop", 15000, 1),
                        new Product("Battery AAA", 1000, 2)
                ))
                .build();
    }
}