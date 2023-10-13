package application.entities;

import java.time.LocalDate;

public class Request {
	private int id;
	private String tieude;
	private String noidung;
	private LocalDate ngaygui;
	private int madouutien;
	private int manv_gui;
	private int manv_xuly;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTieude() {
		return tieude;
	}
	public void setTieude(String tieude) {
		this.tieude = tieude;
	}
	public String getNoidung() {
		return noidung;
	}
	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}
	public LocalDate getNgaygui() {
		return ngaygui;
	}
	public void setNgaygui(LocalDate ngaygui) {
		this.ngaygui = ngaygui;
	}
	public int getMadouutien() {
		return madouutien;
	}
	public void setMadouutien(int madouutien) {
		this.madouutien = madouutien;
	}
	public int getManv_gui() {
		return manv_gui;
	}
	public void setManv_gui(int manv_gui) {
		this.manv_gui = manv_gui;
	}
	public int getManv_xuly() {
		return manv_xuly;
	}
	public void setManv_xuly(int manv_xuly) {
		this.manv_xuly = manv_xuly;
	}
	
	
	

}
