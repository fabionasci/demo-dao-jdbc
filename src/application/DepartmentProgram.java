package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class DepartmentProgram {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST 1: Department getById ===");
        Department department = departmentDao.getById(3);
        System.out.println(department);

        System.out.println("\n=== TEST 2: Department getAll ===");
        List<Department> departments = departmentDao.getAll();
        for (Department s : departments) {
            System.out.println(s);
        }

        Integer depId;
        String depName;

        System.out.println("Want to run INSERT department ? (Y/N): ");
        String insert = scanner.nextLine();

        if (insert.equals("Y")) {

            System.out.println("\n=== TEST 3: Department insert ===");
            System.out.println("\n===> Enter Department name: ");
            depName = scanner.nextLine();

            Department departmentInsert = new Department(null, depName);

            departmentDao.insert(departmentInsert);
            System.out.println("Inserted! ID: " + departmentInsert.getId());
        }

        System.out.println("Want to run UPDATE seller ? (Y/N): ");
        String update = scanner.next();

        if (update.equalsIgnoreCase("y")) {

            System.out.println("\n=== TEST 4: Department update ===");

            System.out.println("\n===> Enter Department ID: ");
            depId = scanner.nextInt();
            scanner.nextLine(); // consome o ENTER deixado pelo nextInt
            System.out.println("\n===> Enter Department name: ");
            depName = scanner.nextLine();

            Department departmentUpdate = new Department(depId, depName);

            departmentDao.update(departmentUpdate);
            System.out.println("Updated! ID: " + departmentUpdate.getId());
        }

        System.out.println("Want to run DELETE department ? (Y/N): ");
        String delete = scanner.next();
        if (delete.equalsIgnoreCase("y")) {

            System.out.println("\n=== TEST 5: Department delete ===");

            System.out.println("\n===> Enter Department ID: ");
            depId = scanner.nextInt();

            departmentDao.deleteById(depId);
            System.out.println("Deleted! ID: " + depId);
        }
    }
}
