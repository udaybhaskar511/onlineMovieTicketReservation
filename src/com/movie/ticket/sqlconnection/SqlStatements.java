package com.movie.ticket.sqlconnection;

public interface SqlStatements {

	public static final String selectAllMovies="select MovieId,NameOfTheMovie,MovieType,Runtime,Trailer,Synopsis,Category,Cast,Rating from Movie";
	public static final String selectShowDatesByMovie="select distinct ShowDate from (select Movie.MovieId,NameOfTheMovie,ShowDate from Movie join Screen on Screen.MovieId=Movie.MovieId where Movie.movieId=(select MovieId from Movie where NameOfTheMovie= ?))tb1";
	public static final String selectScreenListByMovieAndShowDate="select distinct ScreenId from (select ScreenId,NameOfTheMovie,ShowDate from Movie join Screen on Screen.MovieId=Movie.MovieId AND Movie.MovieId=(select MovieId from Movie where NameOfTheMovie= ?) AND Screen.ShowDate=?)tb2";
	public static final String selectShowTimeByMovieShowDateAndScreenId="select distinct Showtime from (select Showtime,ScreenId,NameOfTheMovie,ShowDate from Movie join Screen on Screen.MovieId=Movie.MovieId AND Movie.MovieId=(select MovieId from Movie where NameOfTheMovie= ?) AND Screen.ShowDate=? AND Screen.ScreenId=?)tb3";
	public static final String selectAvailableSeatsByMovieShowDateScreenIdAndShowTime="select NumberOfSeats, price from (select NumberOfSeats,price,ScreenId,NameOfTheMovie,ShowDate from Movie join Screen on Screen.MovieId=Movie.MovieId AND Movie.MovieId=(select MovieId from Movie where NameOfTheMovie= ?) AND Screen.ShowDate=? AND Screen.ScreenId=? AND Screen.ShowTime=?)tb4";
	public static final String updateScreenTable="update Screen set NumberOfSeats=? where movieID=(select MovieId from Movie where NameOfTheMovie=?) and ScreenId=? and ShowTime=? and ShowDate=?";
	public static final String insertIntoBookingTable="insert into Booking(MovieId,NameOfTheMovie,Showtime,ScreenId,BookingId,NumberOfSeatsBooked,ShowDate) values((select MovieId from Movie where NameOfTheMovie=?),?,?,?,?,?,?)";
	public static final String insertIntoCreditCardInfoTable="insert into CreditInfo(NameOnCreditCard,NumberOnCreditCard,ExpiryDate,TypeOfCard,Cvv,BookingId) values (?,?,?,?,?,?)";
	public static final String selectBookingDetailsByBookingId="select NameOfTheMovie,NumberOfSeatsBooked,ScreenID,ShowDate,ShowTime from Booking where BookingID=?";
	public static final String deleteBookingbyBookingId="delete from Booking where BookingID=?";
	public static final String selectTrailerAndRatingbyMovieName="select Trailer,Rating from Movie where NameOfTheMovie=?";
	public static final String updateRatinginMovies="update Movie set Rating=? where NameOfTheMovie=?";
	public static final String selectUserDetails="select UserId from UserInfo where UserId=? and Password=?";
	public static final String insertUserDetails="insert into UserInfo(FirstName,LastName,UserId,Password,EmailID,Contact) values(?,?,?,?,?,?)";
	public static final String insertMovieDetails="insert into Movie(MovieId,NameOfTheMovie,Category,Cast,Runtime,MovieType,Trailer,Rating,Synopsis) values(?,?,?,?,?,?,?,?,?)";
	public static final String selectMovieDetailsbyMovieName="select movieId,MovieType,RunTime,Cast,Category,Rating,Trailer,Synopsis,NameOfTheMovie from Movie where NameOfTheMovie=?";
	public static final String insertIntoScreen = "insert into screen(MovieId,Showtime,NumberOfSeats,showId,ScreenId,ShowDate,Price) values(?,?,?,?,?,?,?)";
	public static final String updateMovieDetailsbyMovieId="update Movie set NameOfTheMovie=?,Category=?,Cast=?,Runtime=?,MovieType=?,Trailer=?,Synopsis=? where MovieId=?";
	public static final String deleteMovieDetailsbyMovieID="delete from Movie where MovieId=?";
	public static final String deleteCreditInfobyBookingId=" delete CreditInfo where BookingId=?";
	public static final String deleteScreenDetailsbyMovieID="delete from Screen where MovieId=?";
	public static final String selectEmailIdFromUserByUserName="select EmailId from UserInfo where UserId=?";
	
	}
