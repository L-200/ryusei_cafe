import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import funcoes_auxiliares.*;
import pessoa.*;
import javax.swing.table.DefaultTableModel;
import java.util.Optional;
import java.time.LocalDate;

// Esta será a nova classe main para a interface gráfica, que herda de JFrame
public class RyuseiCafeGUI extends JFrame {

    private SistemaDeBusca sistema;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Constantes para os nomes dos "cards" no CardLayout
    private static final String VENDAS_CARD = "Vendas";
    private static final String ESTOQUE_CARD = "Estoque";
    private static final String PESSOAS_CARD = "Pessoas";
    private static final String PAGAMENTOS_CARD = "Pagamentos";

    // --- Variáveis de Estado da Venda ---
    private Carrinho_de_compras carrinhoAtual;
    private Usuario usuarioAtual;

    // Componentes da UI
    private JTextField cpfField;
    private JLabel usuarioInfoLabel;
    private JTextField itemSearchField;
    private JButton addItemButton;
    private JTable cartTable;
    private DefaultTableModel cartModel;
    private JLabel totalLabel;
    private JButton finalizarButton;

    public RyuseiCafeGUI() {
        super("☕ Ryusei Cafe - Sistema de Gerenciamento");
        
        // 1. Inicializa Lógica de Negócios
        this.sistema = new SistemaDeBusca();
        carregarDados();
        carrinhoAtual = new Carrinho_de_compras();

        // 2. Configuração Básica do Frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        
        // 3. Inicializa Painel Principal com CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 4. Cria e Adiciona os Componentes
        add(createNavBar(), BorderLayout.WEST); // Barra de navegação à esquerda
        add(mainPanel, BorderLayout.CENTER);    // Conteúdo principal no centro

        // 5. Adiciona os painéis funcionais (telas)
        mainPanel.add(createVendasPanel(), VENDAS_CARD);
        mainPanel.add(createEstoquePanel(), ESTOQUE_CARD);
        mainPanel.add(createPessoasPanel(), PESSOAS_CARD);
        mainPanel.add(createPagamentosPanel(), PAGAMENTOS_CARD);
        
        // 6. Configurações Finais
        cardLayout.show(mainPanel, VENDAS_CARD); // Inicia na tela de Vendas
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // --- Lógica de Dados e I/O ---
    private void carregarDados() {
        System.out.println("Carregando dados dos CSVs...");
        sistema.carregarUsuariosCSV();
        sistema.carregarFuncionariosCSV();
        sistema.carregarMangasCSV();
        sistema.carregarMenuCSV();
        sistema.carregarPagamentosCSV();
        
        // Adicionando dados de exemplo se os CSVs estiverem vazios
        if (sistema.buscarUsuarioPorCpf("111").isEmpty()) {
            sistema.adicionaUsuario("111", "João Silva", "joao@email.com", "9999-0000", 'A');
            sistema.adicionaManga("One Piece Vol 1", new String[]{"Oda"}, new String[]{"Shonen"}, "One Piece", 1, "A1", 10, 29.90f);
            sistema.adicionaItem("Cappuccino", "Leite, café, cacau", 8.50f, 50);
        }
        System.out.println("Dados carregados. Usuário de teste: 111.");
    }

    private void salvarESair() {
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja salvar os dados e sair?", "Confirmação", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Chama os métodos de salvamento do SistemaDeBusca
            sistema.salvarUsuariosCSV();
            sistema.salvarFuncionariosCSV();
            sistema.salvarMangasCSV();
            sistema.salvarMenuCSV();
            sistema.salvarPagamentosCSV();
            
            JOptionPane.showMessageDialog(this, "Dados salvos com sucesso. Saindo do sistema.", "Salvamento", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
    
    // --- Componentes da GUI: Barra de Navegação ---
    private JPanel createNavBar() {
        JPanel navBar = new JPanel();
        navBar.setLayout(new BoxLayout(navBar, BoxLayout.Y_AXIS));
        navBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        navBar.setBackground(new Color(50, 50, 50)); 

        JButton vendasBtn = createNavButton("Vendas", VENDAS_CARD);
        JButton estoqueBtn = createNavButton("Estoque", ESTOQUE_CARD);
        JButton pessoasBtn = createNavButton("Pessoas", PESSOAS_CARD);
        JButton pagamentosBtn = createNavButton("Pagamentos", PAGAMENTOS_CARD);
        JButton sairBtn = createNavButton("Salvar & Sair", null);

        navBar.add(vendasBtn);
        navBar.add(Box.createRigidArea(new Dimension(0, 10)));
        navBar.add(estoqueBtn);
        navBar.add(Box.createRigidArea(new Dimension(0, 10)));
        navBar.add(pessoasBtn);
        navBar.add(Box.createRigidArea(new Dimension(0, 10)));
        navBar.add(pagamentosBtn);
        navBar.add(Box.createVerticalGlue());
        navBar.add(sairBtn);

        sairBtn.addActionListener(e -> salvarESair());
        
        return navBar;
    }
    
    private JButton createNavButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(new Color(80, 80, 80));
        button.setForeground(Color.WHITE);
        
        if (cardName != null) {
            button.addActionListener(e -> cardLayout.show(mainPanel, cardName));
        }
        return button;
    }
    
    // --- Componentes da GUI: Vendas (Implementação Completa) ---
    private JPanel createVendasPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Módulo de Vendas", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        // Painel Central: Busca e Carrinho
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        centerPanel.add(createBuscaPanel());
        centerPanel.add(createCarrinhoPanel());

        panel.add(centerPanel, BorderLayout.CENTER);

        // Painel Inferior: Finalização
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Total: R$ 0.00");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        footerPanel.add(totalLabel);
        
        finalizarButton = new JButton("Finalizar Compra");
        finalizarButton.addActionListener(this::finalizarCompra);
        finalizarButton.setEnabled(false);
        footerPanel.add(finalizarButton);
        
        panel.add(footerPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBuscaPanel() {
        JPanel buscaPanel = new JPanel(new BorderLayout(5, 15));
        
        // 1. Painel de Cliente
        JPanel clienteContainer = new JPanel(new GridLayout(3, 1));
        
        JPanel cpfInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cpfField = new JTextField(10);
        JButton searchCpfButton = new JButton("Buscar CPF");
        searchCpfButton.addActionListener(this::buscarUsuario);
        
        cpfInputPanel.add(new JLabel("CPF do Cliente:"));
        cpfInputPanel.add(cpfField);
        cpfInputPanel.add(searchCpfButton);
        
        usuarioInfoLabel = new JLabel("Cliente: Não identificado");
        usuarioInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        clienteContainer.add(cpfInputPanel);
        clienteContainer.add(usuarioInfoLabel);
        
        // 2. Painel de Busca de Item
        JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        itemSearchField = new JTextField(15);
        addItemButton = new JButton("Adicionar Item");
        addItemButton.addActionListener(this::adicionarItemAoCarrinho);
        addItemButton.setEnabled(false); // Inicia desativado até o cliente ser buscado

        itemPanel.add(new JLabel("Nome do Item (Mangá/Menu):"));
        itemPanel.add(itemSearchField);
        itemPanel.add(addItemButton);
        
        buscaPanel.add(clienteContainer, BorderLayout.NORTH);
        buscaPanel.add(itemPanel, BorderLayout.CENTER);
        
        return buscaPanel;
    }

    private JPanel createCarrinhoPanel() {
        JPanel carrinhoPanel = new JPanel(new BorderLayout());
        carrinhoPanel.setBorder(BorderFactory.createTitledBorder("Itens no Carrinho"));

        // Configuração da Tabela
        String[] columnNames = {"Nome", "Preço", "Estoque Atual"};
        cartModel = new DefaultTableModel(columnNames, 0);
        cartTable = new JTable(cartModel);
        
        carrinhoPanel.add(new JScrollPane(cartTable), BorderLayout.CENTER);
        
        return carrinhoPanel;
    }

    // Métodos de Ação (Ações da GUI ligadas à lógica de negócios)
    
    private void buscarUsuario(ActionEvent e) {
        String cpf = cpfField.getText().trim();
        Optional<Usuario> userOpt = sistema.buscarUsuarioPorCpf(cpf);
        
        if (userOpt.isPresent()) {
            usuarioAtual = userOpt.get();
            usuarioInfoLabel.setText("Cliente: " + usuarioAtual.getNome() + " (Assinatura: " + usuarioAtual.getAssinatura() + ")");
            addItemButton.setEnabled(true);
            finalizarButton.setEnabled(true);
        } else {
            usuarioAtual = null;
            usuarioInfoLabel.setText("Cliente: Usuário não encontrado");
            addItemButton.setEnabled(false);
            finalizarButton.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Usuário com CPF " + cpf + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void adicionarItemAoCarrinho(ActionEvent e) {
        if (usuarioAtual == null) {
            JOptionPane.showMessageDialog(this, "Primeiro, busque um usuário para a compra.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String itemName = itemSearchField.getText().trim();
        if (itemName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome de um item.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Busca Manga ou Item_menu (usando polimorfismo Vendivel)
        Optional<? extends Vendivel> mangaOpt = sistema.buscaMangaPorNome(itemName);
        Optional<? extends Vendivel> itemMenuOpt = sistema.buscaItemMenuPorNome(itemName);
        
        Vendivel item = null;
        if (mangaOpt.isPresent()) {
            item = mangaOpt.get();
        } else if (itemMenuOpt.isPresent()) {
            item = itemMenuOpt.get();
        }

        if (item != null) {
            // CORREÇÃO: Verificar estoque "virtual"
            // Como o estoque só baixa no final, precisamos contar quantos desse item JÁ estão no carrinho
            long qtdNoCarrinho = carrinhoAtual.itensNoCarrinho().stream()
                .filter(i -> i.getNome().equals(itemName))
                .count();

            // Só adiciona se o estoque real for maior que o que já "prometemos" no carrinho
            if (item.getEstoque() > qtdNoCarrinho) {
                carrinhoAtual.adicionaItem_carrinho(item); 
                updateCarrinhoView();
                itemSearchField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Estoque insuficiente! Você já adicionou todo o estoque disponível ao carrinho.", "Estoque Baixo", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Item '" + itemName + "' não encontrado.", "Erro de Busca", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateCarrinhoView() {
        // Limpa a tabela
        cartModel.setRowCount(0);
        float total = 0.0f;
        
        // Repopula a tabela
        for (Vendivel item : carrinhoAtual.itensNoCarrinho()) {
            // Nota: item.getEstoque() mostrará o estoque cheio até que a venda seja finalizada
            cartModel.addRow(new Object[]{item.getNome(), String.format("R$ %.2f", item.getPrecoVenda()), item.getEstoque()});
            total += item.getPrecoVenda();
        }
        
        // Atualiza o total
        totalLabel.setText(String.format("Total: R$ %.2f", total));
    }
    
    private void finalizarCompra(ActionEvent e) {
        if (carrinhoAtual.itensNoCarrinho().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O carrinho está vazio.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        float total = carrinhoAtual.calcula_total(); 
        
        // Coleta dados de pagamento via pop-up
        String tipo = JOptionPane.showInputDialog(this, "Tipo (Compra/Serviço):", "Tipo de Transação", JOptionPane.QUESTION_MESSAGE);
        String metodo = JOptionPane.showInputDialog(this, "Método de Pagamento (Cartão/Pix/Dinheiro):", "Método", JOptionPane.QUESTION_MESSAGE);

        if (tipo == null || metodo == null) {
            JOptionPane.showMessageDialog(this, "Informações de pagamento incompletas. Compra cancelada.", "Cancelado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String data = LocalDate.now().toString(); 
        
        // Cria o Pagamento (status inicial 'pendente' será alterado se for confirmado)
        Pagamento novoPagamento = new Pagamento(usuarioAtual.getCpf(), total, tipo, metodo, data, "pendente");
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            String.format("Confirmar pagamento de R$ %.2f?", total), 
            "Confirmação de Pagamento", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            novoPagamento.Pago(); 
            sistema.adicionaPagamento(novoPagamento);
            
            // O estoque é atualizado APENAS AQUI, após a confirmação
            carrinhoAtual.AtualizaEstoquePosVenda();
            
            JOptionPane.showMessageDialog(this, "✅ Pagamento concluído e registrado! Estoque atualizado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            // Reiniciar o fluxo de venda
            carrinhoAtual = new Carrinho_de_compras(); 
            usuarioAtual = null; 
            cpfField.setText("");
            usuarioInfoLabel.setText("Cliente: Não identificado");
            addItemButton.setEnabled(false);
            finalizarButton.setEnabled(false);
            updateCarrinhoView();
        } else {
            // Cancelamento: Não fazemos nada com o estoque, pois ele não foi alterado ainda
            JOptionPane.showMessageDialog(this, "Pagamento cancelado. O carrinho permanece aberto.", "Cancelado", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Componentes da GUI: Outros Painéis (Simplificados) 
    
    private JPanel createEstoquePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel title = new JLabel("Módulo de Estoque (Simplificado)", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);
        
        JTextArea textArea = new JTextArea("Esta seção gerenciaria Mangás e Itens do Menu.\n\nAções acionariam: sistema.mostraMangas(), sistema.buscaMangaPorNome(), manga.add_estoque().", 15, 40);
        textArea.setEditable(false);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        JButton refreshBtn = new JButton("Listar Mangás no Console (Teste)");
        refreshBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(panel, "Listando dados no console. Verifique a saída da IDE.", "Info", JOptionPane.INFORMATION_MESSAGE);
            sistema.mostraMangas();
        });
        
        panel.add(refreshBtn, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createPessoasPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel title = new JLabel("Módulo de Pessoas (Simplificado)", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);
        
        JTextArea textArea = new JTextArea("Esta seção gerenciaria Usuários e Funcionários.\n\nAções acionariam: sistema.adicionaUsuario(), sistema.mostraUsuarios(), sistema.buscarUsuarioPorCpf().", 15, 40);
        textArea.setEditable(false);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        JButton refreshBtn = new JButton("Listar Usuários no Console (Teste)");
        refreshBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(panel, "Listando usuários no console. Verifique a saída da IDE.", "Info", JOptionPane.INFORMATION_MESSAGE);
            sistema.mostraUsuarios();
        });
        
        panel.add(refreshBtn, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createPagamentosPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel title = new JLabel("Módulo de Pagamentos (Simplificado)", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);
        
        JTextArea textArea = new JTextArea("Esta seção exibira o Histórico de Pagamentos registrados.\n\nAção acionaria: sistema.mostraPagamentos().", 15, 40);
        textArea.setEditable(false);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        JButton refreshBtn = new JButton("Listar Pagamentos no Console (Teste)");
        refreshBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(panel, "Listando pagamentos no console. Verifique a saída da IDE.", "Info", JOptionPane.INFORMATION_MESSAGE);
            sistema.mostraPagamentos();
        });
        
        panel.add(refreshBtn, BorderLayout.SOUTH);
        return panel;
    }

    // Main Method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RyuseiCafeGUI());
    }
}