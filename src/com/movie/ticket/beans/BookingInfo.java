package com.movie.ticket.beans;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="bookingInfo")
@SessionScoped
public class BookingInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1760369465256834202L;
	
	private int movieId;
	private String nameofTheMovie;
	private String showTime;
	private int screenId;
	private int bookingId;
	private int numberOfSeatsBooked;
	private Date showDateBooked;
	private float price;
	
	
	
	
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Date getShowDateBooked() {
		return showDateBooked;
	}
	public void setShowDateBooked(Date showDateBooked) {
		this.showDateBooked = showDateBooked;
	}
	public int getNumberOfSeatsBooked() {
		return numberOfSeatsBooked;
	}
	public void setNumberOfSeatsBooked(int numberOfSeatsBooked) {
		this.numberOfSeatsBooked = numberOfSeatsBooked;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getNameofTheMovie() {
		return nameofTheMovie;
	}
	public void setNameofTheMovie(String nameofTheMovie) {
		this.nameofTheMovie = nameofTheMovie;
	}
	public String getShowTime() {
		return showTime;
	}
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	public int getScreenId() {
		return screenId;
	}
	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	
	
}
