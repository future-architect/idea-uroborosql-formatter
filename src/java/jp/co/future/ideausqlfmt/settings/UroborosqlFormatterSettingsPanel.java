package jp.co.future.ideausqlfmt.settings;

import com.intellij.openapi.components.*;
import com.intellij.openapi.options.*;
import org.jetbrains.annotations.*;

import javax.swing.*;

import static jp.co.future.ideausqlfmt.settings.UroborosqlFormatterSettings.*;

/**
 * Provides plugin settings panel.
 *
 * @author hoshi
 */
public class UroborosqlFormatterSettingsPanel implements Configurable {
	private JPanel rootPanel;
	private JCheckBox useUppercase;
	private JCheckBox useBackslash;
	private JRadioButton useUroboroSQL;
	private JRadioButton useDoma2;
	private final UroborosqlFormatterSettings config = ServiceManager.getService(UroborosqlFormatterSettings.class);

	@Nls
	@Override
	public String getDisplayName() {
		return "uroboroSQL Formatter";
	}

	@Nullable
	@Override
	public String getHelpTopic() {
		return "preferences.topic";
	}

	@Nullable
	@Override
	public JComponent createComponent() {
		bind();
		return rootPanel;
	}

	@Override
	public boolean isModified() {
		return !(useUppercase.isSelected() == config.useUppercase &&
			useBackslash.isSelected() == config.useBackslash &&
			useUroboroSQL.isSelected() && config.commentSyntaxType == CommentSyntaxType.Uroborosql);
	}

	@Override
	public void apply() throws ConfigurationException {
		if (!isModified()) {
			return;
		}
		config.useUppercase = useUppercase.isSelected();
		config.useBackslash = useBackslash.isSelected();
		config.commentSyntaxType = useUroboroSQL.isSelected() ?
			CommentSyntaxType.Uroborosql : CommentSyntaxType.Doma2;
	}

	private void bind() {
		useUppercase.setSelected(config.useUppercase);
		useBackslash.setSelected(config.useBackslash);
		if (config.commentSyntaxType == CommentSyntaxType.Uroborosql) {
			useUroboroSQL.setSelected(true);
		} else {
			useDoma2.setSelected(true);
		}
	}
}
