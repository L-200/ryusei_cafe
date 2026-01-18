import ryusei.*;
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
    // Variáveis para a Tela de Funcionários (dentro de pessoas)
    private JTextField txtNomeFunc, txtCpfFunc, txtEmailFunc, txtTelefoneFunc, txtSalarioFunc, txtCargoFunc;
    private JTable tabelaFuncionarios;
    private DefaultTableModel modeloTabelaFuncionarios;

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
    
        // Inicializa Lógica de Negócios (Conecta ao Banco automaticamente)
        try {
            this.sistema = new SistemaDeBusca();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro crítico ao conectar no banco: " + e.getMessage());
            System.exit(1); // Fecha se não tiver banco
        }

        carrinhoAtual = new Carrinho_de_compras();

        // Configuração Básica do Frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        
        // Inicializa Painel Principal com CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        add(createNavBar(), BorderLayout.WEST); 
        add(mainPanel, BorderLayout.CENTER);    

        mainPanel.add(createVendasPanel(), VENDAS_CARD);
        mainPanel.add(createEstoquePanel(), ESTOQUE_CARD);
        mainPanel.add(createPessoasPanel(), PESSOAS_CARD);
        mainPanel.add(createPagamentosPanel(), PAGAMENTOS_CARD);
        
        cardLayout.show(mainPanel, VENDAS_CARD); 
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void salvarESair() {
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja sair?", "Confirmação", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            sistema.encerrarSistema(); // Certifique-se de ter criado esse método no SistemaDeBusca
            System.exit(0);
        }
    }
    
    // --- Componentes da GUI: Barra de Navegação ---
    private JPanel createNavBar() {
        JPanel navBar = new JPanel();
        
        // Layout Vertical (BoxLayout)
        navBar.setLayout(new BoxLayout(navBar, BoxLayout.Y_AXIS));
        
        // Estilização (Bordas e Cor de Fundo)
        navBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        navBar.setBackground(new Color(50, 50, 50)); // Cinza escuro

        // Criação dos Botões de Navegação
        // Usamos as constantes definidas no topo da classe (VENDAS_CARD, etc.)
        JButton vendasBtn = createNavButton("Vendas", VENDAS_CARD);
        JButton estoqueBtn = createNavButton("Estoque", ESTOQUE_CARD);
        JButton pessoasBtn = createNavButton("Pessoas", PESSOAS_CARD);
        JButton pagamentosBtn = createNavButton("Pagamentos", PAGAMENTOS_CARD);
        
        // Criação do Botão de Sair 
        // (Texto alterado de "Salvar & Sair" para "Sair")
        JButton sairBtn = createNavButton("Sair", null);

        // Adicionando os componentes na barra com espaçamento
        navBar.add(vendasBtn);
        navBar.add(Box.createRigidArea(new Dimension(0, 10))); // Espaço fixo de 10px
        
        navBar.add(estoqueBtn);
        navBar.add(Box.createRigidArea(new Dimension(0, 10)));
        
        navBar.add(pessoasBtn);
        navBar.add(Box.createRigidArea(new Dimension(0, 10)));
        
        navBar.add(pagamentosBtn);
        
        // O Glue (Cola) empurra tudo que vem depois dele para o final (rodapé)
        navBar.add(Box.createVerticalGlue());
        
        navBar.add(sairBtn);

        // Ação do Botão Sair
        // Ele chama o método que agora fecha a conexão com o banco
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
            usuarioInfoLabel.setText("Cliente: " + usuarioAtual.getNome() + " (Ass: " + usuarioAtual.getAssinatura() + ")");
            addItemButton.setEnabled(true);
            finalizarButton.setEnabled(true);
        } else {
            usuarioAtual = null;
            usuarioInfoLabel.setText("Cliente: Usuário não encontrado");
            addItemButton.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Usuário não encontrado no banco.", "Erro", JOptionPane.ERROR_MESSAGE);
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
        // Validação básica: Carrinho vazio?
        if (carrinhoAtual.itensNoCarrinho().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O carrinho está vazio.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cálculo do Total
        float total = carrinhoAtual.calcula_total(); 

        // Seleção do Método de Pagamento
        Object[] opcoesPagamento = {"Cartão de Crédito", "Cartão de Débito", "Pix", "Dinheiro"};
        String metodo = (String) JOptionPane.showInputDialog(
                this, 
                "Selecione o Método de Pagamento:", 
                "Pagamento", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                opcoesPagamento, 
                opcoesPagamento[0]);

        // Se usuário cancelar a janela de seleção
        if (metodo == null) {
            JOptionPane.showMessageDialog(this, "Operação cancelada.", "Cancelado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Confirmação Final
        int confirm = JOptionPane.showConfirmDialog(this, 
            String.format("Confirmar pagamento de R$ %.2f via %s para o cliente %s?", total, metodo, usuarioAtual.getNome()), 
            "Confirmação de Pagamento", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // --- PASSO A: Registrar o Pagamento no Banco ---
                String data = LocalDate.now().toString(); 
                Pagamento novoPagamento = new Pagamento(0, usuarioAtual.getCpf(), total, metodo, data);
                
                sistema.adicionaPagamento(novoPagamento); // INSERT na tabela pagamentos

                // --- PASSO B: Atualizar Estoque no Banco ---
                carrinhoAtual.AtualizaEstoquePosVenda(sistema);
                
                // --- PASSO C: Feedback e Limpeza ---
                JOptionPane.showMessageDialog(this, "✅ Venda realizada com sucesso!\nEstoque atualizado e pagamento registrado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                // Atualiza a tabela de estoque (para refletir a baixa imediatamente)
                atualizarTabelaEstoque();
                
                // Reinicia o processo de venda para o próximo cliente
                resetarTelaVendas();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro crítico ao finalizar venda: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Útil para debugar
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pagamento cancelado. O carrinho permanece aberto.", "Cancelado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void resetarTelaVendas() {
        carrinhoAtual = new Carrinho_de_compras(); // Cria carrinho novo
        usuarioAtual = null;                       // Desloga o cliente atual
        
        // Limpa os campos visuais
        cpfField.setText("");
        usuarioInfoLabel.setText("Cliente: Não identificado");
        itemSearchField.setText("");
        
        // Trava os botões novamente até buscar novo CPF
        addItemButton.setEnabled(false);
        finalizarButton.setEnabled(false);
        
        // Limpa a tabela visual do carrinho
        updateCarrinhoView();
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
    // Verifica se tem linha selecionada
    int row = tabelaEstoque.getSelectedRow();
    if (row == -1) return;

    // Recupera dados da tabela (Tipo e ID)
    String tipo = (String) modeloEstoque.getValueAt(row, 0);
    
    // O ID está na coluna 4. Convertendo objeto para String e depois para Int por segurança
    Object idObj = modeloEstoque.getValueAt(row, 4);
    int id = Integer.parseInt(idObj.toString());

    // Recupera a quantidade digitada
    int quantidade;
    try {
        quantidade = Integer.parseInt(txtNovoEstoque.getText().trim());
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Por favor, digite um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (quantidade == 0) return; // Se for 0, não faz nada

    // Lógica de Atualização (Banco de Dados)
    try {
        if (tipo.equals("Mangá")) {
            // A. Busca o objeto atualizado do banco
            Optional<Manga> optManga = sistema.buscaMangaPorID(id);
            
            if (optManga.isPresent()) {
                Manga m = optManga.get();
                m.add_estoque(quantidade); // Atualiza o objeto Java
                
                // B. Salva a alteração no Postgres!
                sistema.atualizarEstoqueManga(m); 
                
                JOptionPane.showMessageDialog(this, "Estoque de Mangá atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Erro: Mangá não encontrado no banco (ID: " + id + ").");
            }

        } else if (tipo.equals("Menu")) {
            // A. Busca o objeto atualizado do banco
            Optional<Item_menu> optItem = sistema.buscaItemPorID(id);
            
            if (optItem.isPresent()) {
                Item_menu item = optItem.get();
                item.add_estoque(quantidade); // Atualiza o objeto Java
                
                // B. Salva a alteração no Postgres!
                sistema.atualizarEstoqueItemMenu(item);
                
                JOptionPane.showMessageDialog(this, "Estoque do Menu atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Erro: Item de menu não encontrado no banco (ID: " + id + ").");
            }
        }

        // Atualiza a interface visual
        atualizarTabelaEstoque(); // Puxa os dados novos do banco para a tabela
        txtNovoEstoque.setText("0");
        btnAtualizarEstoque.setEnabled(false);

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro ao salvar no banco de dados: " + ex.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
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
            float preco = Float.parseFloat(txtPreco.getText().replace(",", ".").trim());
            int estoque = Integer.parseInt(txtEstoque.getText().trim());
            String local = txtLocal.getText().trim();
            String autores = txtAutores.getText().trim();
            String generos = txtGeneros.getText().trim();

            if (nome.isEmpty() || serie.isEmpty() || local.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Preencha todos os campos de texto.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Chamada ao sistema
            sistema.adicionaManga(nome, autores, generos, serie, local, estoque, estoque, preco);
            
            JOptionPane.showMessageDialog(panel, "Mangá cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            atualizarTabelaEstoque();
            
            // Limpar campos
            txtNome.setText(""); txtSerie.setText("");
            txtPreco.setText(""); txtEstoque.setText(""); txtLocal.setText("");
            txtAutores.setText(""); txtGeneros.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Verifique se Preço e Estoque são números válidos.", "Erro de Formatação", JOptionPane.ERROR_MESSAGE);
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
    
    // sistema.getListaMangas() agora faz um SELECT * FROM mangas
    for (Manga m : sistema.getListaMangas()) {
        modeloEstoque.addRow(new Object[]{
            "Mangá", m.getNome(), String.format("%.2f", m.getPreco()), 
            m.getEstoque(), m.getId(), m.getLocalizacao()
        });
    }

    // sistema.getListaItemMenu() agora faz um SELECT * FROM menu
    for (Item_menu i : sistema.getListaItemMenu()) {
        modeloEstoque.addRow(new Object[]{
            "Menu", i.getNome(), String.format("%.2f", i.getPreco()), 
            i.getEstoque(), i.getID_menu(), i.getIngredientes()
        });
    }
}

// Painel de Atualização
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
        JPanel panel = new JPanel(new BorderLayout());
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Aba 1: Gerenciar Clientes (A lógica antiga movida para cá)
        tabbedPane.addTab("Clientes", createClientesPanel());
        
        // Aba 2: Gerenciar Funcionários (Nova lógica)
        tabbedPane.addTab("Funcionários", createFuncionariosPanel());
        
        panel.add(tabbedPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createClientesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título
        JLabel title = new JLabel("Gerenciamento de Clientes", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);

        // SplitPane: Esquerda (Formulário) | Direita (Tabela)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(350);
        
        // --- Lado Esquerdo: Formulário ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Cliente"));
        GridBagConstraints gbc = createGbc(); // Usa o seu helper existente

        // Campos
        txtNome = addLabelAndField(formPanel, "Nome:", gbc);
        txtCpf = addLabelAndField(formPanel, "CPF:", gbc);
        txtEmail = addLabelAndField(formPanel, "Email:", gbc);
        txtTelefone = addLabelAndField(formPanel, "Telefone:", gbc);

        gbc.gridx = 0; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Assinatura:"), gbc);
        gbc.gridx = 1;
        String[] assinaturas = {"A (Premium)", "B (Padrão)", "C (Básico)", "N (Nenhuma)"};
        cmbAssinatura = new JComboBox<>(assinaturas);
        formPanel.add(cmbAssinatura, gbc);
        gbc.gridy++;

        // Botões
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnDeletar = new JButton("Deletar");
        
        btnSalvar.setBackground(new Color(60, 179, 113));
        btnSalvar.setForeground(Color.WHITE);
        btnDeletar.setBackground(new Color(220, 53, 69));
        btnDeletar.setForeground(Color.WHITE);

        btnPanel.add(btnSalvar);
        btnPanel.add(btnLimpar);
        btnPanel.add(btnDeletar);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        // --- Lado Direito: Tabela ---
        String[] colunas = {"CPF", "Nome", "Email", "Telefone", "Ass."};
        modeloTabelaUsuarios = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaUsuarios = new JTable(modeloTabelaUsuarios);
        
        tabelaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaUsuarios.getSelectedRow() != -1) {
                carregarUsuarioNoFormulario();
            }
        });

        splitPane.setLeftComponent(new JScrollPane(formPanel)); // Adicionado Scroll para segurança
        splitPane.setRightComponent(new JScrollPane(tabelaUsuarios));
        panel.add(splitPane, BorderLayout.CENTER);

        // Ações dos Botões
        btnSalvar.addActionListener(e -> salvarUsuario());
        btnLimpar.addActionListener(e -> limparFormularioPessoas());
        btnDeletar.addActionListener(e -> deletarUsuario());
        
        atualizarTabelaUsuarios();
        return panel;
    }

    private JPanel createFuncionariosPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Gerenciamento de Funcionários", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(350);

        // Formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Funcionário"));
        GridBagConstraints gbc = createGbc();

        // Campos (Equivalentes aos de Usuario, mas com Cargo e Salário em vez de Assinatura)
        txtNomeFunc = addLabelAndField(formPanel, "Nome:", gbc);
        txtCpfFunc = addLabelAndField(formPanel, "CPF:", gbc);
        txtEmailFunc = addLabelAndField(formPanel, "Email:", gbc);
        txtTelefoneFunc = addLabelAndField(formPanel, "Telefone:", gbc);
        txtSalarioFunc = addLabelAndField(formPanel, "Salário (R$):", gbc);
        txtCargoFunc = addLabelAndField(formPanel, "Cargo:", gbc);

        // Botões (Mesma estrutura de Clientes)
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnDeletar = new JButton("Deletar");

        btnSalvar.setBackground(new Color(70, 130, 180)); // Azul para diferenciar
        btnSalvar.setForeground(Color.WHITE);
        btnDeletar.setBackground(new Color(220, 53, 69));
        btnDeletar.setForeground(Color.WHITE);

        btnPanel.add(btnSalvar);
        btnPanel.add(btnLimpar);
        btnPanel.add(btnDeletar);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        // Tabela
        String[] colunas = {"CPF", "Nome", "Cargo", "Salário", "Email", "Telefone"}; 
        
        modeloTabelaFuncionarios = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaFuncionarios = new JTable(modeloTabelaFuncionarios);

        tabelaFuncionarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaFuncionarios.getSelectedRow() != -1) {
                carregarFuncionarioNoFormulario(); // Função equivalente a carregarUsuarioNoFormulario
            }
        });

        splitPane.setLeftComponent(new JScrollPane(formPanel));
        splitPane.setRightComponent(new JScrollPane(tabelaFuncionarios));
        panel.add(splitPane, BorderLayout.CENTER);

        // Ações (Funções equivalentes às de Clientes)
        btnSalvar.addActionListener(e -> salvarFuncionario());
        btnLimpar.addActionListener(e -> limparFormularioFuncionario());
        btnDeletar.addActionListener(e -> deletarFuncionario());

        atualizarTabelaFuncionarios(); 
        return panel;
    }


    // --- Lógica CRUD de Funcionários ---

    // Equivalente a atualizarTabelaUsuarios
    private void atualizarTabelaFuncionarios() {
        modeloTabelaFuncionarios.setRowCount(0);
        java.util.List<Funcionario> funcionarios = sistema.getListaFuncionarios(); 
        
        if (funcionarios != null) {
            for (Funcionario f : funcionarios) {
                modeloTabelaFuncionarios.addRow(new Object[]{
                    f.getCpf(), 
                    f.getNome(), 
                    f.getFuncao(), 
                    String.format("%.2f", f.getSalario()),
                    f.getEmail(),
                    f.getTelefone()
                });
            }
        }
    }
    // 2. Equivalente a salvarUsuario
    private void salvarFuncionario() {
        String nome = txtNomeFunc.getText().trim();
        String cpf = txtCpfFunc.getText().trim();
        String email = txtEmailFunc.getText().trim();
        String tel = txtTelefoneFunc.getText().trim();
        String cargo = txtCargoFunc.getText().trim();
        String salarioStr = txtSalarioFunc.getText().replace(",", ".").trim();

        if (cpf.isEmpty() || nome.isEmpty() || salarioStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "CPF, Nome e Salário são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double salario = Double.parseDouble(salarioStr);

            // Verifica se o funcionário já existe no banco
            Optional<Funcionario> funcOpt = sistema.buscarFuncionarioPorCpf(cpf);

            if (funcOpt.isPresent() && !txtCpfFunc.isEditable()) {
                // --- MODO EDIÇÃO (UPDATE) ---
                // Recuperamos o objeto, atualizamos na memória
                Funcionario f = funcOpt.get();
                f.setNome(nome);
                f.setEmail(email);
                f.setTelefone(tel);
                f.setFuncao(cargo);
                f.setSalario(salario);
                
                // Manda para o banco atualizar!
                sistema.atualizarFuncionario(f);
                JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!");

            } else if (funcOpt.isPresent() && txtCpfFunc.isEditable()) {
                // --- ERRO: TENTANDO CRIAR DUPLICADO ---
                JOptionPane.showMessageDialog(this, "Erro: Já existe um funcionário com este CPF.", "Erro", JOptionPane.WARNING_MESSAGE);
                return;

            } else {
                // --- MODO CRIAÇÃO (INSERT) ---
                sistema.adicionaFuncionario(cpf, nome, tel, email, salario, cargo);
                JOptionPane.showMessageDialog(this, "Novo funcionário cadastrado!");
            }
            
            limparFormularioFuncionario();
            atualizarTabelaFuncionarios();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Salário inválido. Use números.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarFuncionario() {
        int row = tabelaFuncionarios.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cpf = (String) modeloTabelaFuncionarios.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Remover funcionário CPF " + cpf + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Chama o DELETE no banco
            boolean removido = sistema.removerFuncionario(cpf); 
        
            if (removido) {
                atualizarTabelaFuncionarios(); // Recarrega do banco
            }
        }
    }

    // Equivalente a carregarUsuarioNoFormulario
   private void carregarFuncionarioNoFormulario() {
    int row = tabelaFuncionarios.getSelectedRow();
    if (row == -1) return;

    // 0: CPF, 1: Nome, 2: Cargo, 3: Salário, 4: Email, 5: Telefone

    txtCpfFunc.setText(modeloTabelaFuncionarios.getValueAt(row, 0).toString());
    txtNomeFunc.setText(modeloTabelaFuncionarios.getValueAt(row, 1).toString());
    txtCargoFunc.setText(modeloTabelaFuncionarios.getValueAt(row, 2).toString());
    // Tratamento do Salário para tirar o R$ e poder editar
    String salStr = modeloTabelaFuncionarios.getValueAt(row, 3).toString()
                      .replace("R$", "").replace(",", ".").trim();
    txtSalarioFunc.setText(salStr);
    
    txtEmailFunc.setText(modeloTabelaFuncionarios.getValueAt(row, 4).toString());
    
    if (modeloTabelaFuncionarios.getColumnCount() > 5 && modeloTabelaFuncionarios.getValueAt(row, 5) != null) {
        txtTelefoneFunc.setText(modeloTabelaFuncionarios.getValueAt(row, 5).toString());
    } else {
        txtTelefoneFunc.setText(""); // Limpa se não tiver telefone
    }
    
    txtCpfFunc.setEditable(false); 
}

    // 5. Equivalente a limparFormularioPessoas
    private void limparFormularioFuncionario() {
        txtNomeFunc.setText("");
        txtCpfFunc.setText("");
        txtCpfFunc.setEditable(true);
        txtEmailFunc.setText("");
        txtTelefoneFunc.setText("");
        txtSalarioFunc.setText("");
        txtCargoFunc.setText("");
        tabelaFuncionarios.clearSelection();
    }


    // --- Tela de Pagamentos ---
    private JPanel createPagamentosPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Histórico de Pagamentos", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);
        String[] colunas = {"CPF Cliente", "Valor Total", "Método", "Data"};
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
                    p.getUsuario(), String.format("R$ %.2f", p.getValor()), 
                    p.getMetodo(), p.getData()
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
    // Coleta os dados dos campos de texto
    String nome = txtNome.getText().trim();
    String cpf = txtCpf.getText().trim();
    String email = txtEmail.getText().trim();
    String tel = txtTelefone.getText().trim();
    
    // Coleta a assinatura do ComboBox (Pega só a primeira letra: 'A', 'B'...)
    char assinatura = 'N'; // Valor padrão (Nenhuma)
    if (cmbAssinatura.getSelectedItem() != null) {
        assinatura = cmbAssinatura.getSelectedItem().toString().charAt(0);
    }

    // Validação básica
    if (cpf.isEmpty() || nome.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Verifica se o usuário já existe no banco
    Optional<Usuario> userOpt = sistema.buscarUsuarioPorCpf(cpf);

    // Lógica de Decisão (Editar vs Criar)
    try {
        if (userOpt.isPresent() && !txtCpf.isEditable()) {
            // --- CENÁRIO A: EDITAR (UPDATE) ---
            // O usuário existe E o campo CPF está travado (significa que clicamos na tabela para editar)
            
            Usuario u = userOpt.get();
            // Atualiza os dados do objeto na memória
            u.setNome(nome);
            u.setEmail(email);
            u.setTelefone(tel);
            u.mudaAssinatura(assinatura); 
            
            // Manda o sistema atualizar no Banco de Dados
            sistema.atualizarUsuario(u); 
            
            JOptionPane.showMessageDialog(this, "Dados do usuário atualizados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } else if (userOpt.isPresent() && txtCpf.isEditable()) {
            // --- CENÁRIO B: ERRO DE DUPLICATA ---
            // O usuário existe, mas o campo estava livre (tentativa de criar novo com CPF repetido)
            JOptionPane.showMessageDialog(this, "Erro: Este CPF já está cadastrado no sistema!", "Duplicidade", JOptionPane.WARNING_MESSAGE);
            return; // Para aqui para não limpar o formulário
            
        } else {
            // --- CENÁRIO C: CRIAR NOVO (INSERT) ---
            // O usuário não existe no banco
            
            sistema.adicionaUsuario(cpf, nome, email, tel, assinatura);
            JOptionPane.showMessageDialog(this, "Novo usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }

        // Limpeza e Atualização Visual
        limparFormularioPessoas(); // Reseta os campos e destrava o CPF
        atualizarTabelaUsuarios(); // Recarrega a lista do banco para mostrar as mudanças

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao salvar usuário: " + e.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
        }
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