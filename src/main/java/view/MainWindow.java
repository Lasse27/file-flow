package view;


import javax.swing.*;


/**
 * The MainWindow class serves as the main graphical user interface (GUI) container for the application. Corresponding .form file: MainWindow.form.
 *
 * @author Lasse27
 * @since 2025-09-02
 */
public class MainWindow extends JFrame
{
	private JPanel basePanel;



	@Override
	public String toString ()
	{
		return """
			MainWindow{
			    basePanel=$basePanel,
			    super=${super.toString()}
			}""";
	}
}
