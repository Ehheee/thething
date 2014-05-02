package thething.one.dbmapping;

import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.Assert;

public class CombinedSqlParameterSource extends AbstractSqlParameterSource {
	
	private MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
	private BeanPropertySqlParameterSource beanPropertySqlParameterSource;

	public CombinedSqlParameterSource(Object object) {
		this.beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(object);
	}
	
	public void addValue(String paramName, Object value) {
		mapSqlParameterSource.addValue(paramName, value);
	}
	public boolean hasValue(String paramName) {
		return beanPropertySqlParameterSource.hasValue(paramName) || mapSqlParameterSource.hasValue(paramName);
	}

	  
	public void registerSqlType(String paramName, int sqlType){
		Assert.notNull(paramName, "Parameter name must not be null");
		this.mapSqlParameterSource.registerSqlType(paramName, sqlType);
		this.beanPropertySqlParameterSource.registerSqlType(paramName, sqlType);
	}
	
	public Object getValue(String paramName) {
		if(beanPropertySqlParameterSource.hasValue(paramName)){
			return beanPropertySqlParameterSource.getValue(paramName);
		}
		if(mapSqlParameterSource.hasValue(paramName)){
			return mapSqlParameterSource.getValue(paramName);
		}
		return null;
	}

	
	public int getSqlType(String paramName) {
		return beanPropertySqlParameterSource.hasValue(paramName) ? beanPropertySqlParameterSource.getSqlType(paramName) : mapSqlParameterSource.getSqlType(paramName);
	}
}
