/**
 * Ad Soyad: Miraç Duran
 * Öğrenci No: 250541091
 * Proje: Proje 3 - Akıllı Restoran Sipariş Sistemi
 * Tarih: 27.11.2025
 */

import java.util.Scanner;

public class AkilliRestoranSistemi {

    // Happy hour ve öğrenci/hafta içi indirimi için kullanılacak global değişkenler
    static double drinkPriceGlobal = 0.0;
    static boolean isWeekdayGlobal = true;

    // İndirim detaylarını tutmak için (raporlamak adına)
    static double lastComboDiscount = 0.0;
    static double lastHappyHourDiscount = 0.0;
    static double lastStudentDiscount = 0.0;
    static double lastOver200Discount = 0.0;

    // 1) Ana yemek fiyatı (birim fiyat)
    public static double getMainDishPrice(int secim) {
        double fiyat;

        switch (secim) {
            case 1: // Izgara Tavuk
                fiyat = 85.0;
                break;
            case 2: // Adana Kebap
                fiyat = 120.0;
                break;
            case 3: // Levrek
                fiyat = 110.0;
                break;
            case 4: // Mantı
                fiyat = 65.0;
                break;
            default:
                fiyat = 0.0; // Seçilmedi veya geçersiz
                break;
        }

        return fiyat;
    }

    // 2) Başlangıç fiyatı (birim fiyat)
    public static double getAppetizerPrice(int secim) {
        double fiyat;

        switch (secim) {
            case 1: // Çorba
                fiyat = 25.0;
                break;
            case 2: // Humus
                fiyat = 45.0;
                break;
            case 3: // Sigara Böreği
                fiyat = 55.0;
                break;
            default:
                fiyat = 0.0;
                break;
        }

        return fiyat;
    }

    // 3) İçecek fiyatı (birim fiyat)
    public static double getDrinkPrice(int secim) {
        double fiyat;

        switch (secim) {
            case 1: // Kola
                fiyat = 15.0;
                break;
            case 2: // Ayran
                fiyat = 12.0;
                break;
            case 3: // Meyve Suyu
                fiyat = 35.0;
                break;
            case 4: // Limonata
                fiyat = 25.0;
                break;
            default:
                fiyat = 0.0;
                break;
        }

        return fiyat;
    }

    // 4) Tatlı fiyatı (birim fiyat)
    public static double getDessertPrice(int secim) {
        double fiyat;

        switch (secim) {
            case 1: // Künefe
                fiyat = 65.0;
                break;
            case 2: // Baklava
                fiyat = 55.0;
                break;
            case 3: // Sütlaç
                fiyat = 35.0;
                break;
            default:
                fiyat = 0.0;
                break;
        }

        return fiyat;
    }

    // 5) Combo mu? (Ana + İçecek + Tatlı)
    public static boolean isComboOrder(boolean anaVar, boolean icecekVar, boolean tatliVar) {
        return (anaVar && icecekVar && tatliVar);
    }

    // 6) Happy Hour saati mi? (14:00 - 17:00 arası)
    public static boolean isHappyHour(int saat) {
        return (saat >= 14 && saat < 17);
    }

    // 7) Toplam indirimi hesapla
    // - Önce Combo %15
    // - Sonra Happy Hour: içeceklerde %20 (tüm içecek toplamı)
    // - Sonra Öğrenci (hafta içi) %10
    // - Son durumda hala >200 ise ekstra %10
    public static double calculateDiscount(double tutar, boolean combo, boolean ogrenci, int saat) {
        double araToplam = tutar;
        double toplamIndirim = 0.0;

        // Kayıt değişkenlerini sıfırla
        lastComboDiscount = 0.0;
        lastHappyHourDiscount = 0.0;
        lastStudentDiscount = 0.0;
        lastOver200Discount = 0.0;

        // Combo indirimi
        if (combo) {
            lastComboDiscount = araToplam * 0.15;
            toplamIndirim += lastComboDiscount;
            araToplam -= lastComboDiscount;
        }

        // Happy Hour (sadece içecekten %20)
        if (isHappyHour(saat) && drinkPriceGlobal > 0) {
            lastHappyHourDiscount = drinkPriceGlobal * 0.20;
            toplamIndirim += lastHappyHourDiscount;
            araToplam -= lastHappyHourDiscount;
        }

        // Öğrenci indirimi (hafta içi)
        if (ogrenci && isWeekdayGlobal) {
            lastStudentDiscount = araToplam * 0.10;
            toplamIndirim += lastStudentDiscount;
            araToplam -= lastStudentDiscount;
        }

        // 200 TL üzeri ekstra %10
        if (araToplam > 200.0) {
            lastOver200Discount = araToplam * 0.10;
            toplamIndirim += lastOver200Discount;
            // araToplam -= lastOver200Discount; // istersen bunu da güncelleyebilirsin
        }

        return toplamIndirim;
    }

    // 8) Bahşiş önerisi (%10)
    public static double calculateServiceTip(double tutar) {
        return tutar * 0.10;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== Akıllı Restoran Sipariş Sistemi ===");

        // Gün bilgisi (öğrenci indirimi için hafta içi mi?)
        System.out.println("Gün seçiniz: (1=Pzt, 2=Salı, 3=Çrş, 4=Prş, 5=Cuma, 6=Ctesi, 7=Pazar)");
        int gun = input.nextInt();

        boolean haftaIci = (gun >= 1 && gun <= 5);
        isWeekdayGlobal = haftaIci;

        // Saat bilgisi (Happy Hour için)
        System.out.print("Saat (0-23 arasında, sadece tam saat): ");
        int saat = input.nextInt();

        // Öğrenci misiniz?
        System.out.print("Öğrenci misiniz? (1=Evet, 2=Hayır): ");
        int ogrenciSecim = input.nextInt();
        boolean ogrenci = (ogrenciSecim == 1);

        // Menü seçimi

        System.out.println("\n--- Ana Yemekler ---");
        System.out.println("1 - Izgara Tavuk (85 TL)");
        System.out.println("2 - Adana Kebap (120 TL)");
        System.out.println("3 - Levrek (110 TL)");
        System.out.println("4 - Mantı (65 TL)");
        System.out.println("0 - Seçim yok");
        System.out.print("Ana yemek seçiminiz: ");
        int anaSecim = input.nextInt();
        System.out.print("Ana yemek adet: ");
        int anaAdet = input.nextInt();

        System.out.println("\n--- Başlangıçlar ---");
        System.out.println("1 - Çorba (25 TL)");
        System.out.println("2 - Humus (45 TL)");
        System.out.println("3 - Sigara Böreği (55 TL)");
        System.out.println("0 - Seçim yok");
        System.out.print("Başlangıç seçiminiz: ");
        int baslangicSecim = input.nextInt();
        System.out.print("Başlangıç adet: ");
        int baslangicAdet = input.nextInt();

        System.out.println("\n--- İçecekler ---");
        System.out.println("1 - Kola (15 TL)");
        System.out.println("2 - Ayran (12 TL)");
        System.out.println("3 - Meyve Suyu (35 TL)");
        System.out.println("4 - Limonata (25 TL)");
        System.out.println("0 - Seçim yok");
        System.out.print("İçecek seçiminiz: ");
        int icecekSecim = input.nextInt();
        System.out.print("İçecek adet: ");
        int icecekAdet = input.nextInt();

        System.out.println("\n--- Tatlılar ---");
        System.out.println("1 - Künefe (65 TL)");
        System.out.println("2 - Baklava (55 TL)");
        System.out.println("3 - Sütlaç (35 TL)");
        System.out.println("0 - Seçim yok");
        System.out.print("Tatlı seçiminiz: ");
        int tatliSecim = input.nextInt();
        System.out.print("Tatlı adet: ");
        int tatliAdet = input.nextInt();

        // Birim fiyatları al
        double anaBirim = getMainDishPrice(anaSecim);
        double baslangicBirim = getAppetizerPrice(baslangicSecim);
        double icecekBirim = getDrinkPrice(icecekSecim);
        double tatliBirim = getDessertPrice(tatliSecim);

        // Adetle çarpılmış fiyatlar
        double anaFiyat = anaBirim * anaAdet;
        double baslangicFiyat = baslangicBirim * baslangicAdet;
        double icecekFiyat = icecekBirim * icecekAdet;
        double tatliFiyat = tatliBirim * tatliAdet;

        // Happy Hour indirimi için toplam içecek tutarını global değişkene aktar
        drinkPriceGlobal = icecekFiyat;

        boolean anaVar = (anaFiyat > 0);
        boolean icecekVar = (icecekFiyat > 0);
        boolean tatliVar = (tatliFiyat > 0);

        boolean combo = isComboOrder(anaVar, icecekVar, tatliVar);

        double araToplam = anaFiyat + baslangicFiyat + icecekFiyat + tatliFiyat;

        double toplamIndirim = calculateDiscount(araToplam, combo, ogrenci, saat);
        double odenecekTutar = araToplam - toplamIndirim;
        double bahsisOnerisi = calculateServiceTip(odenecekTutar);

        System.out.println("\n======================================");
        System.out.println("             SİPARİŞ ÖZETİ            ");
        System.out.println("======================================");
        System.out.printf("Ara Toplam           : %.2f TL%n", araToplam);
        System.out.printf("Combo İndirimi       : -%.2f TL%n", lastComboDiscount);
        System.out.printf("Happy Hour İndirimi  : -%.2f TL%n", lastHappyHourDiscount);
        System.out.printf("Öğrenci İndirimi     : -%.2f TL%n", lastStudentDiscount);
        System.out.printf("200 TL Üzeri İndirim : -%.2f TL%n", lastOver200Discount);
        System.out.println("--------------------------------------");
        System.out.printf("Toplam İndirim       : -%.2f TL%n", toplamIndirim);
        System.out.printf("Ödenecek Tutar       : %.2f TL%n", odenecekTutar);
        System.out.printf("Bahşiş Önerisi (%%10) : %.2f TL%n", bahsisOnerisi);
        System.out.println("======================================");

        input.close();
    }
}

