package kr.co.iltuo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private String userID;
	private String password;
	private String passwordDuplicateCheck;
	private String userName;
	private String phoneNum;
	private String email;
	private String joinDate;
	private String adminRightsID;
	private int addressID;
	private String postalCode;
	private String address;
	private String detailAddress;
	private String extraAddress;
	private String isMainID;
	private String fullAddress;
	private String optionValidID;
	private int priceID;
	private String addressSelect;

	public void setFullAddress() {
		String postalCode = this.postalCode != null ? this.postalCode : "";
	    String address = this.address != null ? this.address : "";
	    String detailAddress = this.detailAddress != null ? this.detailAddress : "";
	    String extraAddress = this.extraAddress != null ? this.extraAddress : "";
		this.fullAddress = "(" + postalCode + ") " + address + " " + detailAddress + " " + extraAddress;
	}
}
