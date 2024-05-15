/**
 * 
 */
package chat.rest.api;

import java.util.Base64;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 
 */
public class Base64ImageDecoder {
	public static void main(String[] args) {
		String base64Image = "YOUR_BASE64_IMAGE_TEXT_HERE";
		byte[] imageBytes = Base64.getDecoder().decode(base64Image);

		try (OutputStream stream = new FileOutputStream("decoded_image.jpg")) {
			stream.write(imageBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
