package com.movie.ticket.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="creditCardInfo")
@SessionScoped
public class CreditCardInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7603694652568342021L;
	
	
	private String nameOnCreditcard;
	private String typeOfCard;
	private int bookingId;
	private String expiryDate;
	private int cvv;
	private long numberOnCreditcard;
	
	
	public String getNameOnCreditcard() {
		return nameOnCreditcard;
	}
	public void setNameOnCreditcard(String nameOnCreditcard) {
		this.nameOnCreditcard = nameOnCreditcard;
	}
	public String getTypeOfCard() {
		return typeOfCard;
	}
	public void setTypeOfCard(String typeOfCard) {
		this.typeOfCard = typeOfCard;
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public int getCvv() {
		return cvv;
	}
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
	public long getNumberOnCreditcard() {
		return numberOnCreditcard;
	}
	public void setNumberOnCreditcard(long numberOnCreditcard) {
		this.numberOnCreditcard = numberOnCreditcard;
	}
	
	
}
