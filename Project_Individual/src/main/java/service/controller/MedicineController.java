package service.controller;

import service.model.Medicine;
import service.repository.DatabaseException;
import service.repository.MedicineRepository;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

public class MedicineController {
    MedicineRepository medicineRepository = new MedicineRepository();


    //get all medicines
    public List<Medicine> getMedicines() {
//        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();

        try {
            return  medicineRepository.getMedicines();
        } catch (DatabaseException | SQLException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get medicine by id
    public Medicine getMedicineById(int medicineId) {
//        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();

        try {
            return  medicineRepository.getMedicine(medicineId);
        } catch (DatabaseException | SQLException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    //add medicine
    public boolean addMedicine(Medicine medicine) {
//        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();
        try {
            if(medicineRepository.createMedicine(medicine)) {
                return true;
            }
            else
            {
                return false;
            }
        } catch (DatabaseException | SQLException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }
    //delete medicine
    public boolean deleteMedicine(int medicineId) {
//        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();
        try {
            if(medicineRepository.deleteMedicine(medicineId)) {
                return true;
            }
            else
            {
                return false;
            }
        } catch (DatabaseException | SQLException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    //update medicine
    public boolean updateMedicine(Medicine medicine) {
//        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();
        try {
            if(medicineRepository.updateMedicine(medicine)) {
                return true;
            }
            else
            {
                return false;
            }
        } catch (DatabaseException | SQLException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

}
