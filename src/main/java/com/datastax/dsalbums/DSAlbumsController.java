package com.datastax.dsalbums;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.astra.client.Collection;
import com.datastax.astra.client.DataAPIClient;
import com.datastax.astra.client.Database;
import com.datastax.astra.client.model.Document;
import com.datastax.astra.client.model.FindIterable;

import static com.datastax.astra.client.model.Filters.eq;

@RequestMapping("/dsalbums")
@RestController
public class DSAlbumsController {

	static final String TOKEN = System.getenv("DB_APPLICATION_TOKEN");
	static final String API_ENDPOINT = System.getenv("DB_API_ENDPOINT");
	
	private Collection<Document> collection;
	
	public DSAlbumsController() {
		
		DataAPIClient client = new DataAPIClient(TOKEN);
		Database dbAPI = client.getDatabase(API_ENDPOINT);
		collection = dbAPI.getCollection("albums");
	}
	
	public Optional<Document> getOneAlbum(String albumId) {
		return collection.findById(albumId);
	}
	
	public FindIterable<Document> getAlbumsByBand(String band) {
		return collection.find(eq("band", band));
	}
	
	public FindIterable<Document> getAllAlbums() {
		return collection.find();
	}
	
	@GetMapping("/album/{id}")
	public ResponseEntity<Optional<Document>> getAlbumExternal(@PathVariable(value="id") String albumId) {
		
		Optional<Document> album = getOneAlbum(albumId);

		if (album.isPresent()) {
			return ResponseEntity.ok(album);
		}
		
		return ResponseEntity.ok(Optional.ofNullable(null));
	}
	
	@GetMapping("/albums/band/{band}")
	public ResponseEntity<FindIterable<Document>> getAlbumsByBandExternal(@PathVariable(value="band") String band) {
		return ResponseEntity.ok(getAlbumsByBand(band));
	}
	
	
	@GetMapping("/albums")
	public ResponseEntity<FindIterable<Document>> getAlbumsExternal() {
		return ResponseEntity.ok(getAllAlbums());
	}
}
