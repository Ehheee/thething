package thething.one.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import thething.one.dataobjects.Settings;
import thething.one.dbmapping.SettingsDao;

public class SettingsService {

	
	Map<String, Settings> tagSettings;
	
	public Settings getSettings(String tag){
		return this.tagSettings.get(tag);
	}
	
	public void insertSettings(Settings settings){
		this.tagSettings.put(settings.getTag(), settings);
		//TODO
	}
	
	public void deleteSettings(Integer id){
		this.settingsDao.deleteSettings(id);
	}
	
	public void loadSettings(){
		
		for(Settings s: this.settingsDao.getSettings()){
			tagSettings.put(s.getTag(), s);
		}
	}
	@Autowired
	private SettingsDao settingsDao;

	public void setSettingsDao(SettingsDao settingsDao) {
		this.settingsDao = settingsDao;
	}
	
}
