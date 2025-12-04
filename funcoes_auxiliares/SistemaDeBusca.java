package funcoes_auxiliares;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import pessoa.Funcionario;
import pessoa.Usuario;

public class SistemaDeBusca {

    private List<Manga> mangas;
    private List<Usuario> usuarios;
    private List<Funcionario> funcionarios;
    private List<Pagamento> pagamentos;
    private List<Item_menu> itens_menu;

    public SistemaDeBusca() {
        this.mangas = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
        this.itens_menu = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
    }

    public void carregarUsuariosCSV() {
    try (BufferedReader br = new BufferedReader(new FileReader("usuarios.csv"))) {
        String linha;
        while ((linha = br.readLine()) != null) {
            String[] p = linha.split(";");
            adicionaUsuario(p[0], p[1], p[2], p[3], p[4].charAt(0));
        }
    } catch (Exception e) {}
}

    public void carregarFuncionariosCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("funcionarios.csv"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(";");
                adicionaFuncionario(p[0], p[1], p[2], p[3],
                                    Double.parseDouble(p[4]), p[5]);
            }
        } catch (Exception e) {}
    }

    public void carregarMangasCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("mangas.csv"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(";");
                String[] autores = p[1].split(",");
                String[] generos = p[2].split(",");

                adicionaManga(p[0], autores, generos, p[3],
                            Integer.parseInt(p[4]),
                            p[5],
                            Integer.parseInt(p[6]),
                            Float.parseFloat(p[7]));
            }
        } catch (Exception e) {}
    }

    public void carregarMenuCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("menu.csv"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(";");
                Item_menu item = adicionaItem(p[1], p[2],
                                            Float.parseFloat(p[3]),
                                            Integer.parseInt(p[4]),
                                            Integer.parseInt(p[5]));
                item.setID_menu(Integer.parseInt(p[0]));
            }
        } catch (Exception e) {}
    }

    public void carregarPagamentosCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("pagamentos.csv"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(";");
                Pagamento pag = adicionaPagamento(p[1],
                                                Float.parseFloat(p[2]),
                                                p[3], p[4], p[5], p[6]);
                pag.setID_pagamento(p[0]);
            }
        } catch (Exception e) {}
    }

    public Usuario adicionaUsuario(String cpf, String nome, String email, String telefone, char Assinatura) {

        Usuario novo_usuario = new Usuario(cpf, nome, email, telefone, Assinatura);
        this.usuarios.add(novo_usuario);
        return novo_usuario;
    }

    public Funcionario adicionaFuncionario(String cpf, String nome, String telefone, String email, double salario, String funcao) {
        Funcionario novo_funcionario = new Funcionario(cpf, nome, telefone, email, salario, funcao);
        this.funcionarios.add(novo_funcionario);
        return novo_funcionario;
    }

    public Manga adicionaManga(String nome, String[] autores, String[] generos, String serie, int volume, String localizacao, int estoqu, float preco) {
        Manga novo_manga = new Manga(nome, autores, generos, serie, volume, localizacao, estoqu, preco);
        this.mangas.add(novo_manga);
        return novo_manga;
    }

    public Item_menu adicionaItem(String nome, String ingredientes, float preco, int estoque, int qtdVenda) {
        Item_menu novo_item = new Item_menu(nome, ingredientes, preco, estoque, qtdVenda);
        this.itens_menu.add(novo_item);
        return novo_item;
    }
    
    // Sobrecarga para adicionar um objeto Pagamento já criado (usado no fluxo de compra)
    public void adicionaPagamento(Pagamento p) {
        this.pagamentos.add(p);
    }
     
    // Método original usado para carregar o CSV
    public Pagamento adicionaPagamento(String usr, float val, String type, String met, String da, String stt) {
        Pagamento novo_pagamento = new Pagamento(usr, val, type, met, da, stt);
        this.pagamentos.add(novo_pagamento);
        return novo_pagamento;
    }
    
    public void mostraUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        System.out.println("\n--- LISTA DE USUÁRIOS ---");
        for (Usuario u : usuarios) {
            u.mostraUsuario();
            System.out.println("-------------------------");
        }
    }

    public void mostraFuncionarios() {
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }
        System.out.println("\n--- LISTA DE FUNCIONÁRIOS ---");
        for (Funcionario f : funcionarios) {
            f.mostraFuncionario();
            System.out.println("-----------------------------");
        }
    }

    public void mostraMangas() {
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

    public void mostraMenu() {
        if (itens_menu.isEmpty()) {
            System.out.println("Nenhum item no menu do café.");
            return;
        }
        System.out.println("\n--- MENU DO CAFÉ ---");
        for (Item_menu i : itens_menu) {
            i.mostraItem();
            System.out.println("--------------------");
        }
    }
    
    public void mostraPagamentos() {
        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
            return;
        }
        System.out.println("\n--- LISTA DE PAGAMENTOS ---");
        for (Pagamento p : pagamentos) {
            p.mostraPagamento();
            System.out.println("---------------------------");
        }
    }

    // para a GUI
    public List<Pagamento> getListaPagamentos() {
    return this.pagamentos; // Ou o nome da sua variável de lista
}

    // buscas

    public Optional<Usuario> buscarUsuarioPorCpf(String cpf_desejado) {
        return usuarios.stream()
        .filter(usuario -> usuario.getCpf().equals(cpf_desejado))
        .findFirst();
    }

    public Optional<Funcionario> buscarFuncionarioPorCpf(String cpf_desejado) {
        return funcionarios.stream()
        .filter(funcionario -> funcionario.getCpf().equals(cpf_desejado))
        .findFirst();
    }

    public Boolean cliente_existe (String cpf) {
        return buscarUsuarioPorCpf(cpf).isPresent();
    }

    public Optional<Manga> buscaMangaPorNome (String nome_desejado) {
        return mangas.stream()
        .filter(manga -> manga.getNome().equals(nome_desejado))
        .findFirst();
    }

    public Optional<Manga> buscaMangaPorID (String id_desejado) {
        return mangas.stream()
        .filter(mangas -> mangas.getId().equals(id_desejado))
        .findFirst();
    }
    
    // Função de busca para Item_menu por nome, adicionada para o menu interativo
    public Optional<Item_menu> buscaItemMenuPorNome (String nome_desejado) {
        return itens_menu.stream()
        .filter(item_menu -> item_menu.getNome().equalsIgnoreCase(nome_desejado))
        .findFirst();
    }

    public Optional<Pagamento> buscaPagamentoPorID (String ID_do_item_desejado) {
        return pagamentos.stream()
        .filter(pagamento -> pagamento.getID_pagamento().equals(ID_do_item_desejado))
        .findFirst();
    }

    public Optional<Item_menu> buscaItemPorID (int id_desejado) {
        return itens_menu.stream()
        .filter(item_menu -> item_menu.getID_menu() == id_desejado)
        .findFirst();
    }
    
    public void salvarUsuariosCSV() {
    try (PrintWriter pw = new PrintWriter("usuarios.csv")) {
        for (Usuario u : usuarios) {
            pw.println(u.getCpf() + ";" +
                       u.getNome() + ";" +
                       u.getEmail() + ";" +
                       u.getTelefone() + ";" +
                       u.getAssinatura());
        }
    } catch (Exception e) { System.out.println("Erro ao salvar usuarios.csv"); }
}

    public void salvarFuncionariosCSV() {
        try (PrintWriter pw = new PrintWriter("funcionarios.csv")) {
            for (Funcionario f : funcionarios) {
                pw.println(f.getCpf() + ";" +
                        f.getNome() + ";" +
                        f.getTelefone() + ";" +
                        f.getEmail() + ";" +
                        f.getSalario() + ";" +
                        f.getFuncao());
            }
        } catch (Exception e) { System.out.println("Erro ao salvar funcionarios.csv"); }
    }

    public void salvarMangasCSV() {
        try (PrintWriter pw = new PrintWriter("mangas.csv")) {
            for (Manga m : mangas) {

                String autores = String.join(",", m.getAutores());
                String generos = String.join(",", m.getGeneros());

                pw.println(m.getNome() + ";" +
                        autores + ";" +
                        generos + ";" +
                        m.getSerie() + ";" +
                        m.getVolume() + ";" +
                        m.getLocalizacao() + ";" +
                        m.getEstoque() + ";" +
                        m.getPreco());
            }
        } catch (Exception e) { System.out.println("Erro ao salvar mangas.csv"); }
    }

    public void salvarMenuCSV() {
    try (PrintWriter pw = new PrintWriter("menu.csv")) {
        for (Item_menu i : itens_menu) {
            pw.println(i.getID_menu() + ";" +
                    i.getNome() + ";" +
                    i.getIngredientes() + ";" +
                    i.getPreco() + ";" +
                    i.getEstoque() + ";" +   // Adicionado o separador
                    i.getQtdVenda());
        }
    } catch (Exception e) { 
        System.out.println("Erro ao salvar menu.csv"); 
        e.printStackTrace(); // Boa prática: imprime o erro real se houver
    }
}

    public void salvarPagamentosCSV() {
        try (PrintWriter pw = new PrintWriter("pagamentos.csv")) {
            for (Pagamento p : pagamentos) {
                pw.println(p.getID_pagamento() + ";" +
                        p.getUsuario() + ";" +
                        p.getValor() + ";" +
                        p.getTipo() + ";" +
                        p.getMetodo() + ";" +
                        p.getData() + ";" +
                        p.getStatus());
            }
        } catch (Exception e) { System.out.println("Erro ao salvar pagamentos.csv"); }
    }

    // Para a GUI

    public List<Usuario> getListaUsuarios() {
        return this.usuarios;
    }

    // 2. Necessário para o botão "Deletar"
    public boolean removerUsuario(String cpf) {
        // Remove o usuário se o CPF coincidir. Retorna true se removeu.
        return usuarios.removeIf(u -> u.getCpf().equals(cpf));
    }
    public List<Manga> getListaMangas() {
        return this.mangas;
    }

    public List<Item_menu> getListaItemMenu() {
        return this.itens_menu;
    }

}
