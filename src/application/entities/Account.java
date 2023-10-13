package application.entities;

import java.time.LocalDate;

public class Account {
	private int id;
	private String username;
	private String password;
	private String hoten;
	private LocalDate ngaysinh;
	private byte[] hinhanh;
	private String verificationCode;
	
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHoten() {
		return hoten;
	}
	public void setHoten(String hoten) {
		this.hoten = hoten;
	}
	public LocalDate getNgaysinh() {
		return ngaysinh;
	}
	public void setNgaysinh(LocalDate ngaysinh) {
		this.ngaysinh = ngaysinh;
	}
	public byte[] getHinhanh() {
		return hinhanh;
	}
	public void setHinhanh(byte[] hinhanh) {
		this.hinhanh = hinhanh;
	}
	@Override
	public String toString() {
		return id +","+ username;
	}
	
	
	
}
