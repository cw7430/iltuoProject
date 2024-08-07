package kr.co.iltuo.util;

import lombok.Data;

@Data
public class PageMaker {
	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	private int totalPage;
	private int displayPageNum = 5;
	private Criteria criteria;
	
	public PageMaker(Criteria criteria, int totalCount) {
		this.criteria = criteria;
		this.totalCount = totalCount;
		calculatePages();
	}
	
	private void calculatePages() {
		endPage = (int) (Math.ceil(criteria.getPage() / (double) displayPageNum) * displayPageNum);
		startPage = (endPage - displayPageNum) + 1;
		int tempEndPage = (int) (Math.ceil(totalCount / (double) criteria.getPerPageNum()));
		if (endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		prev = startPage > 1;
		next = endPage * criteria.getPerPageNum() < totalCount;
		
		totalPage = tempEndPage;
	}
}
