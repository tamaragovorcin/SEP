package com.BitCoinService.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBitcoinDTO {

	private Long id;
    private String status;
    private String price_currency;
    private String price_amount;
    private String receive_currency;
    private String receive_amount;
    private String created_at;
    private String order_id;
    private String payment_url;
    private String token;

}
