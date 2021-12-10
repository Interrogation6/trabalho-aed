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
		int matriz[][]= {
				/*{Estoque,Custo,PreçoVenda,QuantVenda}*/
/*Infantil Lisa*/		{0,0,0,0},
/*Infantil Estampada*/	{0,0,0,0},
/*Adulto Lisa*/			{0,0,0,0},
/*Adulto Estampada*/	{0,0,0,0}};
		//'QuantVenda' é a quantidade de produtos vendidos.
		Scanner input = new Scanner(System.in);
		int encerra=0;
		
		System.out.println("Programa iniciado.");
		
		
		while(true)
		{
			System.out.println("Insira seu comando(escreva 'help' para lista de comandos) :");
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
				System.out.println("Qual item foi vendido?");
				while(true)
				{
					cmd=input.nextLine();
					cmd=cmd.toLowerCase();
					switch(cmd)
					{
					case "infantil lisa":
						
						encerra=1;	//Aciona variavel de encerramento DESTA REPETICAO.
						break;
					case "infantil estampada":
						
						encerra=1;	//Aciona variavel de encerramento DESTA REPETICAO.
						break;
					case "adulto lisa":
						
						encerra=1;	//Aciona variavel de encerramento DESTA REPETICAO.
						break;
					case "adulto estampada":
						
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

}
