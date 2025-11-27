/**
 * Ad Soyad: Miraç Duran
 * Öğrenci No: 250541091
 * Proje: Proje 1 - Öğrenci Not Değerlendirme Sistemi
 * Tarih: 27.11.2025
 */

import java.util.Scanner;

public class OgrenciNotSistemi {

    // 1) Ortalama hesaplama
    public static double calculateAverage(double vize, double fin, double odev) {
        return vize * 0.30 + fin * 0.40 + odev * 0.30;
    }

    // 2) Geçti mi kaldı mı?
    public static boolean isPassingGrade(double ortalama) {
        return ortalama >= 50.0;
    }

    // 3) Harf notu
    public static char getLetterGrade(double ortalama) {
        char harf;

        if (ortalama >= 90) {
            harf = 'A';
        } else if (ortalama >= 80) {
            harf = 'B';
        } else if (ortalama >= 70) {
            harf = 'C';
        } else if (ortalama >= 60) {
            harf = 'D';
        } else if (ortalama >= 50) {
            harf = 'E';
        } else {
            harf = 'F';
        }

        return harf;
    }

    // 4) Onur listesi kontrolü
    public static boolean isHonorList(double ortalama, double vize, double fin, double odev) {
        boolean tumNotlarYeterli = (vize >= 70 && fin >= 70 && odev >= 70);
        boolean ortalamaYeterli = (ortalama >= 85);

        return tumNotlarYeterli && ortalamaYeterli;
    }

    // 5) Bütünleme hakkı var mı?
    public static boolean hasRetakeRight(double ortalama) {
        return (ortalama >= 40 && ortalama < 50);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Vize notunu giriniz: ");
        double vize = input.nextDouble();

        System.out.print("Final notunu giriniz: ");
        double fin = input.nextDouble();

        System.out.print("Ödev notunu giriniz: ");
        double odev = input.nextDouble();

        // Ortalama
        double ortalama = calculateAverage(vize, fin, odev);

        // Geçti mi?
        boolean gectiMi = isPassingGrade(ortalama);

        // Harf notu
        char harfNotu = getLetterGrade(ortalama);

        // Onur listesi
        boolean onurListesi = isHonorList(ortalama, vize, fin, odev);

        // Bütünleme hakkı
        boolean butunlemeHakki = hasRetakeRight(ortalama);

        System.out.println("====================================");
        System.out.printf("Ortalama        : %.1f%n", ortalama);
        System.out.println("Harf Notu       : " + harfNotu);

        if (gectiMi) {
            System.out.println("Durum           : GEÇTİ");
        } else {
            System.out.println("Durum           : KALDI");
        }

        if (onurListesi) {
            System.out.println("Onur Listesi    : EVET");
        } else {
            System.out.println("Onur Listesi    : HAYIR");
        }

        if (butunlemeHakki) {
            System.out.println("Bütünleme Hakkı : EVET");
        } else {
            System.out.println("Bütünleme Hakkı : HAYIR");
        }

        input.close();
    }
}
