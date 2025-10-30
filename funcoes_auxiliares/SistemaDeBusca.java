package funcoes_auxiliares;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SistemaDeBusca {

    private List<Manga> mangas;
    private List<Usuario> usuarios;
    private List<Pagamento> pagamentos;
    private List<Item_menu> itens_menu;

    public SistemaDeBusca() {
        this.mangas = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
        this.itens_menu = new ArrayList<>();
    }

    public Usuario adicionaUsuario(String cpf, String nome, String email, String telefone, char Assinatura) {

        Usuario novo_usuario = new Usuario(cpf, nome, email, telefone, Assinatura);
        this.usuarios.add(novo_usuario);
        return novo_usuario;
    }

    public Manga adicionaManga(String nome, String[] autores, String[] generos, String serie, int volume, String localizacao, int estoqu, float preco) {
        Manga novo_manga = new Manga(nome, autores, generos, serie, volume, localizacao, estoqu, preco);
        this.mangas.add(novo_manga);
        return novo_manga;
    }

    public Item_menu adiciona_item(String nome, String ingredientes, float preco) {
        Item_menu novo_item = new Item_menu(nome, ingredientes, preco);
        this.itens_menu.add(novo_item);
        return novo_item;
    }
     
    public Pagamento adicionaPagamento(String usr, float val, String type, String met, String da, String stt) {
        Pagamento novo_pagamento = new Pagamento(usr, val, type, met, da, stt);
        this.pagamentos.add(novo_pagamento);
        return novo_pagamento;
    }


    //buscas

    public Optional<Usuario> buscarUsuarioPorCpf(String cpf_desejado) {
        return usuarios.stream()
        .filter(usuario -> usuario.getCpf().equals(cpf_desejado))
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
    
}