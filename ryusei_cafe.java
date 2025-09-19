import ryuccino.*;
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
                    ryuccino.Usuario usr = new ryuccino.Usuario();
                    usr.criarUsuario(cpf, nome, telefone, email, 'A');
                    System.out.println("Cliente " + nome + " adicionado com sucesso!");
                    System.out.println("");
                    System.out.println("Dados do cliente:");
                    usr.mostraUsuario();
                    System.out.println("");
                    break;

                    case 2:
                    ryuccino.Manga manga = new ryuccino.Manga();
                    manga.novoManga("Naruto", new String[]{"Masashi Kishimoto"}, new String[]{"Ação", "Aventura"}, "Naruto", 1, "Prateleira 1", 0, 10);
                    System.out.println("Manga adicionado com sucesso!");
                    System.out.println("");
                    System.out.println("Dados do manga:");
                    manga.mostraManga();
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