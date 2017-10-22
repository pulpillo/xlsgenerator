package xlsgenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@Path("/xls")
public class XlsRestService {

	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public Response generateExcel(@FormParam("json") String json, @FormParam("excelName") String excelName) {
		
		ResponseBuilder response = null;
		
		byte[] decodedBytes = null;
		String jsonDecoded = null;
		try  (final InputStream inputFile =  XlsRestService.class.getResourceAsStream("/Plantilla.xlsx");
			  final Workbook workbook = new XSSFWorkbook(inputFile);) {
			
            Sheet sheet = workbook.getSheet("Base");
			
			decodedBytes = Base64.decode(json.getBytes("UTF-8"));
			jsonDecoded = new String(decodedBytes, "UTF-8");
			jsonDecoded = StringEscapeUtils.unescapeJava(jsonDecoded);
			
			JSONObject jsonObj = (JSONObject) JSONValue.parse(jsonDecoded);
			Set<String> keys = jsonObj.keySet();
			for(String key : keys){
				String value = (String)jsonObj.get(key);
				CellReference cellReference = new CellReference(key);
				Row row = sheet.getRow(cellReference.getRow());
				Cell cell = row.getCell(cellReference.getCol()); 

				if (cell!=null) {
					cell.setCellValue(value);
				}
			}
			//Recalculate Formulas
			HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
			workbook.setForceFormulaRecalculation(true);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			 workbook.write(baos);
			 response = Response.ok(baos.toByteArray());
			 response.header("Content-disposition", "attachment; filename=" + excelName);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		}
		
		return response.build();

	}

}
