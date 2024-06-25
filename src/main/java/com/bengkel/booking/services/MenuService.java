package com.bengkel.booking.services;

import java.util.List;
import java.util.Scanner;

import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.repositories.CustomerRepository;
import com.bengkel.booking.repositories.ItemServiceRepository;

public class MenuService {
	private static List<Customer> listAllCustomers = CustomerRepository.getAllCustomer();
	private static List<ItemService> listAllItemService = ItemServiceRepository.getAllItemService();
	private static BengkelService bengkel = new BengkelService();
	private static Scanner input = new Scanner(System.in);
	
	public static void run() {
		boolean isLooping = true;
		do {
			login();
			mainMenu();
		} while (isLooping);
		
	}
	
	public static void login() {
		boolean isLooping = true;		
		String customerID;
		System.out.println("Login Customer");
		
		do {
			customerID = Validation.validasiInput("Masukan Customer ID: ", "Input Harus Berupa Huruf!", "^[a-zA-Z0-9-]+$");	
			Customer c = BengkelService.CustomerById(listAllCustomers, customerID);
			if(c != null) {
				if(BengkelService.CustomerPassword(c)) {
					BengkelService.setCustomer(c);
					isLooping=false;
				}
			} else {
				System.out.println("Customer id tidak ditemukan");
			}
		} while(isLooping);
	}
	
	public static void mainMenu() {
		String[] listMenu = {"Informasi Customer", "Booking Bengkel", "Top Up Bengkel Coin", "Informasi Booking", "Logout"};
		int menuChoice = 0;
		boolean isLooping = true;
		
		do {
			PrintService.printMenu(listMenu, "Booking Bengkel Menu");
			menuChoice = Validation.validasiNumberWithRange("Masukan Pilihan Menu:", "Input Harus Berupa Angka!", "^[0-9]+$", listMenu.length-1, 0);
			System.out.println(menuChoice);
			
			switch (menuChoice) {
			case 1:
				//panggil fitur Informasi Customer
				bengkel.info();
				break;
			case 2:
				//panggil fitur Booking Bengkel
				bengkel.bookingBengkel();
				break;
			case 3:
				//panggil fitur Top Up Saldo Coin
				bengkel.TopUp();
				break;
			case 4:
				//panggil fitur Informasi Booking Order
				bengkel.infoBookingOrder();
				break;
			default:
				bengkel.Logout();
				System.out.println("Logout");
				isLooping = false;
				break;
			}
		} while (isLooping);		
	}
	
	//Silahkan tambahkan kodingan untuk keperluan Menu Aplikasi
}
