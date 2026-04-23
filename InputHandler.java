import java.util.Scanner;
import java.util.List;

public class InputHandler {
    private Scanner scanner = new Scanner(System.in);
    
    // Method buat baca kode menu (dengan validasi duplikat)
    public String readMenuCode(List<String> kodeYangUdahDipilih) {
        while (true) {
            System.out.print("Masukkan Kode Menu (ketik 'DONE' jika selesai, 'CC' batal): ");
            String input = scanner.nextLine().trim().toUpperCase(); 
            
            if (input.equals("DONE") || input.equals("CC")) {
                return input;
            }
            
            if (kodeYangUdahDipilih.contains(input)) {
                System.out.println("Eh, lu udah input menu ini bro! Pilih menu yang lain ya.");
                continue; 
            }
            
            return input;
        }
    }
    
    // Method untuk baca kuantitas (jangan sampai hilang, ini wajib ada)
    public int readQuantity(MenuItem item) {
        int maxQty = (item instanceof Minuman) ? 3 : 2; // Aturan dari soal: Minuman maks 3, Makanan maks 2
        while (true) {
            System.out.print("Kuantitas untuk [" + item.getNama() + "] (Maks " + maxQty + ", 'S'/'0' batal): ");
            String input = scanner.nextLine().trim().toUpperCase();
            
            if (input.equals("CC")) return -2;
            if (input.equals("0") || input.equals("S")) return 0;
            
            int qty = 1; // Default
            if (!input.isEmpty()) {
                try { 
                    qty = Integer.parseInt(input); 
                } catch (NumberFormatException e) { 
                    System.out.println("Error: Harap masukkan angka yang valid!"); 
                    continue; 
                }
            }
            
            if (qty < 0 || qty > maxQty) { 
                System.out.println("Error: Kuantitas tidak valid atau melebihi batas!"); 
                continue; 
            }
            
            return qty;
        }
    }
    
    // Method untuk baca pembayaran
    public PaymentChannel readPaymentChannel() {
        while (true) {
            System.out.print("Pilih Pembayaran (1. Tunai, 2. QRIS, 3. eMoney): ");
            String ch = scanner.next(); scanner.nextLine(); // pake scanner.nextLine() biar enter-nya ke-consume
            
            if (ch.equals("1")) return new Tunai();
            if (ch.equals("2")) return new QRIS(); 
            if (ch.equals("3")) return new EMoney();
            
            System.out.println("Pilihan tidak valid!");
        }
    }
    
    // Method untuk baca mata uang
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
