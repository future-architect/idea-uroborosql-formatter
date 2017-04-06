package jp.co.future.ideausqlfmt.settings;

import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.*;
import org.jetbrains.annotations.*;

/**
 * Provides plugin settings.
 *
 * @author hoshi
 */
@State(
	name = "UroborosqlFormatterSettings",
	storages = @Storage(id = "ideausqlfmt", file = "$APP_CONFIG$/ideausqlfmt.xml")
)
public class UroborosqlFormatterSettings implements PersistentStateComponent<UroborosqlFormatterSettings> {
	/**
	 * Provides SQL comment syntax type.
	 */
	public enum CommentSyntaxType {
		/**
		 * uroboroSQL, S2Dao, S2JDBC, DBFlute.
		 */
		Uroborosql,
		/**
		 * DOMA2
		 */
		Doma2
	}

	/**
	 * Convert reserved words and identifiers to upper case
	 */
	public boolean useUppercase = true;
	/**
	 * Using backslash escape sequences
	 */
	public boolean useBackslash = false;
	/**
	 * Comment syntax type
	 */
	public CommentSyntaxType commentSyntaxType = CommentSyntaxType.Uroborosql;

	@Nullable
	@Override
	public UroborosqlFormatterSettings getState() {
		return this;
	}

	@Override
	public void loadState(UroborosqlFormatterSettings state) {
		XmlSerializerUtil.copyBean(state, this);
	}
}
