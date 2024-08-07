package kr.co.iltuo.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopDTO {
	private int productID;
	private int mainCategoryID;
	private String mainCategoryName;
	private String optionGrade;
	private int subCategoryID;
	private String subCategoryName;
	private String productName;
	private String productComments;
	private int price;
	private String recommendationID;
	private String fmtPrice;
	private int originalPrice;
	private String fmtOriginalPrice;
	private String discontinuedStatusID;
	private String registrationDate;
	private String productImageName;
	private int majorOptionID;
	private String majorOptionCategory;
	private String majorOptionName;
	private int majorOptionAdjustedPrice;
	private String fmtMajorOptionAdjustedPrice;
	private int finalMajorOptionAdjustedPrice;
	private String fmtFinalMajorOptionAdjustedPrice;
	private int majorOptionPrice;
	private String fmtMajorOptionPrice;
	private String finalMajorOptionName;
	private String majorOptionIsPriceChangeID;
	private BigDecimal majorOptionPriceChanges;
	private int subOptionID;
	private String subOptionCategory;
	private String subOptionName;
	private int subOptionAdjustedPrice;
	private String fmtSubOptionAdjustedPrice;
	private int finalSubOptionAdjustedPrice;
	private String fmtFinalSubOptionAdjustedPrice;
	private int subOptionPrice;
	private String fmtSubOptionPrice;
	private String finalSubOptionName;
	private String subOptionIsPriceChangeID;
	private BigDecimal subOptionPriceChanges;
	private int minerOptionID;
	private String minerOptionCategory;
	private String minerOptionName;
	private int minerOptionAdjustedPrice;
	private String fmtMinerOptionAdjustedPrice;
	private int finalMinerOptionAdjustedPrice;
	private String fmtFinalMinerOptionAdjustedPrice;
	private int minerOptionPrice;
	private String fmtMinerOptionPrice;
	private String finalMinerOptionName;
	private String minerOptionIsPriceChangeID;
	private BigDecimal minerOptionPriceChanges;
	private String optionValidID;
	private int cartID;
	private String userID;
	private int count;
	private int paymentID;
	private int totalPrice;
	private String fmtTotalPrice;
	private int finalTotalPrice;
	private String fmtFinalTotalPrice;
	private int priceID;
	private String priceDate;
	private String deliveryPriceStatusID;
	private int orderID;
	private String orderStatusID;
	private String orderDate;
	private int orderPrice;
	private String fmtOrderPrice;
	private String userIDForOrder;
	private String viewOrder;
	private int sortOrder;
	private String optionGradeValidate;
	private String optionGradeText;
	private String isPriceChangeID;
	private String isPriceChangeText;
	private BigDecimal priceChanges;

	public void setPrice(int price) {
		this.price = price;
		this.fmtPrice = String.format("%,d", price) + "원";
	}
	
	public void setOriginalPrice(int originalPrice) {
		this.originalPrice = originalPrice;
		this.fmtOriginalPrice = String.format("%,d", originalPrice) + "원";
	}

	public void setMajorOptionAdjustedPrice(int majorOptionAdjustedPrice) {
		this.majorOptionAdjustedPrice = majorOptionAdjustedPrice;
		this.fmtMajorOptionAdjustedPrice = String.format("%,d", majorOptionAdjustedPrice) + "원";
	}
	
	public void setFinalMajorOptionAdjustedPrice(int finalMajorOptionAdjustedPrice) {
		this.finalMajorOptionAdjustedPrice = finalMajorOptionAdjustedPrice;
		this.fmtFinalMajorOptionAdjustedPrice = String.format("%,d", finalMajorOptionAdjustedPrice) + "원";
	}

	public void setSubOptionAdjustedPrice(int subOptionAdjustedPrice) {
		this.subOptionAdjustedPrice = subOptionAdjustedPrice;
		this.fmtSubOptionAdjustedPrice = String.format("%,d", subOptionAdjustedPrice) + "원";
	}
	
	public void setFinalSubOptionAdjustedPrice(int finalSubOptionAdjustedPrice) {
		this.finalSubOptionAdjustedPrice = finalSubOptionAdjustedPrice;
		this.fmtFinalSubOptionAdjustedPrice = String.format("%,d", finalSubOptionAdjustedPrice) + "원";
	}

	public void setMinerOptionAdjustedPrice(int minerOptionAdjustedPrice) {
		this.minerOptionAdjustedPrice = minerOptionAdjustedPrice;
		this.fmtMinerOptionAdjustedPrice = String.format("%,d", minerOptionAdjustedPrice) + "원";
	}
	
	public void setFinalMinerOptionAdjustedPrice(int finalMinerOptionAdjustedPrice) {
		this.finalMinerOptionAdjustedPrice = finalMinerOptionAdjustedPrice;
		this.fmtFinalMinerOptionAdjustedPrice = String.format("%,d", finalMinerOptionAdjustedPrice) + "원";
	}

	public void setMajorOptionPrice(int majorOptionPrice) {
		this.majorOptionPrice = majorOptionPrice;
		this.fmtMajorOptionPrice = String.format("%,d", majorOptionPrice) + "원";
	}

	public void setSubOptionPrice(int subOptionPrice) {
		this.subOptionPrice = subOptionPrice;
		this.fmtSubOptionPrice = String.format("%,d", subOptionPrice) + "원";
	}

	public void setMinerOptionPrice(int minerOptionPrice) {
		this.minerOptionPrice = minerOptionPrice;
		this.fmtMinerOptionPrice = String.format("%,d", minerOptionPrice) + "원";
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
		this.fmtTotalPrice = String.format("%,d", totalPrice) + "원";
	}

	public void setFinalTotalPrice(int finalTotalPrice) {
		this.finalTotalPrice = finalTotalPrice;
		this.fmtFinalTotalPrice = String.format("%,d", finalTotalPrice) + "원";
	}
	
	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
		this.fmtOrderPrice  = String.format("%,d", orderPrice) + "원";
	}

	public void updateMajorOptionFields() {
		calculateMajorOptionPrice();
		calculateMajorOptionAdjustedPrice();
		calculateFinalMajorOptionName();
	}

	private void calculateMajorOptionPrice() {
		if (majorOptionIsPriceChangeID != null && majorOptionPriceChanges != null) {
			switch (majorOptionIsPriceChangeID) {
			case "PC1":
				BigDecimal decMajorOptionPrice = majorOptionPriceChanges.multiply(new BigDecimal(price));
				majorOptionPrice = decMajorOptionPrice.setScale(-1, RoundingMode.HALF_UP).intValue();
				break;
			case "PC2":
				majorOptionPrice = price + majorOptionPriceChanges.intValue();
				break;
			case "PC3":
				majorOptionPrice = price - majorOptionPriceChanges.intValue();
				break;
			default:
				majorOptionPrice = price;
				break;
			}
		} else {
			majorOptionPrice = price;
		}
		setMajorOptionPrice(majorOptionPrice);
	}

	private void calculateMajorOptionAdjustedPrice() {
		if (majorOptionIsPriceChangeID != null && majorOptionPriceChanges != null) {
			switch (majorOptionIsPriceChangeID) {
			case "PC1":
				majorOptionAdjustedPrice = majorOptionPriceChanges.compareTo(BigDecimal.ONE) >= 0 ? majorOptionPrice - price
						: price - majorOptionPrice;
				finalMajorOptionAdjustedPrice = majorOptionPrice - price;
				break;
			case "PC2":
				majorOptionAdjustedPrice = majorOptionPriceChanges.intValue();
				finalMajorOptionAdjustedPrice = majorOptionPriceChanges.intValue();
				break;
			case "PC3":
				majorOptionAdjustedPrice = majorOptionPriceChanges.intValue();
				finalMajorOptionAdjustedPrice = -majorOptionPriceChanges.intValue();
				break;
			default:
				majorOptionAdjustedPrice = 0;
				finalMajorOptionAdjustedPrice = majorOptionAdjustedPrice;
				break;
			}
		} else {
			majorOptionAdjustedPrice = 0;
			finalMajorOptionAdjustedPrice = majorOptionAdjustedPrice;
		}
		setMajorOptionAdjustedPrice(majorOptionAdjustedPrice);
		setFinalMajorOptionAdjustedPrice(finalMajorOptionAdjustedPrice);
	}

	private void calculateFinalMajorOptionName() {
		if (majorOptionIsPriceChangeID != null && majorOptionPriceChanges != null) {
			String adjustment = (majorOptionIsPriceChangeID.equals("PC1") && majorOptionPriceChanges.compareTo(BigDecimal.ONE) < 0)
					|| majorOptionIsPriceChangeID.equals("PC3") ? "(-" + fmtMajorOptionAdjustedPrice + ")"
							: "(+" + fmtMajorOptionAdjustedPrice + ")";
			switch (majorOptionIsPriceChangeID) {
			case "PC1":
			case "PC2":
			case "PC3":
				finalMajorOptionName = majorOptionName + adjustment;
				break;
			default:
				finalMajorOptionName = majorOptionName;
				break;
			}
		} else {
			finalMajorOptionName = majorOptionName;
		}
	}

	public void updateSubOptionFields() {
		calculateSubOptionPrice();
		calculateSubOptionAdjustedPrice();
		calculateFinalSubOptionName();
	}

	private void calculateSubOptionPrice() {
		if (subOptionIsPriceChangeID != null && subOptionPriceChanges != null) {
			switch (subOptionIsPriceChangeID) {
			case "PC1":
				BigDecimal decSubOptionPrice = subOptionPriceChanges.multiply(new BigDecimal(majorOptionPrice));
				subOptionPrice = decSubOptionPrice.setScale(-1, RoundingMode.HALF_UP).intValue();
				break;
			case "PC2":
				subOptionPrice = majorOptionPrice + subOptionPriceChanges.intValue();
				break;
			case "PC3":
				subOptionPrice = majorOptionPrice - subOptionPriceChanges.intValue();
				break;
			default:
				subOptionPrice = majorOptionPrice;
				break;
			}
		} else {
			subOptionPrice = majorOptionPrice;
		}
		setSubOptionPrice(subOptionPrice);
	}

	private void calculateSubOptionAdjustedPrice() {
		if (subOptionIsPriceChangeID != null && subOptionPriceChanges != null) {
			switch (subOptionIsPriceChangeID) {
			case "PC1":
				subOptionAdjustedPrice = subOptionPriceChanges.compareTo(BigDecimal.ONE) >= 0 ? subOptionPrice - majorOptionPrice
						: majorOptionPrice - subOptionPrice;
				finalSubOptionAdjustedPrice = subOptionPrice - majorOptionPrice;
				break;
			case "PC2":
				subOptionAdjustedPrice = subOptionPriceChanges.intValue();
				finalSubOptionAdjustedPrice = subOptionPriceChanges.intValue();
				break;
			case "PC3":
				subOptionAdjustedPrice = subOptionPriceChanges.intValue();
				finalSubOptionAdjustedPrice = -subOptionPriceChanges.intValue();
				break;
			default:
				subOptionAdjustedPrice = 0;
				finalSubOptionAdjustedPrice = subOptionAdjustedPrice;
				break;
			}
		} else {
			subOptionAdjustedPrice = 0;
			finalSubOptionAdjustedPrice = subOptionAdjustedPrice;
		}
		setSubOptionAdjustedPrice(subOptionAdjustedPrice);
		setFinalSubOptionAdjustedPrice(finalSubOptionAdjustedPrice);
	}

	private void calculateFinalSubOptionName() {
		if (subOptionIsPriceChangeID != null && subOptionPriceChanges != null) {
			String adjustment = (subOptionIsPriceChangeID.equals("PC1") && subOptionPriceChanges.compareTo(BigDecimal.ONE) < 0)
					|| subOptionIsPriceChangeID.equals("PC3") ? "(-" + fmtSubOptionAdjustedPrice + ")"
							: "(+" + fmtSubOptionAdjustedPrice + ")";
			switch (subOptionIsPriceChangeID) {
			case "PC1":
			case "PC2":
			case "PC3":
				finalSubOptionName = subOptionName + adjustment;
				break;
			default:
				finalSubOptionName = subOptionName;
				break;
			}
		} else {
			finalSubOptionName = subOptionName;
		}
	}

	public void updateMinerOptionFields() {
		calculateMinerOptionPrice();
		calculateMinerOptionAdjustedPrice();
		calculateFinalMinerOptionName();
	}

	private void calculateMinerOptionPrice() {
		if (minerOptionIsPriceChangeID != null && minerOptionPriceChanges != null) {
			switch (minerOptionIsPriceChangeID) {
			case "PC1":
				BigDecimal decMinerOptionPrice = minerOptionPriceChanges.multiply(new BigDecimal(subOptionPrice));
				minerOptionPrice = decMinerOptionPrice.setScale(-1, RoundingMode.HALF_UP).intValue();
				break;
			case "PC2":
				minerOptionPrice = subOptionPrice + minerOptionPriceChanges.intValue();
				break;
			case "PC3":
				minerOptionPrice = subOptionPrice - minerOptionPriceChanges.intValue();
				break;
			default:
				minerOptionPrice = subOptionPrice;
				break;
			}
		} else {
			minerOptionPrice = subOptionPrice;
		}
		setMinerOptionPrice(minerOptionPrice);
	}

	private void calculateMinerOptionAdjustedPrice() {
		if (minerOptionIsPriceChangeID != null && minerOptionPriceChanges != null) {
			switch (minerOptionIsPriceChangeID) {
			case "PC1":
				minerOptionAdjustedPrice = minerOptionPriceChanges.compareTo(BigDecimal.ONE) >= 0
						? minerOptionPrice - subOptionPrice
						: subOptionPrice - minerOptionPrice;
				finalMinerOptionAdjustedPrice = minerOptionPrice - subOptionPrice;
				break;
			case "PC2":
				minerOptionAdjustedPrice = minerOptionPriceChanges.intValue();
				finalMinerOptionAdjustedPrice = minerOptionPriceChanges.intValue();
				break;
			case "PC3":
				minerOptionAdjustedPrice = minerOptionPriceChanges.intValue();
				finalMinerOptionAdjustedPrice = -minerOptionPriceChanges.intValue();
				break;
			default:
				minerOptionAdjustedPrice = 0;
				finalMinerOptionAdjustedPrice = minerOptionAdjustedPrice;
				break;
			}
		} else {
			minerOptionAdjustedPrice = 0;
			finalMinerOptionAdjustedPrice = minerOptionAdjustedPrice;
		}
		setMinerOptionAdjustedPrice(minerOptionAdjustedPrice);
		setFinalMinerOptionAdjustedPrice(finalMinerOptionAdjustedPrice);
	}

	private void calculateFinalMinerOptionName() {
		if (minerOptionIsPriceChangeID != null && minerOptionPriceChanges != null) {
			String adjustment = (minerOptionIsPriceChangeID.equals("PC1") && minerOptionPriceChanges.compareTo(BigDecimal.ONE) < 0)
					|| minerOptionIsPriceChangeID.equals("PC3") ? "(-" + fmtMinerOptionAdjustedPrice + ")"
							: "(+" + fmtMinerOptionAdjustedPrice + ")";
			switch (minerOptionIsPriceChangeID) {
			case "PC1":
			case "PC2":
			case "PC3":
				finalMinerOptionName = minerOptionName + adjustment;
				break;
			default:
				finalMinerOptionName = minerOptionName;
				break;
			}
		} else {
			finalMinerOptionName = minerOptionName;
		}
	}

	public String getFinalMajorOptionName() {
		return finalMajorOptionName != null ? finalMajorOptionName : majorOptionName;
	}

	public String getFinalSubOptionName() {
		return finalSubOptionName != null ? finalSubOptionName : subOptionName;
	}

	public String getFinalMinerOptionName() {
		return finalMinerOptionName != null ? finalMinerOptionName : minerOptionName;
	}

	public void calculateTotalPrice() {
		switch (optionGrade) {
		case "OT0":
			orderPrice = price;
			totalPrice = price * count;
			break;
		case "OT1":
			orderPrice = majorOptionPrice;
			totalPrice = majorOptionPrice * count;
			break;
		case "OT2":
			orderPrice = subOptionPrice;
			totalPrice = subOptionPrice * count;
			break;
		case "OT3":
			orderPrice = minerOptionPrice;
			totalPrice = minerOptionPrice * count;
			break;
		default:
			orderPrice = price;
			totalPrice = price * count;
			break;
		}
		setOrderPrice(orderPrice);
		setTotalPrice(totalPrice);
	}
	
	public void calculateTotalAndOrderPrice() {
		totalPrice = orderPrice * count;
		setTotalPrice(totalPrice);
	}
	
}
