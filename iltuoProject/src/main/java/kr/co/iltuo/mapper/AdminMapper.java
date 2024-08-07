package kr.co.iltuo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iltuo.dto.ShopDTO;
import kr.co.iltuo.util.ShopCriteria;

@Mapper
public interface AdminMapper {
	List<ShopDTO> mainCategoryInvalidList() throws Exception;
	int updateMainCategoryValid(ShopDTO shop) throws Exception;
	int updateMainCategoryName(ShopDTO shop) throws Exception;
	int insertMainCategory(ShopDTO shop) throws Exception;
	ShopDTO mainCategoryDetail(int mainCategoryID) throws Exception;
	List<ShopDTO> subCategoryInvalidList(int mainCategoryID) throws Exception;
	int insertSubCategory(ShopDTO shop) throws Exception;
	int updateSubCategoryValid(ShopDTO shop) throws Exception;
	int updateSubCategoryName(ShopDTO shop) throws Exception;
	int updateMajorOptionCategory(ShopDTO shop) throws Exception;
	int updateSubOptionCategory(ShopDTO shop) throws Exception;
	int updateMinerOptionCategory(ShopDTO shop) throws Exception;
	int updateOptionGrade(ShopDTO shop) throws Exception;
	int updateMajorOptionValid(ShopDTO shop) throws Exception;
	int updateSubOptionValid(ShopDTO shop) throws Exception;
	int updateMinerOptionValid(ShopDTO shop) throws Exception;
	List<ShopDTO> optionGradeList() throws Exception;
	List<ShopDTO> isPriceChangeList() throws Exception;
	List<ShopDTO> majorOptionList(int mainCategoryID) throws Exception;
	List<ShopDTO> subOptionList(int mainCategoryID) throws Exception;
	List<ShopDTO> minerOptionList(int mainCategoryID) throws Exception;
	int updateMajorOptionSort (ShopDTO shop) throws Exception;
	int updateSubOptionSort (ShopDTO shop) throws Exception;
	int updateMinerOptionSort (ShopDTO shop) throws Exception;
	int updateMajorOptionValidOne(ShopDTO shop) throws Exception;
	int updateSubOptionValidOne(ShopDTO shop) throws Exception;
	int updateMinerOptionValidOne(ShopDTO shop) throws Exception;
	int insertMajorOption(ShopDTO shop) throws Exception;
	int insertSubOption(ShopDTO shop) throws Exception;
	int insertMinerOption(ShopDTO shop) throws Exception;
	List<ShopDTO> adminProductList(ShopCriteria criteria) throws Exception;
	int adminGetTotalCountProduct(ShopCriteria criteria) throws Exception;
	List<ShopCriteria> adminMainCategoryList() throws Exception;
}
