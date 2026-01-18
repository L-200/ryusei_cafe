import ryusei.*;
import pessoa.*;
import java.util.Optional;
import java.util.Scanner;

public class ryusei_cafe {

    private static SistemaDeBusca sistema_ryusei;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String art = """
 ===RYUSEI CAFE===
  #############
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
        
        try {
            sistema_ryusei = new SistemaDeBusca();
            System.out.println("Conexão com Banco de Dados estabelecida.");
        } catch (Exception e) {
            System.err.println("Erro crítico ao conectar no banco: " + e.getMessage());
            return; // Encerra o programa se não tiver banco
        }

        boolean system_on = true;
        while (system_on) {
            exibirMenuPrincipal();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    menuPessoas();
                    break;
                case 2:
                    menuMangas();
                    break;
                case 3:
                    menuMenuCafe();
                    break;
                case 4:
                    realizarCompra();
                    break;
                case 5:
                    menuPagamentos();
                    break;
                case 6:
                    system_on = encerrarSistema();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    // --- Métodos de Suporte ---

    private static void exibirMenuPrincipal() {
        System.out.println("\n==================================");
        System.out.println("     MENU PRINCIPAL RYUSEI CAFE   ");
        System.out.println("==================================");
        System.out.println("1. Gerenciar Pessoas (Usuários/Funcionários)");
        System.out.println("2. Gerenciar Mangás");
        System.out.println("3. Gerenciar Menu do Café");
        System.out.println("4. Realizar Nova Compra (Carrinho)");
        System.out.println("5. Gerenciar Pagamentos");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Opção inválida
        }
    }

    private static boolean encerrarSistema() {
        
        System.out.println("Encerrando conexão com o banco...");
        
        sistema_ryusei.encerrarSistema(); 

        System.out.println("Obrigado por usar o Ryusei Cafe!");
        sc.close();
        return false;
    }

    // --- Métodos de Automação ---

    private static void adicionarUsuario() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Assinatura (A, B, C): ");
        char assinatura = sc.nextLine().toUpperCase().charAt(0);

        // O sistema agora salva direto no banco dentro desse método
        sistema_ryusei.adicionaUsuario(cpf, nome, email, telefone, assinatura);
        System.out.println("Usuário " + nome + " salvo no banco de dados.");
    }

    private static void adicionaFuncionário() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Salário: ");
        double salario = Double.parseDouble(sc.nextLine());
        System.out.print("Cargo: ");
        String cargo = sc.nextLine();

        sistema_ryusei.adicionaFuncionario(cpf, nome, telefone, email, salario, cargo);
        System.out.println("Funcionário " + nome + " salvo no banco de dados.");
    }

    private static void buscarUsuario() {
        System.out.print("Digite o CPF do usuário para buscar: ");
        String cpf = sc.nextLine();
        // Agora busca no banco
        Optional<Usuario> usuario_opt = sistema_ryusei.buscarUsuarioPorCpf(cpf);

        if (usuario_opt.isPresent()) {
            usuario_opt.get().mostraUsuario();
        } else {
            System.out.println("Usuário com CPF " + cpf + " não encontrado no banco.");
        }
    }

    private static void buscarManga() {
        System.out.print("Digite o Nome do mangá para buscar: ");
        String nome = sc.nextLine();
        Optional<Manga> manga_opt = sistema_ryusei.buscaMangaPorNome(nome);

        if (manga_opt.isPresent()) {
            manga_opt.get().mostraManga();
        } else {
            System.out.println("Mangá com nome '" + nome + "' não encontrado.");
        }
    }

    private static void adicionarEstoqueManga() {
        System.out.print("Nome do mangá para adicionar estoque: ");
        String nome_manga = sc.nextLine();
        Optional<Manga> manga_estoque_procurado = sistema_ryusei.buscaMangaPorNome(nome_manga);

        if (manga_estoque_procurado.isPresent()) {
            Manga manga = manga_estoque_procurado.get();
            System.out.print("Qual a quantidade de estoque que deseja adicionar?: ");
            int qtd = lerOpcao();
            if (qtd > 0) {
                
                manga.add_estoque(qtd);     
                sistema_ryusei.atualizarEstoqueManga(manga);
                System.out.println("Estoque atualizado: " + manga.getEstoque());
                
            } else {
                System.out.println("Quantidade inválida.");
            }
        } else {
            System.out.println("Mangá não encontrado");
        }
    }

    private static void adicionarItemMenu() {
        System.out.print("Nome do item: ");
        String nome = sc.nextLine();
        
        // Verifica se já existe antes de tentar inserir
        if (sistema_ryusei.buscaItemMenuPorNome(nome).isPresent()) {
            System.out.println("Item já existe!");
            return;
        }

        System.out.print("Ingredientes: ");
        String ingredientes = sc.nextLine();
        System.out.print("Preço: ");
        float preco = Float.parseFloat(sc.nextLine());
        System.out.print("Estoque inicial: ");
        int estoque = Integer.parseInt(sc.nextLine());
        System.out.print("Qtd Vendas inicial: ");
        int qtdVenda = Integer.parseInt(sc.nextLine());

        sistema_ryusei.adicionaItem(nome, ingredientes, preco, estoque, qtdVenda);
        System.out.println("Item salvo no banco com sucesso.");
    }

    private static void adicionarEstoqueMenu() {
        System.out.print("Nome do item do menu para adicionar estoque: ");
        String nome_item = sc.nextLine();
        Optional<Item_menu> item_estoque_procurado = sistema_ryusei.buscaItemMenuPorNome(nome_item);

        if (item_estoque_procurado.isPresent()) {
            Item_menu item = item_estoque_procurado.get();
            System.out.print("Qual a quantidade de estoque que deseja adicionar?: ");
            int qtd = lerOpcao();
            if (qtd > 0) {
                
                item.add_estoque(qtd);                
                sistema_ryusei.atualizarEstoqueItemMenu(item);
                System.out.println("Estoque atualizado: " + item.getEstoque());
            } else {
                System.out.println("Quantidade inválida.");
            }
        } else {
            System.out.println("Item do menu não encontrado");
        }
    }

    // --- Menus ---

    private static void menuPessoas() {
        System.out.println("\n--- Gerenciar Pessoas ---");
        System.out.println("1. Adicionar Novo Usuário");
        System.out.println("2. Adicionar novo Funcionário");
        System.out.println("3. Buscar Usuário por CPF");
        System.out.println("4. Listar Todos os Usuários");
        System.out.println("5. Listar Todos os Funcionários");
        System.out.println("6. Voltar");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcao();
        switch (opcao) {
            case 1: adicionarUsuario(); break;
            case 2: adicionaFuncionário(); break;
            case 3: buscarUsuario(); break;
            case 4: sistema_ryusei.mostraUsuarios(); break;
            case 5: sistema_ryusei.mostraFuncionarios(); break;
            case 6: break;
            default: System.out.println("Opção inválida.");
        }
    }

    private static void menuMangas() {
        System.out.println("\n--- Gerenciar Mangás ---");
        System.out.println("1. Listar Todos os Mangás");
        System.out.println("2. Buscar Mangá por Nome");
        System.out.println("3. Adicionar Estoque em Mangá");
        System.out.println("4. Voltar");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcao();
        switch (opcao) {
            case 1: sistema_ryusei.mostraMangas(); break;
            case 2: buscarManga(); break;
            case 3: adicionarEstoqueManga(); break;
            case 4: break;
            default: System.out.println("Opção inválida.");
        }
    }
    
    private static void menuMenuCafe() {
        System.out.println("\n--- Gerenciar Menu Café ---");
        System.out.println("1. Adicionar Novo Item");
        System.out.println("2. Listar Menu");
        System.out.println("3. Adicionar Estoque");
        System.out.println("4. Voltar");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcao();
        switch (opcao) {
            case 1: adicionarItemMenu(); break;
            case 2: sistema_ryusei.mostraMenu(); break; // Atenção: Implemente mostraMenu no SistemaDeBusca
            case 3: adicionarEstoqueMenu(); break;
            case 4: break;
            default: System.out.println("Opção inválida.");
        }
    }

    private static void realizarCompra() {
        // OBS: Carrinho_de_compras precisará ser adaptado para lidar com IDs ou objetos do banco.
        // Assumindo que Carrinho_de_compras funciona com objetos 'Vendivel' em memória.
        
        Carrinho_de_compras carrinho = new Carrinho_de_compras();
        boolean comprando = true;

        System.out.println("\n--- INICIANDO NOVA COMPRA ---");
        System.out.print("Digite o CPF do usuário: ");
        String cpf_usuario = sc.nextLine();
        Optional<Usuario> usuario_opt = sistema_ryusei.buscarUsuarioPorCpf(cpf_usuario);
        
        if (!usuario_opt.isPresent()) {
            System.out.println("Usuário não encontrado no banco. Cadastre-o primeiro.");
            return;
        }
        Usuario usuario = usuario_opt.get();
        System.out.println("Usuário identificado: " + usuario.getNome());

        while (comprando) {
            System.out.println("\n1. Adicionar Mangá | 2. Item Menu | 3. Finalizar");
            int opcao = lerOpcao();
            
            if (opcao == 3) {
                comprando = false;
                break;
            }

            System.out.print("Digite o nome do item: ");
            String nome = sc.nextLine();
            
            Optional<? extends Vendivel> item_opt = Optional.empty();

            if (opcao == 1) item_opt = sistema_ryusei.buscaMangaPorNome(nome);
            if (opcao == 2) item_opt = sistema_ryusei.buscaItemMenuPorNome(nome);

            if (item_opt.isPresent()) {
                carrinho.adicionaItem_carrinho(item_opt.get());
                System.out.println("Item adicionado!");
            } else {
                System.out.println("Não encontrado.");
            }
        }

        if (carrinho.itensNoCarrinho().isEmpty()) return;

        float total = carrinho.calcula_total();
        System.out.println("Total: R$" + String.format("%.2f", total));
        
        System.out.print("Método (Dinheiro/Pix/Cartão): ");
        String metodo = sc.nextLine();
        
        // Data atual
        String data = java.time.LocalDate.now().toString(); 
        
        // ID 0 pois o banco gera
        Pagamento novo_pagamento = new Pagamento(0, usuario.getCpf(), total, metodo, data);

        System.out.print("Confirmar (S/N)? ");
        if (sc.nextLine().toUpperCase().equals("S")) {
            
            // Salva o pagamento no Banco
            sistema_ryusei.adicionaPagamento(novo_pagamento);
            System.out.println("Pagamento registrado no banco!");
            
            carrinho.AtualizaEstoquePosVenda(sistema_ryusei);
        } else {
            System.out.println("Cancelado.");
        }
    }
    
    private static void menuPagamentos() {
        System.out.println("\n--- Pagamentos ---");
        System.out.println("1. Ver todos");
        System.out.println("2. Ver por CPF");
        System.out.println("3. Voltar");
        
        int opcao = lerOpcao();
        switch (opcao) {
            case 1: sistema_ryusei.mostraPagamentos(); break;
            case 2: 
                System.out.print("CPF: ");
                sistema_ryusei.mostraPagamentosFeitosPorPessoa(sc.nextLine());
                break;
            case 3: break;
        }
    }
}