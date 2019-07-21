API REST que retorna recebe um produto e as condições de pagamento e retorna as parcelas, com juros calculados de acordo com taxa selic em tempo real

Para maiores detalhes, por favor, consulte a documentação do swagger do projeto: http://localhost:8080/swagger-ui.html

Entrada: { 
          "produto": 
            { "codigo": 123, "nome": "Nome do Produto", "valor": 9999.99 }, 
          "condicaoPagamento": 
            { "valorEntrada": 0, "qtdeParcelas": 5 } 
         }

Saída: {"parcelas":
          [{"valor":2000,"taxaJurosAoMes":0,"numeroParcela":1},
          {"valor":2000,"taxaJurosAoMes":0,"numeroParcela":2},
          {"valor":2000,"taxaJurosAoMes":0,"numeroParcela":3},
          {"valor":2000,"taxaJurosAoMes":0,"numeroParcela":4},
          {"valor":2000,"taxaJurosAoMes":0,"numeroParcela":5}]
        }
