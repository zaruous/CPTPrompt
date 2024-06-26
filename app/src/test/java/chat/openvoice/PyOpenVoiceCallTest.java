/**
 * 
 */
package chat.openvoice;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

/**
 * 
 */
@Deprecated
public class PyOpenVoiceCallTest {

	@Test
	public void test() throws Exception {
		String openVoiceHome = "C:\\Users\\KYJ\\git\\OpenVoice";
		// 파이썬 스크립트 경로 설정
		String pythonScriptPath = "java_create_tts_hello.py";
		String outFileName = "create_voice.wav";
		exe(openVoiceHome, pythonScriptPath, outFileName, "alloy", "만나서 반갑습니다. 김영준입니다.");

		File file = new File(openVoiceHome, "demo_result\\" + outFileName);
//		if (file.exists()) {
//			Desktop.getDesktop().open(file);
//		} else {
//			System.out.println("not Support");
//		}
		
		playMP3(file);
	}

	/**
	 * @param filePath
	 */
	public static void playMP3(File audioFile) {
//		File file = new File(filePath);
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			AudioFormat format = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.open(audioStream);
			audioClip.start();

			// Wait for the audio to finish playing
			Thread.sleep(audioClip.getMicrosecondLength() / 1000);

			// Close the audio clip
			audioClip.close();
		} catch (UnsupportedAudioFileException ex) {
			System.out.println("The specified audio file is not supported.");
			ex.printStackTrace();
		} catch (LineUnavailableException ex) {
			System.out.println("Audio line for playing back is unavailable.");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("Error playing the audio file.");
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @작성자 : KYJ (callakrsos@naver.com)
	 * @작성일 : 2019. 1. 14.
	 * @param encode
	 * @param pb
	 * @return
	 * @throws Exception
	 */
	public static void exe(String homePath, String... arguments) {
		try {
			String encoding = "UTF-8";
			String pythonExe = new File(homePath, "venv\\Scripts\\python.exe").getAbsolutePath();
			// ProcessBuilder 생성

			ArrayList<String> argus = new ArrayList<>(arguments.length + 2);
			argus.add(pythonExe);
			for (String argu : arguments)
				argus.add(argu);
			ProcessBuilder pb = new ProcessBuilder(argus);

			pb.directory(new File(homePath));
			Map<String, String> env = pb.environment();
			env.put("PYTHONIOENCODING", encoding); // 인코딩 설정

			// 프로세스 시작
			Process process = pb.start();

			// 출력 스트림 읽기
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), encoding));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			reader = new BufferedReader(new InputStreamReader(process.getErrorStream(), encoding));
			while ((line = reader.readLine()) != null) {
				System.err.println(line);
			}
			// 종료 코드 확인
			int exitCode = process.waitFor();
			System.out.println("Exit Code: " + exitCode);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
