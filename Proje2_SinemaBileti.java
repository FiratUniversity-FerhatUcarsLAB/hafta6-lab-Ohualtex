/**
 * Ad Soyad: Miraç Duran
 * Öğrenci No: 250541091
 * Proje: Proje 2 - Sinema Bileti Fiyatlandırma Sistemi
 * Tarih: 27.11.2025
 */

import java.util.Scanner;

public class SinemaBiletSistemi {

    // 1) Hafta sonu mu?
    public static boolean isWeekend(int gun) {
        return (gun == 6 || gun == 7);
    }

    // 2) Gündüz gösterisi mi? (12:00 öncesi) 
    public static boolean isMatinee(int saat) {
        return (saat < 12);
    }

    // 3) Temel bilet fiyatını hesapla
    public static double calculateBasePrice(int gun, int saat) {
        boolean weekend = isWeekend(gun);
        boolean matinee = isMatinee(saat);
        double fiyat;

        if (!weekend && matinee) {           // Hafta içi matine
            fiyat = 45.0;
        } else if (!weekend && !matinee) {   // Hafta içi normal
            fiyat = 65.0;
        } else if (weekend && matinee) {     // Hafta sonu matine
            fiyat = 55.0;
        } else {                             // Hafta sonu normal
            fiyat = 85.0;
        }

        return fiyat;
    }

    // 4) İndirim oranını hesapla (0.20, 0.35 gibi)
    public static double calculateDiscount(int yas, int meslek, int gun) {
        double indirimOrani = 0.0;

        boolean haftaSonu = isWeekend(gun);
        boolean ogrenci = (meslek == 1);
        boolean ogretmen = (meslek == 2);

        // Yaş indirimleri
        if (yas >= 65) {
            indirimOrani = 0.30; // 65+ %30
        } else if (yas < 12) {
            indirimOrani = 0.25; // 12 yaş altı %25
        }

        // Öğrenci indirimi
        if (ogrenci) {
            double ogrenciIndirimi;

            // Pzt(1) - Prş(4) arası
            if (!haftaSonu && gun >= 1 && gun <= 4) {
                ogrenciIndirimi = 0.20; // %20
            } else {
                ogrenciIndirimi = 0.15; // Cum-Paz %15
            }

            if (ogrenciIndirimi > indirimOrani) {
                indirimOrani = ogrenciIndirimi;
            }
        }

        // Öğretmen indirimi (sadece Çarşamba)
        if (ogretmen && gun == 3) {
            double ogretmenIndirimi = 0.35;
            if (ogretmenIndirimi > indirimOrani) {
                indirimOrani = ogretmenIndirimi;
            }
        }

        return indirimOrani;
    }

    // 5) Film formatına göre ekstra fiyat
    public static double getFormatExtra(int filmTuru) {
        double ekstra;

        switch (filmTuru) {
            case 1: // 2D
                ekstra = 0.0;
                break;
            case 2: // 3D
                ekstra = 25.0;
                break;
            case 3: // IMAX
                ekstra = 35.0;
                break;
            case 4: // 4DX
                ekstra = 50.0;
                break;
            default:
                ekstra = 0.0;
                break;
        }

        return ekstra;
    }

    // 6) Nihai fiyat (temel fiyata indirim uygulanır, sonra format eklenir)
    public static double calculateFinalPrice(int gun, int saat, int yas, int meslek, int filmTuru) {
        double basePrice = calculateBasePrice(gun, saat);
        double discountRate = calculateDiscount(yas, meslek, gun);
        double formatExtra = getFormatExtra(filmTuru);

        double indirimli = basePrice * (1 - discountRate);
        double toplam = indirimli + formatExtra;

        return toplam;
    }

    // 7) Bilet bilgilerini ekrana yazdır
    public static void generateTicketInfo(int gun, int saat, int yas, int meslek, int filmTuru) {
        double basePrice = calculateBasePrice(gun, saat);
        double discountRate = calculateDiscount(yas, meslek, gun);
        double formatExtra = getFormatExtra(filmTuru);
        double indirimli = basePrice * (1 - discountRate);
        double finalPrice = indirimli + formatExtra;

        String gunAdi;
        switch (gun) {
            case 1: gunAdi = "Pazartesi"; break;
            case 2: gunAdi = "Salı"; break;
            case 3: gunAdi = "Çarşamba"; break;
            case 4: gunAdi = "Perşembe"; break;
            case 5: gunAdi = "Cuma"; break;
            case 6: gunAdi = "Cumartesi"; break;
            case 7: gunAdi = "Pazar"; break;
            default: gunAdi = "Geçersiz"; break;
        }

        String meslekAdi;
        switch (meslek) {
            case 1: meslekAdi = "Öğrenci"; break;
            case 2: meslekAdi = "Öğretmen"; break;
            case 3: meslekAdi = "Diğer"; break;
            default: meslekAdi = "Bilinmiyor"; break;
        }

        String formatAdi;
        switch (filmTuru) {
            case 1: formatAdi = "2D"; break;
            case 2: formatAdi = "3D"; break;
            case 3: formatAdi = "IMAX"; break;
            case 4: formatAdi = "4DX"; break;
            default: formatAdi = "Bilinmiyor"; break;
        }

        System.out.println("===================================");
        System.out.println("      SİNEMA BİLET BİLGİLERİ      ");
        System.out.println("===================================");
        System.out.println("Gün           : " + gunAdi + " (" + gun + ")");
        System.out.println("Saat          : " + saat + ":00");
        System.out.println("Yaş           : " + yas);
        System.out.println("Meslek        : " + meslekAdi + " (" + meslek + ")");
        System.out.println("Film Türü     : " + formatAdi + " (" + filmTuru + ")");
        System.out.println("-----------------------------------");
        System.out.printf("Temel Fiyat   : %.2f TL%n", basePrice);
        System.out.printf("İndirim Oranı : %.0f%%%n", discountRate * 100);
        System.out.printf("İndirimli     : %.2f TL%n", indirimli);
        System.out.printf("Format Ekstra : %.2f TL%n", formatExtra);
        System.out.printf("Toplam Fiyat  : %.2f TL%n", finalPrice);
        System.out.println("===================================");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== Sinema Bileti Fiyatlandırma Sistemi ===");

        System.out.println("Gün seçiniz:");
        System.out.println("1=Pazartesi, 2=Salı, 3=Çarşamba, 4=Perşembe, 5=Cuma, 6=Cumartesi, 7=Pazar");
        int gun = input.nextInt();

        System.out.print("Saat (0-23): ");
        int saat = input.nextInt();

        System.out.print("Yaş: ");
        int yas = input.nextInt();

        System.out.println("Meslek seçiniz (1=Öğrenci, 2=Öğretmen, 3=Diğer): ");
        int meslek = input.nextInt();

        System.out.println("Film türü seçiniz (1=2D, 2=3D, 3=IMAX, 4=4DX): ");
        int filmTuru = input.nextInt();

        generateTicketInfo(gun, saat, yas, meslek, filmTuru);

        input.close();
    }
}
