package ryusei; 

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Statement;

import DAOs.FuncionarioDAO;
import DAOs.MangaDAO;
import DAOs.ItemMenuDAO;     
import DAOs.PagamentoDAO;
import DAOs.UsuarioDAO;

import db_management.ConnectionFactory;

import ryusei.Item_menu;
import ryusei.Manga;
import ryusei.Pagamento;
import pessoa.Funcionario;
import pessoa.Usuario;

public class SistemaDeBusca {

    private Connection connection;
    
    private MangaDAO mangaDAO;
    private UsuarioDAO usuarioDAO;
    private FuncionarioDAO funcionarioDAO;
    private PagamentoDAO pagamentoDAO;
    private ItemMenuDAO menuDAO;

    public SistemaDeBusca() {
        // Cria a conexão com o banco
        this.connection = new ConnectionFactory().recuperarConexao();

        this.inicializarBanco();

        // Inicializa os DAOs passando a conexão
        this.mangaDAO = new MangaDAO(this.connection);
        this.usuarioDAO = new UsuarioDAO(this.connection);
        this.funcionarioDAO = new FuncionarioDAO(this.connection);
        this.pagamentoDAO = new PagamentoDAO(this.connection);
        this.menuDAO = new ItemMenuDAO(this.connection);
        
    }

    private void inicializarBanco() {
        try {
            // Tenta encontrar o arquivo na pasta 'resources'
            InputStream arquivoSql = getClass().getClassLoader().getResourceAsStream("init.sql");

            if (arquivoSql == null) {
                System.err.println("ERRO: Arquivo init.sql não foi encontrado em src/main/resources!");
                return;
            }

            // Lê todo o conteúdo do arquivo para uma String
            String sql = new String(arquivoSql.readAllBytes(), StandardCharsets.UTF_8);

            // Executa o SQL no banco
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(sql);
                System.out.println("Banco de dados inicializado com sucesso.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro crítico ao ler/executar init.sql: " + e.getMessage(), e);
        }
    }

    // MÉTODOS DE ESCRITA 
    public Usuario adicionaUsuario(String cpf, String nome, String email, String telefone, char assinatura) {
        Usuario novo = new Usuario(cpf, nome, email, telefone, assinatura);
        this.usuarioDAO.salvarUsuario(novo); // O DAO joga no Postgres
        return novo;
    }

    public Funcionario adicionaFuncionario(String cpf, String nome, String telefone, String email, double salario, String funcao) {
        Funcionario novo = new Funcionario(cpf, nome, telefone, email, salario, funcao);
        this.funcionarioDAO.salvarFuncionario(novo);
        return novo;
    }

    // Adaptado para usar a nova classe Manga (que espera o ID do banco depois)
    public Manga adicionaManga(String nome, String autores, String generos, String serie, String localizacao, int qtd_venda, int estoque, float preco) {
        // ID 0 ou -1 pois o banco vai gerar o serial
        Manga novo = new Manga(0, nome, autores, generos, serie, localizacao, qtd_venda, estoque, preco);
        this.mangaDAO.salvar(novo); 
        return novo;
    }

    public Item_menu adicionaItem(String nome, String ingredientes, float preco, int estoque, int qtdVenda) {
        Item_menu novo = new Item_menu(0, nome, ingredientes, preco, estoque, qtdVenda);
        this.menuDAO.salvarItemMenu(novo);
        return novo;
    }

    public void adicionaPagamento(Pagamento p) {
        this.pagamentoDAO.salvarPagamento(p);
    }

    // MÉTODOS DE LEITURA (READ)

    // --- Listagens Gerais (Para tabelas da GUI) ---

    public List<Usuario> getListaUsuarios() {
        return this.usuarioDAO.listaUsuarios();
    }

    public List<Funcionario> getListaFuncionarios() {
        return this.funcionarioDAO.listaTodos();
    }

    public List<Manga> getListaMangas() {
        return this.mangaDAO.listarTodos();
    }

    public List<Item_menu> getListaItemMenu() {
        return this.menuDAO.listarTodos();
    }
    
    public List<Pagamento> getListaPagamentos() {
        return this.pagamentoDAO.listarTodos();
    }


    // --- Buscas Específicas ---

    public Optional<Usuario> buscarUsuarioPorCpf(String cpf) {
        return this.usuarioDAO.buscaUsuarioPorCPF(cpf);
    }
    
    public Boolean cliente_existe(String cpf) {
        return this.usuarioDAO.buscaUsuarioPorCPF(cpf).isPresent();
    }

    public Optional<Funcionario> buscarFuncionarioPorCpf(String cpf) {
        return this.funcionarioDAO.buscaFuncionarioCPF(cpf); 
       
    }

    public Optional<Manga> buscaMangaPorNome(String nome) {
        return this.mangaDAO.buscaMangaPorNome(nome);
    }

    public Optional<Manga> buscaMangaPorID(int id) {
        return this.mangaDAO.buscaMangaPorID(id);
    }

    public Optional<Item_menu> buscaItemMenuPorNome(String nome) {
        return this.menuDAO.buscaItemPorNome(nome);
    }

    public Optional<Item_menu> buscaItemPorID (int id) {
        return this.menuDAO.buscaItemPorID(id);
    }


    // --- Busca dos pagamentos feitos por uma pessoa para a GUI

    public List<Pagamento> getListaPagamentosPorCPF(String cpf) {
        return this.buscarPagamentosPorCliente(cpf);
    }


    // Método novo específico que criamos antes
    public List<Pagamento> buscarPagamentosPorCliente(String cpf) {
        return this.pagamentoDAO.listaPagamentosFeitosPorCPF(cpf);
    }

    // MÉTODO DE REMOÇÃO (DELETE)

    public boolean removerFuncionario(String cpf) {
        this.funcionarioDAO.demitir(cpf);
        return true;
    }

    public boolean removerUsuario(String cpf) {
        this.usuarioDAO.deletarUsuario(cpf);
        return true;
    }

    // MÉTODOS VISUAIS (CONSOLE)

    public void mostraMenu() {
        List<Item_menu> itens = this.menuDAO.listarTodos();
        if (itens.isEmpty()) {
            System.out.println("Nenhum item no menu.");
            return;
        }
        System.out.println("\n--- MENU ---");
        for (Item_menu item : itens) {
            System.out.println("ID: " + item.getID_menu() + " | Nome: " + item.getNome() + " | Preço: R$" + item.getPrecoVenda());
        }
    }

    public void mostraMangas() {
        List<Manga> mangas = this.mangaDAO.listarTodos();
        if (mangas.isEmpty()) {
            System.out.println("Nenhum mangá cadastrado.");
            return;
        }
        System.out.println("\n--- LISTA DE MANGÁS ---");
        for (Manga m : mangas) {
            m.mostraManga();
            System.out.println("-------------------------");
        }
    }
    
    public void mostraUsuarios() {
        List <Usuario> usuarios = this.usuarioDAO.listaUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário registrado.");
            return;
        } 
        System.out.println("\n--- LISTA DE USUÁRIOS ---");
        for (Usuario u : usuarios) {
            u.mostraUsuario();
            System.out.println("-------------------------");
        }
    }

    public void mostraPagamentos() {
        List <Pagamento> pagamentos = this.pagamentoDAO.listarTodos();
        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
            return;
        }
        System.out.println("\n--- LISTA DE PAGAMENTOS ---");
        for (Pagamento p: pagamentos) {
            p.mostraPagamento();
            System.out.println("-------------------------");
        }   
    }

    public void mostraFuncionarios() {
        List <Funcionario> funcionarios = this.funcionarioDAO.listaTodos();
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário registrado");
            return;
        }
        System.out.println("\n--- LISTA DE FUNCIONÁRIOS ---");
        for (Funcionario f: funcionarios) {
            f.mostraFuncionario();
            System.out.println("-------------------------");
        }
    }

    public void mostraPagamentosFeitosPorPessoa(String cpf) {
        List <Pagamento> pagamentos = this.pagamentoDAO.listaPagamentosFeitosPorCPF(cpf);
        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento registrado nesse cpf");
            return;
        }
        System.out.println("\n--- LISTA DE PAGAMENTOS FEITOS PELO CPF " + cpf + "---");
        for (Pagamento p: pagamentos) {
            p.mostraPagamento();
            System.out.println("-------------------------");
        }
    }

    public void encerrarSistema() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                System.out.println("Conexão com o banco encerrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }

    // UPDATES 

    public void atualizarEstoqueManga(Manga manga) {
        this.mangaDAO.atualizarEstoque(manga);
    }

    public void atualizarqntdVendasManga(Manga manga) {
        this.mangaDAO.atualizarVendas(manga);
    }

    public void atualizarEstoqueItemMenu(Item_menu item) {
        this.menuDAO.atualizarEstoque(item);
    }

    public void atualizarqntdVendasItemeMenu(Item_menu item) {
        this.menuDAO.atualizarQtdVendas(item);
    }

    public void atualizarUsuario(Usuario u) {
        this.usuarioDAO.atualizar(u);
    }

    public void atualizarFuncionario(Funcionario f) {
        this.funcionarioDAO.atualizar(f);
    }

}