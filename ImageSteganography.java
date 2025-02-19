import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ImageSteganography {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Load the image
            BufferedImage img = ImageIO.read(new File("mypic.jpg"));
            if (img == null) {
                throw new IOException("Image not found. Check the file path.");
            }

            int width = img.getWidth();
            int height = img.getHeight();

            // Input secret message and password
            System.out.print("Enter secret message: ");
            String msg = sc.nextLine();

            System.out.print("Enter a passcode: ");
            String password = sc.nextLine();

            // Encryption: Store each character in the blue channel of each pixel
            int n = 0, m = 0;
            for (char ch : msg.toCharArray()) {
                int pixel = img.getRGB(m, n);
                int r = (pixel >> 16) & 0xFF;
                int g = (pixel >> 8) & 0xFF;
                int b = ch; // Directly store ASCII value of the character in blue

                int newPixel = (r << 16) | (g << 8) | b;
                img.setRGB(m, n, newPixel);

                m++;
                if (m >= width) {
                    m = 0;
                    n++;
                    if (n >= height) throw new RuntimeException("Message too long for image.");
                }
            }

            // Save encrypted image
            ImageIO.write(img, "png", new File("encryptedImage.png"));
            System.out.println("✅ Message encrypted and saved as 'encryptedImage.png'");

            // Decryption
            System.out.print("Enter passcode for Decryption: ");
            String pass = sc.nextLine();

            if (password.equals(pass)) {
                StringBuilder decryptedMsg = new StringBuilder();
                n = 0;
                m = 0;

                for (int i = 0; i < msg.length(); i++) {
                    int pixel = img.getRGB(m, n);
                    int b = pixel & 0xFF;  // Retrieve the blue channel (where we stored the char)
                    decryptedMsg.append((char) b);

                    m++;
                    if (m >= width) {
                        m = 0;
                        n++;
                    }
                }
                System.out.println("🔓 Decrypted message: " + decryptedMsg);
            } else {
                System.out.println("🚫 YOU ARE NOT AUTHORIZED. Incorrect passcode.");
            }

        } catch (IOException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        sc.close();
    }
}
