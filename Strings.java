
public class Strings{

    public static StringBuilder wave(String S){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<S.length(); i+=2){
            sb.append(S.charAt(i));
        }
        for (int i=1; i<S.length(); i+=2){
            sb.append(S.charAt(i));
        }
        return sb;
    }
    public static void main(String[] args){
        System.out.print(wave("VIRATKOHLI"));
    }
}