package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: Seller findById ===");
        Seller seller = sellerDao.getById(3);
        System.out.println(seller);

        System.out.println("\n=== TEST 2: Seller findByDepartmentId ===");
        List<Seller> sellers = sellerDao.getByDepartmentId(2);
        for (Seller s : sellers) {
            System.out.println(s);
        }

    }
}