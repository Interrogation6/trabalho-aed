/*------------------------------------------------------------
	Autor: Kelvyn Dantas, Arthur Zica e Izabela Fernanda
	
	Objetivo do Projeto :

	Data Inicial : 	10/12/2021 	14:58
	Ult. Revisão: 	24/11/2021	20:35
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
		Produto matriz[] = {
				/* Infantil Lisa */ new Produto(),
				/* Infantil Estampada */ new Produto(),
				/* Adulto Lisa */ new Produto(),
				/* Adulto Estampada */ new Produto() };
		// 'QuantVenda' é a quantidade de produtos vendidos.
		Scanner input = new Scanner(System.in);
		int encerra = 0;
		File file = new File("src/estoque.txt"); // Cria um arquivo para armazenar os dados do estoque das máscaras. - Iza.
		System.out.println("Programa iniciado.");

		while (true) {
			print_inicial(); // Chama a função que imprime o menu inicial direto, sem a necessidade de digitar o help. - Iza 
			int cmd = input.nextInt();


			// Troquei as opções do case para os nÃºmeros, para facilitar na hora de escrever. - Iza
			switch (cmd) {
				case 1:
					print_inicial(); // Mostra as opçõees. - Iza

					break;
				case 2:
					encerra = 1; // Aciona variavel de encerramento.
					System.out.println("Programa encerrado.");
					
					break;
				case 3:
					matriz = carregaProdutos(matriz, file); // Chama a função que carrega os produtos do arquivo. - Iza
					encerra = venda(matriz, input, encerra); // Transformei a opção venda, em uma função. - Iza 
					
					break;
				case 4:
					cadastrarProduto(input, file); // Chama a função que cadastra os produtos. - Iza
					
					break;
				case 5:
					listarProdutos(input, file);
					
					break;
				case 6:
						listarVendas(input);
						
					break;
				default:
					System.out.println("Comando inválido. Tente novamente.");
					
					break;
			}
			if (encerra == 1) {
				break;
			}
		}
		input.close();
	}

	private static int venda(Produto[] matriz, Scanner input, int encerra) throws IOException {
		System.out.println("Qual tipo de máscara foi vendida?");
		System.out.print("1 - infantil lisa\n2 - infantil estampada\n3 - adulto lisa\n4 - adulto estampada\n"); // Mostra as opções. - Iza
		while (true) {
			int cmd = input.nextInt();

			// Troquei as opções do case para os números, para facilitar na hora de escrever - Iza
			switch (cmd) {
				case 1:

					quant_query("infantis lisas", matriz[0], input);
					

					encerra = 1; // Aciona variavel de encerramento DESTA REPETICAO.
					break;
				case 2:

					quant_query("infantis estampadas", matriz[1], input);
					

					encerra = 1; // Aciona variavel de encerramento DESTA REPETICAO.
					break;
				case 3:

					quant_query("adultas lisas", matriz[2], input);
					

					encerra = 1; // Aciona variavel de encerramento DESTA REPETICAO.
					break;
				case 4:

					quant_query("adultas estampadas", matriz[3], input);
					

					encerra = 1; // Aciona variavel de encerramento DESTA REPETICAO.
					break;
				default:

					System.out.println("Item inválido.");
					break;
			}
			if (encerra == 1) {
				encerra = 0; // Evita que o programa encerre precocemente.
				break;
			}
		}
		return encerra;
	}

	public static void quant_query(String mascara, Produto prod, Scanner input) throws IOException {
		System.out.println("Quantas máscaras " + mascara + " foram vendidas?");
		FileWriter fileWriter = new FileWriter(new File ("src/venda.txt"), true); // Cria arquivo de venda. -  Iza
		int quant = input.nextInt();
		if (quant < 0) {
			System.out.println("Número inválido.");
		} else if (quant > prod.estoque) {
			System.out.println("Não há produtos no estoque suficientes para assumir a demanda.");
			System.out.println("(No momento temos somente " + prod.estoque + " deste produto em estoque.)");
		} else {
			prod.estoque -= quant;
			prod.quantvenda += quant;
			System.out.println(quant + " máscaras " + mascara + " vendidas com sucesso!");
			fileWriter.write(prod.nome + ";" + prod.quantvenda + ";" + prod.estoque + ";" + prod.custo + ";" + prod.custo * prod.quantvenda + "\n"); // Escreve no arquivo a venda realizada com o seu lucro. - Iza
		}
		fileWriter.close();

	}

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

	public static void cadastrarProduto(Scanner entrada, File file) throws IOException {
		FileWriter fileWriter = new FileWriter(file, true); // Cria arquivo de venda. -  Iza
		System.out.println("Essa são as opções de produtos:");
		System.out.print("1 - infantil lisa\n2 - infantil estampada\n3 - adulto lisa\n4 - adulto estampada\n");
		int tamanho = entrada.nextInt();
		String tamanhoStr = "";
		if (tamanho == 1) {
			tamanhoStr = "infantil lisa";
		} else if (tamanho == 2) {
			tamanhoStr = "infantil estampada";
		} else if (tamanho == 3) {
			tamanhoStr = "adulto lisa";
		} else if (tamanho == 4) {
			tamanhoStr = "adulto estampada";
		}
		System.out.print("Digite o valor da mascara: ");
		int custo = entrada.nextInt();
		System.out.print("Digite o quantidade da mascara em estoque: ");
		int estoque = entrada.nextInt();
		Produto produto = new Produto();
		produto.nome = tamanhoStr;
		produto.custo = custo;
		produto.estoque = estoque;

		fileWriter.write(produto.nome + " ; " + produto.custo + " ; " + produto.estoque + "\n");
		fileWriter.close();
		System.out.println("Mascara cadastrada com sucesso!");
	}

	public static void listarProdutos(Scanner entrada, File file) throws IOException {
		FileReader leitor = new FileReader(file);
		BufferedReader leitorBuffer = new BufferedReader(leitor);

		String linha = "";
		System.out.println("Essas são as opções de produtos. (nome, custo, estoque)");
		while ((linha = leitorBuffer.readLine()) != null) {
			String[] produto = linha.split(";");
			System.out.println("nome: " + produto[0] + "  " + "custo: " + produto[1] + "  " + "estoque: " + produto[2]);
		}
		leitorBuffer.close();
	}

	public static void listarVendas(Scanner entrada) throws IOException {
		FileReader leitor = new FileReader(new File ("src/venda.txt")); 
		BufferedReader leitorBuffer = new BufferedReader(leitor);

		String linha = "";
		System.out.println("Essas são as vendas realizadas. (nome, quantidade vendida, estoque, custo, lucro)");
		while ((linha = leitorBuffer.readLine()) != null) {
			System.out.println(linha.replace(";", " / "));
		}
		leitorBuffer.close();
	}

	public static Produto[] carregaProdutos (Produto[] produtos, File file) throws IOException {
		FileReader leitor = new FileReader(file);
		BufferedReader leitorBuffer = new BufferedReader(leitor);

		String linha = "";
		while ((linha = leitorBuffer.readLine()) != null) {
			String[] atributos = linha.split(" ; "); // Separa a linha em um vetor de strings por ; - Iza
			Produto produto = new Produto(); 
			produto.nome = atributos[0]; 
			produto.custo = Integer.parseInt(atributos[1]);
			produto.estoque = Integer.parseInt(atributos[2]);

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
}

class Produto {
	String nome;
	int estoque;
	int custo;
	int preçovenda;
	int quantvenda;

	public Produto() {
		estoque = 0;
		custo = 0;
		preçovenda = 0;
		quantvenda = 0;
	}
}