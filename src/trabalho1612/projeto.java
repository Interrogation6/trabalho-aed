/*------------------------------------------------------------
	Autor: Kelvyn Dantas, Arthur Zica e Izabela Fernanda
	
	Objetivo do Projeto : 	O programa tem como objetivo facilitar o gerenciamento de vendas
							de m�scaras faciais que podem vir em 4 modelos (definido pelo programa).
							Este gerenciamento envolve a intera��o do usu�rio para inserir novas
							vendas e cadastros al�m de ler e escrever relat�rios de vendas e estoque
							realizados durante o seu funcionamento.

	Data Inicial : 	10/12/2021 	14:58
	Ult. Revis�o: 	15/12/2021	14:22
------------------------------------------------------------*/
package trabalho1612;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class projeto {

	public static void main(String[] args) throws IOException {
		
		System.out.println("Programa iniciado.");
		Produto matriz[] = {
	/* Infantil Lisa */ 	new Produto(),
	/* Infantil Estampada */new Produto(),
	/* Adulto Lisa */ 		new Produto(),
	/* Adulto Estampada */ 	new Produto()};
		
		Scanner input = new Scanner(System.in);
		int encerra = 0;
		
		File file = new File("src/estoque.txt"); 	// Cria um arquivo para armazenar os dados do estoque das m�scaras.
		matriz=atualiza_matriz(matriz,file);		// Importa os valores iniciais do estoque.txt
		
		print_inicial(); // Chama a fun��o que imprime o menu inicial direto, sem a necessidade de digitar o help.
		while (true) {
			int cmd = input.nextInt();

			switch (cmd) {
				case 1:	// Comando 'help'.
					print_inicial();

					break;
				case 2:	// Comando 'encerra'.
					encerra = 1; // Aciona variavel de encerramento.
					System.out.println("Programa encerrado.");
					
					break;
				case 3:	// Comando 'venda'.
					matriz = atualiza_matriz(matriz, file); // Atualiza a matriz a ser processada.
					venda(matriz, input); 					// Inicia o processo de venda.
					atualiza_estoque(matriz, file);			// Atualiza o estoque.txt com os novos parametros. (ap�s a venda.)
					
					break;
				case 4:	// Comando 'cadastro'
					cadastrarProduto(matriz,input, file); 	// Inicia o processo de cadastro.
					
					break;
				case 5:	// Comando 'relatorio_produto'
					listarProdutos(file);					// Printa as informa��es sobre os produtos na tela.
					
					break;
				case 6:	// Comando 'relatorio_vendas'
					listarVendas();							// Printa o hist�rico de vendas na tela.
						
					break;
				default:
					System.out.println("Comando inv�lido. Tente novamente.");
					
					break;
			}
			if (encerra == 1) {
				break;
			}
		}
		input.close();
	}
	
/*	Nome: venda
 * 	Descri��o:	Inicia o processo de venda de produtos.
 * 	
 * 	Inputs:		Produto[] 'matriz'	- Parametro passado para ser usado pela fun��o secund�ria 'quant_query'.
 * 				Scanner 'input'		- Parametro usado para a intera��o do usu�rio pelo teclado.
 */
	private static void venda(Produto[] matriz, Scanner input) throws IOException {
		
		System.out.println("Qual tipo de m�scara foi vendida?");
		System.out.print("1 - infantil lisa\n2 - infantil estampada\n3 - adulto lisa\n4 - adulto estampada\n");
		
		int quebra=0;
		while (true) {
			int cmd = input.nextInt();
			switch (cmd) {
				case 1:
					quant_query("infantis lisas", matriz[0], input);
					quebra = 1; // Aciona variavel de encerramento DESTA REPETICAO.
					
					break;
				case 2:
					quant_query("infantis estampadas", matriz[1], input);
					quebra = 1; // Aciona variavel de encerramento DESTA REPETICAO.
					
					break;
				case 3:
					quant_query("adultas lisas", matriz[2], input);
					quebra = 1; // Aciona variavel de encerramento DESTA REPETICAO.
					
					break;
				case 4:
					quant_query("adultas estampadas", matriz[3], input);
					quebra = 1; // Aciona variavel de encerramento DESTA REPETICAO.
					
					break;
				default:
					System.out.println("Item inv�lido.");
					
					break;
			}
			if (quebra == 1) {
				quebra = 0; // Quebra o loop indefinitivo.
				break;
			}
		}
	}

/*	Nome: quant_query
 * 	Descri��o: 	Inicia o processo de inser��o de dados sobre a venda 
 * 				de uma quantia de produtos. Tamb�m registra esta venda no arquivo venda.txt.
 * 
 * 	Inputs:		String 'mascara'- Refere-se ao nome do tipo de mascara vendida.
 * 				Produto 'prod'	- Elemento espec�fico da matriz referente a mascara vendida.
 * 				Scanner 'input'	- Parametro usado para a intera��o do usu�rio pelo teclado.
 */
	public static void quant_query(String mascara, Produto prod, Scanner input) throws IOException {
		FileWriter fileWriter = new FileWriter(new File ("src/venda.txt"), true);	// Prepara o arquivo venda.txt
		
		System.out.println("Quantas m�scaras " + mascara + " foram vendidas?");
		int quant = input.nextInt();
		
		if (quant < 0) {
			System.out.println("N�mero inv�lido.");
		} else if (quant > prod.estoque) {
			System.out.println("N�o h� produtos no estoque suficientes para assumir a demanda.");
			System.out.println("(No momento temos somente " + prod.estoque + " deste produto em estoque.)");
		} else {
			prod.estoque -= quant;
			prod.quantvenda += quant;
			
			String custo = String.format("%.2f",(float)prod.custo*quant/100);		// Converte o n�mero para string com 2 algarismos decimais 
			String venda = String.format("%.2f",(float)prod.pre�ovenda*quant/100);	// (O numero original � 'int', logo n possui algarismos decimais. Necessitando ser convertido para 'float' e ser dividido por 100.)
			String lucro = String.format("%.2f", (float)(prod.pre�ovenda-prod.custo)*quant/100);
			
			System.out.println(quant + " m�scaras " + mascara + " vendidas com sucesso!");
			fileWriter.write("Tipo: "+prod.nome + ";Quantidade: " + quant + ";Estoque atual: " + prod.estoque + ";Custo total: R$" + custo + ";Pre�o total da venda: R$" + venda + ";Lucro total: R$"+ lucro + "\n"); // Escreve no arquivo a venda realizada com o seu lucro. - Iza
		}
		fileWriter.close();

	}
/*	Nome: print_inicial
 * 	Descri��o: 	Printa na tela os comandos dispon�veis para o usu�rio, 
 * 				tipicamente usado quando o usu�rio finaliza uma fun��o 
 * 				ou insere um comando inv�lido. 
 */
	public static void print_inicial() { // Imprime o menu inicial. - Iza
		System.out.println("");
		System.out.println("Insira seu comando :");
		System.out.println("1. Help: Mostra a lista de comandos.");
		System.out.println("2. Encerra: Encerra o programa.");
		System.out.println("3. Venda: Informa o programa que uma quantidade de itens foi vendido.");
		System.out.println("4. Cadastrar produtos: Cadastra novos produtos.");
		System.out.println("5. Listar estoque: Mostra o estoque atual.");
		System.out.println("6. Listas vendas: mostra as vendas realizadas.");

	}

/*	Nome: cadastrarProduto
 * 	Descri��o:	Insere novos atributos de produtos atualizando o elemento especifico da matriz 
 * 				e seu arquivo referente.
 * 
 * 	Inputs:		Produto[] 'matriz'	- Matriz a ser lida para atualizar o novo .txt gerado
 * 				Scanner 'input'		- Parametro usado para a intera��o do usu�rio pelo teclado.
 * 				File 'file'			- Arquivo refer�ncia a ser atualizado com as novas informa��es.
 */
	public static void cadastrarProduto(Produto[] matriz, Scanner input, File file) throws IOException {
		System.out.println("Essa s�o as op��es de produtos:");
		System.out.print("1 - infantil lisa\n2 - infantil estampada\n3 - adulto lisa\n4 - adulto estampada\n");
		
		int tamanho = input.nextInt();
		Produto produto = new Produto();
		if (tamanho == 1) {
			produto.nome = "infantil lisa";
		} else if (tamanho == 2) {
			produto.nome = "infantil estampada";
		} else if (tamanho == 3) {
			produto.nome = "adulto lisa";
		} else if (tamanho == 4) {
			produto.nome = "adulto estampada";
		}
		
		System.out.print("Digite o custo da mascara: ");
		produto.custo = (int)(input.nextDouble()*100);		// Multiplica por 100 pra possibilitar numeros fracionais.
		
		System.out.print("Digite o quantidade da mascara em estoque: ");
		produto.estoque = input.nextInt();
		
		System.out.print("Digite o pre�o de venda da mascara: ");
		produto.pre�ovenda = (int)(input.nextDouble()*100);	// Multiplica por 100 pra possibilitar numeros fracionais.
		
		matriz[tamanho-1]=produto;
		atualiza_estoque(matriz, file);						// Tudo processado, s� falta atualizar o estoque.txt

		System.out.println("M�scara cadastrada com sucesso!");
	}

/*	Nome: listarProdutos
 * 	Descri��o:	 Printar na tela os produtos cadastrados com todas suas informa��es atuais.
 */
	public static void listarProdutos(File file) throws IOException {
		FileReader leitor = new FileReader(file);
		BufferedReader leitorBuffer = new BufferedReader(leitor);

		String linha = "";
		System.out.println("Esses s�o os produtos cadastrados: ");
		while ((linha = leitorBuffer.readLine()) != null) {
			String[] produto = linha.split(";");
			String custo = String.format("%.2f",Float.parseFloat(produto[1])/100);	//Divide por 100 pra escrever no formato correto.
			String venda = String.format("%.2f",Float.parseFloat(produto[3])/100);	//Divide por 100 pra escrever no formato correto.
			System.out.println("nome: " + produto[0] + "  custo: R$" + custo + "  estoque: " + produto[2] + " pre�o de venda: R$" + venda + " Total vendidos: " + produto[4]);
		}
		leitorBuffer.close();
	}

/*	Nome: listarVendas
 * 	Descri��o:	Printar na tela as vendas feitas salvas no arquivo venda.txt 
 * 				em um formato semelhante � uma nota fiscal.
 */
	public static void listarVendas() throws IOException {
		FileReader leitor = new FileReader(new File ("src/venda.txt")); 
		BufferedReader leitorBuffer = new BufferedReader(leitor);

		String linha = "";
		System.out.println("Essas s�o as vendas realizadas: ");
		while ((linha = leitorBuffer.readLine()) != null) {
			System.out.println(linha.replace(";", " / "));	//Substitue ';' por uma '/' com espa�os para facilitar visualiza��o.
		}
		leitorBuffer.close();
	}
	
/*	Nome: atualiza_matriz
 * 	Descri��o: Atualiza vari�vel 'produtos' atrav�s de atributos escritos em um arquivo .txt 'file'.
 * 
 * 	Inputs: Produto[] 'produtos' - Matriz refer�ncia, cont�m os valores a serem alterados.
 * 			File 'file' 		 -  Arquivo .txt onde est�o armazenados valores referentes � matriz.
 * 
 * 	Output: Produto[] 			 - Matriz atualizada com os valores importados.
 */
	public static Produto[] atualiza_matriz (Produto[] produtos, File file) throws IOException {
		FileReader leitor = new FileReader(file);
		BufferedReader leitorBuffer = new BufferedReader(leitor);

		String linha = "";
		while ((linha = leitorBuffer.readLine()) != null) {
			String[] atributos = linha.split(" ; "); // Separa a linha em um vetor de strings por ; - Iza
			Produto produto = new Produto(); 
			if(atributos.length!=5)	{
				System.err.println("Arquivo estoque.txt formatado incorretamente, importa��o ignorada.");
			}
			else {
				produto.nome = atributos[0]; 
				produto.custo = Integer.parseInt(atributos[1]);
				produto.estoque = Integer.parseInt(atributos[2]);
				produto.pre�ovenda = Integer.parseInt(atributos[3]);
				produto.quantvenda = Integer.parseInt(atributos[4]);
			}
			
			if (produto.nome.equals("infantil lisa")) {
				produtos[0] = produto;
			} else if (produto.nome.equals("infantil estampada")) {
				produtos[1] = produto;
			} else if (produto.nome.equals("adulto lisa")) {
				produtos[2] = produto;
			} else if (produto.nome.equals("adulto estampada")) {
				produtos[3] = produto;
			}

		}
		leitorBuffer.close();
		return produtos;
	}

/*	Nome: atualiza_estoque
 * 	Descri��o:	Atualiza o arquivo 'file' com os valores armazenados dentro do vetor 'produtos'.
 * 
 * 	Inputs:		Produto[] 'produtos'	- Vetor que contem os valores a serem armazenados.
 * 				File 'file'				- Arquivo a ser salvo as informa��es
 */
	public static void atualiza_estoque(Produto[] produtos, File file) throws IOException {
		FileWriter fileWriter = new FileWriter(file);
		
		for(int i=0;i<produtos.length;i++) {
			String tamanhoStr = "";
			if (i == 0) {
				tamanhoStr = "infantil lisa";
			} else if (i == 1) {
				tamanhoStr = "infantil estampada";
			} else if (i == 2) {
				tamanhoStr = "adulto lisa";
			} else if (i == 3) {
				tamanhoStr = "adulto estampada";
			}
			
			fileWriter.write(tamanhoStr + " ; " + produtos[i].custo + " ; " + produtos[i].estoque + " ; " + produtos[i].pre�ovenda +" ; " + produtos[i].quantvenda + "\n");			
		}
		fileWriter.close();
	}
}

class Produto {
	String nome;
	int estoque;
	int custo;
	int pre�ovenda;
	int quantvenda;

	public Produto() {
		nome = "";
		estoque = 0;
		custo = 0;
		pre�ovenda = 0;
		quantvenda = 0;
	}
}