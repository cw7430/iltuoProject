package kr.co.iltuo.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ShopCriteria extends Criteria {
	private int mainCategoryID;
	private String mainCategoryName;
	private String optionGrade;
	private int subCategoryID;
	private String subCategoryName;
	private String optionValidID;
	private int sortOrder;
	
	public ShopCriteria() {
        super();
    }
	
}
