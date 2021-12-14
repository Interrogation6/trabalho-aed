/*------------------------------------------------------------
	Autor: Kelvyn Dantas, Arthur Zica e Izabela Fernanda
	
	Objetivo do Projeto :

	Data Inicial : 	10/12/2021 	14:58
	Ult. Revisão: 	24/11/2021	20:35
------------------------------------------------------------*/
package trabalho1612;

import java.util.Scanner;

public class projeto {

	public static void main(String[] args) {
		Produto matriz[]= {
/*Infantil Lisa*/		new Produto(),
/*Infantil Estampada*/	new Produto(),
/*Adulto Lisa*/			new Produto(),
/*Adulto Estampada*/	new Produto()};
		//'QuantVenda' é a quantidade de produtos vendidos.
		Scanner input = new Scanner(System.in);
		int encerra=0;
		
		System.out.println("Programa iniciado.");
		
		print_inicial();
		while(true)
		{
			String cmd=input.nextLine();
			
			switch(cmd)
			{
			case "help":
				System.out.println("Esses são os comandos :");
				System.out.println("-help : Mostra a lista de comandos.");
				System.out.println("-encerra : Encerra o programa.");
				System.out.println("-venda : Informa o programa que uma quantidade de itens foi vendido.");
				
				break;
			case "encerra":
				encerra=1;	//Aciona variavel de encerramento.
				break;
			case "venda":
				System.out.println("Qual tipo de máscara foi vendida?");
				while(true)
				{
					cmd=input.nextLine();
					cmd=cmd.toLowerCase();
					switch(cmd)
					{
					case "infantil lisa":
						
						quant_query("infantis lisas",matriz[0],input);
						print_inicial();
						
						encerra=1;	//Aciona variavel de encerramento DESTA REPETICAO.
						break;
					case "infantil estampada":
						
						quant_query("infantis estampadas",matriz[1],input);
						print_inicial();
						
						encerra=1;	//Aciona variavel de encerramento DESTA REPETICAO.
						break;
					case "adulto lisa":
						
						quant_query("adultas lisas",matriz[2],input);
						print_inicial();
						
						encerra=1;	//Aciona variavel de encerramento DESTA REPETICAO.
						break;
					case "adulto estampada":
						
						quant_query("adultas estampadas",matriz[3],input);
						print_inicial();
						
						encerra=1;	//Aciona variavel de encerramento DESTA REPETICAO.
						break;
					default:
					
						System.out.println("Item invalido. (pode ser 'infantil' ou 'adulto', com 'lisa' ou 'estampado'.");
						break;
					}
					if(encerra==1)
					{
						encerra=0;	// Evita que o programa encerre precocemente.
						break;
					}
				}
				break;
			}
			if(encerra==1)
			{
				break;
			}
		}
		input.close();
	}

	public static void quant_query(String mascara,Produto prod, Scanner input) {
		System.out.println("Quantas máscaras "+mascara+" foram vendidas?");
		int quant=input.nextInt();
		if(quant<0)
		{
			System.out.println("Número inválido.");
		}
		else if(quant>prod.estoque)
		{
			System.out.println("Não há produtos no estoque suficientes para assumir a demanda.");
			System.out.println("(No momento temos somente "+prod.estoque+" deste produto em estoque.)");
		}
		else
		{
			prod.estoque-=quant;
			prod.quantvenda+=quant;
			System.out.println(quant+" máscaras "+mascara+" vendidas com sucesso!");
		}
				
	}
	public static void print_inicial() {
		System.out.println("");
		System.out.println("Insira seu comando(escreva 'help' para lista de comandos) :");
	}
}



class Produto {
	int estoque;
	int custo;
	int preçovenda;
	int quantvenda;
	
	public Produto()
	{
		estoque=0;
		custo=0;
		preçovenda=0;
		quantvenda=0;
	}
}