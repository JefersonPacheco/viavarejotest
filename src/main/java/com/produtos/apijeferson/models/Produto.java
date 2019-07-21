package com.produtos.apijeferson.models;

public class Produto {

	private int codigo;
	private String nome;
	private double valor;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getValor() {
		return Math.round(valor * 100.0) / 100.0;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
}
