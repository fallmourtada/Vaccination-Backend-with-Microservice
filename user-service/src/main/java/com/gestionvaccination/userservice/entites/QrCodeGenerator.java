package com.gestionvaccination.userservice.entites;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QrCodeGenerator {

    public static String generateQRCode(String contenuQRCode, String filePath) throws WriterException, IOException {
        // Spécifie la largeur et la hauteur de l'image QR
        int width = 300;
        int height = 300;

        // Création d'un QRCodeWriter
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(contenuQRCode, BarcodeFormat.QR_CODE, width, height);

        // Chemin où enregistrer l'image
        Path path = FileSystems.getDefault().getPath(filePath);

        // Écrire l'image sur le disque
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        // Retourne le chemin du fichier comme confirmation
        return filePath;
    }

    public static void main(String[] args) {
        try {
            String contenuQRCode = "https://www.example.com"; // Contenu du QR code
            String filePath = "qr_code.png"; // Nom de l'image générée
            String qrImagePath = generateQRCode(contenuQRCode, filePath);

            System.out.println("QR Code généré avec succès à : " + qrImagePath);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
}
