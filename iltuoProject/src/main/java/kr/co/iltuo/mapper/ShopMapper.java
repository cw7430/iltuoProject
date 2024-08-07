package kr.co.iltuo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.iltuo.dto.ShopDTO;
import kr.co.iltuo.util.ShopCriteria;

@Mapper
public interface ShopMapper {
	List<ShopDTO> getMainCategoryList() throws Exception;
	ShopDTO getMainCategoryID(int mainCategoryID) throws Exception;
	List<ShopCriteria> getSubCategoryList(int mainCategoryID) throws Exception;
	List<ShopDTO> productList(ShopCriteria criteria) throws Exception;
	List<ShopDTO> recommenedProductList() throws Exception;
	int getTotalCountProduct(ShopCriteria criteria) throws Exception;
	ShopDTO productDetail(int productID) throws Exception;
	ShopDTO getOptionList(int mainCategoryID) throws Exception;
	List<ShopDTO> getMajorOptionList(int productID) throws Exception;
	ShopDTO getMajorOptionPrice(@Param("productID") int productID, @Param("majorOptionID") int majorOptionID);
	List<ShopDTO> getSubOptionList(@Param("productID") int productID) throws Exception;
	ShopDTO getSubOptionOne(int subOptionID) throws Exception;
	List<ShopDTO> getMinerOptionList(@Param("productID") int productID) throws Exception;
	ShopDTO getMinerOptionOne(int minerOptionID) throws Exception;
	int insertCart(ShopDTO shop) throws Exception;
	List<ShopDTO> getCartList(String userID) throws Exception;
	int deleteCartOne(int cartID) throws Exception;
	
}
