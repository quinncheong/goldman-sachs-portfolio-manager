package com.is442g1t4.gpa.stock.model;

// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.EnumType;
// import javax.persistence.Enumerated;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
// import javax.persistence.Table;
// import javax.validation.constraints.NotNull;

// import org.springframework.data.annotation.Id;

import java.time.LocalDate;

// import com.fasterxml.jackson.annotation.JsonFormat;import org.springframework.data.annotation.Id;

// @Entity
// @Table(name = "stock")
public class Stock {

    // public enum StockStatus {
    // CONFIRMED, COLLECTED, CANCELLED, RETURNED, DUESOWED, DUESPAID
    // }

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private int bookingId;

    // @ManyToOne
    // @JoinColumn(name = "CPID")
    // private CorporatePass corporatePass;

    // @JsonFormat(pattern = "yyyy-MM-dd")
    // private LocalDate bookingDate;

    // @JsonFormat(pattern = "yyyy-MM-dd")
    // private LocalDate borrowDate;

    // @ManyToOne
    // @JoinColumn(name = "email")
    // private UserAccount borrower;

    // @NotNull
    // @Column(length = 32, columnDefinition = "varchar(255) default 'CONFIRMED'")
    // @Enumerated(EnumType.STRING)
    // private BookingStatus bookingStatus = BookingStatus.CONFIRMED;

    // private double feesOwed;

    // public Booking() {
    // this.bookingDate = LocalDate.now();
    // }

    // public Booking(LocalDate borrowDate, UserAccount borrower, CorporatePass
    // corporatePass) {
    // this();
    // this.borrowDate = borrowDate;
    // this.borrower = borrower;
    // this.corporatePass = corporatePass;
    // }

    // public Booking(LocalDate borrowDate, UserAccount borrower, CorporatePass
    // corporatePass,
    // BookingStatus bookingStatus) {
    // this(borrowDate, borrower, corporatePass);
    // this.bookingStatus = bookingStatus;
    // this.feesOwed = 0;
    // }

    // public Booking(LocalDate borrowDate, UserAccount borrower, CorporatePass
    // corporatePass, BookingStatus bookingStatus,
    // Double feesOwed) {
    // this(borrowDate, borrower, corporatePass, bookingStatus);
    // this.feesOwed = feesOwed;
    // }

    // public int getBookingId() {
    // return bookingId;
    // }

    // public void setBookingId(int bookingId) {
    // this.bookingId = bookingId;
    // }

    // public LocalDate getBorrowDate() {
    // return borrowDate;
    // }

    // public void setBorrowDate(LocalDate borrowDate) {
    // this.borrowDate = borrowDate;
    // }

    // public UserAccount getBorrower() {
    // return borrower;
    // }

    // public void setBorrower(UserAccount borrower) {
    // this.borrower = borrower;
    // }

    // public CorporatePass getCorporatePass() {
    // return corporatePass;
    // }

    // public void setCorporatePass(CorporatePass corporatePass) {
    // this.corporatePass = corporatePass;
    // }

    // public BookingStatus getBookingStatus() {
    // return bookingStatus;
    // }

    // public void setBookingStatus(BookingStatus bookingStatus) {
    // this.bookingStatus = bookingStatus;
    // }

    // public double getFeesOwed() {
    // return feesOwed;
    // }

    // public void setFeesOwed(double feesOwed) {
    // this.feesOwed = feesOwed;
    // }

    // public LocalDate getBookingDate() {
    // return bookingDate;
    // }

    // public void setBookingDate(LocalDate bookingDate) {
    // this.bookingDate = bookingDate;
    // }

}