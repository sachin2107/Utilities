package com.mongo.component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.UpdateManyModel;

@Component
public class MongoComponentTransactionalOperations {

	public String readFile(String fileName) {
		Charset charset = StandardCharsets.UTF_8;

		String result = fileName;

		try {
			File file = new File(fileName);
			if (file.exists()) {
				result = new String(Files.readAllBytes(file.toPath()), charset);
			} else {
				URL url = this.getClass().getResource("/" + fileName);
				if (null != url && null != url.getFile() && new File(url.getFile()).exists()) {
					File fileResource = new File(url.getFile());
					if (fileResource.exists()) {
						result = new String(Files.readAllBytes(fileResource.toPath()), charset);
					} else {
						System.out.println("File does not exist at given location" + fileName);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception in reading file...");
		}
		return result;
	}

	public void processCommand(MongoDatabase mongoDatabase, File fileDir, Set<String> fileNames) throws IOException {
		if (fileNames.isEmpty()) {
			System.out.println("No file found for process over collections");
		}
		for (String fileName : fileNames) {
			if (fileName.contains("createCollection="+fileName)) {
				System.out.println("Creating collection...");
				processCreateCollection(mongoDatabase, new File(fileDir, fileName));
			} else if (fileName.contains("runCommand")) {
				processRunCommand(mongoDatabase, new File(fileDir, fileName));
			}
		}
	}

	private void processRunCommand(MongoDatabase mongoDatabase, File commandFile) throws IOException {
		if (commandFile.exists()) {
			String[] commands = readFile(commandFile.getCanonicalPath()).split(System.lineSeparator());
			for (String command : commands) {
				if (command.startsWith("#")) {
					continue;
				}
				Document runCommandResult = mongoDatabase.runCommand(Document.parse(command));
			}
		} else {
			System.out.println("File doesn't exist: " + commandFile.getAbsolutePath());
		}
	}

	public void processCreateCollection(MongoDatabase mongoDatabase, File createCollectionFile) throws IOException {
		ArrayList<String> listOfCollection = mongoDatabase.listCollectionNames().into(new ArrayList<String>());
		if (createCollectionFile.exists()) {
			String[] collections = readFile(createCollectionFile.getCanonicalPath()).split(System.lineSeparator());
			for (String collection : collections) {
				System.out.println("Collection name="+collection);
				if (collection.startsWith("#")) {
					continue;
				}
				String[] collectionParameters = collection.split("####RRRR####");
				if (listOfCollection.contains(collectionParameters[0].trim())) {
					continue;
				}
				String collectionParam = collectionParameters[0].replace("(\\r|\\n)", "");
				if (collectionParameters.length == 1) {
					System.out.println("collectionParameters="+collectionParameters[0]);
					mongoDatabase.createCollection(collectionParam.trim());
					System.out.println("Collection created...");
				} else if (collectionParameters.length == 2) {
					CreateCollectionOptions collectionOptions = getCollectionOption(collectionParameters[1].trim());
					if (null != collectionOptions) {
						mongoDatabase.createCollection(collectionParameters[0].trim(), collectionOptions);
					} else {
						mongoDatabase.createCollection(collectionParameters[0].trim());
					}
				}
			}
		} else {
			System.out.println("File doesn't exist : " + createCollectionFile);
		}
	}

	private CreateCollectionOptions getCollectionOption(String collectionParams) {
		CreateCollectionOptions collectionOptions = null;
		String[] paramList = collectionParams.replace("(\\r|\\n)", "").split("@");
		Boolean isCap = paramList.length > 2 && paramList[0].equalsIgnoreCase("true");
		if (isCap) {
			collectionOptions = new CreateCollectionOptions();
			collectionOptions.capped(true);
			long size = paramList.length < 2 && paramList[1].isEmpty() ? 0L : Long.parseLong(paramList[1].trim());
			if (size != 0) {
				collectionOptions.sizeInBytes(size);
			}
			long documentSize = paramList.length < 3 && paramList[2].isEmpty() ? 0L
					: Long.parseLong(paramList[2].trim());
			if (documentSize != 0) {
				collectionOptions.maxDocuments(documentSize);
			}
		}
		return collectionOptions;
	}
	
	public void processCollections(MongoDatabase mongoDatabase, File fileDir, Set<String> collections ) throws IOException{
		if(collections.isEmpty()) {
			return;
		}
		for(String collectionName : collections) {
			MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
			File fileInsert = new File(fileDir, collectionName + "_" + "insert" + ".txt");
			File fileDelete = new File(fileDir, collectionName + "_" + "delete" + ".txt");
			File fileUpdate = new File(fileDir, collectionName + "_" + "update" + ".txt");
			File fileIndex = new File(fileDir, collectionName + "_" + "index" + ".txt");
			File fileDrop = new File(fileDir, collectionName + "_" + "drop" + ".txt");
			File fileDropIndex = new File(fileDir, collectionName + "_" + "dropIndex" + ".txt");
			
			process(collection, fileInsert, fileDelete, fileUpdate, fileIndex, fileDrop, fileDropIndex);
			
			FindIterable<Document> documents = collection.find();
			for(Document d: documents) {
				System.out.println("Updated documents: "+d);
			}
		}
	}

	private void process(MongoCollection<Document> collection, File fileInsert, File fileDelete, File fileUpdate,
			File fileIndex, File fileDrop, File fileDropIndex) throws IOException {
		// TODO Auto-generated method stub
		if(fileDelete.exists()) {
			deleteData(collection, fileDelete);
		} else {
			System.out.println("Delete File not available : "+fileDelete.getCanonicalPath());
		}
		
		if(fileInsert.exists()) {
			insertData(collection, fileInsert);
		} else {
			System.out.println("Insert File not available : "+fileInsert.getCanonicalPath());
		}
		
		if(fileUpdate.exists()) {
			updateData(collection, fileUpdate);
		} else {
			System.out.println("Update File not available : "+fileUpdate.getCanonicalPath());
		}
		
		if(fileIndex.exists()) {
			indexField(collection, fileIndex);
		} else {
			System.out.println("Index File not available : "+fileIndex.getCanonicalPath());
		}
		
		if(fileDropIndex.exists()) {
			dropIndexField(collection, fileIndex);
		} else {
			System.out.println("Drop index File not available : "+fileDrop.getCanonicalPath());
		}
		
		if(fileDrop.exists()) {
			collection.drop();
		} else {
			System.out.println("Drop File not available : "+fileDropIndex.getCanonicalPath());
		}
	}

	private void dropIndexField(MongoCollection<Document> collection, File fileIndexDrop) throws IOException {
		if( 0 == fileIndexDrop.length()) {
			System.out.println("Dropping all Index on collection : "+collection);
			collection.dropIndexes();
			return;
		}
		String[] jsonArray = readFile(fileIndexDrop.getCanonicalPath()).split(System.lineSeparator());
		for(String indexName : jsonArray) {
			if(indexName.startsWith("#")) {
				continue;
			}
			System.out.println("Dropping Index on collection : "+collection + " ,column: "+indexName);
			collection.dropIndex(indexName);
		}
	}

	private void indexField(MongoCollection<Document> collection, File fileIndex) throws IOException {
		String[] jsonArray = readFile(fileIndex.getCanonicalPath()).split(System.lineSeparator());
		for(String json : jsonArray) {
			if(json.startsWith("#")) {
				continue;
			}
			String[] split = json.replace("(\\r|\\n)", "").split("####RRRR####");
			IndexOptions indexOptions = new IndexOptions();
			if(3 <= split.length && !split[2].trim().isEmpty()) {
				indexOptions.unique(true);
			}
			if(4 <= split.length && !split[3].trim().isEmpty()) {
				indexOptions.expireAfter(Long.parseLong(split[3].trim()), TimeUnit.SECONDS);
			}
			if( 2 <= split.length && split[1].trim().equalsIgnoreCase("ascending")) {
				collection.createIndex(Indexes.ascending(split[0].split(",")),indexOptions);
				System.out.println("Index added to : "+collection.getNamespace().getCollectionName()+" collumn "+split[0] + " ascending unique : "+indexOptions);
			}else {
				collection.createIndex(Indexes.descending(split[0].split(",")),indexOptions);
				System.out.println("Index added to : "+collection.getNamespace().getCollectionName()+" collumn "+split[0] + " descending unique : "+indexOptions);
			}
		}
		
	}

	private void updateData(MongoCollection<Document> collection, File fileUpdate) throws IOException {
		String[] jsonArray = readFile(fileUpdate.getCanonicalPath()).split(System.lineSeparator());
		List<UpdateManyModel<? extends Document>> docs = new ArrayList();
		for(String json : jsonArray) {
			if(json.startsWith("#")) {
				continue;
			}
			String[] split = json.split("####RRRR####");
			Document filter = Document.parse(split[0]);
			Document update = Document.parse(split[1]);
			UpdateManyModel<? extends Document> model = new UpdateManyModel(filter, new Document("$set",update));
			docs.add(model);
		}
		try {
			System.out.println("Update docs : "+docs);
			collection.bulkWrite(docs);
		}catch(MongoBulkWriteException e) {
			System.out.println("Exception occurred writing data in bulk : "+e);
		}
	}

	private void insertData(MongoCollection<Document> collection, File fileInsert) throws IOException {
		String[] jsonArray = readFile(fileInsert.getCanonicalPath()).split(System.lineSeparator());
		List<InsertOneModel<? extends Document>> docs = new ArrayList();
		for(String json : jsonArray) {
			if(json.startsWith("#")) {
				continue;
			}
			Document document = Document.parse(json);
			InsertOneModel<? extends Document> model = new InsertOneModel<>(document);
			docs.add(model);
		}
		try {
			collection.bulkWrite(docs);
		}catch(MongoBulkWriteException e) {
			System.out.println("Exception occurred writing data in bulk : "+e);
		}
	}

	private void deleteData(MongoCollection<Document> collection, File fileDelete) throws IOException {
		// TODO Auto-generated method stub
		String[] jsonArray = readFile(fileDelete.getCanonicalPath()).split(System.lineSeparator());
		List<DeleteOneModel<? extends Document>> docs = new ArrayList();
		for(String json : jsonArray) {
			if(json.startsWith("#")) {
				continue;
			}
			Document document = Document.parse(json);
			DeleteOneModel<? extends Document> model = new DeleteOneModel<>(document);
			docs.add(model);
		}
		collection.bulkWrite(docs);
	}
	
}
