package transformer.test.data;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Sample_ServletProperty extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		String[] servletProperties = new String[]{
			"javax.servlet.request.cipher_suite",
			"javax.servlet.request.key_size",
			"javax.servlet.request.ssl_session_id",
			"javax.servlet.request.X509Certificate",
			"javax.servlet.context.tempdir",
			"javax.servlet.context.orderedLibs",
			"javax.servlet.include.request_uri",
			"javax.servlet.include.context_path",
			"javax.servlet.include.servlet_path",
			"javax.servlet.include.mapping",
			"javax.servlet.include.path_info",
			"javax.servlet.include.query_string",
			"javax.servlet.forward.mapping",
			"javax.servlet.forward.request_uri",
			"javax.servlet.forward.context_path",
			"javax.servlet.forward.servlet_path",
			"javax.servlet.forward.path_info",
			"javax.servlet.forward.query_string",
			"javax.servlet.async.mapping",
			"javax.servlet.async.request_uri",
			"javax.servlet.async.context_path",
			"javax.servlet.async.servlet_path",
			"javax.servlet.async.path_info",
			"javax.servlet.async.query_string",
			"javax.servlet.error.status_code",
			"javax.servlet.error.exception_type",
			"javax.servlet.error.message",
			"javax.servlet.error.exception",
			"javax.servlet.error.request_uri",
			"javax.servlet.error.servlet_name"
		};
	}
}
