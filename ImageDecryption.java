import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class ImageDecryption {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Load the encrypted image
            File encryptedImageFile = new File("encryptedImage.png");
            BufferedImage encryptedImage = ImageIO.read(encryptedImageFile);

            if (encryptedImage == null) {
                System.out.println("Image not found or unsupported format.");
                return;
            }

            // Prompt user for the password
            System.out.print("Enter the password for decryption: ");
            String inputPassword = scanner.nextLine();

            // Define the correct password (replace with dynamic handling if needed)
            String correctPassword = "12345"; // Update this if you saved the password externally

            if (!inputPassword.equals(correctPassword)) {
                System.out.println("Incorrect password. Access denied.");
                return;
            }

            // Decrypt the message
            StringBuilder decryptedMessage = new StringBuilder();
            int width = encryptedImage.getWidth();
            int height = encryptedImage.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = encryptedImage.getRGB(x, y);
                    int blue = pixel & 0xff; // Extracting blue channel

                    if (blue == 0) { // Assuming 0 as the terminator for the message
                        break;
                    }
                    decryptedMessage.append((char) blue);
                }
            }

            // Display the decrypted message
            System.out.println("\nDecrypted message: " + decryptedMessage.toString());

        } catch (IOException e) {
            System.out.println("Error reading the encrypted image: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}