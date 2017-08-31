package com.movie.ticket.controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.movie.ticket.beans.BookingInfo;
import com.movie.ticket.beans.CreditCardInfo;
import com.movie.ticket.beans.Movie;
import com.movie.ticket.beans.Screen;
import com.movie.ticket.beans.UserInfo;
import com.movie.ticket.sqlconnection.ConnectionUtils;
import com.movie.ticket.sqlconnection.SqlStatements;

@ManagedBean(name="movieController")
@SessionScoped
public class MovieController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{movie}")
	private Movie movie;
	@ManagedProperty(value="#{screen}")
	private Screen screen;
	@ManagedProperty(value="#{bookingInfo}")
	private BookingInfo bookingInfo;
	@ManagedProperty(value="#{creditCardInfo}")
	private CreditCardInfo creditCardInfo;
	@ManagedProperty(value="#{userInfo}")
	private UserInfo userInfo;
	
	private Movie newMovie;
	
	
	public Movie getNewMovie() {
		return newMovie;
	}

	public void setNewMovie(Movie newMovie) {
		this.newMovie = newMovie;
	}

	private List<String> movieNamesList = new ArrayList<String>();
	private List<Movie> movieList = new ArrayList<Movie>();
	private List<Date> showDateList = new ArrayList<Date>();
	private List<Integer> screenIdList = new ArrayList<Integer>();
	private List<String> showTimeList = new ArrayList<String>();
	private List<String> displayImages = new ArrayList<String>();
	
	
	public static Connection connection = null;  
    public static PreparedStatement preparedStatement = null;  
    public static ResultSet resultSet = null;
    
    @PostConstruct
    public void getAllMovieList(){
    	movieList = new ArrayList<Movie>();
    	displayImages = new ArrayList<String>();
    	try{ 	        
    		
    		connection= ConnectionUtils.getConnection();
    		preparedStatement = connection.prepareStatement(SqlStatements.selectAllMovies);
    		resultSet = preparedStatement.executeQuery();
    		while(resultSet.next()){
    			Movie m = new Movie();
    			m.setMovieId(resultSet.getInt(1));
    			m.setNameOfMovie(resultSet.getString(2));
    			m.setMovieType(resultSet.getString(3));
    			m.setRuntime(resultSet.getString(4));
    			m.setTrailer(resultSet.getString(5));
    			m.setSynopsis(resultSet.getString(6));
    			m.setCategory(resultSet.getString(7));
    			m.setCast(resultSet.getString(8));
    			m.setRating(resultSet.getInt(9));
    			movieList.add(m);
    			movieNamesList.add(resultSet.getString(2));
    			displayImages.add(resultSet.getString(2));
    		}     		
    		
    		//System.out.println(movieList.get(3).getSynopsis());
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
			ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
		}
    }
    
    /*public List<Movie> getAllMovieList1(){
    	List<Movie> movieList = new ArrayList<Movie>();
    	
    	try{ 	        
    		
    		connection= ConnectionUtils.getConnection();
    		preparedStatement = connection.prepareStatement(SqlStatements.selectAllMovies);
    		resultSet = preparedStatement.executeQuery();
    		while(resultSet.next()){
    			Movie m = new Movie();
    			m.setMovieId(resultSet.getInt(1));
    			m.setNameOfMovie(resultSet.getString(2));
    			m.setMovieType(resultSet.getString(3));
    			m.setRuntime(resultSet.getString(4));
    			m.setTrailer(resultSet.getString(5));
    			m.setSynopsis(resultSet.getString(6));
    			m.setCategory(resultSet.getString(7));
    			m.setCast(resultSet.getString(8));
    			m.setRating(resultSet.getInt(9));
    			movieList.add(m);
    			
    		}   
    		return movieList;
    		
    		//System.out.println(movieList.get(3).getSynopsis());
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
			ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
		}
    	return movieList;
    }
    
    public List<String> getAllMovieList2(){
    	List<String> movieList = new ArrayList<String>();
    	
    	try{ 	        
    		
    		connection= ConnectionUtils.getConnection();
    		preparedStatement = connection.prepareStatement(SqlStatements.selectAllMovies);
    		resultSet = preparedStatement.executeQuery();
    		while(resultSet.next()){
    			Movie m = new Movie();
    			m.setMovieId(resultSet.getInt(1));
    			m.setNameOfMovie(resultSet.getString(2));
    			m.setMovieType(resultSet.getString(3));
    			m.setRuntime(resultSet.getString(4));
    			m.setTrailer(resultSet.getString(5));
    			m.setSynopsis(resultSet.getString(6));
    			m.setCategory(resultSet.getString(7));
    			m.setCast(resultSet.getString(8));
    			m.setRating(resultSet.getInt(9));
    			movieList.add(resultSet.getString(2));
    			
    		}   
    		return movieList;
    		
    		//System.out.println(movieList.get(3).getSynopsis());
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
			ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
		}
    	return movieList;
    }*/


	public String book(){
		showDateList = new ArrayList<Date>();
		try{
			connection= ConnectionUtils.getConnection();
    		preparedStatement = connection.prepareStatement(SqlStatements.selectShowDatesByMovie);
    		preparedStatement.setString(1, this.movie.getNameOfMovie());
    		resultSet = preparedStatement.executeQuery();
    		while(resultSet.next()){
    			java.sql.Date sqlDate = new java.sql.Date(resultSet.getDate(1).getTime());
    			java.util.Date newDate = new java.util.Date(sqlDate.getTime());
    			showDateList.add(newDate);
    		}
    		
    		return "booking";
    		
		}catch(Exception e){
    		e.printStackTrace();
    	}finally{
			ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
		}
		return "failure";
	}
	
	public String submitBooking(){
		String emailId = "";
		String emailmessage="";
		try{
			
			 Random r = new Random( System.currentTimeMillis() );
			 int bookingId = 10000+r.nextInt(59458);
			 int availableSeats= this.screen.getNumberOfSeats();
			 int bookedSeats = this.bookingInfo.getNumberOfSeatsBooked();
			 connection = ConnectionUtils.getConnection();
			 preparedStatement = connection.prepareStatement(SqlStatements.updateScreenTable);
			 preparedStatement.setInt(1, (availableSeats-bookedSeats));
			 preparedStatement.setString(2, this.movie.getNameOfMovie());
			 preparedStatement.setInt(3, this.bookingInfo.getScreenId());
			 preparedStatement.setString(4, this.bookingInfo.getShowTime());
			 preparedStatement.setDate(5, new java.sql.Date(this.bookingInfo.getShowDateBooked().getTime()));
			 int updateScreen = preparedStatement.executeUpdate();
			 
			 preparedStatement = null;
			 preparedStatement = connection.prepareStatement(SqlStatements.insertIntoBookingTable);
			 preparedStatement.setString(1, this.movie.getNameOfMovie());
			 preparedStatement.setString(2, this.movie.getNameOfMovie());
			 preparedStatement.setString(3, this.bookingInfo.getShowTime());
			 preparedStatement.setInt(4, this.bookingInfo.getScreenId());
			 preparedStatement.setInt(5, bookingId);
			 preparedStatement.setInt(6, this.bookingInfo.getNumberOfSeatsBooked());
			 preparedStatement.setDate(7, new java.sql.Date(this.bookingInfo.getShowDateBooked().getTime()));
			 int insertBooking = preparedStatement.executeUpdate();
			 
			 preparedStatement = null;
			 preparedStatement = connection.prepareStatement(SqlStatements.insertIntoCreditCardInfoTable);
			 preparedStatement.setString(1, this.creditCardInfo.getNameOnCreditcard());
			 preparedStatement.setLong(2, this.creditCardInfo.getNumberOnCreditcard());
			 preparedStatement.setString(3, this.creditCardInfo.getExpiryDate());
			 preparedStatement.setString(4, this.creditCardInfo.getTypeOfCard());
			 preparedStatement.setInt(5, this.creditCardInfo.getCvv());
			 preparedStatement.setInt(6, bookingId);
			 int insertCreditInfo = preparedStatement.executeUpdate();
			 
			 this.bookingInfo.setBookingId(bookingId);
			 
			 
			 
			 if(insertBooking>0 && insertCreditInfo>0 && updateScreen>0){
				 preparedStatement =  null;
				 preparedStatement = connection.prepareStatement(SqlStatements.selectEmailIdFromUserByUserName);
				 preparedStatement.setString(1, this.userInfo.getUserName());
				 resultSet = preparedStatement.executeQuery();
				 while(resultSet.next()){
					 emailId = resultSet.getString(1);
				 }
				 
				 emailmessage = "Your tikcet is booked successfull. your booking id is:"+this.bookingInfo.getBookingId()+". Thank you for booking!!!";
				 sendMailNotification(emailId,this.bookingInfo.getBookingId(), emailmessage);
				 return "bookingSuccess";
			 }
			 
			 
			   
		}catch(Exception e){
    		e.printStackTrace();
    	}finally{
			ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
		}
		
		return "failure";
	}
	
	public void onselectDate(){
		
		//System.out.println("inside date select");
			screenIdList = new ArrayList<Integer>();

			try{	
				connection = ConnectionUtils.getConnection();
				preparedStatement = connection.prepareStatement(SqlStatements.selectScreenListByMovieAndShowDate);
				preparedStatement.setString(1, this.movie.getNameOfMovie());
				preparedStatement.setDate(2, new java.sql.Date(this.bookingInfo.getShowDateBooked().getTime()));
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){
					screenIdList.add(resultSet.getInt(1));
				}
				
			}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
				ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
			}
		
		
	}
	
	public void onScreenSelect(){
		showTimeList = new ArrayList<String>();
			try{
				connection = ConnectionUtils.getConnection();
				preparedStatement = connection.prepareStatement(SqlStatements.selectShowTimeByMovieShowDateAndScreenId);
				preparedStatement.setString(1, this.movie.getNameOfMovie());
				preparedStatement.setDate(2, new java.sql.Date(this.bookingInfo.getShowDateBooked().getTime()));
				preparedStatement.setInt(3, this.bookingInfo.getScreenId());
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){
					showTimeList.add(resultSet.getString(1));
				}
				
			}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
				ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
			}
		
		
	}
	
	public void onShowTimeSelect(){
		
		
			try{
				connection = ConnectionUtils.getConnection();
				preparedStatement = connection.prepareStatement(SqlStatements.selectAvailableSeatsByMovieShowDateScreenIdAndShowTime);
				preparedStatement.setString(1, this.movie.getNameOfMovie());
				preparedStatement.setDate(2, new java.sql.Date(this.bookingInfo.getShowDateBooked().getTime()));
				preparedStatement.setInt(3, this.bookingInfo.getScreenId());
				preparedStatement.setString(4, this.bookingInfo.getShowTime());
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){
					this.screen.setNumberOfSeats(resultSet.getInt(1));
					this.bookingInfo.setPrice(resultSet.getFloat(2));
				}
				
			}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
				ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
			}
		
		
	}
	
	public void onNumberOfSeats(){
		float updatePrice =0.0f;
		int numberOfSeatsBooked = this.bookingInfo.getNumberOfSeatsBooked();
		float price = this.bookingInfo.getPrice();
		updatePrice=numberOfSeatsBooked*price;
		this.bookingInfo.setPrice(updatePrice);
	}
	
	public String cancelTicket(){
		String emailId="";
		String emailmessage = "";
		try{
			connection = ConnectionUtils.getConnection();
			preparedStatement = connection.prepareStatement(SqlStatements.deleteCreditInfobyBookingId);
			preparedStatement.setInt(1, this.bookingInfo.getBookingId());
			int delBooking = preparedStatement.executeUpdate();
			preparedStatement=null;
			
			preparedStatement = connection.prepareStatement(SqlStatements.deleteBookingbyBookingId);
			preparedStatement.setInt(1, this.bookingInfo.getBookingId());
			int deleteBooking = preparedStatement.executeUpdate();
			
			if(delBooking>0 && deleteBooking>0){
				 preparedStatement =  null;
				 preparedStatement = connection.prepareStatement(SqlStatements.selectEmailIdFromUserByUserName);
				 preparedStatement.setString(1, this.userInfo.getUserName());
				 resultSet = preparedStatement.executeQuery();
				 while(resultSet.next()){
					 emailId = resultSet.getString(1);
				 }
				 emailmessage = "Your ticket with booking id:"+this.bookingInfo.getBookingId()+" is successfully cancelled. Thank you!!";
				 sendMailNotification(emailId,this.bookingInfo.getBookingId(), emailmessage);
				
				return "cancelSuccess";
			}
			
		}catch(Exception e){
    		e.printStackTrace();
    	}finally{
			ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
		}
		return "failure";
	}
	
	public void updateBookingDetails(){
		
		try{
			connection = ConnectionUtils.getConnection();
			preparedStatement = connection.prepareStatement(SqlStatements.selectBookingDetailsByBookingId);
			preparedStatement.setInt(1, this.bookingInfo.getBookingId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				this.bookingInfo.setNameofTheMovie(resultSet.getString(1));
				this.bookingInfo.setNumberOfSeatsBooked(resultSet.getInt(2));
				this.bookingInfo.setScreenId(resultSet.getInt(3));
				java.util.Date newDate = resultSet.getDate(4);
				this.bookingInfo.setShowDateBooked(newDate);
				this.bookingInfo.setShowTime(resultSet.getString(5));
			}
			
		}catch(Exception e){
    		e.printStackTrace();
    	}finally{
			ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
		}
		
	}
	
	public String updateRating(){
		int newRating =0;
		int currentRating = 0;
		try{
			
			connection = ConnectionUtils.getConnection();
			preparedStatement = connection.prepareStatement(SqlStatements.selectTrailerAndRatingbyMovieName);
			preparedStatement.setString(1, this.bookingInfo.getNameofTheMovie());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				currentRating = resultSet.getInt(2);
			}
			preparedStatement = null;
			newRating = (currentRating+this.movie.getRating())/2;
			preparedStatement = connection.prepareStatement(SqlStatements.updateRatinginMovies);
			preparedStatement.setInt(1, newRating);
			preparedStatement.setString(2, this.bookingInfo.getNameofTheMovie());
			int updateRating = preparedStatement.executeUpdate();
			if(updateRating>0){
				this.movie.setRating(newRating);
				return "ratingSuccess";
			}

			
		}catch(Exception e){
    		e.printStackTrace();
    	}finally{
			ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
		}
		return "failure";
	}
	
	public void onMovieNameSelect(){
		//System.out.println("inside on movie select");
		try{
			connection = ConnectionUtils.getConnection();
			preparedStatement = connection.prepareStatement(SqlStatements.selectTrailerAndRatingbyMovieName);
			preparedStatement.setString(1, this.movie.getNameOfMovie());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				this.movie.setTrailer(resultSet.getString(1));
			}
			//System.out.println("trailer link"+this.movie.getTrailer());
		}catch(Exception e){
    		e.printStackTrace();
    	}finally{
			ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
		}
	}
	
	
	public String validate(){
		
		try{		
			connection = ConnectionUtils.getConnection();
			preparedStatement = connection.prepareStatement(SqlStatements.selectUserDetails);
			preparedStatement.setString(1, this.userInfo.getUserName());
			preparedStatement.setString(2, this.userInfo.getPassword());		
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				String userName = resultSet.getString(1);
				if(userName.equalsIgnoreCase("admin")){
					return "adminHome";
				}else if(userName.equalsIgnoreCase(this.userInfo.getUserName())){
					return "home";
				}	
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
		}
		return "failure";
	}
	
	public String validateGuest(){
		try{
			this.userInfo.setUserName("Guest");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "home";
	}
	
	public void logout() {
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        FacesContext.getCurrentInstance()
                .getApplication().getNavigationHandler()
                .handleNavigation(FacesContext.getCurrentInstance(), null, "login.xhtml");
    }

	
	public String register(){
		try{
			connection = ConnectionUtils.getConnection();
			preparedStatement = connection.prepareStatement(SqlStatements.insertUserDetails);
			preparedStatement.setString(1, this.userInfo.getFirstName());
			preparedStatement.setString(2, this.userInfo.getLastName());
			preparedStatement.setString(3, this.userInfo.getUserName());
			preparedStatement.setString(4, this.userInfo.getPassword());
			preparedStatement.setString(5, this.userInfo.getEmailId());
			preparedStatement.setString(6, this.userInfo.getPhoneNumber());
			int insertUSer = preparedStatement.executeUpdate();
			if(insertUSer>0){
				return "login";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
		}
		return "failure";
	}
	
	
	public String addMovie(){
		try{
			connection = ConnectionUtils.getConnection();
			preparedStatement = connection.prepareStatement(SqlStatements.insertMovieDetails);
			preparedStatement.setInt(1, this.movie.getMovieId());
			preparedStatement.setString(2, this.movie.getNameOfMovie());
			preparedStatement.setString(3, this.movie.getCategory());
			preparedStatement.setString(4, this.movie.getCast());
			preparedStatement.setString(5, this.movie.getRuntime());
			preparedStatement.setString(6, this.movie.getMovieType());
			preparedStatement.setString(7, this.movie.getTrailer());
			preparedStatement.setInt(8, this.movie.getRating());
			preparedStatement.setString(9, this.movie.getSynopsis());
			int insertMovieDetails = preparedStatement.executeUpdate();
			if(insertMovieDetails>0){
				this.movieList.add(this.movie);
				this.movieNamesList.add(this.movie.getNameOfMovie());
				this.displayImages.add(this.movie.getNameOfMovie());
				return "adminHome";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
		}
		return "adminFailure";
	}
	
	public String addScreen(){
		try{
			connection = ConnectionUtils.getConnection();
			preparedStatement = connection.prepareStatement(SqlStatements.insertIntoScreen);
			preparedStatement.setInt(1, this.screen.getMovieId());
			preparedStatement.setString(2, this.screen.getShowTime());
			preparedStatement.setInt(3, this.screen.getNumberOfSeats());
			preparedStatement.setInt(4, this.screen.getShowId());
			preparedStatement.setInt(5, this.screen.getScreenId());
			preparedStatement.setDate(6, new java.sql.Date(this.screen.getShowDate().getTime()));
			preparedStatement.setFloat(7, this.screen.getPrice());
			int insertScreenDetails = preparedStatement.executeUpdate();
			if(insertScreenDetails>0){
				return "adminHome";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
		}
		return "adminFailure";
	}
	
	public void onMovieNameSelectinAdmin(){
		
		try{
			connection = ConnectionUtils.getConnection();
			preparedStatement = connection.prepareStatement(SqlStatements.selectMovieDetailsbyMovieName);
			preparedStatement.setString(1, this.movie.getNameOfMovie());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				this.screen.setMovieId(resultSet.getInt(1));
				this.movie.setMovieId(resultSet.getInt(1));
				this.movie.setMovieType(resultSet.getString(2));
				this.movie.setRuntime(resultSet.getString(3));
				this.movie.setCast(resultSet.getString(4));
				this.movie.setCategory(resultSet.getString(5));
				this.movie.setRating(resultSet.getInt(6));
				this.movie.setTrailer(resultSet.getString(7));
				this.movie.setSynopsis(resultSet.getString(8));
				this.movie.setNameOfMovie(resultSet.getString(9));
			}
			
		}catch(Exception e){
    		e.printStackTrace();
    	}finally{
			ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
		}
		
	}
	
	public String updateMovie(){
		
		try{
			connection = ConnectionUtils.getConnection();
			preparedStatement = connection.prepareStatement(SqlStatements.updateMovieDetailsbyMovieId);
			preparedStatement.setString(1, this.movie.getNameOfMovie());
			preparedStatement.setString(2, this.movie.getCategory());
			preparedStatement.setString(3, this.movie.getCast());
			preparedStatement.setString(4, this.movie.getRuntime());
			preparedStatement.setString(5, this.movie.getMovieType());
			preparedStatement.setString(6, this.movie.getTrailer());
			preparedStatement.setString(7, this.movie.getSynopsis());
			preparedStatement.setInt(8, this.movie.getMovieId());
			int updateMovieDetails = preparedStatement.executeUpdate();
			if(updateMovieDetails>0){
			
			
				return "updateSuccess";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
		}
		return "adminFailure";
	}
	
	public String deleteMovie(){
		
		try{
			connection = ConnectionUtils.getConnection();
			
			preparedStatement = connection.prepareStatement(SqlStatements.deleteScreenDetailsbyMovieID);
			preparedStatement.setInt(1, this.movie.getMovieId());
			int deleteScreenDetails = preparedStatement.executeUpdate();
			
			preparedStatement=null;
			preparedStatement = connection.prepareStatement(SqlStatements.deleteMovieDetailsbyMovieID);
			preparedStatement.setInt(1, this.movie.getMovieId());
			int deleteMovieDetails = preparedStatement.executeUpdate();
			if(deleteMovieDetails>0 && deleteScreenDetails>0){
				
				return "deleteSuccess";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			ConnectionUtils.closeAll(connection, preparedStatement, resultSet);
		}
		return "adminFailure";
	}
	
	
	public void sendMailNotification(String emailRecipient, int bookingId, String emailmessage1){
		
		 final String username = "vechalapu.551uday@yahoo.com";
	        final String password = "";
	        final String recipient = emailRecipient;
	        final String subject = "Your Booking ID is:"+bookingId;
	        final String emailmessage = emailmessage1;

	        Properties props = new Properties();
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.host", "smtp.mail.yahoo.com");
	        props.put("mail.smtp.port", "587");
	        props.put("mail.stmp.user", "vechalapu.551uday@yahoo.com");
	        props.put("mail.smtp.password", "");

	        Session session = Session.getDefaultInstance(props, new Authenticator() {
	        	@Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	        		String username2 = "vechalapu.551uday@yahoo.com";
	                String password2 = "";
	                return new PasswordAuthentication(username2, password2);
	            }
	          });

	        try {
	        	System.out.println("recipient email Id:"+emailRecipient);

	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(username));
	            message.setRecipients(Message.RecipientType.TO,    InternetAddress.parse(recipient));
	            message.setSubject(subject);
	            message.setText(emailmessage);

	            Transport.send(message);

	            System.out.println("Done");

	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }

	}
	
	
	public List<Movie> getMovieList() {
		return movieList;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}


	public Screen getScreen() {
		return screen;
	}


	public void setScreen(Screen screen) {
		this.screen = screen;
	}


	public BookingInfo getBookingInfo() {
		return bookingInfo;
	}


	public void setBookingInfo(BookingInfo bookingInfo) {
		this.bookingInfo = bookingInfo;
	}


	public CreditCardInfo getCreditCardInfo() {
		return creditCardInfo;
	}


	public void setCreditCardInfo(CreditCardInfo creditCardInfo) {
		this.creditCardInfo = creditCardInfo;
	}


	public UserInfo getUserInfo() {
		return userInfo;
	}


	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}


	public List<Date> getShowDateList() {
		return showDateList;
	}


	public void setShowDateList(List<Date> showDateList) {
		this.showDateList = showDateList;
	}


	public List<Integer> getScreenIdList() {
		return screenIdList;
	}


	public void setScreenIdList(List<Integer> screenIdList) {
		this.screenIdList = screenIdList;
	}


	public List<String> getShowTimeList() {
		return showTimeList;
	}


	public void setShowTimeList(List<String> showTimeList) {
		this.showTimeList = showTimeList;
	}

	public List<String> getMovieNamesList() {
		return movieNamesList;
	}

	public List<String> getDisplayImages() {
		return displayImages;
	}

	public void setMovieNamesList(List<String> movieNamesList) {
		this.movieNamesList = movieNamesList;
	}

	public void setMovieList(List<Movie> movieList) {
		this.movieList = movieList;
	}

	
	
}
