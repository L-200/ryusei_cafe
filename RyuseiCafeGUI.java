import funcoes_auxiliares.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.Optional;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import pessoa.*;


// Esta será a nova classe main para a interface gráfica, que herda de JFrame
public class RyuseiCafeGUI extends JFrame {

    private SistemaDeBusca sistema;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Variáveis para a Tela de Estoque
    private JTable tabelaEstoque;
    private DefaultTableModel modeloEstoque;
    private JComboBox<String> cmbTipoItem;
    private JTextField txtNomeEstoque, txtEstoqueAtual, txtNovoEstoque;
    private JButton btnAtualizarEstoque;
    // Variáveis para Criação de Item e atualizacao de estoque
    private JTextField txtNomeCriacao, txtEstoqueInicialCriacao;
    private JTextField txtPreco, txtIngredientesOuAutores, txtOutrosDetalhes;
    private JButton btnCriarNovoItem;


    // Variáveis para a Tela de Pessoas
    private JTextField txtNome, txtCpf, txtEmail, txtTelefone;
    private JComboBox<String> cmbAssinatura;
    private JTable tabelaUsuarios;
    private DefaultTableModel modeloTabelaUsuarios;

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
    private DefaultTableModel pagamentosModel;
    private JLabel totalLabel;
    private JButton finalizarButton;

    public RyuseiCafeGUI() {
        super("☕ Ryusei Cafe - Sistema de Gerenciamento");
        
        // Inicializa Lógica de Negócios
        this.sistema = new SistemaDeBusca();
        carregarDados();
        carrinhoAtual = new Carrinho_de_compras();

        // Configuração Básica do Frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        
        // Inicializa Painel Principal com CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Cria e Adiciona os Componentes
        add(createNavBar(), BorderLayout.WEST); // Barra de navegação à esquerda
        add(mainPanel, BorderLayout.CENTER);    // Conteúdo principal no centro

        // Adiciona os painéis funcionais (telas)
        mainPanel.add(createVendasPanel(), VENDAS_CARD);
        mainPanel.add(createEstoquePanel(), ESTOQUE_CARD);
        mainPanel.add(createPessoasPanel(), PESSOAS_CARD);
        mainPanel.add(createPagamentosPanel(), PAGAMENTOS_CARD);
        
        //Configurações Finais
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
            sistema.adicionaItem("Cappuccino", "Leite, café, cacau", 8.50f, 50, 0);
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
    
    // Vendas
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
        
        // Painel de Cliente
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
        
        // Painel de Busca de Item
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

    Object[] opcoesTipo = {"Compra", "Serviço"};
    String tipo = (String) JOptionPane.showInputDialog(
            this, 
            "Selecione o Tipo:", 
            "Tipo de Transação", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            opcoesTipo, 
            opcoesTipo[0]);

    Object[] opcoesPagamento = {"Cartão de Crédito", "Cartão de Débito", "Pix", "Dinheiro"};
    String metodo = (String) JOptionPane.showInputDialog(
            this, 
            "Selecione o Método de Pagamento:", 
            "Pagamento", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            opcoesPagamento, 
            opcoesPagamento[0]);

    // Se o usuário clicar em "Cancelar" ou fechar a janela, as variáveis serão null
    if (tipo == null || metodo == null) {
        JOptionPane.showMessageDialog(this, "Operação cancelada.", "Cancelado", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    String data = LocalDate.now().toString(); 
    
    // Cria o Pagamento
    Pagamento novoPagamento = new Pagamento(usuarioAtual.getCpf(), total, tipo, metodo, data, "pendente");
    
    int confirm = JOptionPane.showConfirmDialog(this, 
        String.format("Confirmar pagamento de R$ %.2f via %s?", total, metodo), 
        "Confirmação de Pagamento", JOptionPane.YES_NO_OPTION);
        
    if (confirm == JOptionPane.YES_OPTION) {
        novoPagamento.Pago(); 
        sistema.adicionaPagamento(novoPagamento);
        
        // Atualiza diretamente o item original no sistema para garantir que a tabela veja a mudança
        for (Vendivel itemDoCarrinho : carrinhoAtual.itensNoCarrinho()) {
            Optional<? extends Vendivel> itemOriginal = sistema.buscaMangaPorNome(itemDoCarrinho.getNome());
            
            if (!itemOriginal.isPresent()) {
                itemOriginal = sistema.buscaItemMenuPorNome(itemDoCarrinho.getNome());
            }
            
            if (itemOriginal.isPresent()) {
                itemOriginal.get().add_estoque(-1); // Remove 1 do estoque original
            }
        }
        
        // Atualiza a tabela visual
        atualizarTabelaEstoque();
        
        JOptionPane.showMessageDialog(this, "✅ Pagamento concluído! Estoque atualizado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        
        // Reiniciar o fluxo de venda
        carrinhoAtual = new Carrinho_de_compras(); 
        usuarioAtual = null; 
        cpfField.setText("");
        usuarioInfoLabel.setText("Cliente: Não identificado");
        addItemButton.setEnabled(false);
        finalizarButton.setEnabled(false);
        updateCarrinhoView();
    } else {
        JOptionPane.showMessageDialog(this, "Pagamento cancelado. O carrinho permanece aberto.", "Cancelado", JOptionPane.WARNING_MESSAGE);
    }
}
    

private void carregarItemNoFormularioEstoque() {
    int row = tabelaEstoque.getSelectedRow();
    if (row == -1) return;

    // Pega nome (col 1) e estoque (col 3)
    String nome = (String) modeloEstoque.getValueAt(row, 1);
    String estoque = String.valueOf(modeloEstoque.getValueAt(row, 3));
    
    txtNomeEstoque.setText(nome);
    txtEstoqueAtual.setText(estoque);
    txtNovoEstoque.setText("0");
    btnAtualizarEstoque.setEnabled(true);
}

private void adicionarEstoque(ActionEvent e) {
    int row = tabelaEstoque.getSelectedRow();
    if (row == -1) return;

    String tipo = (String) modeloEstoque.getValueAt(row, 0);
    String idStr = String.valueOf(modeloEstoque.getValueAt(row, 4)); // ID na coluna 4
    
    int quantidade;
    try {
        quantidade = Integer.parseInt(txtNovoEstoque.getText().trim());
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Número inválido.");
        return;
    }

    if (quantidade == 0) return;

    // Lógica de busca e atualização
    Vendivel item = null;
    if (tipo.equals("Mangá")) {
        // Tenta buscar por ID
        Optional<Manga> m = sistema.buscaMangaPorID(idStr);
        if (m.isPresent()) item = m.get();
    } else {
        // Trata o ID do menu (ex: remove prefixos se houver ou converte direto)
        try {
            String limpaId = idStr.replace("ID: ", "").trim();
            Optional<Item_menu> i = sistema.buscaItemPorID(Integer.parseInt(limpaId));
            if (i.isPresent()) item = i.get();
        } catch (Exception ex) { /* Erro de parse de ID */ }
    }

    if (item != null) {
        item.add_estoque(quantidade);
        JOptionPane.showMessageDialog(this, "Estoque atualizado!");
        atualizarTabelaEstoque(); // Recarrega a tabela
        btnAtualizarEstoque.setEnabled(false);
        txtNovoEstoque.setText("0");
    } else {
        JOptionPane.showMessageDialog(this, "Erro: Item não encontrado no sistema.");
    }
}

private JPanel createEstoquePanel() {
    // Cria o painel principal
    JPanel panel = new JPanel(new BorderLayout(10, 10)); 
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Título
    JLabel title = new JLabel("Módulo de Gerenciamento de Estoque", SwingConstants.CENTER);
    title.setFont(new Font("SansSerif", Font.BOLD, 24));
    panel.add(title, BorderLayout.NORTH);

    // --- LADO DIREITO: Tabela de Visualização ---
    String[] colunas = {"Tipo", "Nome", "Preço (R$)", "Estoque", "ID", "Detalhes Extras"};
    modeloEstoque = new DefaultTableModel(colunas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };
    tabelaEstoque = new JTable(modeloEstoque);
    
    // Listener de Seleção para carregar dados no formulário de atualização
    tabelaEstoque.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting() && tabelaEstoque.getSelectedRow() != -1) {
            carregarItemNoFormularioEstoque();
        }
    });

    // --- LADO ESQUERDO: Controles (Abas Separadas) ---
    JTabbedPane controlTabs = new JTabbedPane();
    
    // Aba 1: Atualizar Estoque (Existente)
    controlTabs.addTab("Atualizar Estoque", createUpdatePanel());
    
    // Aba 2: Cadastro Específico de Mangá (Todos os atributos)
    controlTabs.addTab("Cadastrar Mangá", createMangaFormPanel());
    
    // Aba 3: Cadastro Específico de Menu (Todos os atributos)
    controlTabs.addTab("Cadastrar Menu", createMenuFormPanel());

    // Combina tudo no JSplitPane
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setDividerLocation(400); // Aumentei um pouco para caber os formulários maiores
    splitPane.setLeftComponent(controlTabs);
    splitPane.setRightComponent(new JScrollPane(tabelaEstoque));

    panel.add(splitPane, BorderLayout.CENTER); 

    // Inicializa o conteúdo da tabela
    atualizarTabelaEstoque();

    return panel;
}

// ---------------------------------------------------------
// PAINEL DE CADASTRO DE MANGÁ (Completo com todos atributos)
// ---------------------------------------------------------
private JComponent createMangaFormPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = createGbc(); // Método auxiliar para layout

    // Título da Seção
    JLabel lblTitulo = new JLabel("Novo Mangá");
    lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));
    gbc.gridwidth = 2; 
    panel.add(lblTitulo, gbc);
    gbc.gridwidth = 1; gbc.gridy++;

    // Campos
    JTextField txtNome = addLabelAndField(panel, "Nome da Obra:", gbc);
    JTextField txtSerie = addLabelAndField(panel, "Série:", gbc);
    JTextField txtVolume = addLabelAndField(panel, "Volume (nº):", gbc);
    JTextField txtPreco = addLabelAndField(panel, "Preço (R$):", gbc);
    JTextField txtEstoque = addLabelAndField(panel, "Estoque Inicial:", gbc);
    JTextField txtLocal = addLabelAndField(panel, "Localização (Estante):", gbc);
    JTextField txtAutores = addLabelAndField(panel, "Autores (separe por vírgula):", gbc);
    JTextField txtGeneros = addLabelAndField(panel, "Gêneros (separe por vírgula):", gbc);

    // Botão Salvar
    JButton btnSalvar = new JButton("Cadastrar Mangá");
    btnSalvar.setBackground(new Color(100, 149, 237)); // Azul Cornflower
    btnSalvar.setForeground(Color.WHITE);
    
    gbc.gridy++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
    panel.add(btnSalvar, gbc);
    
    // Lógica do Botão
    btnSalvar.addActionListener(e -> {
        try {
            String nome = txtNome.getText().trim();
            String serie = txtSerie.getText().trim();
            int volume = Integer.parseInt(txtVolume.getText().trim());
            float preco = Float.parseFloat(txtPreco.getText().replace(",", ".").trim());
            int estoque = Integer.parseInt(txtEstoque.getText().trim());
            String local = txtLocal.getText().trim();
            
            // Processamento de Arrays
            String[] autores = txtAutores.getText().split(",");
            for(int i=0; i<autores.length; i++) autores[i] = autores[i].trim();
            
            String[] generos = txtGeneros.getText().split(",");
            for(int i=0; i<generos.length; i++) generos[i] = generos[i].trim();

            if (nome.isEmpty() || serie.isEmpty() || local.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Preencha todos os campos de texto.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Chamada ao sistema
            sistema.adicionaManga(nome, autores, generos, serie, volume, local, estoque, preco);
            
            JOptionPane.showMessageDialog(panel, "Mangá cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            atualizarTabelaEstoque();
            
            // Limpar campos
            txtNome.setText(""); txtSerie.setText(""); txtVolume.setText("");
            txtPreco.setText(""); txtEstoque.setText(""); txtLocal.setText("");
            txtAutores.setText(""); txtGeneros.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Verifique se Preço, Volume e Estoque são números válidos.", "Erro de Formatação", JOptionPane.ERROR_MESSAGE);
        }
    });

    // Espaço final para empurrar tudo para cima
    gbc.gridy++; gbc.weighty = 1.0;
    panel.add(Box.createVerticalGlue(), gbc);

    return new JScrollPane(panel);
}

// ---------------------------------------------------------
// PAINEL DE CADASTRO DE MENU (Completo com todos atributos)
// ---------------------------------------------------------
private JComponent createMenuFormPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = createGbc();

    // Título
    JLabel lblTitulo = new JLabel("Novo Item de Menu");
    lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));
    gbc.gridwidth = 2;
    panel.add(lblTitulo, gbc);
    gbc.gridwidth = 1; gbc.gridy++;

    // Campos
    JTextField txtNome = addLabelAndField(panel, "Nome do Prato/Bebida:", gbc);
    JTextField txtPreco = addLabelAndField(panel, "Preço (R$):", gbc);
    JTextField txtEstoque = addLabelAndField(panel, "Estoque:", gbc);
    JTextField txtIngredientes = addLabelAndField(panel, "Ingredientes:", gbc);

    // Botão Salvar
    JButton btnSalvar = new JButton("Cadastrar Item Menu");
    btnSalvar.setBackground(new Color(60, 179, 113)); // Verde
    btnSalvar.setForeground(Color.WHITE);

    gbc.gridy++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
    panel.add(btnSalvar, gbc);

    // Lógica do Botão
    btnSalvar.addActionListener(e -> {
        try {
            String nome = txtNome.getText().trim();
            float preco = Float.parseFloat(txtPreco.getText().replace(",", ".").trim());
            int estoque = Integer.parseInt(txtEstoque.getText().trim());
            String ingredientes = txtIngredientes.getText().trim();

            if (nome.isEmpty() || ingredientes.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Preencha Nome e Ingredientes.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Chamada ao sistema (qtdVenda inicia em 0)
            sistema.adicionaItem(nome, ingredientes, preco, estoque, 0);

            JOptionPane.showMessageDialog(panel, "Item de Menu adicionado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            atualizarTabelaEstoque();

            // Limpar campos
            txtNome.setText(""); txtPreco.setText(""); 
            txtEstoque.setText(""); txtIngredientes.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Preço e Estoque devem ser números.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    });

    gbc.gridy++; gbc.weighty = 1.0;
    panel.add(Box.createVerticalGlue(), gbc);

    return panel;
}

// ---------------------------------------------------------
// MÉTODOS AUXILIARES DE GUI E ATUALIZAÇÃO
// ---------------------------------------------------------

// Helper para adicionar label + textfield rapidamente
private JTextField addLabelAndField(JPanel panel, String labelText, GridBagConstraints gbc) {
    gbc.gridx = 0; 
    gbc.anchor = GridBagConstraints.WEST;
    panel.add(new JLabel(labelText), gbc);
    
    gbc.gridx = 1;
    JTextField field = new JTextField(20);
    panel.add(field, gbc);
    
    gbc.gridy++; // Prepara para a próxima linha
    gbc.gridx = 0; // Reseta X
    return field;
}

// Helper para configurações padrão do GridBag
private GridBagConstraints createGbc() {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridx = 0; gbc.gridy = 0;
    return gbc;
}

private void atualizarTabelaEstoque() {
    modeloEstoque.setRowCount(0);
    
    // Mangás: Coluna 4 DEVE ser o ID para a atualização funcionar
    for (Manga m : sistema.getListaMangas()) {
        String detalhes = "Loc: " + m.getLocalizacao() + " | Vol: " + m.getVolume();
        modeloEstoque.addRow(new Object[]{
            "Mangá", 
            m.getNome(), 
            String.format("%.2f", m.getPreco()), 
            m.getEstoque(), 
            m.getId(), // <--- MUDANÇA AQUI: Passando ID na coluna 4
            detalhes
        });
    }

    // Menu
    for (Item_menu i : sistema.getListaItemMenu()) {
        modeloEstoque.addRow(new Object[]{
            "Menu", 
            i.getNome(), 
            String.format("%.2f", i.getPreco()), 
            i.getEstoque(), 
            String.valueOf(i.getID_menu()), // ID na coluna 4
            i.getIngredientes()
        });
    }
}

// Painel de Atualização (Mantido similar, apenas ajustado para o contexto)
private JPanel createUpdatePanel() {
    JPanel formPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = createGbc();

    // --- NOVIDADE: Mensagem de Instrução ---
    JLabel lblAviso = new JLabel("<html><center>Clique em um item na tabela à direita<br>para carregá-lo aqui.</center></html>");
    lblAviso.setForeground(new Color(0, 102, 204)); // Azul para chamar atenção (ou use Color.RED)
    lblAviso.setFont(new Font("SansSerif", Font.BOLD, 11));
    lblAviso.setHorizontalAlignment(SwingConstants.CENTER);
    
    // Configura para ocupar as duas colunas do topo
    gbc.gridwidth = 2; 
    gbc.anchor = GridBagConstraints.CENTER;
    formPanel.add(lblAviso, gbc);

    // Reseta configurações para os campos de texto abaixo
    gbc.gridy++; 
    gbc.gridwidth = 1; 
    gbc.anchor = GridBagConstraints.WEST;
    
    // --- Campos Normais (Como antes) ---
    formPanel.add(new JLabel("Nome Item Selecionado:"), gbc);
    gbc.gridy++;
    txtNomeEstoque = new JTextField(20);
    txtNomeEstoque.setEditable(false); // Continua travado, mas agora o usuário sabe o porquê
    txtNomeEstoque.setBackground(new Color(230, 230, 230)); // Cinza claro para indicar visualmente que é "leitura"
    formPanel.add(txtNomeEstoque, gbc);

    gbc.gridy++;
    formPanel.add(new JLabel("Estoque Atual:"), gbc);
    gbc.gridy++;
    txtEstoqueAtual = new JTextField(5);
    txtEstoqueAtual.setEditable(false);
    txtEstoqueAtual.setBackground(new Color(230, 230, 230));
    formPanel.add(txtEstoqueAtual, gbc);

    gbc.gridy++;
    formPanel.add(new JLabel("Quantidade:"), gbc);
    
    gbc.gridy++;
    txtNovoEstoque = new JTextField("0", 5);
    formPanel.add(txtNovoEstoque, gbc);
    
    // Dica em texto pequeno
    gbc.gridy++;
    JLabel lblDica = new JLabel("(Ex: 10 adiciona, -5 remove)");
    lblDica.setFont(new Font("SansSerif", Font.PLAIN, 10));
    lblDica.setForeground(Color.GRAY);
    formPanel.add(lblDica, gbc);

    gbc.gridy++;
    btnAtualizarEstoque = new JButton("Atualizar Estoque");
    btnAtualizarEstoque.addActionListener(this::adicionarEstoque);
    btnAtualizarEstoque.setEnabled(false);
    
    // Centraliza o botão
    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    btnPanel.add(btnAtualizarEstoque);
    gbc.gridwidth = 2; // Botão ocupa largura total
    formPanel.add(btnPanel, gbc);

    gbc.gridy++; gbc.weighty = 1.0;
    formPanel.add(Box.createVerticalGlue(), gbc);

    return formPanel;
}

    // Pessoas
    private JPanel createPessoasPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel title = new JLabel("Gerenciamento de Clientes", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        // SplitPane: Esquerda (Formulário) | Direita (Tabela)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(350);
        
        // --- Lado Esquerdo: Formulário ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Usuário"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        // Campos
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridy++;
        txtNome = new JTextField(20);
        formPanel.add(txtNome, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("CPF:"), gbc);
        gbc.gridy++;
        txtCpf = new JTextField(15);
        formPanel.add(txtCpf, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridy++;
        txtEmail = new JTextField(20);
        formPanel.add(txtEmail, gbc);
        
        gbc.gridy++;
        formPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridy++;
        txtTelefone = new JTextField(15);
        formPanel.add(txtTelefone, gbc);

        gbc.gridy++;
        formPanel.add(new JLabel("Assinatura:"), gbc);
        gbc.gridy++;
        String[] assinaturas = {"A (Premium)", "B (Padrão)", "C (Básico)", "N (Nenhuma)"};
        cmbAssinatura = new JComboBox<>(assinaturas);
        formPanel.add(cmbAssinatura, gbc);

        // Botões
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnDeletar = new JButton("Deletar");
        
        btnSalvar.setBackground(new Color(60, 179, 113)); // Verde
        btnSalvar.setForeground(Color.WHITE);
        btnDeletar.setBackground(new Color(220, 53, 69)); // Vermelho
        btnDeletar.setForeground(Color.WHITE);

        btnPanel.add(btnSalvar);
        btnPanel.add(btnLimpar);
        btnPanel.add(btnDeletar);

        gbc.gridy++;
        formPanel.add(btnPanel, gbc);

        // --- Lado Direito: Tabela ---
        String[] colunas = {"CPF", "Nome", "Email", "Telefone", "Ass."};
        modeloTabelaUsuarios = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaUsuarios = new JTable(modeloTabelaUsuarios);
        
        // Listener de Seleção da Tabela
        tabelaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaUsuarios.getSelectedRow() != -1) {
                carregarUsuarioNoFormulario();
            }
        });

        splitPane.setLeftComponent(formPanel);
        splitPane.setRightComponent(new JScrollPane(tabelaUsuarios));
        panel.add(splitPane, BorderLayout.CENTER);

        // Ações dos Botões
        btnSalvar.addActionListener(e -> salvarUsuario());
        btnLimpar.addActionListener(e -> limparFormularioPessoas());
        btnDeletar.addActionListener(e -> deletarUsuario());
        
        // Inicializa a tabela
        atualizarTabelaUsuarios();

        return panel;
    }

    // --- Tela de Pagamentos ---
    private JPanel createPagamentosPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Histórico de Pagamentos", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        String[] colunas = {"CPF Cliente", "Valor Total", "Tipo", "Método", "Data", "Status"};
        pagamentosModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        JTable tabelaPagamentos = new JTable(pagamentosModel);
        tabelaPagamentos.setFillsViewportHeight(true);
        panel.add(new JScrollPane(tabelaPagamentos), BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.addActionListener(e -> atualizarTabelaPagamentos());
        
        footerPanel.add(btnAtualizar);
        panel.add(footerPanel, BorderLayout.SOUTH);

        atualizarTabelaPagamentos();

        return panel;
    }

    // --- MÉTODOS AUXILIARES ---

    private void atualizarTabelaPagamentos() {
        pagamentosModel.setRowCount(0);
        java.util.List<Pagamento> lista = sistema.getListaPagamentos(); 

        if (lista != null) {
            for (Pagamento p : lista) {
                pagamentosModel.addRow(new Object[]{
                    p.getUsuario(),
                    String.format("R$ %.2f", p.getValor()),
                    p.getTipo(),
                    p.getMetodo(),
                    p.getData(),
                    p.getStatus()
                });
            }
        }
    }

    // --- Lógica CRUD de Pessoas ---

    private void atualizarTabelaUsuarios() {
        modeloTabelaUsuarios.setRowCount(0);
        java.util.List<Usuario> usuarios = sistema.getListaUsuarios();
        
        if (usuarios != null) {
            for (Usuario u : usuarios) {
                modeloTabelaUsuarios.addRow(new Object[]{
                    u.getCpf(), u.getNome(), u.getEmail(), u.getTelefone(), u.getAssinatura()
                });
            }
        }
    }

    private void salvarUsuario() {
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String email = txtEmail.getText().trim();
        String tel = txtTelefone.getText().trim();
        
        char assinatura = 'N';
        if (cmbAssinatura.getSelectedItem() != null) {
            assinatura = cmbAssinatura.getSelectedItem().toString().charAt(0);
        }

        if (cpf.isEmpty() || nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Optional<Usuario> userOpt = sistema.buscarUsuarioPorCpf(cpf);

        if (userOpt.isPresent() && !txtCpf.isEditable()) {
            // Editar
            Usuario u = userOpt.get();
            u.setNome(nome);
            u.setEmail(email);
            u.setTelefone(tel);
            u.mudaAssinatura(assinatura); 
            JOptionPane.showMessageDialog(this, "Usuário atualizado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else if (userOpt.isPresent() && txtCpf.isEditable()) {
            JOptionPane.showMessageDialog(this, "CPF já cadastrado!", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            // Criar
            sistema.adicionaUsuario(cpf, nome, email, tel, assinatura);
            JOptionPane.showMessageDialog(this, "Usuário cadastrado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }

        limparFormularioPessoas();
        atualizarTabelaUsuarios();
    }

    private void deletarUsuario() {
        int row = tabelaUsuarios.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cpf = (String) modeloTabelaUsuarios.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Remover usuário CPF " + cpf + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean removido = sistema.removerUsuario(cpf);
            if (removido) {
                JOptionPane.showMessageDialog(this, "Usuário removido.");
                limparFormularioPessoas();
                atualizarTabelaUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao remover (verifique SistemaDeBusca).");
            }
        }
    }

    private void carregarUsuarioNoFormulario() {
        int row = tabelaUsuarios.getSelectedRow();
        if (row == -1) return;

        txtCpf.setText(modeloTabelaUsuarios.getValueAt(row, 0).toString());
        txtNome.setText(modeloTabelaUsuarios.getValueAt(row, 1).toString());
        txtEmail.setText(modeloTabelaUsuarios.getValueAt(row, 2).toString());
        txtTelefone.setText(modeloTabelaUsuarios.getValueAt(row, 3).toString());
        
        String assStr = modeloTabelaUsuarios.getValueAt(row, 4).toString();
        if (!assStr.isEmpty()) {
            char ass = assStr.charAt(0);
            for (int i=0; i < cmbAssinatura.getItemCount(); i++) {
                if (cmbAssinatura.getItemAt(i).charAt(0) == ass) {
                    cmbAssinatura.setSelectedIndex(i);
                    break;
                }
            }
        }
        txtCpf.setEditable(false); 
    }

    private void limparFormularioPessoas() {
        txtNome.setText("");
        txtCpf.setText("");
        txtCpf.setEditable(true);
        txtEmail.setText("");
        txtTelefone.setText("");
        if (cmbAssinatura.getItemCount() > 0) cmbAssinatura.setSelectedIndex(0);
        tabelaUsuarios.clearSelection();
    }

    // Método MAIN (Deve estar DENTRO da classe, mas fora de outros métodos)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RyuseiCafeGUI());
    }

} // FIM DA CLASSE RyuseiCafeGUI