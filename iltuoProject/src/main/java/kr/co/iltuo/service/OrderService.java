package kr.co.iltuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.iltuo.dto.OrderDTO;
import kr.co.iltuo.dto.ShopDTO;
import kr.co.iltuo.dto.UserDTO;
import kr.co.iltuo.mapper.OrderMapper;
import kr.co.iltuo.mapper.ShopMapper;
import kr.co.iltuo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	ShopMapper shopMapper;
	@Autowired
	UserMapper userMapper;

	private final String encoding = "UTF-8";

	public String orderOne(int orderID, String userID ,Model model) {
		final String SURL = "order/orderOne";
		final String FURL = "redirect:/";
		String view = FURL;
		try {
			ShopDTO order = orderMapper.getOrderOne(orderID, userID);
			if (!order.getOptionGrade().equals("OT0")) {
				order.updateMajorOptionFields();
				if (!order.getOptionGrade().equals("OT1")) {
					order.updateSubOptionFields();
					if (!order.getOptionGrade().equals("OT2")) {
						order.updateMinerOptionFields();
					}
				}
			}
			order.calculateTotalPrice();
			model.addAttribute("order", order);
			view = SURL;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	public String orderOne(ShopDTO shop, HttpServletRequest request) {
		String view = "redirect:/";
		try {
			request.setCharacterEncoding(encoding);
			int insertOrderOne = orderMapper.insertOrderOne(shop);
			if (insertOrderOne == 1) {
				int orderID = shop.getOrderID();
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				String userID = authentication.getName();
				view = "redirect:/orderOne?orderID=" + orderID + "&userID=" + userID;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	@Transactional
	public String insertPriceOne(ShopDTO shop, HttpServletRequest request) {
		String view = "redirect:/";
		try {
			request.setCharacterEncoding(encoding);
			int insertPrice = orderMapper.insertPrice(shop);
			if (insertPrice == 1) {
				int priceID = shop.getPriceID();
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				String userID = authentication.getName();
				int setPriceID = orderMapper.setPriceID(shop);
				if(setPriceID == 1) {
					view = "redirect:/payment?priceID=" + priceID + "&userID=" + userID;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	@Transactional
	public String payment(int priceID, String userID, Model model) {
		final String SURL = "order/payment";
		final String FURL = "redirect:/";
		String view = FURL;
		try {
			OrderDTO price = orderMapper.getPrice(priceID, userID);
			UserDTO mainAddress = userMapper.findMainAddress(userID);
			List<UserDTO> address = userMapper.findAddress(userID);
			mainAddress.setFullAddress();
			address.forEach(UserDTO::setFullAddress);
			model.addAttribute("price", price);
			model.addAttribute("mainAddress", mainAddress);
			model.addAttribute("address", address);
			view = SURL;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	@Transactional
	public String insertPriceMultiple(ShopDTO shop, List<ShopDTO> cartList, HttpServletRequest request) {
		String view = "redirect:/";
		try {
			request.setCharacterEncoding(encoding);
			int insertPrice = orderMapper.insertPrice(shop);
			if (insertPrice == 1) {
				int priceID = shop.getPriceID();
				cartList.forEach(item -> item.setPriceID(priceID));
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				String userID = authentication.getName();
				int insertOrder = orderMapper.insertOrderMultiple(cartList);
				if(insertOrder > 0) {
					log.info("주문테이블" + insertOrder + "건 삽입");
					int deleteCart = orderMapper.deleteAllCart(userID);
					log.info("장바구니" + deleteCart + "건 삭제");
					view = "redirect:/payment?priceID=" + priceID + "&userID=" + userID;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	@Transactional
	public String payment(UserDTO user, OrderDTO order, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/orderList?userID=" + order.getUserID();
		String msg = null;
		try {
			request.setCharacterEncoding(encoding);
			if(user.getAddressSelect().equals("B")) {
				int insertUnregisteredAddress = orderMapper.insertUnregisteredAddress(user);
				if(insertUnregisteredAddress > 0) {
					int addressID = user.getAddressID();
					order.setAddressID(addressID);
				}
			}
			int insertPayment = orderMapper.insertPayment(order);
			log.info("결제내역 " + insertPayment + "건 insert");
			if(insertPayment > 0) {
				String paymentMethodID = order.getPaymentMethodID();
				int insertDeliveryAddress = orderMapper.insertDeliveryAddress(order);
				log.info("주소 " + insertDeliveryAddress + "건 insert");
				if(insertDeliveryAddress > 0) {
					if(paymentMethodID.equals("PM0")) {
						int updateOrderStatus = orderMapper.updateOrderStatus(order);
						log.info("주문상태 " + updateOrderStatus + "건 update");
						if(updateOrderStatus > 0) {
							msg = "결제가 완료되었습니다.";
						}
					} else if(paymentMethodID.equals("PM1")) {
						msg = "지정된 계좌로 입금 바랍니다.";
					}
				}
			} else {
				msg = "결제 중 오류가 발생하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "결제 중 오류가 발생하였습니다.";
		}
		rttr.addFlashAttribute("msg", msg);
		log.info(msg);
		return view;
	}
	
	public String orderList(String userID, Model model) {
		final String SURL = "order/orderList";
		final String FURL = "redirect:/";
		String view = FURL;
		try {
			List<OrderDTO> orderList = orderMapper.getPaymentList(userID);
			model.addAttribute("orderList", orderList);
			view = SURL;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	@Transactional
	public String orderDetail(int priceID, String userID, Model model) {
		final String SURL = "order/orderDetail";
		final String FURL = "redirect:/orderList?userID=" + userID;
		String view = FURL;
		try {
			OrderDTO payment = orderMapper.getPaymentOne(priceID);
			List<ShopDTO> orderList = orderMapper.getOrderList(priceID);
			for (ShopDTO dto : orderList) {
				dto.calculateTotalAndOrderPrice();
			}
			UserDTO address = orderMapper.getOrderAddress(priceID);
			address.setFullAddress();
			model.addAttribute("payment", payment);
			model.addAttribute("orderList", orderList);
			model.addAttribute("address", address);
			view = SURL;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	@Transactional
	public String cancelOrder(OrderDTO order, HttpServletRequest request, RedirectAttributes rttr) {
		final String listURL = "redirect:/orderList?userID=" + order.getUserID();
		final String detailURL = "redirect:/orderDetail?priceID=" + order.getPriceID() + "&userID=" + order.getUserID();
		String view = "redirect:/orderList?userID=" + order.getUserID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			request.setCharacterEncoding(encoding);
			int cancelDelivery = orderMapper.updateDeliveryStatus(order);
			if(cancelDelivery > 0) {
				log.info("배송번호 " + order.getPriceID() + "번 상품 " + cancelDelivery + "건 취소완료");
				int canclePayment = orderMapper.updatePaymentStatus(order);
				if(canclePayment > 0) {
					log.info("결제번호 " + order.getPriceID() + "번 상품 " + canclePayment + "건 취소완료");
					int cancleOrder = orderMapper.updateOrderStatus(order);
					if(cancleOrder > 0) {
						log.info("주문번호 " + order.getPriceID() + "번 상품 " + cancleOrder + "건 취소완료");
						msg = "주문번호 " + order.getPriceID() + "번 상품이 취소완료 되었습니다.";
						if(order.getViewOrder().equals("a")) {
							view = listURL;
						} else if(order.getViewOrder().equals("b")) {
							view = detailURL;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		log.info(msg);
		return view;
	}
}
