package com.bengkel.booking.services;

import java.util.ArrayList;
import java.util.List;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.models.Vehicle;

public class BengkelService {
	private static Customer customer;
	private static List<BookingOrder> listBookingOrder = new ArrayList<>();;
	private static ReservationService reservation;
	//Silahkan tambahkan fitur-fitur utama aplikasi disini
	
	public static void setCustomer(Customer customer) {
		BengkelService.customer = customer;
	}
	
	public static Customer getCustomer() {
		return customer;
	}

	
	//Login
	public static Customer CustomerById(List<Customer> listAllCustomers, String id) {		
		for(Customer c: listAllCustomers) {
			if(c.getCustomerId().equals(id)) {
				return c;
			}
		}
		
		return null;
	}
	
	public static boolean CustomerPassword(Customer cus) {		
		boolean isLooping = true;
		String password;
		int i=0;

		do {
			if(i==3) {
				System.out.println("Gagal Login");
				System.exit(0);
			}
			
			password = Validation.validasiInput("Masukan Password: ", "Input Harus Berupa Huruf!", "^[a-zA-Z0-9]+$");
			if(cus.getPassword().equals(password)) {
				return true;
			} else {
				System.out.println("Pasword tidak sesuai");
			}
			
			i++;
		} while(isLooping);
		return false;
	}
	
	//Info Customer
	public void info() {
		if(Validation.validasiMember(customer) != null) {
			PrintService.infoCustomerMember((MemberCustomer) customer);
		} else {
			PrintService.infoCustomer(customer);
		}
	}
	
	//Booking atau Reservation
	public static void bookingBengkel() {
		boolean isLooping = true;		
		String vehicleID, payment;
		BookingOrder booking = new BookingOrder();
		
		System.out.println("Booking Reservation");
		
		do {
			vehicleID = Validation.validasiInput("Masukan Vehicles ID: ", "Input Harus Berupa Huruf!", "^[a-zA-Z0-9-]+$");	
			Vehicle v = reservation.getVehicleById(customer.getVehicles(), vehicleID);
			if(v != null) {
				List<ItemService> listService = reservation.bookingList(v);
				double totalPay = reservation.totalPrice(listService);
//				System.out.print(listService.toString());
				
				if(Validation.validasiMember(customer) != null) {
					payment = reservation.PaymentMethod();
				} else {
					payment = "Cash";
				}
				
				booking.setBookingId(vehicleID);
				booking.setCustomer(customer);
				booking.setServices(listService);
				booking.setPaymentMethod(payment);
				booking.setTotalServicePrice(totalPay);
				booking.calculatePayment();
				
				listBookingOrder.add(booking);
				
				if(payment.equals("Saldo Coin")) {
					if(Validation.validasiMember(customer) != null) {
						PaymentCoin(booking.getTotalPayment());
					}
				}				
				isLooping = false;
			} else {
				System.out.println("Customer id tidak ditemukan");
			}
		} while(isLooping);
	}
	
	private static void PaymentCoin(double coin) {
		((MemberCustomer) customer).setSaldoCoin(((MemberCustomer) customer).getSaldoCoin()-coin);
	}
	

	//Top Up Saldo Coin Untuk Member Customer
	public void TopUp() {
		double koin;
		if(Validation.validasiMember(customer) != null) {
			koin =  Validation.validasiNumberWithRange("Masukan Jumlah Koin: ", "Input Harus Berupa Huruf!", "^[0-9]+$", 999999999, 1);
			topup(koin);
		} else {
			System.out.println("Maaf fitur ini hanya untuk Member saja!");
		}		
	}
	
	public static void topup(double coin) {
		((MemberCustomer) customer).setSaldoCoin(((MemberCustomer) customer).getSaldoCoin()+coin);
	}
	
	
	//info booking order
	public void infoBookingOrder() {
		PrintService.printOrder(listBookingOrder);
	}
	
	//Logout
	public void Logout() {
		this.customer = null;
	}
	
}
