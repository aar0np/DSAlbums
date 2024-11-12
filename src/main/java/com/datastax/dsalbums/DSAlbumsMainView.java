package com.datastax.dsalbums;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.datastax.astra.client.model.Document;
import com.datastax.astra.client.model.FindIterable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@Route("")
public class DSAlbumsMainView extends VerticalLayout {

	private static final long serialVersionUID = 7790221227525542119L;
	
	private Button bandSearchButton;
	private H3 title = new H3();
	private Image albumImage = new Image();
	private Paragraph bandyear = new Paragraph();
	private TextField bandSearchField;
	
	private DSAlbumsController controller;
	
	private ListBox<String> albumIndex = new ListBox<>();
	private Map<String,String> albums;
	
	public DSAlbumsMainView() {
		
		controller = new DSAlbumsController();

		add(new H1("AaronsAlbums"));
		
		albums = new HashMap<String,String>();
		
		loadAlbums();
		
		albumIndex.setHeight("500px");
		albumIndex.getStyle().set("font", "10px");
		albumIndex.getStyle().set("border", "2px solid Black");
		albumIndex.addValueChangeListener(click -> {
			if (albumIndex.getValue() != null) {
				String id = albums.get(albumIndex.getValue());
				viewAlbum(id);
			}
		});
		
		add(buildSearchBar());
		
		HorizontalLayout controlDataLayout = new HorizontalLayout();
		controlDataLayout.add(albumIndex, albumComponents());
		
		add(controlDataLayout);
	}

	private void loadAlbums() {
		
		FindIterable<Document> docs = controller.getAllAlbums();
		
		for (Document album : docs) {
			albums.put(album.getString("title"), album.getString("_id"));
		}
		
		albumIndex.setItems(albums.keySet());
	}

	private Component albumComponents() {
		VerticalLayout returnVal = new VerticalLayout();
		returnVal.setSpacing(false);
	
		bandyear.getStyle().set("white-space", "pre-line");

		albumImage.setHeight("300px");
		albumImage.getStyle().set("border", "1px solid Black");
		
		returnVal.add(title, bandyear, albumImage);
		
		return returnVal;
	}

	private void viewAlbum(String id) {

		Optional<Document> album = controller.getOneAlbum(id);
		
		if (album.isPresent()) {
			String strTitle = album.get().getString("title");
			title.setText(strTitle);
			bandyear.setText(album.get().getString("band") + "\n" + album.get().getString("year"));
			
			// album images will be defined as the lowercase title with all spaces removed
			String filename = strTitle.toLowerCase().replace(" ","") + ".jpeg";
			try {
				albumImage.setSrc(new StreamResource(strTitle, () -> getClass()
						.getResourceAsStream("/images/" + filename)));
			} catch (Exception ex) {
				albumImage.setSrc(new StreamResource(strTitle, () -> getClass()
						.getResourceAsStream("/images/noImage.png")));
			}
		}
	}
	
	private Component buildSearchBar() {
		HorizontalLayout layout = new HorizontalLayout();
		
		bandSearchButton = new Button("Search");
		bandSearchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		bandSearchField = new TextField();
		Icon searchIcon = new Icon(VaadinIcon.SEARCH);
		bandSearchField.setPrefixComponent(searchIcon);
		bandSearchField.setHelperText("search by band name");
		
		bandSearchButton.addClickListener(click -> {
			albums.clear();
			if (bandSearchField.getValue().isEmpty() || bandSearchField.getValue().isBlank()) {
				loadAlbums();
			} else {
				FindIterable<Document> docs = 
						controller.getAlbumsByBand(bandSearchField.getValue());
				
				for (Document album : docs) {
					albums.put(album.getString("title"), album.getString("_id"));
				}
				
				albumIndex.setItems(albums.keySet());
			}
		});
		
		layout.add(bandSearchField,bandSearchButton);
		
		return layout;
	}
}
