/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import java.sql.*;
import java.text.DateFormat;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Formatter;

/**
 *
 * @author victo
 */
public class Exam {

    public static Connection getKoneksiKeDB() throws SQLException {
        String URL = "jdbc:sqlserver://localhost;databaseName=MITS;instanceName=SQLEXPRESS_2017";
        String Username = "sa";
        String Password = "localhost";

        return DriverManager.getConnection(URL, Username, Password);
    }

    private static void insertEmployees(String tanggalLahir, String tempatLahir, String bpjsKerja, String bpjsSehat, String expDriverLicense, String driverLicense, String empNo, String firstName, String lastName, String middleName,
            String noKTP, String npwp, String photoPath, int contactID, int genderID, int loginDetails, int maritalStatus, int nationalityID, int religionID, int workShiftID) {
        Connection connection = null;

        try {
            connection = getKoneksiKeDB();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO Employees(BirthDate, BirthPlate,  BPJSKerjaNo, BPJSSehatNo, DriverLicenseExpDate, DriverLicenseNo, EmpNo, FirstName, LastName, MiddleName, NoKTP,NPWP, PhotoPath, ContactID, GenderID, LoginDetailsId, MaritalStatusID, NationalityID, RelogionID, WorkShiftID )\n"
                    + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement prestatement = connection.prepareStatement(sql);
            prestatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            prestatement.setString(2, tempatLahir);
            prestatement.setString(3, bpjsKerja);
            prestatement.setString(4, bpjsSehat);
            prestatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            prestatement.setString(6, driverLicense);
            prestatement.setString(7, empNo);
            prestatement.setString(8, firstName);
            prestatement.setString(9, lastName);
            prestatement.setString(10, middleName);
            prestatement.setString(11, noKTP);
            prestatement.setString(12, npwp);
            prestatement.setString(13, photoPath);
            prestatement.setInt(14, contactID);
            prestatement.setInt(15, genderID);
            prestatement.setInt(16, loginDetails);
            prestatement.setInt(17, maritalStatus);
            prestatement.setInt(18, nationalityID);
            prestatement.setInt(19, religionID);
            prestatement.setInt(20, workShiftID);

            prestatement.executeUpdate();
            connection.commit();

            prestatement.close();
            connection.close();

        } catch (SQLException sqle) {
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Rollback Dilakukan");
                    sqle.printStackTrace();
                } catch (SQLException sqle2) {
                    System.out.println("Tidak dapat Melakukan rollback");
                    sqle2.printStackTrace();
                }
            }

        }
    }

    private static void deleteEmployee(int id) {
        Connection connection = null;
        try {
            connection = getKoneksiKeDB();
            String sql = "DELETE FROM employees WHERE id=? ";
            PreparedStatement prestatement = connection.prepareStatement(sql);
            prestatement.setInt(1, id);

            prestatement.executeUpdate();
            connection.commit();

            prestatement.close();
            connection.close();
        } catch (SQLException sqle) {
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Berhasil di rollback");
                    sqle.printStackTrace();
                } catch (SQLException sqle2) {
                    System.out.println("tidak berhasil melakukan rollback");
                    sqle2.printStackTrace();
                }
            }
        }
    }

    private static void updateEmployee(String tanggalLahir, String tempatLahir, String bpjsKerja, String bpjsSehat, String expDriverLicense, String driverLicense, String empNo, String firstName, String lastName, String middleName,
            String noKTP, String npwp, String photoPath, int contactID, int genderID, int loginDetails, int maritalStatus, int nationalityID, int religionID, int workShiftID, int ID) {
        Connection connection = null;
        try {
            connection = getKoneksiKeDB();
            String sql = "UPDATE Employees SET BirthDate=?, BirthPlate,  BPJSKerjaNo=?, BPJSSehatNo=?, DriverLicenseExpDate=?, DriverLicenseNo=?, EmpNo=?, FirstName=?, LastName=?, MiddleName=?, NoKTP=?,NPWP=?, PhotoPath=?, ContactID=?, GenderID=?, LoginDetailsId=?, MaritalStatusID=?, NationalityID=?, RelogionID=?, WorkShiftID=? WHERE ID = ? ";
            PreparedStatement prestatement = connection.prepareStatement(sql);
            prestatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            prestatement.setString(2, tempatLahir);
            prestatement.setString(3, bpjsKerja);
            prestatement.setString(4, bpjsSehat);
            prestatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            prestatement.setString(6, driverLicense);
            prestatement.setString(7, empNo);
            prestatement.setString(8, firstName);
            prestatement.setString(9, lastName);
            prestatement.setString(10, middleName);
            prestatement.setString(11, noKTP);
            prestatement.setString(12, npwp);
            prestatement.setString(13, photoPath);
            prestatement.setInt(14, contactID);
            prestatement.setInt(15, genderID);
            prestatement.setInt(16, loginDetails);
            prestatement.setInt(17, maritalStatus);
            prestatement.setInt(18, nationalityID);
            prestatement.setInt(19, religionID);
            prestatement.setInt(20, workShiftID);
            prestatement.setInt(21, ID);

            prestatement.executeUpdate();
            connection.commit();

            prestatement.close();
            connection.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Rollback Berhasil");
                } catch (SQLException sqle2) {
                    System.out.println("Tidak dapat melakukan rollback");
                    sqle2.printStackTrace();
                }

            }

        }

    }

    private static void menampilkanNationality() {
        Connection connection = null;
        try {
            connection = getKoneksiKeDB();
            String SQL = "SELECT * FROM Nationality";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String description = resultSet.getString(2);

                System.out.println("pilihan = " + id + " adalah = " + description);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Rollback Berhasil");
                } catch (SQLException sqle2) {
                    System.out.println("Tidak dapat melakukan rollback");
                    sqle2.printStackTrace();
                }

            }
        }
    }

    private static void menampilkanReligion() {
        Connection connection = null;
        try {
            connection = getKoneksiKeDB();
            String SQL = "SELECT * FROM Religion";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String description = resultSet.getString(2);

                System.out.println("pilihan = " + id + " adalah = " + description);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Rollback Berhasil");
                } catch (SQLException sqle2) {
                    System.out.println("Tidak dapat melakukan rollback");
                    sqle2.printStackTrace();
                }

            }
        }
    }

    private static void menampilkanMaritalStatus() {
        Connection connection = null;
        try {
            connection = getKoneksiKeDB();
            String SQL = "SELECT * FROM MaritalStatus";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String description = resultSet.getString(2);

                System.out.println("pilihan = " + id + " adalah = " + description);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Rollback Berhasil");
                } catch (SQLException sqle2) {
                    System.out.println("Tidak dapat melakukan rollback");
                    sqle2.printStackTrace();
                }

            }
        }
    }

    private static void menampilkanLoginDetil() {
        Connection connection = null;
        try {
            connection = getKoneksiKeDB();
            String SQL = "SELECT * FROM LoginDetails";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                Byte IsEnable = resultSet.getByte(4);
                System.out.println("pilihan = " + id + " Username = " + username + " Password = " + password + " diperbolehkan = " + IsEnable);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Rollback Berhasil");
                } catch (SQLException sqle2) {
                    System.out.println("Tidak dapat melakukan rollback");
                    sqle2.printStackTrace();
                }

            }
        }
    }

    private static void menamppilkanContacts() {
        Connection connection = null;
        try {
            connection = getKoneksiKeDB();
            String SQL = "SELECT * FROM Contacts";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int addressId = resultSet.getInt(2);
                String homePhone = resultSet.getString(3);
                String mobilePhone = resultSet.getString(4);
                String workPhone = resultSet.getString(5);
                String workEmail = resultSet.getString(6);
                String otherEmail = resultSet.getString(7);
                System.out.println("ID Contac = "+id+" id Address = "+ addressId+" Home Phone = "+homePhone+ " Mobile Phone = "+ mobilePhone+ " workPhone = "+ workPhone
                +" Work Email= "+ workEmail+" other Email = "+otherEmail);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Rollback Berhasil");
                } catch (SQLException sqle2) {
                    System.out.println("Tidak dapat melakukan rollback");
                    sqle2.printStackTrace();
                }

            }
        }
    }

    private static void menampilkanWorkShift() {
        Connection connection = null;
        try {
            connection = getKoneksiKeDB();
            String SQL = "SELECT * FROM WorkShift";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String description = resultSet.getString(2);

                System.out.println("pilihan = " + id + " adalah = " + description);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Rollback Berhasil");
                } catch (SQLException sqle2) {
                    System.out.println("Tidak dapat melakukan rollback");
                    sqle2.printStackTrace();
                }

            }
        }
    }

    private static void menampilkanGender() {
        Connection connection = null;
        try {
            connection = getKoneksiKeDB();
            String SQL = "SELECT * FROM Gender";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String description = resultSet.getString(2);

                System.out.println("pilihan = " + id + " adalah = " + description);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Rollback Berhasil");
                } catch (SQLException sqle2) {
                    System.out.println("Tidak dapat melakukan rollback");
                    sqle2.printStackTrace();
                }

            }
        }
    }

    private static void menampilkanDataEmployee() {
        Connection connection = null;
        try {
            connection = getKoneksiKeDB();
            String sql = "SELECT * FROM Employees";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String tanggalLahir = resultSet.getTimestamp(2).toString();
                String tempatLahir = resultSet.getString(3);
                String bpjsKerja = resultSet.getString(4);
                String bpjsSehat = resultSet.getString(5);
                String expDriverLicense = resultSet.getTimestamp(6).toString();
                String driverLicense = resultSet.getString(7);
                String empNo = resultSet.getString(8);
                String firstName = resultSet.getString(9);
                String lastName = resultSet.getString(10);
                String middleName = resultSet.getString(11);
                String noKTP = resultSet.getString(12);
                String npwp = resultSet.getString(13);
                String photoPath = resultSet.getString(14);
                int contacID = resultSet.getInt(15);
                int genderID = resultSet.getInt(16);
                int loginDetails = resultSet.getInt(17);
                int maritalStatus = resultSet.getInt(18);
                int nationalityID = resultSet.getInt(19);
                int religionID = resultSet.getInt(20);
                int workShiftID = resultSet.getInt(21);

                System.out.println("ID = " + id + " Tanggal Lahir = " + tanggalLahir + " Tempat Lahir = " + tempatLahir + " no BPJS Kerja = " + bpjsKerja + " no BPJS Sehat = " + bpjsSehat);
                System.out.println("Masa Aktif SIM = " + expDriverLicense + " Nomor SIM = " + driverLicense + " NO emp = " + empNo + " Nama = " + firstName + " " + middleName + " " + lastName);
                System.out.println("nomor KTP = " + noKTP + " NPWP = " + npwp + " Direktori foto = " + photoPath);

            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Rollback Berhasil");
                } catch (SQLException sqle2) {
                    System.out.println("Tidak dapat melakukan rollback");
                    sqle2.printStackTrace();
                }

            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner input = new Scanner(System.in);
        String tanggalLahir;
        String tanggal;
        String bulan;
        String tahun;
        String tempatLahir;
        String bpjsKerja;
        String bpjsSehat;
        String expDriverLicense;
        String driverLicense;
        String empNo;
        String firstName;
        String lastName;
        String middleName;
        String noKTP;
        String npwp;
        String photoPath;
        int contacID;
        int genderID;
        int loginDetails;
        int maritalStatus;
        int nationalityID;
        int religionID;
        int workShiftID;
        int id;

        int menu;
        System.out.println("1. Menampilkan data Employee");
        System.out.println("2. Menambah Data Employee");
        System.out.println("3. Update Data Employee");
        System.out.println("4. Delete Data Employee");
        System.out.println("Masukkan Menu: ");
        menu = input.nextInt();
        if (menu == 1) {
            menampilkanDataEmployee();
        }
        if (menu == 2) {

            System.out.println("Masukkan Tanggal Lahir : ");
            System.out.println("Masukkan Tanggal :");
            tanggal = input.next();
            System.out.println("Masukkan Bulan : ");
            bulan = input.next();
            System.out.println("Masukkan Tahun : ");
            tahun = input.next();
            tanggalLahir = tahun + "/" + bulan + "/" + tanggal;

            System.out.println("Masukkan Tempat Lahir");
            tempatLahir = input.next();
            System.out.println("Masukkan Nomor BPJS Kerja");
            bpjsKerja = input.next();
            System.out.println("Masukkan Nomor BPJS Sehat");
            bpjsSehat = input.next();
            System.out.println("Masukkan Masa Aktif SIM ");
            System.out.println("Masukkan Tanggal : ");
            tanggal = input.next();
            System.out.println("Masukkan Bulan : ");
            bulan = input.next();
            System.out.println("Masukkan Tahun : ");
            tahun = input.next();
            expDriverLicense = tahun + "/" + bulan + "/" + tanggal;
            System.out.println("Masukkan Nomor SIM  : ");
            driverLicense = input.next();
            System.out.println("Masukkan Nomor EMP :  ");
            empNo = input.next();
            System.out.println("Masukkan Nama Depan  : ");
            firstName = input.next();
            System.out.println("Masukkan Nama Belakang  : ");
            lastName = input.next();
            System.out.println("Masukan Nama Tengah  ( Bila ada ) : ");
            middleName = input.next();
            System.out.println("Masukkan Nomor KTP  : ");
            noKTP = input.next();

            System.out.println("Masukkan Nomor NPWP  : ");
            npwp = input.next();
            System.out.println("Masukkan Directory PATH Photo : ");
            
            photoPath = input.next();
            menamppilkanContacts();
            System.out.println("Masukkan Contact ID : ");
            contacID = input.nextInt();
            
            menampilkanGender();
            System.out.println("Masukkan Gender ID : ");
            genderID = input.nextInt();
            
            menampilkanLoginDetil();
            System.out.println("Masukkan Login Detil ID : ");
            loginDetails = input.nextInt();
            
            menampilkanMaritalStatus();
            System.out.println("Masukkan Marital Status ID : ");
            maritalStatus = input.nextInt();
            
            menampilkanNationality();
            System.out.println("Masukkan Nationality ID");
            nationalityID = input.nextInt();
            
            menampilkanReligion();
            System.out.println("Masukkan Religion ID : ");
            religionID = input.nextInt();
            
            menampilkanWorkShift();
            System.out.println("Masukkan Work Shift ID : ");
            workShiftID = input.nextInt();

            insertEmployees(tanggalLahir, tempatLahir, bpjsKerja, bpjsSehat, expDriverLicense, driverLicense, empNo, firstName, lastName, middleName, noKTP, npwp, photoPath, contacID, genderID, loginDetails, maritalStatus, nationalityID, religionID, workShiftID);
        }
        if (menu == 3) {
            System.out.println("Masukkan ID yang ingin di update");
            id = input.nextInt();

            System.out.println("Masukkan Tanggal Lahir : ");
            System.out.println("Masukkan Tanggal :");
            tanggal = input.next();
            System.out.println("Masukkan Bulan : ");
            bulan = input.next();
            System.out.println("Masukkan Tahun : ");
            tahun = input.next();
            tanggalLahir = tahun + "/" + bulan + "/" + tanggal;

            System.out.println("Masukkan Tempat Lahir");
            tempatLahir = input.next();
            System.out.println("Masukkan Nomor BPJS Kerja");
            bpjsKerja = input.next();
            System.out.println("Masukkan Nomor BPJS Sehat");
            bpjsSehat = input.next();
            System.out.println("Masukkan Masa Aktif SIM ");
            System.out.println("Masukkan Tanggal : ");
            tanggal = input.next();
            System.out.println("Masukkan Bulan : ");
            bulan = input.next();
            System.out.println("Masukkan Tahun : ");
            tahun = input.next();
            expDriverLicense = tahun + "/" + bulan + "/" + tanggal;
            System.out.println("Masukkan Nomor SIM  : ");
            driverLicense = input.next();
            System.out.println("Masukkan Nomor EMP :  ");
            empNo = input.next();
            System.out.println("Masukkan Nama Depan  : ");
            firstName = input.next();
            System.out.println("Masukkan Nama Belakang  : ");
            lastName = input.next();
            System.out.println("Masukan Nama Tengah  ( Bila ada ) : ");
            middleName = input.next();
            System.out.println("Masukkan Nomor KTP  : ");
            noKTP = input.next();

            System.out.println("Masukkan Nomor NPWP  : ");
            npwp = input.next();
            System.out.println("Masukkan Directory PATH Photo : ");
            photoPath = input.next();

            System.out.println("Masukkan Contact ID : ");
            contacID = input.nextInt();
            System.out.println("Masukkan Gender ID : ");
            genderID = input.nextInt();
            System.out.println("Masukkan Login Detil ID : ");
            loginDetails = input.nextInt();
            System.out.println("Masukkan Marital Status ID : ");
            maritalStatus = input.nextInt();
            System.out.println("Masukkan Nationality ID");
            nationalityID = input.nextInt();
            System.out.println("Masukkan Religion ID : ");
            religionID = input.nextInt();
            System.out.println("Masukkan Work Shift ID : ");
            workShiftID = input.nextInt();

            updateEmployee(tanggalLahir, tempatLahir, bpjsKerja, bpjsSehat, expDriverLicense, driverLicense, empNo, firstName, lastName, middleName, noKTP, npwp, photoPath, contacID, genderID, loginDetails, maritalStatus, nationalityID, religionID, workShiftID, id);
        }
        if (menu == 4) {
            System.out.println("Masukkan ID employee yang ingi dihapus");

            deleteEmployee(id);
        }

    }

}
