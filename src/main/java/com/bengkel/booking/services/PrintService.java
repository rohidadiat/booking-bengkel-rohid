package com.bengkel.booking.services;

import java.util.List;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Car;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.models.Vehicle;


public class PrintService {
	
	public static void printMenu(String[] listMenu, String title) {
		String line = "+---------------------------------+";
		int number = 1;
		String formatTable = " %-2s. %-25s %n";
		
		System.out.printf("%-25s %n", title);
		System.out.println(line);
		
		for (String data : listMenu) {
			if (number < listMenu.length) {
				System.out.printf(formatTable, number, data);
			}else {
				System.out.printf(formatTable, 0, data);
			}
			number++;
		}
		System.out.println(line);
		System.out.println();
	}
	
	public static void printVechicle(List<Vehicle> listVehicle) {
		String formatTable = "| %-2s | %-15s | %-10s | %-15s | %-15s | %-5s | %-15s |%n";
		String line = "+----+-----------------+------------+-----------------+-----------------+-------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Vechicle Id", "Warna", "Brand", "Transmisi", "Tahun", "Tipe Kendaraan");
	    System.out.format(line);
	    int number = 1;
	    String vehicleType = "";
	    for (Vehicle vehicle : listVehicle) {
	    	if (vehicle instanceof Car) {
				vehicleType = "Mobil";
			}else {
				vehicleType = "Motor";
			}
	    	System.out.format(formatTable, number, vehicle.getVehiclesId(), vehicle.getColor(), vehicle.getBrand(), vehicle.getTransmisionType(), vehicle.getYearRelease(), vehicleType);
	    	number++;
	    }
	    System.out.printf(line);
	}
	
	public static void infoCustomer(Customer c) {
		System.out.println("\nINFO CUSTOMER");
		System.out.printf("%-15s : %s \n%-15s : %s \n%-15s : %s \n%-15s : %s \n", 
				"Id Customer", c.getCustomerId(), "Nama", c.getName(), "Alamat", c.getAddress(), "Customer Status", "Non-Member");
		printVechicle(c.getVehicles());
		System.out.println();
	}
	
	public static void infoCustomerMember(MemberCustomer c) {
		System.out.println("\nINFO CUSTOMER");
		System.out.printf("%-15s : %s \n%-15s : %s \n%-15s : %s \n%-15s : %s \n%-15s : %s \n", 
				"Id Customer", c.getCustomerId(), "Nama", c.getName(), "Alamat", c.getAddress(), "Customer Status", "Member", "Saldo koin", c.getSaldoCoin());
		printVechicle(c.getVehicles());
		System.out.println();
	}
	
	public static void printOrder(List<BookingOrder> listBookingOrder) {
		String formatTable = "| %-2s | %-15s | %-15s | %-30s | %-15s | %-15s | %-15s |%n";
		String line = "+----+-----------------+-----------------+--------------------------------+-----------------+-----------------+-----------------+%n";
		System.out.format(line);
		int number = 1;
	    System.out.format(formatTable, "No", "Booking Id", "Nama Customer", "List Service", "Payment Method", "Total Price", "Total Payment");
	    System.out.format(line);
	    for (BookingOrder booking : listBookingOrder) {
	    	System.out.format(formatTable, number, booking.getBookingId(), booking.getCustomer().getName(), printServices(booking.getServices()), booking.getPaymentMethod(), booking.getTotalServicePrice(), booking.getTotalPayment());
	    	number++;
	    }
	    System.out.printf(line);
	}
	
	public static String printServices(List<ItemService> listService){
        String result = "";
        // Bisa disesuaikan kembali
        for (ItemService service : listService) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }
	
	//Silahkan Tambahkan function print sesuai dengan kebutuhan.
	
}
