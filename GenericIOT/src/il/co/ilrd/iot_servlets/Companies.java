package il.co.ilrd.iot_servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class Companies
 */
@WebServlet("/companies")
public class Companies extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CompaniesCrud compCrud;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Companies() {
        try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        compCrud = new CompaniesCrud("jdbc:mysql://localhost:3306/GenericIOT", "root", "ct,h kvmkhj");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		CompanyDetails details = validateToken(request);
		if (details == null) {
			out.println(Status.INVALID_TOKEN.getDescription());
			return;
		}

		response.setStatus(Status.OK.getCode());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(CompanyDetails.toJson(details));
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String jsonString;
		JsonObject json;
		
		try {
			// FIXME close the reader
			BufferedReader reader = request.getReader();
			jsonString = reader.lines().collect(Collectors.joining());
			json =  JsonParser.parseString(jsonString).getAsJsonObject();
		} catch (Exception e) {
			throw new IOException("Error parsing JSON request string");
		}
		CompanyDetails details = CompanyDetails.getCompanyDetails(json);
		if (details == null) {
			out.println(Status.INVALID_PARAMS.getDescription());
		}
		response.setStatus(compCrud.create(details).getCode());
		out.println(TokenManager.generateToken(json.get("email").getAsString()));
		out.flush();
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String token = request.getHeader("token");
		if (token == null) {
			out.println(Status.INVALID_TOKEN.getDescription());
			return;
		}
		String email = TokenManager.getEmail(token);
		out.println(compCrud.delete(email).getDescription());
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getHeader("token");
		PrintWriter out = response.getWriter();
		if (token == null) {
			out.println(Status.INVALID_TOKEN.getDescription());
			return;
		}
		response.setContentType("application/json");
		String jsonString;
		JsonObject json;
		
		try {
			// FIXME close the reader
			BufferedReader reader = request.getReader();
			jsonString = reader.lines().collect(Collectors.joining());
			json =  JsonParser.parseString(jsonString).getAsJsonObject();
		} catch (Exception e) {
			// crash and burn
			throw new IOException("Error parsing JSON request string");
		}
		 
		CompanyDetails details = CompanyDetails.getCompanyDetails(json);
		
		if (details == null) {
			out.println(Status.INVALID_PARAMS.getDescription());
		}
		out.println(compCrud.update(TokenManager.getEmail(token), details));
		out.close();
	}
	
	private CompanyDetails validateToken(HttpServletRequest request) throws ServletException, IOException{
		CompanyDetails details = null;
		String token = request.getHeader("token");
		if (token == null) {
			return details;
		}
		String email = TokenManager.getEmail(token);
		details = compCrud.read(email);
		
		return details;
	}
}
