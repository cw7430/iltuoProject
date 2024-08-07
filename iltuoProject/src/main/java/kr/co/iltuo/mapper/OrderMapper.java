package kr.co.iltuo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.iltuo.dto.OrderDTO;
import kr.co.iltuo.dto.ShopDTO;
import kr.co.iltuo.dto.UserDTO;

@Mapper
public interface OrderMapper {
	int insertPrice(ShopDTO shop) throws Exception;
	int insertOrderOne(ShopDTO shop) throws Exception;
	ShopDTO getOrderOne(@Param("orderID") int orderID, @Param("userID") String userID) throws Exception;
	int setPriceID(ShopDTO shop) throws Exception;
	int insertOrderMultiple(@Param("cartList") List<ShopDTO> cartList) throws Exception;
	int deleteAllCart(String userID) throws Exception;
	OrderDTO getPrice(@Param("priceID") int priceID, @Param("userID") String userID) throws Exception;
	int insertPayment(OrderDTO order) throws Exception;
	int insertUnregisteredAddress(UserDTO user) throws Exception;
	int insertDeliveryAddress(OrderDTO order) throws Exception;
	int updateOrderStatus(OrderDTO order) throws Exception;
	List<OrderDTO> getPaymentList(String userID) throws Exception;
	OrderDTO getPaymentOne(int priceID) throws Exception;
	List<ShopDTO> getOrderList(int priceID) throws Exception;
	UserDTO getOrderAddress(int priceID) throws Exception;
	int updateDeliveryStatus(OrderDTO order) throws Exception;
	int updatePaymentStatus(OrderDTO order) throws Exception;
}
