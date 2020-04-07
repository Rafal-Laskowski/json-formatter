package io.github.laskowski.gui;

import io.github.laskowski.JSONFormatter;
import io.github.laskowski.JSONFormatterException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

	@FXML
	private TextArea leftArea;

	@FXML
	private ListView<String> rightArea;

	@FXML
	private Label lengthLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	public void format() {
		try {
			String formattedJSON = JSONFormatter.format(leftArea.getText());
			formatJSONString(formattedJSON);
		} catch (JSONFormatterException ignore) {
		}
	}

	@FXML
	public void loadFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");

		Stage stage = getStage(event);

		File selectedFile = fileChooser.showOpenDialog(stage);

		if (selectedFile != null) {
			if (selectedFile.isFile()) {
				String rawJSON = readRawJSONFile(selectedFile);
				leftArea.setText(rawJSON);

				String formattedJSON = JSONFormatter.format(selectedFile);
				formatJSONString(formattedJSON);
			}
		}
	}

	private void formatJSONString(String jsonString) {
		ObservableList<String> items = FXCollections.observableArrayList();
		items.addAll(jsonString.split("\n"));

		rightArea.setItems(items);
		lengthLabel.setText(String.valueOf(jsonString.length()));
	}

	private String readRawJSONFile(File file) {
		StringBuilder builder = new StringBuilder();

		try (FileReader fr = new FileReader(file)) {
			int i;
			while ((i = fr.read()) != -1) {
				builder.append((char) i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return builder.toString();
	}

	@FXML
	public void copyToClipboard() {
		String joinedString = getJoinedString();

		StringSelection stringSelection = new StringSelection(joinedString);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}

	@FXML
	public void saveFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save .json file");

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
		fileChooser.getExtensionFilters().add(extFilter);

		File file = fileChooser.showSaveDialog(getStage(event));
		if (file != null) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				writer.write(getJoinedString());
				writer.flush();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	private String getJoinedString() {
		ObservableList<String> items = rightArea.getItems();

		return String.join("\n", items);
	}

	private Stage getStage(ActionEvent event) {
		Node source = (Node) event.getSource();
		return (Stage) source.getScene().getWindow();
	}
}
