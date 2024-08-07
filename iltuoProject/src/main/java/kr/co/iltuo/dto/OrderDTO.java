package kr.co.iltuo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	private int priceID;
	private String userID;
	private int productPrice;
	private String fmtProductPrice;
	private int deliveryPrice;
	private String fmtDeliveryPrice;
	private int totalPrice;
	private String fmtTotalPrice;
	private LocalDateTime priceDate;
	private String deliveryPriceStatusID;
	private int orderID;
	private int optionPrice;
	private String fmtOptionPrice;
	private String orderStatusID;
	private LocalDateTime orderDate;
	private String paymentMethodID;
	private String paymentStatusID;
	private String paymentDate;
	private String deliveryStatusID;
	private int addressID;
	private String courierCompany;
	private String invoiceNumber;
	private String deliveryDate;
	private String arriveDate;
	private String paymentMethod;
	private String status;
	private String paymentStatus;
	private String deliveryStatus;
	private String viewOrder;
	
	public void setOptionPrice(int optionPrice) {
		this.optionPrice = optionPrice;
		this.fmtOptionPrice  = String .format("%,d", optionPrice + "원");
	}
	
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
		this.fmtTotalPrice = String.format("%,d", totalPrice) + "원";
	}
	
	public void setDeliveryPriceStatusID(String deliveryPriceStatusID) {
	    this.deliveryPriceStatusID = deliveryPriceStatusID;
	    if (deliveryPriceStatusID.equals("DP0")) {
	        this.deliveryPrice = 3000;
	        this.fmtDeliveryPrice = String.format("%,d", 3000) + "원";
	    } else {
	    	this.deliveryPrice = 0;
	    	this.fmtDeliveryPrice = 0 + "원";
	    }
	    this.productPrice = this.totalPrice - this.deliveryPrice;
        this.fmtProductPrice = String.format("%,d", productPrice) + "원";
	}
	
}
