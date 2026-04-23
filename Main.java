import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private OrderDisplay orderDisplay;
    private InputHandler handler;
    private MenuDisplay menuDisplay;

    public Main() {
        orderDisplay = new OrderDisplay();
        handler = new InputHandler();
        
        List<MenuItem> items = Arrays.asList(
                new Minuman("A1", "Caffe Latte", 46, "Coffee"),
                new Minuman("A2", "Cappuccino", 46, "Coffee"),
                new Minuman("E1", "Caffe Americano", 37, "Coffee"),
                new Minuman("E2", "Caffe Mocha", 55, "Coffee"),
                new Minuman("E3", "Caramel Macchiato", 59, "Coffee"),
                new Minuman("E4", "Asian Dolce Latte", 55, "Coffee"),
                new Minuman("E5", "Double Shots Iced Shaken Espresso", 50, "Coffee"),
                new Minuman("B1", "Freshly Brewed Coffee", 23, "Brew"),
                new Minuman("B2", "Vanilla Sweet Cream Cold Brew", 50, "Brew"),
                new Minuman("B3", "Cold Brew", 44, "Brew"),
                new Makanan("M1", "Petemania Pizza", 112, "Food"),
                new Makanan("M2", "Mie Rebus Super Mario", 35, "Food"),
                new Makanan("M3", "Ayam Bakar Goreng Rebus Spesial", 72, "Food"),
                new Makanan("M4", "Soto Kambing Iga Guling", 124, "Food"),
                new Makanan("S1", "Singkong Bakar A La Carte", 37, "Snack"),
                new Makanan("S2", "Ubi Cilembu Bakar Arang", 58, "Snack"),
                new Makanan("S3", "Tempe Mendoan", 18, "Snack"),
                new Makanan("S4", "Tahu Bakso Extra Telur", 28, "Snack")
        );
        menuDisplay = new MenuDisplay(items);
    }
    
    // BUNGKUS LOGIKANYA DI DALAM METHOD INI
    public void runOrderFlow() {
        List<String> historiKode = new ArrayList<>(); 
        List<MenuItem> selectedItems = new ArrayList<>();
        int drinkCount = 0, foodCount = 0;

        // 2. Loop Input Kode Menu
        while (true) {
            // Panggil handler dengan mengirimkan historiKode
            String code = handler.readMenuCode(historiKode); 
            
            if (code.equalsIgnoreCase("CC")) {
                System.out.println("Pesanan Dibatalkan. Program Berhenti.");
                return;
            }
            if (code.equalsIgnoreCase("DONE")) break; // Keluar loop kalau DONE

            // Karena code dari user huruf besar/kecil bisa beda, ubah ke uppercase untuk cari menu (opsional tergantung logic getItemByCode)
            MenuItem item = menuDisplay.getItemByCode(code);
            
            if (item == null) {
                System.out.println("Error: Kode tidak valid. Silakan coba lagi.");
                continue;
            }

            // Cek batas maksimal 5 jenis item per kategori
            if (item instanceof Minuman) {
                if (drinkCount >= 5) { System.out.println("Maksimal 5 jenis minuman tercapai!"); continue; }
                drinkCount++;
            } else {
                if (foodCount >= 5) { System.out.println("Maksimal 5 jenis makanan tercapai!"); continue; }
                foodCount++;
            }

            // Masukkan item ke list pesanan
            selectedItems.add(item);
            
            // Catat kode yang baru saja berhasil diinput ke histori agar input berikutnya tidak duplikat
            historiKode.add(code.toUpperCase()); 
        } // <--- TUTUP KURUNG WHILE LOOP DI SINI, BUKAN DI BAWAH

        // --- PROSES SETELAH LOOP KODE MENU SELESAI ("DONE") ---

        if (selectedItems.isEmpty()) {
            System.out.println("Tidak ada pesanan. Program Berhenti.");
            return;
        }

        System.out.println("\n-- Tentukan Kuantitas (Enter = 1, 'S'/'0' = Batal, 'CC' = Batal Semua) --");
        for (MenuItem item : selectedItems) {
            int qty = handler.readQuantity(item);
            if (qty == -2) return;
            if (qty > 0) {
                orderDisplay.addItem(new Order(item, qty));
                menuDisplay.showOrderTable(orderDisplay.getDrinks(), orderDisplay.getFoods());
            }
        }

        PaymentChannel channel = handler.readPaymentChannel();
        Currency currency = handler.readCurrency();
        
        PaymentCalculator calc = new PaymentCalculator(orderDisplay, channel, currency);
        ReceiptPrinter printer = new ReceiptPrinter(calc);
        
        printer.printHeader();
        printer.printOrderLines();
        printer.printTotals();
    }

    public static void main(String[] args) {
        new Main().runOrderFlow();
    }
}
