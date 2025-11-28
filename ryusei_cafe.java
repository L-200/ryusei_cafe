import funcoes_auxiliares.*;
import pessoa.*;

import java.util.Optional;
import java.util.Scanner;

// vai ser a nossa main
public class ryusei_cafe {

    private static SistemaDeBusca sistema_ryusei;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String art = """
    ______   ___   _ ____  _____ ___ 
    |  _ \\\\ \\\\ / / | | / ___|| ____|_ _|
    | |_) \\\\ V /| | | \\\\___ \\\\|  _|  | | 
    |  _ < | | | |_| |___) | |___ | | 
    |_| \\\\_\\\\|_|  \\\\___/|____/|_____|___|
   ..
      ..  ..
            ..
             ..\
            ..\
           ..\
         ..\
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
        sistema_ryusei = new SistemaDeBusca();
        System.out.println("Sistema foi inicializado.");

        // Carregar dados dos arquivos CSV
        carregarDados();

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
                    mostrarPagamentos();
                    break;
                case 6:
                    system_on = sairESalvar();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    // --- Métodos de Suporte ---

    private static void carregarDados() {
        sistema_ryusei.carregarUsuariosCSV();
        sistema_ryusei.carregarFuncionariosCSV();
        sistema_ryusei.carregarMangasCSV();
        sistema_ryusei.carregarMenuCSV();
        sistema_ryusei.carregarPagamentosCSV();
        System.out.println("Dados carregados.");
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n==================================");
        System.out.println("     MENU PRINCIPAL RYUSEI CAFE   ");
        System.out.println("==================================");
        System.out.println("1. Gerenciar Pessoas (Usuários/Funcionários)");
        System.out.println("2. Gerenciar Mangás");
        System.out.println("3. Gerenciar Menu do Café");
        System.out.println("4. Realizar Nova Compra (Carrinho)");
        System.out.println("5. Mostrar todos os Pagamentos");
        System.out.println("6. Sair e Salvar Dados");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Opção inválida
        }
    }

    private static boolean sairESalvar() {
        sistema_ryusei.salvarUsuariosCSV();
        sistema_ryusei.salvarFuncionariosCSV();
        sistema_ryusei.salvarMangasCSV();
        sistema_ryusei.salvarMenuCSV();
        sistema_ryusei.salvarPagamentosCSV();

        System.out.println("✅ Dados salvos com sucesso.");
        System.out.println("👋 Saindo do sistema...");
        sc.close();
        return false;
    }

    // --- Funcionalidades do Menu ---

    private static void menuPessoas() {
        System.out.println("\n--- Gerenciar Pessoas ---");
        System.out.println("1. Adicionar Novo Usuário");
        System.out.println("2. Buscar Usuário por CPF");
        System.out.println("3. Listar Todos os Usuários");
        System.out.println("4. Listar Todos os Funcionários");
        System.out.println("5. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcao();
        switch (opcao) {
            case 1:
                adicionarUsuario();
                break;
            case 2:
                buscarUsuario();
                break;
            case 3:
                sistema_ryusei.mostraUsuarios();
                break;
            case 4:
                sistema_ryusei.mostraFuncionarios();
                break;
            case 5:
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

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

        sistema_ryusei.adicionaUsuario(cpf, nome, telefone, email, assinatura);
        System.out.println("✅ Usuário " + nome + " adicionado com sucesso.");
    }

    private static void buscarUsuario() {
        System.out.print("Digite o CPF do usuário para buscar: ");
        String cpf = sc.nextLine();
        Optional<Usuario> usuario_opt = sistema_ryusei.buscarUsuarioPorCpf(cpf);

        if (usuario_opt.isPresent()) {
            usuario_opt.get().mostraUsuario();
        } else {
            System.out.println("Usuário com CPF " + cpf + " não encontrado.");
        }
    }

    private static void menuMangas() {
        System.out.println("\n--- Gerenciar Mangás ---");
        System.out.println("1. Listar Todos os Mangás");
        System.out.println("2. Buscar Mangá por Nome");
        System.out.println("3. Adicionar Estoque em Mangá");
        System.out.println("4. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcao();
        switch (opcao) {
            case 1:
                sistema_ryusei.mostraMangas();
                break;
            case 2:
                buscarManga();
                break;
            case 3:
                adicionarEstoqueManga();
                break;
            case 4:
                break;
            default:
                System.out.println("Opção inválida.");
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
            Manga manga_estoque_achado = manga_estoque_procurado.get();
            System.out.print("Qual a quantidade de estoque que deseja adicionar?: ");
            int qtd_estoque_manga_adicionar = lerOpcao();
            if (qtd_estoque_manga_adicionar > 0) {
                manga_estoque_achado.add_estoque(qtd_estoque_manga_adicionar);
                System.out.println("✅ Estoque adicionado com sucesso!");
                System.out.println("Estoque atual do mangá: " + manga_estoque_achado.getEstoque());
            } else {
                System.out.println("❌ Quantidade inválida.");
            }
        } else {
            System.out.println("Mangá com o nome '" + nome_manga + "' não encontrado");
        }
    }
    
    private static void menuMenuCafe() {
        System.out.println("\n--- Gerenciar Menu Café ---");
        System.out.println("1. Listar Todos os Itens do Menu");
        System.out.println("2. Adicionar Estoque em Item do Menu");
        System.out.println("3. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcao();
        switch (opcao) {
            case 1:
                sistema_ryusei.mostraMenu();
                break;
            case 2:
                adicionarEstoqueMenu();
                break;
            case 3:
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }
    
    private static void adicionarEstoqueMenu() {
        System.out.print("Nome do item do menu para adicionar estoque: ");
        String nome_item = sc.nextLine();
        Optional<Item_menu> item_estoque_procurado = sistema_ryusei.buscaItemMenuPorNome(nome_item);

        if (item_estoque_procurado.isPresent()) {
            Item_menu item_estoque_achado = item_estoque_procurado.get();
            System.out.print("Qual a quantidade de estoque que deseja adicionar?: ");
            int qtd_estoque_item_adicionar = lerOpcao();
            if (qtd_estoque_item_adicionar > 0) {
                item_estoque_achado.add_estoque(qtd_estoque_item_adicionar);
                System.out.println("✅ Estoque adicionado com sucesso!");
                System.out.println("Estoque atual do item: " + item_estoque_achado.getEstoque());
            } else {
                System.out.println("❌ Quantidade inválida.");
            }
        } else {
            System.out.println("Item do menu com o nome '" + nome_item + "' não encontrado");
        }
    }


    private static void realizarCompra() {
        Carrinho_de_compras carrinho = new Carrinho_de_compras();
        boolean comprando = true;

        System.out.println("\n🛒 --- INICIANDO NOVA COMPRA ---");
        
        System.out.print("Digite o CPF do usuário para esta compra: ");
        String cpf_usuario = sc.nextLine();
        Optional<Usuario> usuario_opt = sistema_ryusei.buscarUsuarioPorCpf(cpf_usuario);
        
        if (!usuario_opt.isPresent()) {
            System.out.println("❌ Usuário não encontrado. Cancelando compra.");
            return;
        }
        Usuario usuario = usuario_opt.get();
        System.out.println("Usuário: " + usuario.getNome() + " (Assinatura: " + usuario.getAssinatura() + ")");


        while (comprando) {
            System.out.println("\n--- Adicionar Item ---");
            System.out.println("1. Adicionar Mangá");
            System.out.println("2. Adicionar Item do Menu");
            System.out.println("3. Finalizar Compra");
            System.out.print("Opção: ");

            int opcao = lerOpcao();
            String nome_item;
            Optional<? extends Vendivel> item_opt = Optional.empty();

            switch (opcao) {
                case 1:
                    System.out.print("Nome do Mangá: ");
                    nome_item = sc.nextLine();
                    item_opt = sistema_ryusei.buscaMangaPorNome(nome_item);
                    break;
                case 2:
                    System.out.print("Nome do Item do Menu: ");
                    nome_item = sc.nextLine();
                    item_opt = sistema_ryusei.buscaItemMenuPorNome(nome_item);
                    break;
                case 3:
                    comprando = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
                    continue;
            }

            if (item_opt.isPresent()) {
                Vendivel item = item_opt.get();
                carrinho.adicionaItem_carrinho(item);
            } else if (opcao != 3) {
                System.out.println("Item não encontrado.");
            }
        }

        if (carrinho.itensNoCarrinho().isEmpty()) {
            System.out.println("Carrinho vazio. Compra cancelada.");
            return;
        }

        // Finalização
        float total = carrinho.calcula_total();
        System.out.println("💰 Valor Total: R$" + String.format("%.2f", total));
        
        // Simular Pagamento
        System.out.print("Tipo (Compra/Serviço): ");
        String tipo = sc.nextLine();
        System.out.print("Método de Pagamento (Cartão/Pix/Dinheiro): ");
        String metodo = sc.nextLine();
        
        // Usar data atual (simplificado como 'hoje')
        String data = java.time.LocalDate.now().toString(); 
        
        // Cria o pagamento inicialmente como "pendente"
        Pagamento novo_pagamento = new Pagamento(usuario.getCpf(), total, tipo, metodo, data, "pendente");

        System.out.print("Confirmar pagamento (S/N)? ");
        String confirmacao = sc.nextLine().toUpperCase();
        
        if (confirmacao.equals("S")) {
            novo_pagamento.Pago(); // Altera o status para 'pago'
            sistema_ryusei.adicionaPagamento(novo_pagamento);
            System.out.println("✅ Pagamento concluído e registrado no sistema!");
            novo_pagamento.mostraPagamento();
        } else {
            System.out.println("❌ Pagamento cancelado. Itens do carrinho descartados.");
            // NOTA: O estoque já foi atualizado (decrementado) na adição.
            // Em um sistema real, o estoque precisaria ser revertido aqui.
        }
    }
    
    private static void mostrarPagamentos() {
        System.out.println("\n--- Histórico de Pagamentos ---");
        sistema_ryusei.mostraPagamentos();
    }
}