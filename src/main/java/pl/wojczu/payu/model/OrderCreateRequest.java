package pl.wojczu.payu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreateRequest {
    private String notifyUrl;
    private String extOrderId;
    private String customerIp;
    private String merchantPosId;
    private String description;
    private String currencyCode;
    private Integer totalAmount;
    private String continueUrl;
    private Buyer buyer;
    private List<Product> products;
}