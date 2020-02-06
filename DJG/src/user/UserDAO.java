package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() { //������ MySQL�� �������ִ� �κ�
		try {
			String dbURL = "jdbc:mysql://localhost:3306/DJG?characterEncoding=UTF-8&serverTimezone=UTC";
			String dbID = "root";
			String dbPassword = "twins5302";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//�α��� �õ����ִ� �Լ�
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?"; //������ ����ؼ� �����´�
		try {
			pstmt = conn.prepareStatement(SQL);//SQL ������ �����ͺ��̽��� �����ϴ�
			pstmt.setString(1, userID); //�߿�, SQL ��ŷ ��� ����ϱ� ���� �������� preapredStatement ����ϴµ� �ϳ��� ���� �غ��س��� ? �־���� ?�� �ش��ϴ� �������� userID�� �־���. ���� �����ͺ��̽����� ����� ���̵� �Է¹޾Ƽ� ������ �����ϴ��� �����Ѵٸ� ��й�ȣ�� ���� ���������� �ϴ� ��
			rs = pstmt.executeQuery();
			if (rs.next()) { //��� �����ϸ�
				if(rs.getString(1).equals(userPassword))  //����� �����ϴٸ�(��й�ȣ�� ���ӽõ��� ��й�ȣ ��ġ�ϸ�)
					return 1; //�α��� ����
				else
					return 0; //��й�ȣ ����ġ
			}
			rs.close();
			return -1; //���̵� ����
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return -2; //�����ͺ��̽� ����
	}
	
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());;
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserMajor());
			pstmt.setString(5, user.getUserYear());
			pstmt.setString(6, user.getUserGrade());
			pstmt.setString(7, user.getUserLgrade());
			pstmt.setString(8, user.getUserMchild());
			pstmt.setString(9, user.getUserIncome());
			pstmt.setString(10, user.getUserVol());
			pstmt.setString(11, user.getUserMentor());
			pstmt.setString(12, user.getUserExam());
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //�����ͺ��̽� ����
	}

	public User djg(String userID) throws SQLException {
		String SQL = "SELECT * FROM USER WHERE userID = ?";
		User user = null;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				 user = new User();
				 user.setUserID(rs.getString("userID"));
				 user.setUserPassword(rs.getString("userPassword"));
		         user.setUserName(rs.getString("userName"));
		         user.setUserYear(rs.getString("userYear"));
		         user.setUserGrade(rs.getString("userGrade"));
		         user.setUserLgrade(rs.getString("userLgrade"));
		         user.setUserMchild(rs.getString("userMchild"));
		         user.setUserIncome(rs.getString("userIncome"));
		         user.setUserVol(rs.getString("userVol"));
		         user.setUserMentor(rs.getString("userMentor"));
		         user.setUserExam(rs.getString("userExam"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
			   if(rs!=null)	try{rs.close();}catch(SQLException ex){}
			   if(pstmt!=null)	try{pstmt.close();}catch(SQLException ex){}
			   if(conn!=null)	try{conn.close();}catch(SQLException ex){}
			  }
		return user;
	}


public boolean modification(User user) throws SQLException {
	boolean b = false;
	String SQL = "UPDATE USER SET userPassword = ?, userMajor = ?, userYear = ?, userGrade = ?, userLgrade = ?, userMchild = ?, userIncome = ?, userVol = ?, userMentor = ?, userExam = ? WHERE userID = ?";
	try {
		pstmt = conn.prepareStatement(SQL);
		pstmt.setString(1, user.getUserPassword());
		pstmt.setString(2, user.getUserMajor());
		pstmt.setString(3, user.getUserYear());
		pstmt.setString(4, user.getUserGrade());
		pstmt.setString(5, user.getUserLgrade());
		pstmt.setString(6, user.getUserMchild());
		pstmt.setString(7, user.getUserIncome());
		pstmt.setString(8, user.getUserVol());
		pstmt.setString(9, user.getUserMentor());
		pstmt.setString(10, user.getUserExam());
		pstmt.setString(11, user.getUserID());
		if(pstmt.executeUpdate() > 0)
			b = true;
	} catch(Exception e) {
		e.printStackTrace();
	} finally{
		   if(rs!=null)	try{rs.close();}catch(SQLException ex){}
		   if(pstmt!=null)	try{pstmt.close();}catch(SQLException ex){}
		   if(conn!=null)	try{conn.close();}catch(SQLException ex){}
		  }
	return b;
	}
}
	