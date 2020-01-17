package com.italia.marxmind.bris.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.italia.marxmind.bris.database.ConnectDB;
import com.italia.marxmind.bris.enm.DocTypes;
import com.italia.marxmind.bris.enm.Purpose;
import com.italia.marxmind.bris.utils.DateUtils;
import com.italia.marxmind.bris.utils.LogU;

public class ORTransaction {

	private long id;
	private String dateTrans;
	private String orNumber;
	private double amount;
	private int isActive;
	private int status;
	private double grossAmount;
	private String orStatus;
	
	private Customer customer;
	private UserDtls userDtls;
	
	private int cnt;
	
	private String address;
	private String purpose;
	
	public static String getLastORNumber() {
		String sql = "SELECT orno FROM orlisting WHERE oractive=1 ORDER BY orid DESC LIMIT 1";
			
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
		conn = ConnectDB.getConnection();
		ps = conn.prepareStatement(sql);
		
		rs = ps.executeQuery();
		
		while(rs.next()){
			return rs.getString("orno");
		}
		
		rs.close();
		ps.close();
		ConnectDB.close(conn);
		}catch(Exception e){e.getMessage();}
		
		
		return "0";
	}
	
	public static ORTransaction loadORIfExist(Customer cus){
		ORTransaction ort = null;
		String sql = " AND orl.ordate=? AND orl.oractive=1 AND orl.orstatus=1 AND cuz.customerid=" + cus.getCustomerid();
		String[] params = new String[1];
		params[0] = DateUtils.getCurrentDateYYYYMMDD();
		sql += " ORDER BY orl.orid DESC limit 1";
		try{
			ort = ORTransaction.retrieve(sql, params).get(0);
		}catch(Exception e){}
		
		return ort;
	}
	
	public static ORTransaction retrieveOROnlyInfo(String orNumber) {
		String sql = "SELECT * FROM orlisting WHERE oractive=1 AND orno=" + orNumber;
		ORTransaction ort = new ORTransaction();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
		conn = ConnectDB.getConnection();
		ps = conn.prepareStatement(sql);
		
		rs = ps.executeQuery();
		
		while(rs.next()){
			
			try{ort.setId(rs.getLong("orid"));}catch(NullPointerException e){}
			try{ort.setDateTrans(rs.getString("ordate"));}catch(NullPointerException e){}
			try{ort.setOrNumber(rs.getString("orno"));}catch(NullPointerException e){}
			try{ort.setAmount(rs.getDouble("oramount"));}catch(NullPointerException e){}
			try{ort.setIsActive(rs.getInt("oractive"));}catch(NullPointerException e){}
			try{ort.setPurpose(rs.getString("purpose"));}catch(NullPointerException e){}
			try{ort.setAddress(rs.getString("orissuedaddress"));}catch(NullPointerException e){}
			try{ort.setStatus(rs.getInt("orstatus"));}catch(NullPointerException e){}
			try{ort.setGrossAmount(rs.getDouble("grossamount"));}catch(NullPointerException e){}
			
			try{ort.setOrStatus(ort.getStatus()==1? "Delivered" : "Cancelled");}catch(NullPointerException e){}
			Customer cus = new Customer();
			try{cus.setCustomerid(rs.getLong("customerid"));}catch(NullPointerException e){}
			ort.setCustomer(cus);
			
			UserDtls user = new UserDtls();
			try{user.setUserdtlsid(rs.getLong("userdtlsid"));}catch(NullPointerException e){}
			ort.setUserDtls(user);
		}
		
		rs.close();
		ps.close();
		ConnectDB.close(conn);
		}catch(Exception e){e.getMessage();}
		
		return ort;
	}
	
	public static List<ORTransaction> retrieve(String sqlAdd, String[] params){
		List<ORTransaction> orls = new ArrayList<>();
		
		String tableOR = "orl";
		String tableCus = "cuz";
		String tableUser = "usr";
		
		String sql = "SELECT * FROM orlisting " + tableOR + ", customer " + tableCus + ", userdtls " + tableUser + " WHERE " +
				tableOR + ".customerid=" + tableCus + ".customerid AND " +
				tableOR + ".userdtlsid=" + tableUser + ".userdtlsid ";
		sql = sql + sqlAdd;		
				
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
		conn = ConnectDB.getConnection();
		ps = conn.prepareStatement(sql);
		
		if(params!=null && params.length>0){
		
			for(int i=0; i<params.length; i++){
				ps.setString(i+1, params[i]);
			}
			
		}
		
		System.out.println("Clearance SQL " + ps.toString());
		
		rs = ps.executeQuery();
		
		while(rs.next()){
			
			ORTransaction ort = new ORTransaction();
			try{ort.setId(rs.getLong("orid"));}catch(NullPointerException e){}
			try{ort.setDateTrans(rs.getString("ordate"));}catch(NullPointerException e){}
			try{ort.setOrNumber(rs.getString("orno"));}catch(NullPointerException e){}
			try{ort.setAmount(rs.getDouble("oramount"));}catch(NullPointerException e){}
			try{ort.setIsActive(rs.getInt("oractive"));}catch(NullPointerException e){}
			try{ort.setPurpose(rs.getString("purpose"));}catch(NullPointerException e){}
			try{ort.setAddress(rs.getString("orissuedaddress"));}catch(NullPointerException e){}
			try{ort.setStatus(rs.getInt("orstatus"));}catch(NullPointerException e){}
			try{ort.setGrossAmount(rs.getDouble("grossamount"));}catch(NullPointerException e){}
			try{ort.setOrStatus(ort.getStatus()==1? "Delivered" : "Cancelled");}catch(NullPointerException e){}
			
			Customer cus = new Customer();
			try{cus.setCustomerid(rs.getLong("customerid"));}catch(NullPointerException e){}
			try{cus.setFirstname(rs.getString("cusfirstname"));}catch(NullPointerException e){}
			try{cus.setMiddlename(rs.getString("cusmiddlename"));}catch(NullPointerException e){}
			try{cus.setLastname(rs.getString("cuslastname"));}catch(NullPointerException e){}
			try{cus.setFullname(rs.getString("fullname"));}catch(NullPointerException e){}
			try{cus.setGender(rs.getString("cusgender"));}catch(NullPointerException e){}
			try{cus.setAge(rs.getInt("cusage"));}catch(NullPointerException e){}
			//try{cus.setAddress(rs.getString("cusaddress"));}catch(NullPointerException e){}
			try{cus.setContactno(rs.getString("cuscontactno"));}catch(NullPointerException e){}
			try{cus.setDateregistered(rs.getString("cusdateregistered"));}catch(NullPointerException e){}
			try{cus.setCardno(rs.getString("cuscardno"));}catch(NullPointerException e){}
			try{cus.setIsactive(rs.getInt("cusisactive"));}catch(NullPointerException e){}
			try{cus.setTimestamp(rs.getTimestamp("timestamp"));}catch(NullPointerException e){}
			try{cus.setCivilStatus(rs.getInt("civilstatus"));}catch(NullPointerException e){}
			try{cus.setPhotoid(rs.getString("photoid"));}catch(NullPointerException e){}
			
			if("1".equalsIgnoreCase(cus.getGender())){
				cus.setGenderName("Male");
			}else{
				cus.setGenderName("Female");
			}
			
			try{cus.setBirthdate(rs.getString("borndate"));}catch(NullPointerException e){}
			try{Customer emergency = new Customer();
			emergency.setCustomerid(rs.getLong("emeperson"));
			cus.setEmergencyContactPerson(emergency);}catch(NullPointerException e){}
			try{cus.setRelationship(rs.getInt("relid"));}catch(NullPointerException e){}
			ort.setCustomer(cus);
			
			UserDtls user = new UserDtls();
			try{user.setUserdtlsid(rs.getLong("userdtlsid"));}catch(NullPointerException e){}
			try{user.setRegdate(rs.getString("regdate"));;}catch(NullPointerException e){}
			try{user.setFirstname(rs.getString("firstname"));}catch(NullPointerException e){}
			try{user.setMiddlename(rs.getString("middlename"));}catch(NullPointerException e){}
			try{user.setLastname(rs.getString("lastname"));}catch(NullPointerException e){}
			try{user.setAddress(rs.getString("address"));}catch(NullPointerException e){}
			try{user.setContactno(rs.getString("contactno"));}catch(NullPointerException e){}
			try{user.setAge(rs.getInt("age"));}catch(NullPointerException e){}
			try{user.setGender(rs.getInt("gender"));}catch(NullPointerException e){}
			try{user.setTimestamp(rs.getTimestamp("timestamp"));}catch(NullPointerException e){}
			try{user.setIsActive(rs.getInt("isactive"));}catch(NullPointerException e){}
			ort.setUserDtls(user);
			
			orls.add(ort);
			
		}
		
		rs.close();
		ps.close();
		ConnectDB.close(conn);
		}catch(Exception e){e.getMessage();}
		
		return orls;
	}
	
	public static ORTransaction save(ORTransaction or){
		if(or!=null){
			
			long id = ORTransaction.getInfo(or.getId() ==0? ORTransaction.getLatestId()+1 : or.getId());
			LogU.add("checking for new added data");
			if(id==1){
				LogU.add("insert new Data ");
				or = ORTransaction.insertData(or, "1");
			}else if(id==2){
				LogU.add("update Data ");
				or = ORTransaction.updateData(or);
			}else if(id==3){
				LogU.add("added new Data ");
				or = ORTransaction.insertData(or, "3");
			}
			
		}
		return or;
	}
	
	public void save(){
			
			long id = getInfo(getId() ==0? getLatestId()+1 : getId());
			LogU.add("checking for new added data");
			if(id==1){
				LogU.add("insert new Data ");
				insertData("1");
			}else if(id==2){
				LogU.add("update Data ");
				updateData();
			}else if(id==3){
				LogU.add("added new Data ");
				insertData("3");
			}
			
	}
	
	public static ORTransaction insertData(ORTransaction or, String type){
		String sql = "INSERT INTO orlisting ("
				+ "orid,"
				+ "ordate,"
				+ "orno,"
				+ "oramount,"
				+ "purpose,"
				+ "orissuedaddress,"
				+ "oractive,"
				+ "customerid,"
				+ "userdtlsid,"
				+ "orstatus,"
				+ "grossamount)" 
				+ "values(?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement ps = null;
		Connection conn = null;
		
		try{
		conn = ConnectDB.getConnection();
		ps = conn.prepareStatement(sql);
		long id =1;
		int cnt = 1;
		LogU.add("===========================START=========================");
		LogU.add("inserting data into table orlisting");
		if("1".equalsIgnoreCase(type)){
			ps.setLong(cnt++, id);
			or.setId(id);
			LogU.add("id: 1");
		}else if("3".equalsIgnoreCase(type)){
			id=getLatestId()+1;
			ps.setLong(cnt++, id);
			or.setId(id);
			LogU.add("id: " + id);
		}
		
		ps.setString(cnt++, or.getDateTrans());
		ps.setString(cnt++, or.getOrNumber());
		ps.setDouble(cnt++, or.getAmount());
		ps.setString(cnt++, or.getPurpose());
		ps.setString(cnt++, or.getAddress());
		ps.setInt(cnt++, or.getIsActive());
		ps.setLong(cnt++, or.getCustomer()==null? 0 : or.getCustomer().getCustomerid());
		ps.setLong(cnt++, or.getUserDtls()==null? 0 : or.getUserDtls().getUserdtlsid());
		ps.setInt(cnt++, or.getStatus());
		ps.setDouble(cnt++, or.getGrossAmount());
		
		LogU.add(or.getDateTrans());
		LogU.add(or.getOrNumber());
		LogU.add(or.getAmount());
		LogU.add(or.getPurpose());
		LogU.add(or.getAddress());
		LogU.add(or.getIsActive());
		LogU.add(or.getCustomer()==null? 0 : or.getCustomer().getCustomerid());
		LogU.add(or.getUserDtls()==null? 0 : or.getUserDtls().getUserdtlsid());
		LogU.add(or.getStatus());
		LogU.add(or.getGrossAmount());
		
		LogU.add("executing for saving...");
		ps.execute();
		LogU.add("closing...");
		ps.close();
		ConnectDB.close(conn);
		LogU.add("data has been successfully saved...");
		}catch(SQLException s){
			LogU.add("error inserting data to orlisting : " + s.getMessage());
		}
		LogU.add("===========================END=========================");
		return or;
	}
	
	public void insertData(String type){
		String sql = "INSERT INTO orlisting ("
				+ "orid,"
				+ "ordate,"
				+ "orno,"
				+ "oramount,"
				+ "purpose,"
				+ "orissuedaddress,"
				+ "oractive,"
				+ "customerid,"
				+ "userdtlsid,"
				+ "orstatus,"
				+ "grossamount)" 
				+ "values(?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement ps = null;
		Connection conn = null;
		
		try{
		conn = ConnectDB.getConnection();
		ps = conn.prepareStatement(sql);
		long id =1;
		int cnt = 1;
		LogU.add("===========================START=========================");
		LogU.add("inserting data into table orlisting");
		if("1".equalsIgnoreCase(type)){
			ps.setLong(cnt++, id);
			setId(id);
			LogU.add("id: 1");
		}else if("3".equalsIgnoreCase(type)){
			id=getLatestId()+1;
			ps.setLong(cnt++, id);
			setId(id);
			LogU.add("id: " + id);
		}
		
		ps.setString(cnt++, getDateTrans());
		ps.setString(cnt++, getOrNumber());
		ps.setDouble(cnt++, getAmount());
		ps.setString(cnt++, getPurpose());
		ps.setString(cnt++, getAddress());
		ps.setInt(cnt++, getIsActive());
		ps.setLong(cnt++, getCustomer()==null? 0 : getCustomer().getCustomerid());
		ps.setLong(cnt++, getUserDtls()==null? 0 : getUserDtls().getUserdtlsid());
		ps.setInt(cnt++, getStatus());
		ps.setDouble(cnt++, getGrossAmount());
		
		LogU.add(getDateTrans());
		LogU.add(getOrNumber());
		LogU.add(getAmount());
		LogU.add(getPurpose());
		LogU.add(getAddress());
		LogU.add(getIsActive());
		LogU.add(getCustomer()==null? 0 : getCustomer().getCustomerid());
		LogU.add(getUserDtls()==null? 0 : getUserDtls().getUserdtlsid());
		LogU.add(getStatus());		
		LogU.add(getGrossAmount());
		
		LogU.add("executing for saving...");
		ps.execute();
		LogU.add("closing...");
		ps.close();
		ConnectDB.close(conn);
		LogU.add("data has been successfully saved...");
		}catch(SQLException s){
			LogU.add("error inserting data to orlisting : " + s.getMessage());
		}
		LogU.add("===========================END=========================");
		
	}
	
	public static ORTransaction updateData(ORTransaction or){
		String sql = "UPDATE orlisting SET "
				+ "ordate=?,"
				+ "orno=?,"
				+ "oramount=?,"
				+ "purpose=?,"
				+ "orissuedaddress=?,"
				+ "customerid=?,"
				+ "userdtlsid=?,"
				+ "orstatus=?,"
				+ "grossamount=?" 
				+ " WHERE orid=?";
		
		PreparedStatement ps = null;
		Connection conn = null;
		
		try{
		conn = ConnectDB.getConnection();
		ps = conn.prepareStatement(sql);
		
		int cnt = 1;
		LogU.add("===========================START=========================");
		LogU.add("updating data into table orlisting");
		
		ps.setString(cnt++, or.getDateTrans());
		ps.setString(cnt++, or.getOrNumber());
		ps.setDouble(cnt++, or.getAmount());
		ps.setString(cnt++, or.getPurpose());
		ps.setString(cnt++, or.getAddress());
		ps.setLong(cnt++, or.getCustomer()==null? 0 : or.getCustomer().getCustomerid());
		ps.setLong(cnt++, or.getUserDtls()==null? 0 : or.getUserDtls().getUserdtlsid());
		ps.setInt(cnt++, or.getStatus());
		ps.setDouble(cnt++, or.getGrossAmount());
		ps.setLong(cnt++, or.getId());
		
		LogU.add(or.getDateTrans());
		LogU.add(or.getOrNumber());
		LogU.add(or.getAmount());
		LogU.add(or.getPurpose());
		LogU.add(or.getAddress());
		LogU.add(or.getCustomer()==null? 0 : or.getCustomer().getCustomerid());
		LogU.add(or.getUserDtls()==null? 0 : or.getUserDtls().getUserdtlsid());
		LogU.add(or.getStatus());
		LogU.add(or.getGrossAmount());
		LogU.add(or.getId());
				
		LogU.add("executing for saving...");
		ps.executeUpdate();
		LogU.add("closing...");
		ps.close();
		ConnectDB.close(conn);
		LogU.add("data has been successfully saved...");
		}catch(SQLException s){
			LogU.add("error updating data to orlisting : " + s.getMessage());
		}
		LogU.add("===========================END=========================");
		return or;
	}
	
	public void updateData(){
		String sql = "UPDATE orlisting SET "
				+ "ordate=?,"
				+ "orno=?,"
				+ "oramount=?,"
				+ "purpose=?,"
				+ "orissuedaddress=?,"
				+ "customerid=?,"
				+ "userdtlsid=?,"
				+ "orstatus=?,"
				+ "grossamount=?" 
				+ " WHERE orid=?";
		
		PreparedStatement ps = null;
		Connection conn = null;
		
		try{
		conn = ConnectDB.getConnection();
		ps = conn.prepareStatement(sql);
		
		int cnt = 1;
		LogU.add("===========================START=========================");
		LogU.add("updating data into table orlisting");
		
		ps.setString(cnt++, getDateTrans());
		ps.setString(cnt++, getOrNumber());
		ps.setDouble(cnt++, getAmount());
		ps.setString(cnt++, getPurpose());
		ps.setString(cnt++, getAddress());
		ps.setLong(cnt++, getCustomer()==null? 0 : getCustomer().getCustomerid());
		ps.setLong(cnt++, getUserDtls()==null? 0 : getUserDtls().getUserdtlsid());
		ps.setInt(cnt++, getStatus());
		ps.setDouble(cnt++, getGrossAmount());
		ps.setLong(cnt++, getId());
		
		LogU.add(getDateTrans());
		LogU.add(getOrNumber());
		LogU.add(getAmount());
		LogU.add(getPurpose());
		LogU.add(getAddress());
		LogU.add(getCustomer()==null? 0 : getCustomer().getCustomerid());
		LogU.add(getUserDtls()==null? 0 : getUserDtls().getUserdtlsid());
		LogU.add(getStatus());
		LogU.add(getGrossAmount());
		LogU.add(getId());
				
		LogU.add("executing for saving...");
		ps.executeUpdate();
		LogU.add("closing...");
		ps.close();
		ConnectDB.close(conn);
		LogU.add("data has been successfully saved...");
		}catch(SQLException s){
			LogU.add("error updating data to orlisting : " + s.getMessage());
		}
		LogU.add("===========================END=========================");
		
	}
	
	public static long getLatestId(){
		long id =0;
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "";
		try{
		sql="SELECT orid FROM orlisting  ORDER BY orid DESC LIMIT 1";	
		conn = ConnectDB.getConnection();
		prep = conn.prepareStatement(sql);	
		rs = prep.executeQuery();
		
		while(rs.next()){
			id = rs.getLong("orid");
		}
		
		rs.close();
		prep.close();
		ConnectDB.close(conn);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return id;
	}
	
	public static Long getInfo(long id){
		boolean isNotNull=false;
		long result =0;
		//id no data retrieve.
		//application will insert a default no which 1 for the first data
		long val = getLatestId();	
		if(val==0){
			isNotNull=true;
			result= 1; // add first data
			System.out.println("First data");
		}
		
		//check if empId is existing in table
		if(!isNotNull){
			isNotNull = isIdNoExist(id);
			if(isNotNull){
				result = 2; // update existing data
				System.out.println("update data");
			}else{
				result = 3; // add new data
				System.out.println("add new data");
			}
		}
		
		
		return result;
	}
	public static boolean isIdNoExist(long id){
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		boolean result = false;
		try{
		conn = ConnectDB.getConnection();
		ps = conn.prepareStatement("SELECT orid FROM orlisting WHERE orid=?");
		ps.setLong(1, id);
		rs = ps.executeQuery();
		
		if(rs.next()){
			result=true;
		}
		
		rs.close();
		ps.close();
		ConnectDB.close(conn);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public static void delete(String sql, String[] params){
		
		Connection conn = null;
		PreparedStatement ps = null;
		try{
		conn = ConnectDB.getConnection();
		ps = conn.prepareStatement(sql);
		
		if(params!=null && params.length>0){
			
			for(int i=0; i<params.length; i++){
				ps.setString(i+1, params[i]);
			}
			
		}
		
		ps.executeUpdate();
		ps.close();
		ConnectDB.close(conn);
		}catch(SQLException s){}
		
	}
	
	public void delete(){
		
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "UPDATE orlisting set oractive=0,userdtlsid="+ getUserDtls().getUserdtlsid() +" WHERE orid=?";
		
		String[] params = new String[1];
		params[0] = getId()+"";
		try{
		conn = ConnectDB.getConnection();
		ps = conn.prepareStatement(sql);
		
		if(params!=null && params.length>0){
			
			for(int i=0; i<params.length; i++){
				ps.setString(i+1, params[i]);
			}
			
		}
		
		ps.executeUpdate();
		ps.close();
		ConnectDB.close(conn);
		}catch(SQLException s){}
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDateTrans() {
		return dateTrans;
	}
	public void setDateTrans(String dateTrans) {
		this.dateTrans = dateTrans;
	}
	public String getOrNumber() {
		return orNumber;
	}
	public void setOrNumber(String orNumber) {
		this.orNumber = orNumber;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public UserDtls getUserDtls() {
		return userDtls;
	}

	public void setUserDtls(UserDtls userDtls) {
		this.userDtls = userDtls;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrStatus() {
		return orStatus;
	}

	public void setOrStatus(String orStatus) {
		this.orStatus = orStatus;
	}

	public double getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}
	
}