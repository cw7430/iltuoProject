package kr.co.iltuo.util;

public class Criteria {
	private int page;
	private int perPageNum;
	private int pageStart;
	
	public Criteria() {
		this.page = 1;
		this.perPageNum = 12;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		if (page <= 0) {
			this.page = 1;
		} else {
			this.page = page;
		}
	}
	
	public int getPerPageNum() {
		return perPageNum;
	}
	
	public void setPerPageNum(int perPageNum) {
		if (perPageNum != 12) {
			this.perPageNum = 12;
		} else {
			this.perPageNum = perPageNum;
		}	
	}
	
	public int getPageStart() {
		return (this.page - 1) * perPageNum;
	}
	
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}
	
	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + ", pageStart=" + pageStart + "]";
	}
}
