package fontys.service;

import fontys.service.model.Medicine;
import fontys.service.model.Patient;
import fontys.service.repository.JDBCMedicineRepository;
import fontys.service.repository.JDBCPatientRepository;
import fontys.service.repository.DatabaseException;

import java.util.List;

public class PersistenceController {

    /**
     * Show/print all countries.
     */


    public List<Patient> getPatients() {
        JDBCPatientRepository jdbcPatientRepository = new JDBCPatientRepository();

        try {
            List<Patient> patients = (List<Patient>) jdbcPatientRepository.getPatients();
            return patients;
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return null;
    }
    // get medicine by id
    public Patient getPatientById(int patientId) {
        JDBCPatientRepository patientRepository = new JDBCPatientRepository();

        try {
            Patient patient = patientRepository.getPatientById(patientId);
            return  patient;
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return null;
    }
    //add medicine
    public boolean addPatient(Patient patient) {
        JDBCPatientRepository patientRepository = new JDBCPatientRepository();
        try {
            if(patientRepository.createPatient(patient)) {
                return true;
            }
            else
            {
                return false;
            }
//            System.out.println("Created student: " + medicine);
        } catch (DatabaseException e) {
            e.printStackTrace();
            return false;
        }
    }

    //get all medicines
    public List<Medicine> getMedicines() {
        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();

        try {
            List<Medicine> medicines = (List<Medicine>) medicineRepository.getMedicines();
            return medicines;
//            System.out.println("All countries");
//            for (Medicine medicine : medicines) {
//                System.out.println("\t" + medicine.getMedName());
//                //return medicines;
//            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get medicine by id
    public Medicine getMedicineById(int medicineId) {
        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();

        try {
            Medicine medicine = medicineRepository.getMedicine(medicineId);
            return  medicine;
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //add medicine
    public boolean addMedicine(Medicine medicine) {
        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();
        try {
            if(medicineRepository.createMedicine(medicine)) {
                return true;
            }
            else
            {
                return false;
            }
//            System.out.println("Created student: " + medicine);
        } catch (DatabaseException e) {
            e.printStackTrace();
            return false;
        }
    }
    //delete medicine
    public boolean deleteMedicine(int medicineId) {
        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();
        try {
            if(medicineRepository.deleteMedicine(medicineId)) {
                return true;
            }
            else
            {
                return false;
            }
//            System.out.println("Created student: " + medicine);
        } catch (DatabaseException e) {
            e.printStackTrace();
            return false;
        }
    }

    //update medicine
    public boolean updateMedicine(Medicine medicine) {
        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();
        try {
            if(medicineRepository.updateMedicine(medicine)) {
                return true;
            }
            else
            {
                return false;
            }
//            System.out.println("Created student: " + medicine);
        } catch (DatabaseException e) {
            e.printStackTrace();
            return false;
        }
    }
//
//    /**
//     * Show/print all students.
//     */
//    void showAllStudents() {
//        JDBCStudentsRepository studentsRepository = new JDBCStudentsRepository();
//        try {
//            Collection<Student> students = studentsRepository.getStudents();
//            System.out.println("All students");
//            for (Student student : students) {
//                System.out.println("\t" + student);
//            }
//        } catch (SchoolDatabaseException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Show/print all students from given country.
//     *
//     * @param country from which the students are shown.
//     */
//    void showStudents(Country country) {
//        JDBCStudentsRepository studentsRepository = new JDBCStudentsRepository();
//        try {
//            Collection<Student> students = studentsRepository.getStudents(country);
//            System.out.println("Students from country " + country);
//            for (Student student : students) {
//                System.out.println("\t" + student);
//            }
//        } catch (SchoolDatabaseException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Add/create a new student.
//     *
//     * @param student should be inserted into the DB.
//     */
//    void addStudent(Student student) {
//        JDBCStudentsRepository studentsRepository = new JDBCStudentsRepository();
//        try {
//            studentsRepository.create(student);
//            System.out.println("Created student: " + student);
//        } catch (SchoolDatabaseException e) {
//            e.printStackTrace();
//        }
//    }
}
