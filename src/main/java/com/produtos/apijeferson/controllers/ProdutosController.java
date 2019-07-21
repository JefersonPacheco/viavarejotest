package com.produtos.apijeferson.controllers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.produtos.apijeferson.models.ContextParcela;
import com.produtos.apijeferson.models.Parcelas;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping()
@Api(value="API REST")
public class ProdutosController {
	
	@PostMapping("/parcelarProduto")
	@ApiOperation(value="Este método retorna as parcelas de um produto")
	public String parcelarProduto(@RequestBody ContextParcela context) {

		Parcelas parcela = new Parcelas();

		ArrayList<Parcelas> sList = parcela.calcParcelasComJuros(context.getCondicaoPagamento(), context.getProduto().getValor());
		
		// Construção da lista de parcelas em JSON
		JSONArray allDataArray = new JSONArray();
		
		if (!(sList.size() == 0)) {
			for(int index = 0; index < sList.size(); index++) {
				JSONObject eachData = new JSONObject();
				try {
					eachData.put("numeroParcela", sList.get(index).getNumeroParcela());
					eachData.put("valor", sList.get(index).getValor());
					eachData.put("taxaJurosAoMes", sList.get(index).getTaxaJurosAoMes());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				allDataArray.put(eachData);
			}
		}

		JSONObject root = new JSONObject();
		root.put("parcelas", allDataArray);
		    
		return root.toString();
	}
	
}
