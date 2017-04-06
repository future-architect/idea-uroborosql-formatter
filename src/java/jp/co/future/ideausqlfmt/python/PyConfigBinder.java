package jp.co.future.ideausqlfmt.python;

import com.intellij.openapi.components.*;
import jp.co.future.ideausqlfmt.settings.*;

import static jp.co.future.ideausqlfmt.settings.UroborosqlFormatterSettings.*;

/**
 * Binding plugin settings to python.
 *
 * @author hoshi
 */
public class PyConfigBinder {
	/**
	 * Plugin settings
	 */
	private final UroborosqlFormatterSettings settings = ServiceManager.getService(UroborosqlFormatterSettings.class);

	/**
	 * Binding plugin settings to python.
	 *
	 * @param engine PyEngine
	 */
	public void bind(PyEngine engine) {
		engine.eval("config = LocalConfig()");
		if (!settings.useUppercase) {
			engine.eval("config.set_uppercase(False)");
		}
		if (settings.commentSyntaxType == CommentSyntaxType.Doma2) {
			engine.eval("config.set_commentsyntax(Doma2CommentSyntax())");
		}
		if (settings.useBackslash) {
			engine.eval("uroborosqlfmt.config.glb.escape_sequence_u005c = True");
		}
	}
}
