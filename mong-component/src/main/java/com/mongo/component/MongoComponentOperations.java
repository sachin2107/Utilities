package com.mongo.component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.UpdateOneModel;

@Component("mongoDBBulkOperation")
public class MongoComponentOperations {

	@Autowired
	MongoComponentTransactionalOperations mongoTransOperations;

	@Autowired
	MongoTemplate mongoTemplate;

	public void updateMultipleDocs(String collectionName, String dataFile) {
		System.out.println("In Updatemultiple docs...");
		MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
		String[] jsonArray = mongoTransOperations.readFile(dataFile).split(System.lineSeparator());
		System.out.println("Collection = "+collection);
		
		List docs = new ArrayList<>();
		for (String jsonString : jsonArray) {
			if (jsonString.startsWith("#")) {
				continue;
			}
			String[] split = jsonString.split("####RRRR####");
			if (2 == split.length && "insert".equalsIgnoreCase(split[0].trim())) {
				Document document = Document.parse(split[1]);
				InsertOneModel<? extends Document> model = new InsertOneModel<>(document);
				docs.add(model);
			} else if (2 == split.length && "delete".equalsIgnoreCase(split[0].trim())) {
				Document document = Document.parse(split[1]);
				DeleteOneModel<? extends Document> model = new DeleteOneModel<>(document);
				docs.add(model);
			} else if (2 == split.length && "dropIndex".equalsIgnoreCase(split[0].trim())) {
				collection.dropIndex(split[1]);
			} else if (2 == split.length && "drop".equalsIgnoreCase(split[0].trim())) {
				collection.drop();
			} else if (3 == split.length && "update".equalsIgnoreCase(split[0].trim())) {
				Document documentSearch = Document.parse(split[1]);
				Document documentUpdate = Document.parse(split[2]);
				UpdateOneModel<? extends Document> model = new UpdateOneModel<>(documentSearch,
						new Document("$set", documentUpdate));
				docs.add(model);
			} else if (3 < split.length && "index".equalsIgnoreCase(split[0].trim())) {
				IndexOptions indexOptions = new IndexOptions();
				if (4 == split.length && !split[3].isEmpty()) {
					indexOptions.unique(true);
				}
				if (split[2].toLowerCase().contains("ascending")) {
					collection.createIndex(Indexes.ascending(split[1]), indexOptions);
				} else {
					collection.createIndex(Indexes.descending(split[1]), indexOptions);
				}

			} else {
				System.out.println("Unable to determine operation" + split[0] + " length=" + split.length);
			}

		}

		if (docs.isEmpty()) {
			System.out.println("Bulk operation not required...");
		} else {
			collection.bulkWrite(docs);
		}

		FindIterable<Document> documents = collection.find();
		for (Document d : documents) {
			System.out.println("Updated Documents: " + d);
		}
	}

	public void updateMultipleDocs(String dataFileLocation) throws IOException {
		File fileDir = new File(dataFileLocation);
		Set<String> collections = new HashSet<>();
		Set<String> runCommandCollections = new HashSet<>();

		if (fileDir.exists() && fileDir.isDirectory()) {
			for (String fileName : Objects.requireNonNull(fileDir.list())) {
				System.out.println("Checking file name=" + fileName);
				int extPosition = fileName.lastIndexOf('_');
				extPosition = -1 == extPosition ? fileName.length() - 1 : extPosition;
				if (fileName.contains("insert") || fileName.contains("delete") || fileName.contains("update")
						|| fileName.contains("index") || fileName.contains("drop") || fileName.contains("dropIndex")) {
					collections.add(fileName.subSequence(0, extPosition).toString().trim());
				} else if (fileName.contains("createCollection") || fileName.contains("runCommand")) {
					runCommandCollections.add(fileName);
				} else {
					System.out.println("FileName="+fileName);
					System.out.println("File name is not associated with any operation");
				}
			}
			System.out.println("Mongotemplate db....");
			System.out.println("mongotemplate getdb = "+mongoTemplate.getDb());
			System.out.println("runcommandcollections="+runCommandCollections);
			mongoTransOperations.processCommand(mongoTemplate.getDb(), fileDir, runCommandCollections);
			mongoTransOperations.processCollections(mongoTemplate.getDb(), fileDir, collections);

		}

	}
}
