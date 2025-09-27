import funcoes_auxiliares.*;
import java.util.Scanner;

   
// nao sei se funciona, nem se é uma boa ideia
/*
class item_do_Estoque{
    String nome;
    int qtd;
}
class Estoque_Geral{
    item_do_Estoque[] estq_ger;
}
*/

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
        boolean system_on = true;
        while (system_on) {
            System.out.println("O que deseja fazer?");
            System.out.println("1 - Adicionar novo cliente");
            System.out.println("2 - Adicionar novo manga");
            System.out.println("3 - Adicionar novo pagamento");
            System.out.println("4 - Sair do sistema");
            Scanner sc = new Scanner(System.in);
            int escolha = sc.nextInt();
            sc.nextLine();
            switch (escolha) {

                case 1:
                    System.out.println("Adicionar novo cliente");
                    System.out.println("Qual o CPF do cliente?");
                    String cpf = sc.next();

                    System.out.println("Qual o nome do cliente?");
                    String nome = sc.next();    

                    System.out.println("Qual o telefone do cliente?");
                    String telefone = sc.next();

                    System.out.println("Qual o email do cliente?");
                    String email = sc.next();

                    funcoes_auxiliares.Usuario usr = new funcoes_auxiliares.Usuario();
                    usr.criarUsuario(cpf, nome, telefone, email, 'A');
                    System.out.println("Cliente " + nome + " adicionado com sucesso!");
                    System.out.println("");
                    System.out.println("Dados do cliente:");
                    usr.mostraUsuario();
                    System.out.println("");
                    break;


                    case 2:
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

                    funcoes_auxiliares.Manga manga = new funcoes_auxiliares.Manga();
                    manga.novoManga(nome_manga, autores, generos, serie, volume, localizacao, 0, estoque);
                    System.out.println("Manga " + nome_manga + " adicionado com sucesso!");
                    System.out.println("");
                    System.out.println("Dados do manga:");
                    manga.mostraManga();
                    System.out.println("");
                    break;

                    case 3:
                    System.out.println("Adicionar novo pagamento");
                    System.out.println("Qual o ID do usuário?");
                    String id_usuario = sc.nextLine();

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
                    
                    funcoes_auxiliares.Pagamento pagamento = new funcoes_auxiliares.Pagamento();
                    pagamento.novoPagamento(id_usuario, valor, tipo, metodo, data, status);
                    System.out.println("Pagamento de R$" + valor + " adicionado com sucesso!");
                    System.out.println("");
                    System.out.println("Dados do pagamento:");
                    pagamento.mostraPagamento();
                    System.out.println("");
                    break;

                    case 4:
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