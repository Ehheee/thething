package thething.one.dbmapping.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import thething.one.dataobjects.AbstractThing;
public class AbstractThingExtractor implements ResultSetExtractor<List<AbstractThing>>{

	public List<AbstractThing> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		AbstractThingMapper mapper = new AbstractThingMapper();
		
		return mapper.mapRows(rs);
	}

}
