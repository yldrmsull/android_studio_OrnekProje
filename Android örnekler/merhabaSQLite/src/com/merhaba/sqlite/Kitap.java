package com.merhaba.sqlite;

public class Kitap {

	public int id;
	public String kitapAdi;
	public int yil;
	
	public Kitap()
	{
		
	}
	public Kitap(int id,String kitapAdi,int yil)
	{
		this.id = id;
		this.kitapAdi = kitapAdi;
		this.yil = yil;
	}
	
	public Kitap(String kitapAdi,int yil)
	{
		this.kitapAdi = kitapAdi;
		this.yil = yil;
	}
}
