package com.bengkel.booking.services;

import java.util.ArrayList;
import java.util.List;

import com.bengkel.booking.models.Car;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.models.Motorcyle;
import com.bengkel.booking.models.Vehicle;
import com.bengkel.booking.repositories.ItemServiceRepository;

public class ReservationService {
	private static Customer customer = BengkelService.getCustomer();
	private static List<ItemService> listAllItemService = ItemServiceRepository.getAllItemService();
	
	public static Vehicle getVehicleById(List<Vehicle> vehicles, String id) {
	    for(Vehicle v : vehicles) {
	        if(v instanceof Car && ((Car)v).getVehiclesId().equalsIgnoreCase(id)) {
	            return v;
	        } else if(v instanceof Motorcyle && ((Motorcyle)v).getVehiclesId().equalsIgnoreCase(id)) {
	            return v;
	        }
	    }
	    
	    return null;
	}
	
	public static double totalPrice(List<ItemService> bookingList) {
		double result=0;
		for(ItemService S: bookingList) {
			result+=S.getPrice();
		}
		return result;
	}
	
	public static List<ItemService> bookingList(Vehicle v){
		List<ItemService> listService, bookingList = new ArrayList<>();
		boolean isLooping = true;		
		if(v instanceof Car) {
			listService = ListServiceByType("Car");
		} else {
			listService = ListServiceByType("Motorcyle");
		}
		String[] menu = getmenu(listService);		
		do {
			PrintService.printMenu(menu, "Pilih metode pembayaran");
			int menuChoice = Validation.validasiNumberWithRange("Masukan Pilihan Menu:", "Input Harus Berupa Angka!", "^[0-9]+$", menu.length-1, 0);
			for(int i=0; i<menu.length;i++) {
				if (menuChoice == 0){
	                isLooping = false;
	            }else if (menuChoice == i) {
					bookingList.add(listService.get(menuChoice-1));
				} 
			}

            if (customer instanceof MemberCustomer && bookingList.size() == 2) {
                isLooping = false;
            } else if (!(customer instanceof MemberCustomer) && bookingList.size() == 1) {
                isLooping = false;
            }
		} while (isLooping);
		
		return bookingList;
	}
	
	public static String PaymentMethod(){
		boolean isLooping = true;	
		String[] menu = {"Saldo Coin", "Cash", "Cancel"};
		do {
			PrintService.printMenu(menu, "Booking Reservation Menu");
			int menuChoice = Validation.validasiNumberWithRange("Masukan Pilihan Menu:", "Input Harus Berupa Angka!", "^[0-9]+$", menu.length-1, 0);
			switch(menuChoice) {
			case 1 : 
				return "Saldo Coin";
			case 2 :
				return "Cash";
			default :
				return "Cash";				
			}
		} while (isLooping);

	}
	
	public static String[] getmenu(List<ItemService> listService) {
		String[] menu = new String[listService.size()+1];
		menu[listService.size()] = "Cancel";
		int i=0;
		for (ItemService s: listService) {
			menu[i] = s.getServiceName();
			i++;
		}
		return menu;
	}
	
	public static List<ItemService> ListServiceByType(String type) {
		List<ItemService> listMenu = new ArrayList<>();
		for(ItemService S: listAllItemService) {
			if(type.equals(S.getVehicleType())) {
				listMenu.add(S);
			} 
		}
		return listMenu;
	}
}
