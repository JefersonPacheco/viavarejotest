package com.produtos.apijeferson.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Parcelas {
	
	private int numeroParcela;
	private double valor;
	private double taxaJurosAoMes;

	public int getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(int numeroParcela) {
		this.numeroParcela = numeroParcela;
	}

	public double getValor() {
		return Math.round(valor * 100.0) / 100.0;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getTaxaJurosAoMes() {
		return Math.round(taxaJurosAoMes * 100.0) / 100.0;
	}

	public void setTaxaJurosAoMes(double taxaJurosAoMes) {
		this.taxaJurosAoMes = taxaJurosAoMes;
	}

	public ArrayList<Parcelas> calcParcelasComJuros(CondicaoPagamento pagamento, double vlrProduto) {
		
		ArrayList<Parcelas> listParcelas = new ArrayList<Parcelas>();
		int numParcela = 1;
		double valorParcela;
		double taxaSelic;
		
		// Consulta a lista de taxas da API do governo, formato JSON
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<TaxaSelic>> rateResponse = restTemplate.exchange(
				"https://api.bcb.gov.br/dados/serie/bcdata.sgs.11/dados?formato=json",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<TaxaSelic>>() { });
		List<TaxaSelic> rates = rateResponse.getBody();
		
		// Insere na variável "taxaSelic" a ultima taxa retornada (Ou seja, a mais atual disponível)
		taxaSelic = rates.get(rates.size()-1).getValor();
		
		// Se a qtde de parcelas for maior que 6, o valor da parcela deve conter a taxa de juros
		if(pagamento.getQtdeParcelas() <= 6) {
			
			valorParcela = (vlrProduto - pagamento.getValorEntrada()) / pagamento.getQtdeParcelas();
			while (numParcela <= pagamento.getQtdeParcelas()) {
				Parcelas parcela = new Parcelas();
				parcela.numeroParcela = numParcela;
				parcela.taxaJurosAoMes = 0;
				parcela.valor = valorParcela;
				listParcelas.add(parcela);
				numParcela++;
			}
			return listParcelas;
			
		}else{
			
			valorParcela = (vlrProduto - pagamento.getValorEntrada()) / pagamento.getQtdeParcelas();
			while (numParcela <= pagamento.getQtdeParcelas()) {
				Parcelas parcela = new Parcelas();
				parcela.numeroParcela = numParcela;
				parcela.taxaJurosAoMes = taxaSelic;
				parcela.valor = valorParcela + (valorParcela * taxaSelic);
				listParcelas.add(parcela);
				numParcela++;
			}
			return listParcelas;
			
		}
	}
}
