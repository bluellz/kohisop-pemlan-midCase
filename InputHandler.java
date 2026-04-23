import java.util.Scanner;

public class InputHandler {
    private Scanner scanner = new Scanner(System.in);
    
   import java.util.Scanner;
import java.util.List;

public class InputHandler {
    private Scanner scanner = new Scanner(System.in);
    
    // Tambahkan parameter List<String> buat nyimpen histori kode yang udah diinput
    public String readMenuCode(List<String> kodeYangUdahDipilih) {
        while (true) {
            System.out.print("Masukkan Kode Menu (ketik 'DONE' jika selesai, 'CC' batal): ");
            String input = scanner.nextLine().trim().toUpperCase(); // Bikin uppercase biar gampang cocokinnya
            
            // Kalau user ngetik DONE atau CC, biarin lewat (jangan dicek duplikatnya)
            if (input.equals("DONE") || input.equals("CC")) {
                return input;
            }
            
            // Logika validasinya ada di sini:
            if (kodeYangUdahDipilih.contains(input)) {
                System.out.println("Eh, lu udah input menu ini bro! Pilih menu yang lain ya.");
                continue; // Ngulang loop buat minta input lagi
            }
            
            return input;
        }
    }
    
    public PaymentChannel readPaymentChannel() {
        while (true) {
            System.out.print("Pilih Pembayaran (1. Tunai, 2. QRIS, 3. eMoney): ");
            String ch = scanner.next(); scanner.nextLine();
            if (ch.equals("1")) return new Tunai();
            if (ch.equals("2")) return new QRIS();
            if (ch.equals("3")) return new EMoney();
            System.out.println("Pilihan tidak valid!");
        }
    }
    
    public Currency readCurrency() {
        while (true) {
            System.out.print("Pilih Mata Uang (1. IDR, 2. USD, 3. JPY, 4. MYR, 5. EUR): ");
            String mu = scanner.next(); scanner.nextLine();
            if (mu.equals("1")) return new IDR();
            if (mu.equals("2")) return new USD();
            if (mu.equals("3")) return new JPY();
            if (mu.equals("4")) return new MYR();
            if (mu.equals("5")) return new EUR();
            System.out.println("Pilihan tidak valid!");
        }
    }
}
