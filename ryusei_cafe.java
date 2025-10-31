import funcoes_auxiliares.*;

import java.util.Optional;
import java.util.Scanner;

//vai ser a nossa main
public class ryusei_cafe {
    public static void main (String[] args) {
        String art = """
    ______   ___   _ ____  _____ ___ 
    |  _ \\ \\ / / | | / ___|| ____|_ _|
    | |_) \\ V /| | | \\___ \\|  _|  | | 
    |  _ < | | | |_| |___) | |___ | | 
    |_| \\_\\|_|  \\___/|____/|_____|___|
   ..
      ..  ..
            ..
             ..
            ..
           ..
         ..
##       ..    ####
##.............##  ##
##.............##   ##
##.............## ##
##.............###
 ##...........##
  #############
  #############
#################
        
Bem-vindo ao sistema do ryusei cafe!

""";
        
        System.out.print(art);
        SistemaDeBusca sistema_ryusei = new SistemaDeBusca();
        System.out.println("Sistema foi inicializado.");
        boolean system_on = true;
        Scanner sc = new Scanner(System.in);
        while (system_on) {
            System.out.println("O que deseja fazer?");
            System.out.println("1 - Adicionar novo cliente");
            System.out.println("2 - Adicionar novo funcionário");
            System.out.println("3 - Adicionar novo manga");
            System.out.println("4 - Adicionar novo pagamento");
            System.out.println("5 - Adicionar novo item do menu");
            System.out.println("6 - Pesquisar cliente");
            System.out.println("7 - Pesquisar funcionário");
            System.out.println("8 - Pesquisar manga");
            System.out.println("9 - Pesquisar pagamento");
            System.out.println("10 - Realizar compras");
            System.out.println("11 - Sair do sistema");
            int escolha = sc.nextInt();
            sc.nextLine();
            System.out.println("");
            switch (escolha) {

                case 1:
                    System.out.println("Adicionar novo cliente");
                    System.out.println("Qual o CPF do cliente?");
                    String cpf = sc.nextLine();

                    System.out.println("Qual o nome do cliente?");
                    String nome = sc.nextLine();   

                    System.out.println("Qual o telefone do cliente?");
                    String telefone = sc.nextLine();

                    System.out.println("Qual o email do cliente?");
                    String email = sc.nextLine();

                    Usuario Ususario_novo = sistema_ryusei.adicionaUsuario(cpf, nome, email, telefone, 'A');
                    System.out.println("Cliente " + nome + " adicionado com sucesso!");
                    System.out.println("");
                    System.out.println("Dados do cliente:");
                    Ususario_novo.mostraUsuario();
                    System.out.println("");
                    break;

                    case 2:
                    System.out.println("Adicionar novo funcionário");
                    System.out.println("Qual o CPF do funcionário?");
                    String cpf_func = sc.nextLine();
                    System.out.println("Qual o nome do funcionário?");
                    String nome_func = sc.nextLine();
                    System.out.println("Qual o telefone do funcionário?");
                    String telefone_func = sc.nextLine();
                    System.out.println("Qual o email do funcionário?");
                    String email_func = sc.nextLine();
                    System.out.println("Qual o salário do funcionário?");
                    double salario_func = sc.nextDouble();
                    sc.nextLine();
                    System.out.println("Qual a função do funcionário? (Ex: Vendedor, Gerente, Atendente)");
                    String funcao_func = sc.nextLine();
                    Funcionario funcionario_novo = sistema_ryusei.adicionaFuncionario(cpf_func, nome_func, telefone_func, email_func, salario_func, funcao_func);
                    System.out.println("Funcionário " + nome_func + " adicionado com sucesso!");
                    System.out.println("");
                    System.out.println("Dados do funcionário:");
                    funcionario_novo.mostraFuncionario();
                    System.out.println("");
                    break;

                    case 3:
                    System.out.println("Adicionar novo manga");
                    System.out.println("Qual o nome do manga?");
                    String nome_manga = sc.nextLine();

                    System.out.println("Quem são os autores do manga? (separados por vírgula)");
                    String autores_input = sc.nextLine();
                    String[] autores = autores_input.split(",");

                    System.out.println("Quais os gêneros do manga? (separados por vírgula)");
                    String generos_input = sc.nextLine();
                    String[] generos = generos_input.split(",");

                    System.out.println("Qual a série do manga?");
                    String serie = sc.nextLine();

                    System.out.println("Qual o volume do manga?");
                    int volume = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Qual a localização do manga na loja?");
                    String localizacao = sc.nextLine();

                    System.out.println("Qual o estoque inicial do manga?");
                    int estoque = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Qual o preço inicial do Manga?");
                    float preco_manga = sc.nextFloat();
                    sc.nextLine();

                    Manga manga = sistema_ryusei.adicionaManga(nome_manga, autores, generos, serie, volume, localizacao, estoque, preco_manga);
                    System.out.println("Manga " + nome_manga + " adicionado com sucesso!");
                    System.out.println("");
                    System.out.println("Dados do manga:");
                    manga.mostraManga();
                    System.out.println("");
                    break;

                    case 4:
                    System.out.println("Adicionar novo pagamento");
                    System.out.println("Qual o CPF do usuário?");
                    String id_usuario = sc.nextLine();
                    if (sistema_ryusei.cliente_existe(id_usuario) != true) {
                        System.out.println("Não existe cliente com esse cpf!");
                        System.out.println("");
                        break;
                    }
                    System.out.println("Qual o valor do pagamento?");
                    float valor = sc.nextFloat();
                    sc.nextLine();

                    System.out.println("Qual o tipo do pagamento? (ex: compra, assinatura)");
                    String tipo = sc.nextLine();

                    System.out.println("Qual o método do pagamento? (ex: cartão, pix, dinheiro)");
                    String metodo = sc.nextLine();

                    System.out.println("Qual a data do pagamento? (formato: DD/MM/AAAA)");
                    String data = sc.nextLine();

                    System.out.println("Qual o status do pagamento? (ex: pendente, pago)");
                    String status = sc.nextLine();
                    
                    Pagamento pagamento = sistema_ryusei.adicionaPagamento(id_usuario, valor, tipo, metodo, data, status);
                    System.out.println("");
                    System.out.println("Dados do pagamento:");
                    pagamento.mostraPagamento();
                    System.out.println("");
                    break;

                    case 5: 
                    System.out.println("Adicionar novo item do menu");
                    System.out.println("Qual o nome do item?");
                    String nome_item = sc.nextLine();
                    System.out.println("Quais os ingredientes do item?");
                    String ingredientes_item = sc.nextLine();
                    System.out.println("Qual o preço do item?");
                    float preco_item = sc.nextFloat();
                    sc.nextLine();
                    System.out.println("Qual o estoque inicial do item?");
                    int estoque_item = sc.nextInt();
                    sc.nextLine();
                    Item_menu item_novo = sistema_ryusei.adiciona_item(nome_item, ingredientes_item, preco_item, estoque_item);
                    System.out.println("Item " + nome_item + " adicionado com sucesso!");
                    System.out.println("");
                    System.out.println("Dados do item:");
                    item_novo.mostraItem();
                    System.out.println("");
                    break;
                    
                    case 6:
                    System.out.println("Pesquisar por cliente");
                    System.out.println("Qual o CPF do cliente?");
                    String CPF = sc.nextLine();
                    Optional<Usuario> usuario_procurado = sistema_ryusei.buscarUsuarioPorCpf(CPF);
                    if (usuario_procurado.isPresent()) {
                        Usuario usuario_achado = usuario_procurado.get();
                        System.out.println("Usuário achado com sucesso!");
                        System.out.println("");
                        System.out.println("Dados do cliente");
                        usuario_achado.mostraUsuario();
                    } else {
                        System.out.println("Usuário com CPF " + CPF + "não foi encontrado");
                        System.out.println("");
                    }

                    case 7:
                    System.out.println("Pesquisar por funcionário");
                    System.out.println("Qual o CPF do funcionário?");
                    String CPF_func = sc.nextLine();
                    Optional<Funcionario> funcionario_procurado = sistema_ryusei.buscarFuncionarioPorCpf(CPF_func);
                    if (funcionario_procurado.isPresent()) {
                        Funcionario funcionario_achado = funcionario_procurado.get();
                        System.out.println("Funcionário achado com sucesso!");
                        System.out.println("");
                        System.out.println("Dados do funcionário");
                        funcionario_achado.mostraFuncionario();
                    } else {
                        System.out.println("Funcionário com CPF " + CPF_func + "não foi encontrado");
                        System.out.println("");
                    }
                    break;

                    case 8:
                    System.out.println("Buscando por Manga");
                    System.out.println("Qual o nome do manga?");
                    String nome_manga_procurado = sc.nextLine();
                    Optional<Manga> manga_procurado = sistema_ryusei.buscaMangaPorNome(nome_manga_procurado);
                    if (manga_procurado.isPresent()) {
                        Manga manga_achado = manga_procurado.get();
                        System.out.println("Manga encontrado com sucesso!");
                        System.out.println("");
                        System.out.println("Dados do Manga:");
                        manga_achado.mostraManga();
                    } else {
                        System.out.println("Manga com o nome " + nome_manga_procurado + " não encontrado");
                        System.out.println("");
                    }
                    break;

                    case 9:
                    System.out.println("Pesquisar pagamento");
                    System.out.println("Qual o ID do pagamento?");
                    String id_do_pagamento_desejado = sc.nextLine();
                    Optional <Pagamento> pagamento_procurado = sistema_ryusei.buscaPagamentoPorID(id_do_pagamento_desejado);
                    if (pagamento_procurado.isPresent()) {
                        Pagamento pagamento_achado = pagamento_procurado.get();
                        System.out.println("Pagamento encontrado com sucesso!");
                        System.out.println("");
                        System.out.println("Dados do Pagamento:");
                        pagamento_achado.mostraPagamento();
                    } else {
                        System.out.println("Pagamento de ID " + id_do_pagamento_desejado + " não encontrado!");
                        System.out.println("");
                    }
                    break;

                    case 10:
                    System.out.println("Realizar compras");
                    Boolean flag_carrinho = true;
                    Carrinho_de_compras meu_carrinho = new Carrinho_de_compras();
                    System.out.println("Carrinho iniciado com sucesso!");
                    while (flag_carrinho) {
                        System.out.println("O que deseja inserir no carrinho?");
                        System.out.println("1 - Manga");
                        System.out.println("3 - Item do menu");
                        System.out.println("4 - Já terminei de adicionar itens e quero ver o total");
                        int escolha_carrinho = sc.nextInt();
                        sc.nextLine();
                        switch (escolha_carrinho) {
                            case 1:
                                System.out.println("Qual o nome do manga que deseja adicionar?");
                                String nome_manga_carrinho = sc.nextLine();
                                Optional<Manga> manga_carrinho_procurado = sistema_ryusei.buscaMangaPorNome(nome_manga_carrinho);
                                if (manga_carrinho_procurado.isPresent()) {
                                    Manga manga_carrinho_achado = manga_carrinho_procurado.get();
                                    meu_carrinho.adicionaItem_carrinho(manga_carrinho_achado);
                                    System.out.println("Manga " + nome_manga_carrinho + " adicionado ao carrinho com sucesso!");
                                } else {
                                    System.out.println("Manga com o nome " + nome_manga_carrinho + " não encontrado");
                                }
                                break;
                            case 3:
                                System.out.println("Qual o ID do item do menu que deseja adicionar?");
                                int id_item_carrinho = sc.nextInt();
                                sc.nextLine();
                                Optional<Item_menu> item_carrinho_procurado = sistema_ryusei.buscaItemPorID(id_item_carrinho);
                                if (item_carrinho_procurado.isPresent()) {
                                    Item_menu item_carrinho_achado = item_carrinho_procurado.get();
                                    meu_carrinho.adicionaItem_carrinho(item_carrinho_achado);//MESMA FUNÇÃO DO CASE ANTERIOR, POLIMORFISMO!
                                    System.out.println("Item do menu de ID " + id_item_carrinho + " adicionado ao carrinho com sucesso!");
                                } else {
                                    System.out.println("Item do menu com o ID " + id_item_carrinho + " não encontrado");
                                }
                                break;
                            case 4:
                                System.out.println("Finalizando carrinho...");
                                flag_carrinho = false;
                                break;
                            default:
                                System.out.println("Opção inválida. Tente novamente.");
                                break;
                    }
                }
                    float total_a_ser_pago = meu_carrinho.calcula_total();
                    System.out.println("Total a ser pago: R$" + total_a_ser_pago);
                    System.out.println("");
                    break;

                    case 11:
                    System.out.println("Saindo do sistema...");
                    system_on = false;
                    sc.close();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}