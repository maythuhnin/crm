package com.eniac.projects.bet.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class DailyExpenseBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer busId;
	private Date fromDate;
	private String fromDateAsString;
	private Date toDate;
	private String toDateAsString;
	private String path;
	private Double onPaperIncomeLeave;
	private Double onPaperIncomeReturn;
	private Double inHandCash;
	private Double extraIncome;
	private Boolean restDay;

	private List<ExpenseItemBean> expenseItemList;

	public Integer getBusId() {
		return busId;
	}

	public void setBusId(Integer busId) {
		this.busId = busId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Double getOnPaperIncomeLeave() {
		return onPaperIncomeLeave;
	}

	public void setOnPaperIncomeLeave(Double onPaperIncomeLeave) {
		this.onPaperIncomeLeave = onPaperIncomeLeave;
	}

	public Double getOnPaperIncomeReturn() {
		return onPaperIncomeReturn;
	}

	public void setOnPaperIncomeReturn(Double onPaperIncomeReturn) {
		this.onPaperIncomeReturn = onPaperIncomeReturn;
	}

	public Double getInHandCash() {
		return inHandCash;
	}

	public void setInHandCash(Double inHandCash) {
		this.inHandCash = inHandCash;
	}

	public Double getExtraIncome() {
		return extraIncome;
	}

	public void setExtraIncome(Double extraIncome) {
		this.extraIncome = extraIncome;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getFromDateAsString() {
		return fromDateAsString;
	}

	public void setFromDateAsString(String fromDateAsString) {
		String[] fromDateArr = fromDateAsString.split("/");
		String yyyymmdd = fromDateArr[2] + "-" + fromDateArr[1] + "-" + fromDateArr[0];
		this.fromDate = java.sql.Date.valueOf(yyyymmdd);
		this.fromDateAsString = fromDateAsString;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getToDateAsString() {
		return toDateAsString;
	}

	public void setToDateAsString(String toDateAsString) {
		String[] toDateArr = toDateAsString.split("/");
		String yyyymmdd = toDateArr[2] + "-" + toDateArr[1] + "-" + toDateArr[0];
		this.toDate = java.sql.Date.valueOf(yyyymmdd);
		this.toDateAsString = toDateAsString;
	}

	public List<ExpenseItemBean> getExpenseItemList() {
		return expenseItemList;
	}

	public void setExpenseItemList(List<ExpenseItemBean> expenseItemList) {
		this.expenseItemList = expenseItemList;
	}

	public Boolean getRestDay() {
		return restDay;
	}

	public void setRestDay(Boolean restDay) {
		this.restDay = restDay;
	}

	@Override
	public String toString() {
		return String.format(
				"DailyExpenseBean [busId=%s, fromDate=%s, fromDateAsString=%s, toDate=%s, toDateAsString=%s, path=%s, onPaperIncomeLeave=%s, onPaperIncomeReturn=%s, inHandCash=%s, extraIncome=%s, restDay=%s, expenseItemList=%s]",
				busId, fromDate, fromDateAsString, toDate, toDateAsString, path, onPaperIncomeLeave,
				onPaperIncomeReturn, inHandCash, extraIncome, restDay, expenseItemList);
	}

}
