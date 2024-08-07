package kr.co.iltuo.dto;

import java.util.List;

import lombok.Data;

@Data
public class CartListDTO {
	private List<ShopDTO> cartList;
}
