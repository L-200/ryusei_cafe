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

    public Manga adicionaManga(String nome, String[] autores, String[] generos, String serie, int volume, String localizacao, int estoque) {
        Manga novo_manga = new Manga(nome, autores, generos, serie, volume, localizacao, volume, estoque);
        this.mangas.add(novo_manga);
        return novo_manga;
    }

    public Item_menu adiciona_item(String nome, String ingredientes, float preco, int id_item) {
        Item_menu novo_item = new Item_menu(nome, ingredientes, preco, id_item);
        this.itens_menu.add(novo_item);
        return novo_item;
    }
     
    public Pagamento adicionaPagamento(String usr, float val, String type, String met, String da, String stt) {
        Pagamento novo_pagamento = new Pagamento(usr, val, type, met, da, stt);
        this.pagamentos.add(novo_pagamento);
        return novo_pagamento;
    }


    //buscas

}