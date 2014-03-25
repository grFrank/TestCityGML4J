import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.citygml4j.CityGMLContext;
import org.citygml4j.builder.CityGMLBuilder;
import org.citygml4j.model.citygml.CityGML;
import org.citygml4j.model.citygml.CityGMLClass;
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.model.citygml.core.CityModel;
import org.citygml4j.model.citygml.core.CityObjectMember;
import org.citygml4j.xml.io.CityGMLInputFactory;
import org.citygml4j.xml.io.reader.CityGMLReader;

public class SimpleReader {

    public static void main(String[] args) throws Exception {
	SimpleDateFormat df = new SimpleDateFormat("[HH:mm:ss] ");
	CityGMLContext ctx = new CityGMLContext();
	CityGMLBuilder builder = ctx.createCityGMLBuilder();

	System.out.println(df.format(new Date()) + "reading CityGML file into main memory");

	CityGMLInputFactory in = builder.createCityGMLInputFactory();

	CityGMLReader reader = in.createCityGMLReader(new File("../testfiles/Gruenbuehl_LOD2.gml"));

	while (reader.hasNext()) {
	    CityGML citygml = reader.nextFeature();

	    System.out.println("Found " + citygml.getCityGMLClass() + " " + citygml.getCityGMLModule().getVersion());

	    if (citygml.getCityGMLClass() == CityGMLClass.CITY_MODEL) {
		CityModel cityModel = (CityModel) citygml;

		int count = 0;
		for (CityObjectMember cityObjectMember : cityModel.getCityObjectMember()) {
		    AbstractCityObject cityObject = cityObjectMember.getCityObject();
		    
		    System.out.println(cityObject.getBoundedBy().getEnvelope().getSrsName());
		    System.out.println("-----------------------------------------");
		    if (cityObject.getCityGMLClass() == CityGMLClass.BUILDING) {
			count++;
		    }
		}

		System.out.println("The city model contains " + count + " building features");
	    }
	}

	reader.close();
	System.out.println(df.format(new Date()) + "citygml4j application successfully finished");
    }

}
