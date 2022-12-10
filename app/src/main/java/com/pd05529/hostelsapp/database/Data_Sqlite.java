package com.pd05529.hostelsapp.database;

public class Data_Sqlite {
    //Create table
    //Loại phòng
    public static String create_RoomType = "CREATE TABLE RoomType(" +
            "idType INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "price INTEGER NOT NULL," +
            "squareArea INTEGER NOT NULL," +
            "maxMember INTEGER NOT NULL)";
    //Phòng
    public static String create_Room = "CREATE TABLE Room(" +
            "idRoom TEXT PRIMARY KEY," +
            "name TEXT NOT NULL," +
            "idType INTEGER NOT NULL," +
            "mode INTEGER NOT NULL," +
            "note TEXT)";
    //Chủ trọ
    public static String create_Owner = "CREATE TABLE Owner(" +
            "idOwner INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "username TEXT NOT NULL," +
            "password TEXT NOT NULL," +
            "phone INTEGER NOT NULL," +
            "email TEXT NOT NULL," +
            "address TEXT," +
            "gender INTEGER NOT NULL," +
            "cmnd INTEGER," +
            "date DATE NOT NULL)";
    //Khách trọ
    public static String create_Customer = "CREATE TABLE Customer(" +
            "idCus TEXT PRIMARY KEY," +
            "name TEXT NOT NULL," +
            "gender INTEGER NOT NULL," +
            "email TEXT," +
            "phone INTEGER NOT NULL," +
            "cmnd INTEGER NOT NULL," +
            "address TEXT," +
            "birthDay DATE NOT NULL)";
    //Ser electricity
    public static String create_Ser = "CREATE TABLE Ser(" +
            "idSer INTEGER PRIMARY KEY AUTOINCREMENT," +
            "price INTEGER NOT NULL," +
            "date DATE NOT NULL)";

    //Đăng ký phòng trọ
    public static String create_Register = "CREATE TABLE Register(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "idRoom TEXT NOT NULL," +
            "dateReg DATE NOT NULL," +
            "dateOut DATE)";
    //Thông tin đăng kí phòng
    public static String create_register_info = "CREATE TABLE Reg_info(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "idReg INTEGER NOT NULL," +
            "idCus TEXT NOT NULL)";
    //Hóa đơn
    public static String create_Bill = "CREATE TABLE Bill(" +
            "idBill INTEGER PRIMARY KEY AUTOINCREMENT," +
            "idRoom TEXT NOT NULL," +
            "amount INTEGER NOT NULL," +
            "prePay INTEGER," +
            "owe INTEGER," +
            "more INTEGER," +
            "date DATE," +
            "note TEXT," +
            "oldWater INTEGER NOT NULL," +
            "newWater INTEGER NOT NULL," +
            "oldElec INTEGER NOT NULL," +
            "newElec INTEGER NOT NULL)";
    //Insert data table owner
    public static String insert_owner = "INSERT INTO Owner VALUES" +
            "(1,'Trương Công Nghĩa','admin','admin',339214624,'nghiatcpd05529@fpt.edu.vn','Liên Chiểu - Đà Nẵng',0,0,'27-01-1999')";
    //Insert date table customer
    public static String insert_customer = "INSERT INTO Customer VALUES" +
            "('KH01','Nguyễn A',1,null,339123,1230812093,null,'22-01-1999')," +
            "('KH02','Nguyễn Chính',0,null,14578,775897982,null,'10-07-1989')," +
            "('KH03','Trần Hưng',0,null,98788,976431,null,'22-06-1978')," +
            "('KH04','Thị Mèo',1,null,339123,0657931564,null,'23-09-2002')";
    //Insert data table room type
    public static String insert_room_type = "INSERT INTO RoomType VALUES" +
            "(1,'loại 1',500000,40,4)," +
            "(2,'loại 2',500000,10,2)," +
            "(3,'loại 3',500000,50,5)," +
            "(4,'loại 4',500000,20,3)";
    //Insert data table room type
    public static String insert_room = "INSERT INTO Room VALUES" +
            "('P101','Phòng 101',1,1,null)," +
            "('P201','Phòng 201',2,1,null)," +
            "('P304','Phòng 304',1,1,null)," +
            "('P102','Phòng phòng 204',4,1,null)";
    //Insert data table service
    public static String insert_service = "INSERT INTO Ser(price,date) VALUES" +
            "(3500,'23-10-2010')," +
            "(100,'11-09-2021')";


}
