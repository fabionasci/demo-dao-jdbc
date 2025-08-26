package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException {

        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: Seller getById ===");
        Seller seller = sellerDao.getById(3);
        System.out.println(seller);

        System.out.println("\n=== TEST 2: Seller getByDepartmentId ===");
        List<Seller> sellers = sellerDao.getByDepartmentId(2);
        for (Seller s : sellers) {
            System.out.println(s);
        }

        System.out.println("\n=== TEST 3: Seller getAll ===");
        List<Seller> sellers1 = sellerDao.getAll();
        for (Seller s : sellers1) {
            System.out.println(s);
        }

        String sellerName;
        String sellerEmail;
        String birthDateStr;
        Date birthDate;
        Double salary;
        Integer departmentId;

        System.out.println("Want to run INSERT seller ? (Y/N): ");
        String insert = scanner.nextLine();

        if (insert.equals("Y")) {

            System.out.println("\n=== TEST 4: Seller insert ===");
            System.out.println("\n===> Enter Seller name: ");
            sellerName = scanner.nextLine();
            System.out.println("\n===> Enter Seller email: ");
            sellerEmail = scanner.nextLine();
            System.out.println("\n===> Enter Seller birth date (dd/MM/yyyy): ");
            birthDateStr = scanner.nextLine();
            birthDate = sdf.parse(birthDateStr);
            System.out.println("\n===> Enter Seller salary: ");
            salary = scanner.nextDouble();
            scanner.nextLine(); // consome o ENTER deixado pelo nextDouble
            System.out.println("\n===> Enter Department ID: ");
            departmentId = scanner.nextInt();
            scanner.nextLine(); // consome o ENTER deixado pelo nextInt
            Department department = new Department(departmentId, null);

            Seller sellerInsert = new Seller(null, sellerName, sellerEmail, birthDate, salary, department);

            sellerDao.insert(sellerInsert);
            System.out.println("Inserted! ID: " + sellerInsert.getId());
        }

        System.out.println("Want to run UPDATE seller ? (Y/N): ");
        String update = scanner.next();

        if (update.equalsIgnoreCase("y")) {

            System.out.println("\n=== TEST 5: Seller update ===");

            System.out.println("\n===> Enter Seller ID: ");
            Integer sellerId = scanner.nextInt();
            scanner.nextLine(); // consome o ENTER deixado pelo nextInt
            System.out.println("\n===> Enter Seller name: ");
            sellerName = scanner.nextLine();
            System.out.println("\n===> Enter Seller email: ");
            sellerEmail = scanner.nextLine();
            System.out.println("\n===> Enter Seller birth date (dd/MM/yyyy): ");
            birthDateStr = scanner.nextLine();
            birthDate = sdf.parse(birthDateStr);
            System.out.println("\n===> Enter Seller salary: ");
            salary = scanner.nextDouble();
            scanner.nextLine(); // consome o ENTER deixado pelo nextDouble
            System.out.println("\n===> Enter Department ID: ");
            departmentId = scanner.nextInt();
            scanner.nextLine(); // consome o ENTER deixado pelo nextInt
            Department department2 = new Department(departmentId, null);

            Seller sellerUpdate = new Seller(sellerId, sellerName, sellerEmail, birthDate, salary, department2);

            sellerDao.update(sellerUpdate);
            System.out.println("Updated! ID: " + sellerUpdate.getId());
        }

        System.out.println("Want to run DELETE seller ? (Y/N): ");
        String delete = scanner.next();
        if (delete.equalsIgnoreCase("y")) {

            System.out.println("\n=== TEST 6: Seller delete ===");

            System.out.println("\n===> Enter Seller ID: ");
            Integer sellerId = scanner.nextInt();

            sellerDao.deleteById(sellerId);
            System.out.println("Deleted! ID: " + sellerId);
        }
    }
}