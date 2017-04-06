package jp.co.future.ideausqlfmt.python;

import jp.co.future.ideausqlfmt.*;
import org.python.core.*;
import org.python.jsr223.*;
import org.python.util.*;

import javax.script.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

/**
 * Adapt to python engine.
 *
 * @author hoshi
 */
public class PyEngine implements AutoCloseable {
	private final ScriptEngine engine;

	public PyEngine() {
		String pythonPath = getPythonPath();
		Properties props = new Properties();
		props.put("python.path", pythonPath);
		props.put("python.console.encoding", "UTF-8");
		props.put("python.security.respectJavaAccessibility", "false");

		PythonInterpreter.initialize(System.getProperties(), props, new String[0]);
		engine = new ScriptEngineManager(PyEngine.class.getClassLoader())
			.getEngineByName("python");

		PyEngine.eval(engine,
			"import uroborosqlfmt",
			"from uroborosqlfmt import api",
			"from uroborosqlfmt.config import LocalConfig",
			"from uroborosqlfmt.commentsyntax import Doma2CommentSyntax");
	}

	public <T> T eval(String script) {
		return eval(engine, script);
	}

	void eval(String... scripts) {
		eval(engine, scripts);
	}

	public void put(String key, String value) {
		engine.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) engine.get(key);
	}

	static void eval(ScriptEngine engine, String... scripts) {
		for (String script : scripts) {
			eval(engine, script);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T eval(ScriptEngine engine, String script) {
		try {
			return (T) engine.eval(script);
		} catch (ScriptException e) {
			throw adjustException(e, script);
		}
	}

	private static RuntimeException adjustException(ScriptException scriptException, String... scripts) {
		Throwable cause = scriptException.getCause();
		if (!(cause instanceof PyException)) {
			if (scripts.length == 0) {
				return new IllegalStateException(scriptException);
			} else {
				return new IllegalStateException(
					scriptException.getMessage() + "\nscript:" + String.join("\n", scripts), scriptException);
			}
		}

		PyException e = (PyException) cause;

		PyObject o = e.value.getDict();
		if (!(o instanceof PyStringMap)) {
			return e;
		}
		PyStringMap m = (PyStringMap) o;

		String message;
		if (e.value instanceof PyBaseException) {
			message = String.valueOf(((PyBaseException) e.value).getMessage());
		} else {
			message = scriptException.getMessage();
		}

		PyObject tlist = m.getMap().get("tlist");
		PyObject trace = m.getMap().get("trace");

		return new UroborosqlFormatterException(message, String.valueOf(tlist), String.valueOf(trace), e);
	}

	@Override
	public void close() {
		if (engine instanceof PyScriptEngine) {
			((PyScriptEngine) engine).close();
		}
	}

	private static String getPythonPath() {
		try {
			ClassLoader classLoader = PyEngine.class.getClassLoader();
			URL root = classLoader.getResource("python/uroborosqlfmt");

			if ("file".equals(root.getProtocol())) {
				Path path = Paths.get(root.toURI());
				Path parent = path.getParent();
				if (parent != null) {
					return parent.toString();
				}
			} else if ("jar".equals(root.getProtocol())) {
				URI jarFileUri = new URI(root.getPath().replaceFirst("!/.*$", ""));
				Path path = Paths.get(jarFileUri);
				return path.resolve("python").toString();
			}
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
		throw new IllegalStateException("Python directory not found.");
	}
}
